package com.hof.cms.writer.data.mapper;

import com.hof.cms.writer.entity.Writer;
import com.hof.cms.writer.data.WriterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WriterMapper {
    private final PersonalDetailsMapper personalDetailsMapper;

    public Writer toWriter(WriterDto writerDto){

        if (writerDto == null){
            return null;
        }

        Writer writer= new Writer();
        writer.setId(writerDto.getId());
        writer.setPersonalDetails(personalDetailsMapper.toPersonalDetails(writerDto.getPersonalDetails()));

        return writer;
    }

    public WriterDto toWriterDto(Writer writer){

        if (writer == null){
            return null;
        }

        return WriterDto.builder()
                .id(writer.getId())
                .personalDetails(personalDetailsMapper.toPersonalDetailsDto(writer.getPersonalDetails()))
                .build();

    }
}
