package com.findwise;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
class SearchController {

    private SearchEngineImpl searchEngine;
    private int counter = 1;

    SearchController(SearchEngineImpl searchEngine) {
        this.searchEngine = searchEngine;
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    List<IndexEntry> search(@RequestParam String term) {
        return searchEngine.search(term);
    }

    @PostMapping(value = "/documents")
    Map<String, String> addDocuments(@RequestBody List<String> documentList) {

        Map<String, String> documents = new TreeMap<>();
        documentList.forEach(content -> {
            String id = String.join("","document",Integer.toString(counter++));
            documents.put(id, content);
            searchEngine.indexDocument(id, content);
        });
        return documents;
    }
}
