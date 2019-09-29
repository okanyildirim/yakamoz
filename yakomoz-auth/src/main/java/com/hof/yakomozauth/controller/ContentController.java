package com.hof.yakomozauth.controller;

import com.hof.yakomozauth.data.ContentCreateRequest;
import com.hof.yakomozauth.data.ContentDto;
import com.hof.yakomozauth.entity.Content;
import com.hof.yakomozauth.service.ContentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("content")
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @PostMapping
    public ResponseEntity createContent(@RequestBody ContentCreateRequest request){
        ContentDto contentDto = contentService.createContent(request);
        return ResponseEntity.ok(contentDto);
    }

    @GetMapping
    public ResponseEntity<List<ContentDto>> getContent(){
        return ResponseEntity.ok(contentService.getAllContents());
    }
}
