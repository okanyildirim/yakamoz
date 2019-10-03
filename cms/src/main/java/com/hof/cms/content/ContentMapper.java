package com.hof.cms.content;

import org.springframework.stereotype.Component;

@Component
public class ContentMapper {

	public Content toContent(ContentDto request){
		if (request == null){
			return null;
		}
		Content content = new Content();

		content.setId(request.getId());
		content.setTitle(request.getTitle());
		content.setBody(request.getBody());
		content.setContentType(request.getContentType());

		return content;
	}

	public ContentDto toContentDto(Content content){
		if (content == null){
			return null;
		}
		ContentDto contentDto = new ContentDto();

		contentDto.setId(content.getId());
		contentDto.setTitle(content.getTitle());
		contentDto.setBody(content.getBody());
		contentDto.setContentType(content.getContentType());

		return contentDto;
	}
}
