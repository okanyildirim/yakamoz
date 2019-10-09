package com.hof.cms.content;

import com.hof.cms.writer.Writer;
import com.hof.cms.writer.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cms/contents")
@RequiredArgsConstructor
public class ContentController {

	private final ContentService contentService;

	@PostMapping
	public ResponseEntity<Void> createContent(@RequestBody @Valid ContentDto contentCreateRequest){
		contentService.createContent(contentCreateRequest);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<ContentDto>> getContents(){
		List<ContentDto> contents = contentService.getContents();
		return ResponseEntity.ok(contents);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContentDto> getContent(@PathVariable Long id){
		ContentDto contentDto = contentService.getContent(id);
		return ResponseEntity.ok(contentDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateContent(@PathVariable Long id, @RequestBody @Valid ContentDto request){
		contentService.updateContent(id, request);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteContent(@PathVariable Long id){
		contentService.deleteContent(id);
		return ResponseEntity.ok().build();
	}

}
