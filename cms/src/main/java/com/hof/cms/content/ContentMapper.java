package com.hof.cms.content;

import com.hof.cms.writer.data.mapper.WriterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentMapper {
    private final WriterMapper writerMapper;
    public Content toContent(ContentDto request) {
        if (request == null) {
            return null;
        }
        Content content = new Content();

        content.setId(request.getId());
        content.setTitle(request.getTitle());
        content.setBody(request.getBody());
        content.setContentType(request.getContentType());
        content.setWriter(writerMapper.toWriter(request.getWriter()));

        return content;
    }

    public ContentDto toContentDto(Content content) {
        if (content == null) {
            return null;
        }

        return ContentDto.builder()
                .id(content.getId())
                .body(content.getBody())
                .title(content.getTitle())
                .contentType(content.getContentType())
                .writer(writerMapper.toWriterDto(content.getWriter()))
                .build();
    }
}
