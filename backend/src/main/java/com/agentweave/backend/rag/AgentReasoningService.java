package com.agentweave.backend.rag;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.stereotype.Service;

@Service
public class AgentReasoningService {

    private final ChatLanguageModel chatLanguageModel;
    private final EmbeddingStore embeddingStore;
    private final ContextCacheService cacheService;

    public AgentReasoningService(ChatLanguageModel chatLanguageModel, EmbeddingStore embeddingStore, ContextCacheService cacheService) {
        this.chatLanguageModel = chatLanguageModel;
        this.embeddingStore = embeddingStore;
        this.cacheService = cacheService;
    }

    public String summarize(String documentId, String context) {
        cacheService.cacheContext("ctx:" + documentId, context);
        // Placeholder call for LLM integration
        return chatLanguageModel.generate("Summarize document " + documentId + ": " + context).content().text();
    }

    public EmbeddingStore embeddingStore() {
        return embeddingStore;
    }
}
