package com.ivanboyukliev.schoolgradingapp.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MarkDTO {

    private double mark;

    private String date;

    @JsonProperty("student_name")
    private String studentName;

    @JsonProperty("course_name")
    private String courseName;

    @JsonProperty("mark_url")
    private String markUrl;
}
