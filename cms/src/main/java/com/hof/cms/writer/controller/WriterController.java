package com.hof.cms.writer.controller;

import com.hof.cms.writer.data.WriterDto;
import com.hof.cms.writer.service.WriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/writers")
@RequiredArgsConstructor
public class WriterController {

    private final WriterService writerService;

    public ResponseEntity<Void> createWriter(@RequestBody WriterDto request){
        writerService.createWriter(request);
        return ResponseEntity.ok().build();
    }

}
