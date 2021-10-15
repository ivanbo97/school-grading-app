package com.ivanboyukliev.schoolgradingapp.controller;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkListDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.MARK_BASE_URL;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MarkController.class)
class MarkControllerTest {

    @MockBean
    private MarkController markController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void findAllMarksTest() throws Exception {
        // given
        MarkDTO mark1 = new MarkDTO(5.5, LocalDateTime.now().toString(), "Ivan Doe", "History", "api/v1/mark/1");
        MarkDTO mark2 = new MarkDTO(4.5, LocalDateTime.now().toString(), "Ivan Doe", "Agriculture", "api/v1/mark/1");
        MarkListDTO retrievedMarks = new MarkListDTO(Arrays.asList(mark1, mark2));
        given(markController.findAllCourses()).willReturn(retrievedMarks);

        // when, then
        mockMvc.perform(get(MARK_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marks", hasSize(2)))
                .andExpect(jsonPath("$.marks[0].mark", is(mark1.getMark())))
                .andExpect(jsonPath("$.marks[1].mark", is(mark2.getMark())));
    }
}