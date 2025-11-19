package com.agentweave.backend.api;

import com.agentweave.backend.documents.DocumentService;
import com.agentweave.backend.persistence.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/docs")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/ingest")
    public ResponseEntity<Document> ingest(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(documentService.ingest(file));
    }
}
