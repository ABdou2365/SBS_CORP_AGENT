package com.nexacorp.ai.controller;

import com.nexacorp.ai.lifecycle.KnowledgeLifecycleService;
import com.nexacorp.ai.lifecycle.model.KnowledgeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeLifecycleController {

    private final KnowledgeLifecycleService knowledgeLifecycleService;

    public KnowledgeLifecycleController(KnowledgeLifecycleService knowledgeLifecycleService) {
        this.knowledgeLifecycleService = knowledgeLifecycleService;
    }

    @PostMapping("/ingest")
    public ResponseEntity<String> ingest(@RequestBody KnowledgeRequest knowledgeRequest) {
        try {
            knowledgeLifecycleService.ingest(knowledgeRequest);
            return ResponseEntity.ok("Knowledge ingested successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Ingestion failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/{identity}")
    public ResponseEntity<String> delete(@PathVariable String identity) {
        knowledgeLifecycleService.delete(identity);
        return ResponseEntity.ok("Knowledge deleted successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        knowledgeLifecycleService.deleteAll();
        return ResponseEntity.ok("All knowledge deleted successfully");
    }
}