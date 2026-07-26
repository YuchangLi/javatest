package com.java11.design.state;

/**
 * 状态接口 - 定义订单在各状态下可执行的操作
 */
public interface OrderState {

    /**
     * 提交订单
     */
    void submit(OrderContext context);

    /**
     * 审批通过
     */
    void approve(OrderContext context);

    /**
     * 审批驳回
     */
    void reject(OrderContext context);

    /**
     * 取消订单
     */
    void cancel(OrderContext context);

    /**
     * 获取当前状态名称
     */
    String getStateName();
}
