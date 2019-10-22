package com.hof.cms.cmsuser.data;

import com.hof.cms.cmsuser.entity.CmsUser;
import com.hof.cms.writer.data.mapper.PersonalDetailsMapper;
import com.hof.yakamozauth.data.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CmsUserMapper {

    private final PersonalDetailsMapper personalDetailsMapper;
    public CmsUser toUser(CmsUserDto userDto) {

        if (userDto == null) {
            return null;
        }
        CmsUser cmsUser = new CmsUser();
        cmsUser.setId(userDto.getId());
        cmsUser.setEmail(userDto.getEmail());
        cmsUser.setUsername(userDto.getUsername());
        cmsUser.setPassword(userDto.getPassword());
        cmsUser.setRoles(userDto.getRoles());
        cmsUser.setPersonalDetails(personalDetailsMapper.toPersonalDetails(userDto.getPersonalDetails()));
        return cmsUser;
    }


    public CmsUserDto toUserDto(CmsUser cmsUser) {
        if (cmsUser == null) {
            return null;
        }
        return CmsUserDto.builder()
                .id(cmsUser.getId())
                .email(cmsUser.getEmail())
                .username(cmsUser.getUsername())
                .roles(cmsUser.getRoles())
                .password(cmsUser.getPassword())
                .personalDetails(personalDetailsMapper.toPersonalDetailsDto(cmsUser.getPersonalDetails()))
                .build();

    }
    //userDto.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
}
