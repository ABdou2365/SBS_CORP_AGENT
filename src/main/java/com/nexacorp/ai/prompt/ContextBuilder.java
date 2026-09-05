package com.nexacorp.ai.prompt;


import com.nexacorp.ai.chunking.model.Chunk;
import com.nexacorp.ai.prompt.model.PromptContext;
import com.nexacorp.ai.retrieval.model.RetrievalResult;

import java.util.Map;

public class ContextBuilder {

    public PromptContext build(RetrievalResult retrievalResult){
        StringBuilder contextBuilder = new StringBuilder();
        int index = 1;
        for (var chunk : retrievalResult.getChunks()) {
            contextBuilder.append("Index : ").append(index++).append("\n");
            appendCitation(contextBuilder,chunk);
            contextBuilder.append(chunk.getContent());
            contextBuilder.append("\n\n");
        }
        return new PromptContext(contextBuilder.toString().trim());
    }

    private void appendCitation(StringBuilder contextBuilder, Chunk chunk) {
        Map<String, Object> metadata = chunk.getMetadata();
        String source = metadata.get("source").toString();
        switch (source) {
            case "DB":
                String table = metadata.get("table").toString();
                String id = metadata.get("id").toString();
                contextBuilder.append("Source : ").append("[DB:").append(table).append("#").append(id).append("]\n");
                break;
            case "PDF":
                String fileName = metadata.get("fileName").toString();
                contextBuilder.append("Source : ").append("[PDF:").append(fileName).append("]\n");
                break;
            case "WIKI":
                String wikiFileName = metadata.get("fileName").toString();
                contextBuilder.append("Source : ").append("[WIKI:").append(wikiFileName).append("]\n");
                break;
            default:
                contextBuilder.append("Source : ").append(source).append("\n");
        }
    }   ;
    }


