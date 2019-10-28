package com.hof.cms.cmsuser.data;

import com.hof.cms.cmsuser.entity.CmsUserRole;
import com.hof.cms.writer.data.PersonalDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsUserDto {

    private Long id;
    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @NotNull
    private String password;
    private Set<CmsUserRole> roles;

    private PersonalDetailsDto personalDetails;

   /* public void userCreateRequestValidator() {
        Utility.notNullAndLessThan("First Name", this.getUsername(), 3, 50);
        Utility.notNullAndLessThan("Password", this.getPassword(), 8,50);
        // todo email check
        // Utility.validateMail(request.getEmail());
    }*/
}
