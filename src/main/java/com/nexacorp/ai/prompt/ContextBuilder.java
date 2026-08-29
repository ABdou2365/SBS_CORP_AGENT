package com.nexacorp.ai.prompt;


import com.nexacorp.ai.prompt.model.PromptContext;
import com.nexacorp.ai.retrieval.model.RetrievalResult;

public class ContextBuilder {

    public PromptContext build(RetrievalResult retrievalResult){
        StringBuilder contextBuilder = new StringBuilder();
        int index = 1;
        for (var chunk : retrievalResult.getChunks()) {
            contextBuilder.append("Index : ").append(index++).append("\n");
            contextBuilder.append(chunk.getContent());
            contextBuilder.append("\n\n");
        }
        return new PromptContext(contextBuilder.toString());
    }

}
