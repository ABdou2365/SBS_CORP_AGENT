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
            case "PDF":
            case "WIKI":
                contextBuilder.append("Source : ").append("[").append(source).append(":")
                        .append(metadata.get("fileName")).append("]\n");
                break;
            case "DB":
                contextBuilder.append("Source : ").append("[").append(source).append(":")
                        .append(metadata.get("table"))
                        .append("#").append(metadata.get("id")).append("]\n");
                break;
            default:
                contextBuilder.append("Source : ").append(source).append("\n");
        }
    }   ;
    }


