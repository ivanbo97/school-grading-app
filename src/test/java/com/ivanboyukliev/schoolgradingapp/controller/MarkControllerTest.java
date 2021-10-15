package com.ivanboyukliev.schoolgradingapp.controller;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkListDTO;
import com.ivanboyukliev.schoolgradingapp.service.MarkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.ivanboyukliev.schoolgradingapp.controller.StudentControllerTest.asJsonString;
import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.MARK_BASE_URL;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MarkController.class)
class MarkControllerTest {

    @MockBean
    private MarkService markService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void findAllMarksTest() throws Exception {
        // given
        MarkDTO mark1 = new MarkDTO(5.5, LocalDateTime.now().toString(), "Ivan Doe", "History", "api/v1/mark/1");
        MarkDTO mark2 = new MarkDTO(4.5, LocalDateTime.now().toString(), "Ivan Doe", "Agriculture", "api/v1/mark/1");
        MarkListDTO retrievedMarks = new MarkListDTO(Arrays.asList(mark1, mark2));
        given(markService.findAllMarks()).willReturn(retrievedMarks);

        // when, then
        mockMvc.perform(get(MARK_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marks", hasSize(2)))
                .andExpect(jsonPath("$.marks[0].mark", is(mark1.getMark())))
                .andExpect(jsonPath("$.marks[1].mark", is(mark2.getMark())));
    }


    @Test
    void findMarkByIdTest() throws Exception {
        // given
        MarkDTO mark1 = new MarkDTO(5.5, LocalDateTime.now().toString(), "Ivan Doe", "History", "api/v1/mark/1");

        given(markService.findMarkById(anyLong())).willReturn(mark1);

        // when, then
        mockMvc.perform(get(MARK_BASE_URL + "/13")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mark", is(mark1.getMark())))
                .andExpect(jsonPath("$.student_name", is(mark1.getStudentName())))
                .andExpect(jsonPath("$.course_name", is(mark1.getCourseName())));

        then(markService).should().findMarkById(anyLong());
    }

    @Test
    void saveMarkTest() throws Exception {
        // given
        MarkDTO providedMark = new MarkDTO(5.5, LocalDateTime.now().toString(), "Ivan Doe", "History", "api/v1/mark/1");
        given(markService.saveMark(any(MarkDTO.class))).willReturn(providedMark);
        // when, then

        mockMvc.perform(post(MARK_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(providedMark)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mark", is(providedMark.getMark())))
                .andExpect(jsonPath("$.student_name", is(providedMark.getStudentName())));

        then(markService).should().saveMark(any());
    }

    @Test
    void updateMarkTest() throws Exception {

        // given
        MarkDTO markForUpdate = new MarkDTO(4.5, LocalDateTime.now().toString(), "Georgi Doe", "Psychology", "api/v1/mark/7");
        given(markService.updateMark(anyLong(), any(MarkDTO.class))).willReturn(markForUpdate);

        // when, then
        mockMvc.perform(put(MARK_BASE_URL + "/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(markForUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mark", is(markForUpdate.getMark())))
                .andExpect(jsonPath("$.date", is(markForUpdate.getDate())))
                .andExpect(jsonPath("$.mark_url", is(markForUpdate.getMarkUrl())))
                .andExpect(jsonPath("$.student_name", is(markForUpdate.getStudentName())))
                .andExpect(jsonPath("$.course_name", is(markForUpdate.getCourseName())));
        then(markService).should().updateMark(anyLong(), any(MarkDTO.class));
    }

    @Test
    void deleteMarkTest() throws Exception {

        mockMvc.perform(delete(MARK_BASE_URL + "/7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}