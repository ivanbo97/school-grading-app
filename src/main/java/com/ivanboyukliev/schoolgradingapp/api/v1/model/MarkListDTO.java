package com.ivanboyukliev.schoolgradingapp.api.v1.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MarkListDTO {
    List<MarkDTO> marks;
}
