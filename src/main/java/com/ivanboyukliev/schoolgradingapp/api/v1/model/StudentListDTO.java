package com.ivanboyukliev.schoolgradingapp.api.v1.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentListDTO {
    List<StudentDTO> students;
}
