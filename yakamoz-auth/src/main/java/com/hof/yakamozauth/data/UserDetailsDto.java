package com.hof.yakamozauth.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {

	private Long id;
	private String firstName;
	private String lastName;
	private Integer age;
	private Long gsmNo;
}
