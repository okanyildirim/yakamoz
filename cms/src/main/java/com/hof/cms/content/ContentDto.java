package com.hof.cms.content;

import com.hof.cms.writer.data.WriterDto;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentDto {

	private Long id;
	@NotNull
	private String title;
	@NotNull
	private ContentType contentType;
	@NotNull
	private String body;

	private WriterDto writer;
}
