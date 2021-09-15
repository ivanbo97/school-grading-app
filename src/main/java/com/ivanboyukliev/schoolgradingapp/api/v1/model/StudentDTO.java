package com.ivanboyukliev.schoolgradingapp.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.ivanboyukliev.schoolgradingapp.baseentity.BaseNamedEntity;
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
public class StudentDTO implements BaseNamedEntity {

	@JsonProperty("student_name")
	private String name;
	
	@JsonProperty("student_url")
	private String studentUrl;

	@Override
	public String getName(){
		return this.name;
	}
}
