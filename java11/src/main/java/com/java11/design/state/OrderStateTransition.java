package com.java11.design.state;

/**
 * 状态转换配置表 - 集中管理所有合法的状态转换规则
 */
public enum OrderStateTransition {
    DRAFT_SUBMIT(DraftState.class, SubmittedState.class, "草稿提交"),
    
    SUBMITTED_APPROVE(SubmittedState.class, ApprovedState.class, "提交审批通过"),
    SUBMITTED_REJECT(SubmittedState.class, RejectedState.class, "提交审批驳回"),
    SUBMITTED_CANCEL(SubmittedState.class, CancelledState.class, "提交后取消"),
    
    REJECTED_SUBMIT(RejectedState.class, SubmittedState.class, "驳回后重新提交"),
    REJECTED_CANCEL(RejectedState.class, CancelledState.class, "驳回后取消"),
    
    APPROVED_CANCEL(ApprovedState.class, CancelledState.class, "已批准后取消"),
    
    DRAFT_CANCEL(DraftState.class, CancelledState.class, "草稿取消");

    private final Class<? extends OrderState> fromState;
    private final Class<? extends OrderState> toState;
    private final String description;

    OrderStateTransition(Class<? extends OrderState> fromState, 
                        Class<? extends OrderState> toState, 
                        String description) {
        this.fromState = fromState;
        this.toState = toState;
        this.description = description;
    }

    public Class<? extends OrderState> getFromState() {
        return fromState;
    }

    public Class<? extends OrderState> getToState() {
        return toState;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 验证状态转换是否合法
     */
    public static boolean isValid(Class<? extends OrderState> fromState, 
                                  Class<? extends OrderState> toState) {
        for (OrderStateTransition transition : values()) {
            if (transition.fromState.equals(fromState) && transition.toState.equals(toState)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取目标状态类
     */
    public static Class<? extends OrderState> getTargetState(Class<? extends OrderState> fromState, 
                                                             String operation) {
        for (OrderStateTransition transition : values()) {
            if (transition.fromState.equals(fromState) && 
                transition.description.startsWith(operation)) {
                return transition.toState;
            }
        }
        return null;
    }
}