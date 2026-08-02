package com.nexacorp.ai.embedding.model;


import com.nexacorp.ai.chunking.model.Chunk;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmbeddedChunk {
    private final Chunk chunk;
    private final float[] vector;
}
