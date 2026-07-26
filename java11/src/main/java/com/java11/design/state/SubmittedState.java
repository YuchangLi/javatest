package com.java11.design.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 具体状态 - 已提交，待审批
 */
public class SubmittedState implements OrderState {

    private static final Logger log = LoggerFactory.getLogger(SubmittedState.class);

    @Override
    public void submit(OrderContext context) {
        log.warn("  操作失败: 订单已提交，请勿重复提交");
    }

    @Override
    public void approve(OrderContext context) {
        log.info("  审批通过，订单进入已批准状态");
        context.transitionTo(ApprovedState.class);
    }

    @Override
    public void reject(OrderContext context) {
        log.info("  审批驳回");
        context.transitionTo(RejectedState.class);
    }

    @Override
    public void cancel(OrderContext context) {
        log.info("  取消待审批订单");
        context.transitionTo(CancelledState.class);
    }

    @Override
    public String getStateName() {
        return "已提交";
    }
}
