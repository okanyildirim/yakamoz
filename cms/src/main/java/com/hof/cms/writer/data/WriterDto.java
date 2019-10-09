package com.hof.cms.writer.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class WriterDto {

    private Long id;
    private PersonalDetailsDto personalDetails;
}
