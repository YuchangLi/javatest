package com.java11.design.state;

/**
 * 状态模式演示类
 *
 * 状态模式是一种行为设计模式，允许对象在内部状态改变时改变其行为。
 * 对象看起来好像修改了自身的类。
 *
 * 主要角色：
 * 1. State（状态接口）：定义状态行为 - OrderState
 * 2. ConcreteState（具体状态）：实现各状态下的行为与转换 - DraftState, SubmittedState 等
 * 3. Context（上下文）：维护当前状态并委托操作 - OrderContext
 *
 * 本示例场景：订单审批工作流
 * 状态流转：草稿 -> 已提交 -> 已批准 / 已驳回 / 已取消
 *
 * 优点：
 * - 将状态相关的行为封装到独立类中，避免大量 if-else
 * - 新增状态只需添加具体状态类，符合开闭原则
 * - 状态转换逻辑清晰，易于理解和维护
 *
 * 适用场景：
 * - 对象行为依赖状态，且状态在运行时频繁切换
 * - 条件分支过多，难以维护
 */
public class StatePatternDemo {

    public static void main(String[] args) {
        System.out.println("========== 状态模式演示：订单审批工作流 ==========\n");

        // 场景1: 正常审批流程 Draft -> Submitted -> Approved
        System.out.println("场景1: 正常审批流程");
        var order1 = new OrderContext("ORD-001");
        System.out.println("  初始状态: " + order1.getStateName() + "\n");
        order1.submit();
        order1.approve();

        // 场景2: 审批驳回后重新提交 Draft -> Submitted -> Rejected -> Submitted -> Approved
        System.out.println("场景2: 驳回后修改重新提交");
        var order2 = new OrderContext("ORD-002");
        order2.submit();
        order2.reject();
        order2.submit();
        order2.approve();

        // 场景3: 提交后取消 Draft -> Submitted -> Cancelled
        System.out.println("场景3: 提交后取消订单");
        var order3 = new OrderContext("ORD-003");
        order3.submit();
        order3.cancel();

        // 场景4: 非法操作演示
        System.out.println("场景4: 非法操作（终态与越级操作）");
        var order4 = new OrderContext("ORD-004");
        order4.approve();  // 非法: 草稿不能直接审批
        var order5 = new OrderContext("ORD-005");
        order5.cancel();   // 取消订单
        order5.submit();   // 非法: 已取消不能提交

        System.out.println("========== 演示结束 ==========");
    }
}
