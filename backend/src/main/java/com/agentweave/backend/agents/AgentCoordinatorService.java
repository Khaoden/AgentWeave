package com.agentweave.backend.agents;

import com.agentweave.backend.persistence.Task;
import com.agentweave.backend.persistence.TaskRepository;
import com.agentweave.backend.persistence.TaskResult;
import com.agentweave.backend.persistence.TaskResultRepository;
import com.agentweave.backend.persistence.TaskStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AgentCoordinatorService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TaskRepository taskRepository;
    private final TaskResultRepository taskResultRepository;

    public AgentCoordinatorService(KafkaTemplate<String, String> kafkaTemplate,
                                   TaskRepository taskRepository,
                                   TaskResultRepository taskResultRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.taskRepository = taskRepository;
        this.taskResultRepository = taskResultRepository;
    }

    public Task enqueuePlanTask(Long documentId) {
        Task task = new Task();
        task.setType("PLAN");
        task.setReference(String.valueOf(documentId));
        task.setStatus(TaskStatus.PENDING);
        Task saved = taskRepository.save(task);
        kafkaTemplate.send(KafkaTopics.PLAN_AGENT, saved.getId().toString());
        return saved;
    }

    @KafkaListener(topics = KafkaTopics.DOC_AGENT, groupId = "agentweave")
    public void handleDocumentIngest(String documentId) {
        enqueuePlanTask(Long.valueOf(documentId));
    }

    @KafkaListener(topics = KafkaTopics.PLAN_AGENT, groupId = "agentweave-plan")
    public void handlePlanComplete(String message) {
        Task task = taskRepository.findById(Long.valueOf(message)).orElse(null);
        if (task == null) {
            return;
        }
        task.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task);
        kafkaTemplate.send(KafkaTopics.WRITE_AGENT, message);
    }

    @KafkaListener(topics = KafkaTopics.WRITE_AGENT, groupId = "agentweave-write")
    public void handleWriteComplete(String message) {
        TaskResult result = new TaskResult();
        result.setTaskId(Long.valueOf(message));
        result.setResult("Report drafted for task " + message);
        taskResultRepository.save(result);
    }
}
