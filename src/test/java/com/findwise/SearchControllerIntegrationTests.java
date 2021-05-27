package com.findwise;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerIntegrationTests {

   @Autowired
   private MockMvc mockMvc;
   @Autowired
   private SearchEngineImpl searchEngine;

   @Test
   void httpGet_returnsSearchResult() {
      //given
      searchEngine.indexDocument("document1", "the brown fox jumped over the brown dog");
      searchEngine.search("fox");
      //when
      try {
         mockMvc.perform(MockMvcRequestBuilders.get("/search?term=fox"))
                 .andExpect(status().is2xxSuccessful())
                 .andExpect(content()
                         .contentType("application/json"))
                 .andExpect(jsonPath("$[0]id")
                         .value("document1"));

      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   @Test
   void httpPost_AddDocuments(){
      //given & when
      try {
         mockMvc.perform(MockMvcRequestBuilders.post("/documents")
                 .content("brown fox jumped over the brown dog")
                 .contentType(MediaType.APPLICATION_JSON)).andDo(print());

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
