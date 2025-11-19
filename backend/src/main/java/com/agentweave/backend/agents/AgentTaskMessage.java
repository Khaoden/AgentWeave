package com.agentweave.backend.agents;

public class AgentTaskMessage {
    private Long taskId;
    private String payload;

    public AgentTaskMessage() {
    }

    public AgentTaskMessage(Long taskId, String payload) {
        this.taskId = taskId;
        this.payload = payload;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
