package com.hof.yakamozauth.data;

import com.hof.yakamozauth.common.Utility;
import com.hof.yakamozauth.entity.UserDetails;
import com.hof.yakamozauth.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private UserDetailsDto userDetails;
    private Set<UserRole> roles;

    public void userCreateRequestValidator() {
        Utility.notNullAndLessThan("First Name", this.getUsername(), 3, 50);
        Utility.notNullAndLessThan("Password", this.getPassword(), 8,50);
        // todo email check
        // Utility.validateMail(request.getEmail());
    }
}
