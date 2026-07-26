package com.java11.design.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 具体状态 - 草稿
 */
public class DraftState implements OrderState {

    private static final Logger log = LoggerFactory.getLogger(DraftState.class);

    @Override
    public void submit(OrderContext context) {
        log.info("  提交订单，等待审批...");
        context.transitionTo(SubmittedState.class);
    }

    @Override
    public void approve(OrderContext context) {
        log.warn("  操作失败: 草稿状态的订单不能直接审批");
    }

    @Override
    public void reject(OrderContext context) {
        log.warn("  操作失败: 草稿状态的订单无需驳回");
    }

    @Override
    public void cancel(OrderContext context) {
        log.info("  取消草稿订单");
        context.transitionTo(CancelledState.class);
    }

    @Override
    public String getStateName() {
        return "草稿";
    }
}
