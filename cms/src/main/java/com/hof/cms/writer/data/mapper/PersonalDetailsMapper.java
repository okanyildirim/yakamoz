package com.hof.cms.writer.data.mapper;

import com.hof.cms.writer.entity.PersonalDetails;
import com.hof.cms.writer.data.PersonalDetailsDto;

public class PersonalDetailsMapper {

    public PersonalDetails toPersonalDetails(PersonalDetailsDto personalDetailsDto) {

        if (personalDetailsDto == null){
            return null;
        }
        PersonalDetails personalDetails = new PersonalDetails();

        personalDetails.setId(personalDetailsDto.getId());
        personalDetails.setAge(personalDetailsDto.getAge());
        personalDetails.setFirstName(personalDetailsDto.getFirstName());
        personalDetails.setLastName(personalDetailsDto.getLastName());

        return personalDetails;
    }

    public PersonalDetailsDto toPersonalDetailsDto(PersonalDetails personalDetails) {

        if (personalDetails == null){
            return null;
        }

        return PersonalDetailsDto.builder()
            .id(personalDetails.getId())
            .firstName(personalDetails.getFirstName())
            .lastName(personalDetails.getLastName())
            .age(personalDetails.getAge()).build();
    }
}
