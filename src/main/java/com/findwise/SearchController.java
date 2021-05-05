package com.findwise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
class SearchController {

    private SearchEngineImpl searchEngine;
    private int counter = 1;

    SearchController(SearchEngineImpl searchEngine) {
        this.searchEngine = searchEngine;
    }


    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<IndexEntry> search(@RequestParam String term) {
        return searchEngine.search(term);
    }

    @PostMapping(value = "/documents")
    public @ResponseBody
    Map<String, String> addDocuments(@RequestBody List<String> documentList) {

        Map<String, String> documents = searchEngine.getDocuments();
        documentList.forEach(content -> {
            String id = String.join("","document",Integer.toString(counter++));
            documents.put(id, content);
            searchEngine.indexDocument(id, content);
        });
        return documents;
    }
}
