package com.agentweave.backend.rag;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RagConfig {

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv().getOrDefault("OPENAI_API_KEY", "changeme"))
                .modelName("gpt-4o-mini")
                .build();
    }

    @Bean
    public EmbeddingStore embeddingStore(@Qualifier("taskDataSource") DataSource dataSource) {
        return PgVectorEmbeddingStore.builder()
                .dataSource(dataSource)
                .schema("public")
                .table("document_embeddings")
                .dimension(384)
                .build();
    }
}
