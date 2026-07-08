package com.eop.userservice.dummy.seeder;

import com.eop.baseservice.common.constant.MaritalStatus;
import com.eop.baseservice.common.constant.UserRole;
import com.eop.baseservice.common.constant.UserStatus;
import com.eop.userservice.dummy.factory.UserDummyFactory;
import com.eop.userservice.entity.User;
import com.eop.userservice.entity.UserPerson;
import com.eop.userservice.repository.UserPersonRepository;
import com.eop.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserPersonRepository userPersonRepository;
    private final UserDummyFactory userDummyFactory;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() > 0){
            return;
        }

        // CREATE USER 1
        User user1 = userDummyFactory.create(
            "202600001",
            UserRole.ADMIN,
            UserStatus.ACTIVE
        );

        UserPerson userPerson1 = userDummyFactory.createPerson(user1, "36710000001", "Jhon", MaritalStatus.MARRIED, null);

        if (!userRepository.existsByUid(user1.getUid())) {
            userRepository.save(user1);
            userPersonRepository.save(userPerson1);
        }

        // CREATE USER 2
        User user2 = userDummyFactory.create(
            "202600002",
            UserRole.MEMBER,
            UserStatus.ACTIVE
        );

        UserPerson userPerson2 = userDummyFactory.createPerson(user2, "36710000002", "Marine", MaritalStatus.MARRIED, null);

        if (!userRepository.existsByUid(user2.getUid())) {
            userRepository.save(user2);
            userPersonRepository.save(userPerson2);
        }

        // CREATE USER 3
        User user3 = userDummyFactory.create(
                "202600003",
                UserRole.MEMBER,
                UserStatus.ACTIVE
        );

        UserPerson userPerson3 = userDummyFactory.createPerson(user3, "36710000003", "Gilbert", MaritalStatus.MARRIED, null);

        if (!userRepository.existsByUid(user3.getUid())) {
            userRepository.save(user3);
            userPersonRepository.save(userPerson3);
        }

        // CREATE USER 4
        User user4 = userDummyFactory.create(
                "202600004",
                UserRole.TREASURER,
                UserStatus.ACTIVE
        );

        UserPerson userPerson4 = userDummyFactory.createPerson(user4, "36710000004", "Hanna", MaritalStatus.MARRIED, null);

        if (!userRepository.existsByUid(user4.getUid())) {
            userRepository.save(user4);
            userPersonRepository.save(userPerson4);
        }
    }
}
