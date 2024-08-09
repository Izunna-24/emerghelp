package com.emerghelp.emerghelp.controllers;

import com.emerghelp.emerghelp.dtos.responses.UpdateProfileResponse;
import com.emerghelp.emerghelp.exceptions.EmerghelpBaseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/data.sql"})
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;




//    @Test
//    public void testThatUserProfileCanBeViewed() throws Exception {
//        long userId = 200L;
//        try {
//            mockMvc.perform(MockMvcRequestBuilders.get("/user/view/" + userId )
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andDo(print());
//        }
//        catch (EmerghelpBaseException e){
//            e.getMessage();
//        }
//    }


//    @Test
//    public void testThatUserProfileCanBeUpdated() throws Exception {
//        long userId = 200L;
//        String jsonPatch = "[{\"op\":\"replace\",\"path\":\"/firstName\",\"value\":\"Olajumoke\"}]";
//
//        mockMvc.perform(MockMvcRequestBuilders.patch("/user/update/" + userId)
//                        .contentType(MediaType.valueOf("application/json-patch+json"))
//                        .content(jsonPatch))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect((ResultMatcher) jsonPath("$.firstName").value("Olajumoke"))
//                .andDo(print());
//    }



}
