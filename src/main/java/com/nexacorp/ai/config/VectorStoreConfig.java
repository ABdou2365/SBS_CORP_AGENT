package com.nexacorp.ai.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;

@Configuration
class VectorStoreConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private Integer redisPort;

    @Bean(name = "customVectorStore")
    public VectorStore vectorStore(JedisPooled jedisPooled, EmbeddingModel embeddingModel) {

        return RedisVectorStore.builder(jedisPooled, embeddingModel)
                .indexName("nexacorp_index")
                .initializeSchema(true)
                .metadataFields(
                        RedisVectorStore.MetadataField.text("identity"),
                        RedisVectorStore.MetadataField.text("source"),
                        RedisVectorStore.MetadataField.numeric("chunkIndex"),
                        // PDF
                        RedisVectorStore.MetadataField.text("fileName"),
                        RedisVectorStore.MetadataField.numeric("chunkSize"),
                        RedisVectorStore.MetadataField.numeric("chunkOverlap"),
                        RedisVectorStore.MetadataField.text("chunkStrategy"),
                        // WIKI
                        RedisVectorStore.MetadataField.text("chunkType"),
                        //DB
                        RedisVectorStore.MetadataField.text("table"),
                        RedisVectorStore.MetadataField.numeric("id"),
                        RedisVectorStore.MetadataField.text("department") ,
                        RedisVectorStore.MetadataField.text("visibility") ,
                        RedisVectorStore.MetadataField.text("version") ,
                        RedisVectorStore.MetadataField.text("releaseDate") ,
                        RedisVectorStore.MetadataField.text("category") ,
                        RedisVectorStore.MetadataField.text("effectiveFrom") ,
                        RedisVectorStore.MetadataField.text("effectiveTo") ,
                        RedisVectorStore.MetadataField.text("sourceType")
                )
                .build();
    }

    @Bean
    public JedisPooled jedisPooled() {
        return new JedisPooled(redisHost, redisPort);
    }

}
