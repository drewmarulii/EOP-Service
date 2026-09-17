package com.eop.userservice.repository;

import com.eop.userservice.entity.UserFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFamilyRepository extends JpaRepository<UserFamily, String>, JpaSpecificationExecutor<UserFamily> {

    boolean existsByLeaderUserIdAndMemberUserId(String leaderUserId, String memberUserId);

    List<UserFamily> findByLeaderUserId(String leaderUserId);

    UserFamily findByMemberUserId(String memberUserId);

}
