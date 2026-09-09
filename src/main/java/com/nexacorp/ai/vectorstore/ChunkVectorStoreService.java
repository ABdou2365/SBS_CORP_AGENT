package com.nexacorp.ai.vectorstore;

import com.nexacorp.ai.chunking.model.Chunk;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChunkVectorStoreService {

    private final RedisVectorStore vectorStore;

    public ChunkVectorStoreService(@Qualifier("customVectorStore") RedisVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void store(List<Chunk> chunks) {

        List<Document> documents = chunks.stream().map(chunk -> {
            Map<String, Object> metadata = new HashMap<>(chunk.getMetadata());
            metadata.put("source", chunk.getSource());
            metadata.put("chunkIndex", chunk.getChunkIndex());

            return new Document(
                    chunk.getContent(),
                    metadata
            );
        }).collect(Collectors.toList());

        vectorStore.add(documents);
    }

    /**
     * Deletes all chunks stored in the vector store.
     *
     * <p>Redis Vector Store does not support deleting all records in a single
     * operation. To work around this limitation, a filter expression matching
     * all chunks is used. The deletion is performed repeatedly until no
     * matching records remain.</p>
     *
     * <p>The {@code chunkIndex >= -1} condition is intentionally broad and is
     * used as a "match all chunks" filter, since every stored chunk is expected
     * to have a valid chunk index.</p>
     *
     * <p>The operation is performed in batches to avoid relying on a single
     * delete-all operation that is not supported by the underlying Redis
     * implementation.</p>
     */
    public void deleteAll() {
        FilterExpressionBuilder filterBuilder = new FilterExpressionBuilder();

        Filter.Expression filter =
                filterBuilder.gte("chunkIndex", -1).build();

        while (vectorStore.count(filter) > 0) {
            vectorStore.delete(filter);
        }
    }

    /**
     * Deletes all chunks associated with the specified identity.
     *
     * <p>The identity is stored as metadata on each vector store document and
     * is used to identify all chunks belonging to the same source or document.</p>
     *
     * @param identity the identity of the chunks to delete
     */
    public void deleteByIdentity(String identity) {
        FilterExpressionBuilder filterBuilder = new FilterExpressionBuilder();

        vectorStore.delete(
                filterBuilder.eq("identity", identity).build()
        );
    }
}
