package com.nexacorp.ai.lifecycle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class KnowledgeRequest {
    private SourceType sourceType;
    private String fileName;
}
