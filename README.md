# Java assignment - Simple search engine

The goal of this assignment is to create a simple search engine in Java. The search engine should be implemented as an inverted index which return a list that is sorted by TF-IDF

Example
The following documents are indexed: 
- Document 1: "the brown fox jumped over the brown dog" 
- Document 2: "the lazy brown dog sat in the corner" 
- Document 3: "the red fox bit the lazy dog"

A search for "brown" should now return the list: [document 1, document 2] A search for "fox" should now return the list: [document 1, document 3]

**To run program use IDE or .jar file:**

``` java -jar Search-engine-0.0.1-SNAPSHOT.jar```

**To run unit tests use IDE or Maven:**

``` mvn clean test```

[In httpRequsts directory you will find necessary requests to work with program]

**Technologies:**

- Java 16
- Spring 5
- Spring Boot 2
- JUnit
- AssertJ
- Mockito

