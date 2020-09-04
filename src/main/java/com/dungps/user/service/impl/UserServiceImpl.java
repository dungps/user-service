package com.dungps.user.service.impl;

import com.dungps.user.entity.UserEntity;
import com.dungps.user.repository.UserRepository;
import com.dungps.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserEntity getByUserName(String username) {
        return userRepository.findTopByUsername(username).orElse(null);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void delete(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }

    @Override
    public boolean usernameExsisted(String user) {
        return getByUserName(user) != null;
    }

    @Override
    public Page<UserEntity> search(int page, int limit, Specification<UserEntity> specification) {
        return userRepository.findAll(
                specification,
                PageRequest.of(page - 1 < 0 ? 0 : page - 1, limit, Sort.by("createdAt"))
        );
    }
}
