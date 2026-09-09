package com.nexacorp.ai.ingestion.wiki;

import com.nexacorp.ai.ingestion.model.IngestedDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WikiIngestionService {

    private static final Logger log = LoggerFactory.getLogger(WikiIngestionService.class);
    private static final String PDF_DIRECTORY = "src/main/java/com/nexacorp/ai/data/wiki";

    public List<IngestedDocument> ingestWikis() throws IOException {
        File[] wikiDirectory = new File(PDF_DIRECTORY).listFiles();

        List<IngestedDocument> docs = new ArrayList<>();

        if (wikiDirectory == null) {
            throw new IOException("Wiki directory does not exist or cannot be read: " + PDF_DIRECTORY);
        }

        for (File pdfFile : wikiDirectory) {
            docs.add(ingestSingleWiki(pdfFile));
        }
        return docs;
    }

    private IngestedDocument ingestSingleWiki(File pdfFile) throws IOException {
        log.info("Ingesting Wiki: {}", pdfFile.getName());

        String content = Files.readString(pdfFile.toPath());

        log.info("----- WiKi Content ({}) -----", pdfFile.getName());
        log.info(content);
        return new IngestedDocument(
                "WIKI",
                content,
                Map.of("fileName", pdfFile.getName(),
                        "identity", "WIKI#" + pdfFile.getName()

                )
        );
        }
}
