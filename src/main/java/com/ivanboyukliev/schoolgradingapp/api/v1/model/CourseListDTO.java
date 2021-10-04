package com.ivanboyukliev.schoolgradingapp.api.v1.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CourseListDTO {
    List<CourseDTO> courses;
}
