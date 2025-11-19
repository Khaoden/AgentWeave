package com.agentweave.backend.api;

import com.agentweave.backend.persistence.Task;
import com.agentweave.backend.persistence.TaskRepository;
import com.agentweave.backend.persistence.TaskResult;
import com.agentweave.backend.persistence.TaskResultRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final TaskResultRepository taskResultRepository;

    public TaskController(TaskRepository taskRepository, TaskResultRepository taskResultRepository) {
        this.taskRepository = taskRepository;
        this.taskResultRepository = taskResultRepository;
    }

    @GetMapping
    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{taskId}/result")
    public ResponseEntity<TaskResult> getResult(@PathVariable Long taskId) {
        return taskResultRepository.findAll().stream()
                .filter(res -> taskId.equals(res.getTaskId()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
