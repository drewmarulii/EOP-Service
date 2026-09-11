package com.eop.userservice.dummy.seeder;

import com.eop.baseservice.common.constant.Gender;
import com.eop.baseservice.common.constant.MaritalStatus;
import com.eop.baseservice.common.constant.UserRole;
import com.eop.baseservice.common.dto.user.request.CreateUserPersonRequest;
import com.eop.baseservice.common.dto.user.request.CreateUserRequest;
import com.eop.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UserDataSeeder implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // CREATE ADMIN - NO USER PROFILE
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("admin");
        request.setPassword("Password123");
        request.setRole(UserRole.ADMIN);
//        userService.create(request);

        // CREATE ADMIN - PROFILE [SECRETARY]
        CreateUserRequest request1 = new CreateUserRequest();
        request1.setUsername("2026-0001");
        request1.setPassword("Password123");
        request1.setRole(UserRole.ADMIN);

        CreateUserPersonRequest personRequest1 = new CreateUserPersonRequest();
        personRequest1.setFullName("Andrew Maruli");
        personRequest1.setPlaceOfBirth("Jakarta");
        personRequest1.setDateOfBirth(LocalDate.now().minusYears(25));
        personRequest1.setGender(Gender.MALE);
        personRequest1.setAddress("JL. CIMONE PERMAI VII NO 21");
        personRequest1.setMaritalStatus(MaritalStatus.SINGLE);
        personRequest1.setMarriedDate(null);
        personRequest1.setMobilePhone("081298375449");
        personRequest1.setEmail("drewmaruli@gmail.com");
        personRequest1.setIsFamilyLeader(false);
        personRequest1.setIsPassedAway(false);
        personRequest1.setProfilePicture(null);
        request1.setUserPersonRequest(personRequest1);
//        userService.create(request1);

        // CREATE TREASURER - PROFILE [TREASURER]
        CreateUserRequest request2 = new CreateUserRequest();
        request2.setUsername("2026-0002");
        request2.setPassword("Password123");
        request2.setRole(UserRole.TREASURER);

        CreateUserPersonRequest personRequest2 = new CreateUserPersonRequest();
        personRequest2.setFullName("Andrew Maruli");
        personRequest2.setPlaceOfBirth("Jakarta");
        personRequest2.setDateOfBirth(LocalDate.now().minusYears(25));
        personRequest2.setGender(Gender.MALE);
        personRequest2.setAddress("JL. CIMONE PERMAI VII NO 21");
        personRequest2.setMaritalStatus(MaritalStatus.SINGLE);
        personRequest2.setMarriedDate(null);
        personRequest2.setMobilePhone("081298375449");
        personRequest2.setEmail("drewmaruli@gmail.com");
        personRequest2.setIsFamilyLeader(false);
        personRequest2.setIsPassedAway(false);
        personRequest2.setProfilePicture(null);
        request2.setUserPersonRequest(personRequest2);
//        userService.create(request2);

        // CREATE MEMBER - PROFILE [MEMBER]
        CreateUserRequest request3 = new CreateUserRequest();
        request3.setUsername("2026-0003");
        request3.setPassword("Password123");
        request3.setRole(UserRole.MEMBER);

        CreateUserPersonRequest personRequest3 = new CreateUserPersonRequest();
        personRequest3.setFullName("Andrew Maruli");
        personRequest3.setPlaceOfBirth("Jakarta");
        personRequest3.setDateOfBirth(LocalDate.now().minusYears(25));
        personRequest3.setGender(Gender.MALE);
        personRequest3.setAddress("JL. CIMONE PERMAI VII NO 21");
        personRequest3.setMaritalStatus(MaritalStatus.SINGLE);
        personRequest3.setMarriedDate(null);
        personRequest3.setMobilePhone("081298375449");
        personRequest3.setEmail("drewmaruli@gmail.com");
        personRequest3.setIsFamilyLeader(false);
        personRequest3.setIsPassedAway(false);
        personRequest3.setProfilePicture(null);
        request3.setUserPersonRequest(personRequest3);
//        userService.create(request3);
    }
}
