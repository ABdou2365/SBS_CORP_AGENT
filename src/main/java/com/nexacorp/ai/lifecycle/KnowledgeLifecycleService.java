package com.nexacorp.ai.lifecycle;

import ch.qos.logback.core.util.FileUtil;
import com.nexacorp.ai.ingestion.db.DatabaseIngestionService;
import com.nexacorp.ai.ingestion.pdf.PdfIngestionService;
import com.nexacorp.ai.ingestion.wiki.WikiIngestionService;
import com.nexacorp.ai.lifecycle.model.KnowledgeRequest;
import com.nexacorp.ai.vectorstore.ChunkVectorStoreService;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.nexacorp.ai.lifecycle.model.SourceType.*;

@Service
public class KnowledgeLifecycleService {


    private final ChunkVectorStoreService vectorStore;

    public KnowledgeLifecycleService(ChunkVectorStoreService vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void ingest(KnowledgeRequest knowledgeRequest) throws Exception {
        // Implement the ingestion logic based on the source type and file name
        // For example, you can call different services based on the source type


        String identity = KnowledgeIdentity.from(knowledgeRequest);

//        switch (knowledgeRequest.getSourceType()) {
//            case PDF:
//                // Call PDF ingestion service
//                String identity = KnowledgeIdentity.from(knowledgeRequest);
//
//                // pdfIngestionService.ingestPdfs();
//                break;
//            case WIKI:
//                // Call WIKI ingestion service
//
//                String identity = KnowledgeIdentity.from(knowledgeRequest);
//                // wikiIngestionService.ingestWikis();
//                break;
//            case DATABASE:
//                // Call Database ingestion service
//                // databaseIngestionService.ingestDatabaseContent();
//                break;
//            default:
//                throw new IllegalArgumentException("Unsupported source type: " + knowledgeRequest.getSourceType());
//        }
    }

    public void delete(KnowledgeRequest knowledgeRequest) {
        String identity = KnowledgeIdentity.from(knowledgeRequest);
        vectorStore.deleteByIdentity(identity);
    }

    public void deleteAll() {
        vectorStore.deleteAll();
    }

}
