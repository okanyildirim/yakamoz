package com.hof.cms.cmsuser.repository;

import com.hof.cms.cmsuser.entity.CmsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmsUserRepository extends JpaRepository<CmsUser,Long> {
    boolean existsByEmail(String email);
    CmsUser findByEmail(String email);
    CmsUser findByUsername(String username);
    boolean existsByUsername(String username);

}
