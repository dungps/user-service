package com.dungps.user.controllers.impl;

import com.dungps.user.common.exception.UserException;
import com.dungps.user.common.utils.ObjectMapperUtils;
import com.dungps.user.controllers.UserApi;
import com.dungps.user.dto.*;
import com.dungps.user.entity.UserEntity;
import com.dungps.user.repository.specification.UserSpecification;
import com.dungps.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController implements UserApi {
    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity<UserDto> create(CreateUserDto createUserDto) throws UserException {
        if (createUserDto.getUsername() == null || createUserDto.getUsername().isEmpty()) {
            throw new UserException("Username is missing.", HttpStatus.BAD_REQUEST);
        }

        if (createUserDto.getPassword() == null || createUserDto.getPassword().isEmpty()) {
            throw new UserException("Password is missing.", HttpStatus.BAD_REQUEST);
        }

        if (userService.usernameExsisted(createUserDto.getUsername())) {
            throw new UserException("Username already existed.", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = ObjectMapperUtils.map(createUserDto, UserEntity.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(createUserDto.getPassword()));

        userEntity = userService.save(userEntity);

        return ResponseEntity.ok(ObjectMapperUtils.map(userEntity, UserDto.class));
    }

    @Override
    public ResponseEntity<BaseList<UserDto>> search(SearchUserDto searchUserDto) {
        int currentPage = searchUserDto.getPage() == null || searchUserDto.getPage() <= 0 ? 1 : searchUserDto.getPage();
        int limit = searchUserDto.getLimit() == null || searchUserDto.getLimit() <= 0 ? 10 : searchUserDto.getLimit();

        Page<UserEntity> userEntityPage = userService.search(currentPage, limit, UserSpecification.getByCondition(searchUserDto));

        BaseList<UserDto> list = new BaseList<>();
        list.setData(ObjectMapperUtils.mapCollection(userEntityPage.getContent(), UserDto.class));
        list.setPagination(
                new BasePagination(
                        userEntityPage.getSize(),
                        userEntityPage.getNumber(),
                        userEntityPage.getTotalElements(),
                        userEntityPage.getTotalPages()
                )
        );

        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<UserDto> get(Long id) throws UserException {
        UserEntity userEntity = userService.getById(id);

        if (userEntity == null) {
            throw new UserException("User ID not found.", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(ObjectMapperUtils.map(userEntity, UserDto.class));
    }

    @Override
    public ResponseEntity<UserDto> get(String username) throws UserException {
        UserEntity userEntity = userService.getByUserName(username);

        if (userEntity == null) {
            throw new UserException("Username not found.", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(ObjectMapperUtils.map(userEntity, UserDto.class));
    }

    @Override
    public ResponseEntity<UserDto> update(Long id, UpdateUserDto updateUserDto) throws UserException {
        UserEntity userEntity = userService.getById(id);

        if (userEntity == null) {
            throw new UserException("User ID not found.", HttpStatus.BAD_REQUEST);
        }

        userEntity = update(userEntity, updateUserDto);

        return ResponseEntity.ok(ObjectMapperUtils.map(userEntity, UserDto.class));
    }

    @Override
    public ResponseEntity<UserDto> update(String username, UpdateUserDto updateUserDto) throws UserException {
        UserEntity userEntity = userService.getByUserName(username);

        if (userEntity == null) {
            throw new UserException("Username not found.", HttpStatus.BAD_REQUEST);
        }

        userEntity = update(userEntity, updateUserDto);

        return ResponseEntity.ok(ObjectMapperUtils.map(userEntity, UserDto.class));
    }

    @Override
    public ResponseEntity delete(Long id) throws UserException {
        UserEntity userEntity = userService.getById(id);

        if (userEntity == null) {
            throw new UserException("User ID not found.", HttpStatus.BAD_REQUEST);
        }

        userService.delete(userEntity);

        return ResponseEntity.ok(new ResponseDto("Deleted successfully."));
    }

    @Override
    public ResponseEntity delete(String username) throws UserException {
        UserEntity userEntity = userService.getByUserName(username);

        if (userEntity == null) {
            throw new UserException("Username not found.", HttpStatus.BAD_REQUEST);
        }

        userService.delete(userEntity);

        return ResponseEntity.ok(new ResponseDto("Deleted successfully."));
    }

    private UserEntity update(UserEntity userEntity, UpdateUserDto updateUserDto) {
        if (updateUserDto.getDisplayName() != null && !updateUserDto.getDisplayName().isEmpty()) {
            userEntity.setDisplayName(updateUserDto.getDisplayName());
        }

        if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().isEmpty()) {
            userEntity.setPassword(bCryptPasswordEncoder.encode(updateUserDto.getPassword()));
        }

        return userEntity;
    }
}
