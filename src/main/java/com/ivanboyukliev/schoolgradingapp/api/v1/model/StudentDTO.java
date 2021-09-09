package com.ivanboyukliev.schoolgradingapp.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class StudentDTO {

	@JsonProperty("student_name")
	private String name;
	
	@JsonProperty("student_url")
	private String studentUrl;
}
