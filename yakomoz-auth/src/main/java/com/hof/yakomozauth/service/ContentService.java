package com.hof.yakomozauth.service;

import com.hof.yakomozauth.data.ContentCreateRequest;
import com.hof.yakomozauth.data.ContentDto;
import com.hof.yakomozauth.data.ContentMapper;
import com.hof.yakomozauth.entity.Content;
import com.hof.yakomozauth.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;

    public ContentDto createContent(ContentCreateRequest request) {
        request.validate();
        Content content = new Content();
        content.setName(request.getName());
        contentRepository.save(content);

        return contentMapper.toContentDto(content);
    }

    public List<ContentDto> getAllContents() {
        List<Content> contents = contentRepository.findAll();
        return contents.stream().map(contentMapper::toContentDto).collect(Collectors.toList());
    }
}
