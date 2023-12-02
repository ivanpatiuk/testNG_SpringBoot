package org.ivanpatiuk;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public UserDTO findUserById(final Long id){
        return UserDTO.builder()
                .nickName("Nickname73")
                .email("email@gmail.com")
                .build();
    }
}
