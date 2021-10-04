package com.ivanboyukliev.schoolgradingapp.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivanboyukliev.schoolgradingapp.baseentity.BaseNamedEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CourseDTO implements BaseNamedEntity {

    @JsonProperty("course_name")
    private String name;

    @JsonProperty("course_url")
    private String courseUrl;

    @Override
    public String getName() {
        return name;
    }
}
