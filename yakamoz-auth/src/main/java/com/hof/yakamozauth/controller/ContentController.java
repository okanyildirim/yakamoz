package com.hof.yakamozauth.controller;

import com.hof.yakamozauth.data.ContentCreateRequest;
import com.hof.yakamozauth.data.ContentDto;
import com.hof.yakamozauth.service.ContentService;
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
