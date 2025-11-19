package com.agentweave.backend.persistence;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "task_results")
public class TaskResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;

    @Lob
    private String result;

    private Instant completedAt = Instant.now();

    public Long getId() {
        return id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }
}
