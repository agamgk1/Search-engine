package com.findwise;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
class SearchEngineImpl implements SearchEngine {

    private Map<String, String> documents;
    private Map<String, Set<String>> index;

    SearchEngineImpl(){
        this.documents = new TreeMap<>();
        this.index = new HashMap<>();
    }

    public Map<String, String> getDocuments() {
        return documents;
    }

    @Override
    public void indexDocument(String id, String content) {
        documents.put(id, content);
        List<String> tokenizedContent = Arrays.asList(content.toLowerCase().trim().split("\\s+"));
        tokenizedContent.forEach(s -> {
            index.computeIfAbsent(s, k -> new HashSet<>()).add(id);
        });
    }

    @Override
    public List<IndexEntry> search(String term) {
        if(term==null) {
            throw new IllegalArgumentException("Term can not be null");
        }
        List<IndexEntry> orderedDocuments = new ArrayList<>();
        Set<String> result = index.get(term.toLowerCase());
        if(result==null || result.isEmpty()) {
            return List.of();
        }
        result.forEach(s -> {
           IndexEntry indexEntry = new IndexEntryImpl(s,calculateTfIdfScore(s, term));
           orderedDocuments.add(indexEntry);
        });

        return orderedDocuments.stream()
                .sorted(Comparator.comparingDouble(IndexEntry::getScore).reversed())
                .collect(Collectors.toList());
    }

    private double calculateTfIdfScore(String id, String term){
        return tf(id, term) * idf(term);
    }

      private double tf(String id, String term){
        List<String> document = Arrays.asList(documents.get(id).trim().split("\\s+"));
        double result = 0;
        for (String word : document) {
            if (term.equals(word))
                result++;
          }
          return result / document.size();
    }

    private double idf(String term){
        double wordsCount = documents.size();
        double termCount = index.get(term.toLowerCase()).size();

        return Math.log10(wordsCount/termCount);
    }
}
