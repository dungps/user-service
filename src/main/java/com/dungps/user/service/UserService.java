package com.dungps.user.service;

import com.dungps.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {
    UserEntity save(UserEntity userEntity);

    UserEntity getById(Long id);

    UserEntity getByUserName(String username);

    void delete(Long id);

    void delete(UserEntity userEntity);

    boolean usernameExsisted(String username);

    Page<UserEntity> search(int page, int limit, Specification<UserEntity> specification);
}
