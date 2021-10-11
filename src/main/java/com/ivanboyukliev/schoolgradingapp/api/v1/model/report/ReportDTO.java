package com.ivanboyukliev.schoolgradingapp.api.v1.model.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReportDTO {

    @JsonProperty("report_result")
    private String reportResult;
}
