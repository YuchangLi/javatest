package com.java11.design.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 具体状态 - 已批准
 */
public class ApprovedState implements OrderState {

    private static final Logger log = LoggerFactory.getLogger(ApprovedState.class);

    @Override
    public void submit(OrderContext context) {
        log.warn("  操作失败: 已批准订单不能重新提交");
    }

    @Override
    public void approve(OrderContext context) {
        log.warn("  操作失败: 订单已批准，无需重复审批");
    }

    @Override
    public void reject(OrderContext context) {
        log.warn("  操作失败: 已批准订单不能驳回");
    }

    @Override
    public void cancel(OrderContext context) {
        log.info("  取消已批准订单");
        context.transitionTo(CancelledState.class);
    }

    @Override
    public String getStateName() {
        return "已批准";
    }
}
