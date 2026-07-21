package com.eop.userservice.repository;

import com.eop.userservice.entity.UserPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPersonRepository extends JpaRepository<UserPerson, String> {

    UserPerson findByParentId(@Param("parentId") String parentId);

    boolean existsByNik(String nik);

}
