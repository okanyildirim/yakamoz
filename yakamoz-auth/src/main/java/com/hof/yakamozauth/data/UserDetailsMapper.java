package com.hof.yakamozauth.data;

import com.hof.yakamozauth.entity.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {

	public UserDetails toUserDetails(UserDetailsDto userDetailsDto) {

		if (userDetailsDto == null){
			return null;
		}

		UserDetails userDetails = new UserDetails();

		userDetails.setId(userDetailsDto.getId());
		userDetails.setFirstName(userDetailsDto.getFirstName());
		userDetails.setLastName(userDetailsDto.getLastName());
		userDetails.setAge(userDetailsDto.getAge());
		userDetails.setGsmNo(userDetailsDto.getGsmNo());

		return userDetails;
	}

	public UserDetailsDto toUserDetailsDto(UserDetails userDetails) {

		if (userDetails == null){
			return null;
		}

		UserDetailsDto userDetailsDto = new UserDetailsDto();
		userDetailsDto.setId(userDetails.getId());
		userDetailsDto.setFirstName(userDetails.getFirstName());
		userDetailsDto.setLastName(userDetails.getLastName());
		userDetailsDto.setAge(userDetails.getAge());
		userDetailsDto.setGsmNo(userDetails.getGsmNo());

		return userDetailsDto;
	}
}
