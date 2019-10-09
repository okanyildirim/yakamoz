package com.hof.cms.writer.data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalDetailsDto {

    private Long id;
    @NotNull
    private String firstName;
    private String lastName;
    private Integer age;

}
