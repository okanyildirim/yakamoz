package com.hof.cms.writer.service;

import com.hof.cms.repository.WriterRepository;
import com.hof.cms.writer.entity.Writer;
import com.hof.cms.writer.data.WriterDto;
import com.hof.cms.writer.data.mapper.WriterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WriterService {

    private final WriterRepository writerRepository;
    private final WriterMapper writerMapper;

    public void createWriter(WriterDto request) {
        Writer writer = writerMapper.toWriter(request);
        writerRepository.save(writer);
    }

    private Writer findContetnById(Long id){
        return writerRepository.findById(id).orElseThrow(()-> new RuntimeException("Writer is not found"));
    }
}
