package com.findwise;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class SearchEngineImplTest {

    SearchEngineImpl searchEngine = new SearchEngineImpl();

    public void addDocuments(){
        searchEngine.indexDocument("Document 1", "the brown fox jumped over the brown dog");
        searchEngine.indexDocument("Document 2", "the lazy brown dog sat in the corner");
        searchEngine.indexDocument("Document 3", "the red fox bit the lazy dog");
    }

    @Test
    public void searchBrownTerm(){
        //Given & When
        addDocuments();
        List<IndexEntry> indexEntry = searchEngine.search("Brown");
        //Then
        assertThat(indexEntry.size()).isEqualTo(2);
        assertThat(indexEntry.get(0).getId()).isEqualTo("Document 1");
        assertThat(indexEntry.get(1).getId()).isEqualTo("Document 2");
    }

    @Test
    public void searchFoxTerm(){
        //Given & When
        addDocuments();
        List<IndexEntry> indexEntry = searchEngine.search("FOX");
        //Then
        assertThat(indexEntry.size()).isEqualTo(2);
        assertThat(indexEntry.get(0).getId()).isEqualTo("Document 3");
        assertThat(indexEntry.get(1).getId()).isEqualTo("Document 1");
    }

    @Test
    public void searchWrongTerm() {
        //Given & When
        addDocuments();
        List<IndexEntry> indexEntry = searchEngine.search("AAA");
        //Then
        assertThat(indexEntry.size()).isEqualTo(0);
    }

    @Test
    public void searchNullValue_throwsIllegalArgumentException(){
        //Given
        addDocuments();
        //when
        var exception = catchThrowable(() -> searchEngine.search(null));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Term can not be null");
    }
}
