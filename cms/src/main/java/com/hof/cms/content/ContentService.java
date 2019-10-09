package com.hof.cms.content;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentService {
	private final ContentRepository contentRepository;
	private final ContentMapper contentMapper;

	public void createContent(ContentDto request) {
		Content content = contentMapper.toContent(request);
		contentRepository.save(content);
	}

	public List<ContentDto> getContents() {
		List<ContentDto> contentDtoList = contentRepository.findAll().stream()
				.map(contentMapper::toContentDto).collect(Collectors.toList());
		return contentDtoList;
	}

	public ContentDto getContent(Long id) {
		Content content = findContentById(id);
		return contentMapper.toContentDto(content);
	}

	public void updateContent(Long id, ContentDto request) {
		Content content = findContentById(id);
		content.setTitle(request.getTitle());
		content.setBody(request.getBody());
		content.setContentType(request.getContentType());
		contentRepository.save(content);
	}

	private Content findContentById(Long id) {
		return contentRepository.findById(id).orElseThrow(()-> new RuntimeException("Content is not found"));
	}

	public void deleteContent(Long id) {
		Content content = findContentById(id);
			contentRepository.deleteById(id);
	}
}
