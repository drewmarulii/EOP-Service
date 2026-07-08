package com.eop.userservice.repository;

import com.eop.userservice.entity.UserPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPersonRepository extends JpaRepository<UserPerson, String> {
}
