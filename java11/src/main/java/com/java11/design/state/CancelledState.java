package com.java11.design.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 具体状态 - 已取消（终态）
 */
public class CancelledState implements OrderState {

    private static final Logger log = LoggerFactory.getLogger(CancelledState.class);

    @Override
    public void submit(OrderContext context) {
        log.warn("  操作失败: 已取消的订单不能提交");
    }

    @Override
    public void approve(OrderContext context) {
        log.warn("  操作失败: 已取消的订单不能审批");
    }

    @Override
    public void reject(OrderContext context) {
        log.warn("  操作失败: 已取消的订单不能驳回");
    }

    @Override
    public void cancel(OrderContext context) {
        log.warn("  操作失败: 订单已取消");
    }

    @Override
    public String getStateName() {
        return "已取消";
    }
}
