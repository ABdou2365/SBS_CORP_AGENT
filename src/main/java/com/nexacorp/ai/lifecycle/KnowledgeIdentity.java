package com.nexacorp.ai.lifecycle;

import com.nexacorp.ai.lifecycle.model.KnowledgeRequest;

public class KnowledgeIdentity {

    public static String from(KnowledgeRequest request){
        return switch (request.getSourceType()) {
            case PDF -> "PDF#" + request.getFileName();
            case WIKI -> "WIKI#" + request.getFileName();
            case DATABASE -> "DB#" + request.getFileName();
            default -> "";
        };
    }

}
