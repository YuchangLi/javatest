package com.java11.design.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 具体状态 - 已驳回
 */
public class RejectedState implements OrderState {

    private static final Logger log = LoggerFactory.getLogger(RejectedState.class);

    @Override
    public void submit(OrderContext context) {
        log.info("  修改后重新提交订单");
        context.transitionTo(SubmittedState.class);
    }

    @Override
    public void approve(OrderContext context) {
        log.warn("  操作失败: 已驳回订单需先重新提交");
    }

    @Override
    public void reject(OrderContext context) {
        log.warn("  操作失败: 订单已处于驳回状态");
    }

    @Override
    public void cancel(OrderContext context) {
        log.info("  取消已驳回订单");
        context.transitionTo(CancelledState.class);
    }

    @Override
    public String getStateName() {
        return "已驳回";
    }
}
