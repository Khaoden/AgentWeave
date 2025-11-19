package com.agentweave.backend.documents;

import com.agentweave.backend.persistence.Document;
import com.agentweave.backend.persistence.DocumentRepository;
import org.apache.tika.Tika;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Tika tika = new Tika();

    public DocumentService(DocumentRepository documentRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.documentRepository = documentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Document ingest(MultipartFile file) throws IOException {
        String text = tika.parseToString(file.getInputStream());
        Document document = new Document();
        document.setFilename(file.getOriginalFilename());
        document.setContent(text);
        document.setContentType(file.getContentType());
        Document saved = documentRepository.save(document);
        kafkaTemplate.send("doc-agent-topic", saved.getId().toString());
        return saved;
    }
}
