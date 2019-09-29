package com.hof.yakamozauth.data;

import com.hof.yakamozauth.entity.Content;
import org.springframework.stereotype.Component;

@Component
public class ContentMapper {

    public ContentDto toContentDto(Content content){
        if (content == null){
            return null;
        }
        ContentDto contentDto = new ContentDto();
        contentDto.setId(content.getId());
        contentDto.setName(content.getName());
        contentDto.setState(content.getState());
        return contentDto;
    }
}
