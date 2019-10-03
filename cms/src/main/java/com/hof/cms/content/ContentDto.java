package com.hof.cms.content;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDto {

	private Long id;
	@NotNull
	private String title;
	@NotNull
	private ContentType contentType;
	@NotNull
	private String body;
}
