package com.nexacorp.ai.lifecycle.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class KnowledgeRequest {
    private SourceType sourceType;
    private String fileName;
}
