package com.java11.design.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 上下文类 - 维护当前订单状态，将操作委托给具体状态对象
 */
public class OrderContext {

    private static final Logger log = LoggerFactory.getLogger(OrderContext.class);
    
    private final String orderId;
    private OrderState currentState;

    public OrderContext(String orderId) {
        this.orderId = orderId;
        this.currentState = new DraftState();
    }

    /**
     * 状态转换（带验证）
     */
    void transitionTo(Class<? extends OrderState> targetStateClass) {
        if (!OrderStateTransition.isValid(currentState.getClass(), targetStateClass)) {
            throw new IllegalStateException(
                String.format("非法状态转换: %s -> %s", 
                    currentState.getClass().getSimpleName(), 
                    targetStateClass.getSimpleName())
            );
        }
        try {
            this.currentState = targetStateClass.getDeclaredConstructor().newInstance();
            log.info("  -> 订单 [{}] 当前状态: {}", orderId, currentState.getStateName());
        } catch (Exception e) {
            log.error("状态转换失败", e);
            throw new RuntimeException("状态转换失败", e);
        }
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStateName() {
        return currentState.getStateName();
    }

    public void submit() {
        log.info("订单 [{}] 执行: 提交", orderId);
        currentState.submit(this);
    }

    public void approve() {
        log.info("订单 [{}] 执行: 审批通过", orderId);
        currentState.approve(this);
    }

    public void reject() {
        log.info("订单 [{}] 执行: 审批驳回", orderId);
        currentState.reject(this);
    }

    public void cancel() {
        log.info("订单 [{}] 执行: 取消", orderId);
        currentState.cancel(this);
    }
}
