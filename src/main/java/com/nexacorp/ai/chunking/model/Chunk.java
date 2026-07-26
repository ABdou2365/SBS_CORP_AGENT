package com.nexacorp.ai.chunking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class Chunk {
    private String source;
    private String content;
    private Map<String, Object> metadata;
    private int chunkIndex;
}
