package com.dungps.user.controllers;

import com.dungps.user.common.exception.UserException;
import com.dungps.user.dto.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface UserApi {
    @PostMapping
    @ApiOperation(value = "Create an user", nickname = "createAnUser")
    ResponseEntity<UserDto> create(CreateUserDto createUserDto) throws UserException;

    @GetMapping
    @ApiOperation(value = "Search user in database", nickname = "searchUser")
    ResponseEntity<BaseList<UserDto>> search(SearchUserDto searchUserDto);

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an user with ID", nickname = "getAnUserWithId")
    ResponseEntity<UserDto> get(@PathVariable("id") Long id) throws UserException;

    @GetMapping("/username/{username}")
    @ApiOperation(value = "Get an user with username", nickname = "getAnUserWithUsername")
    ResponseEntity<UserDto> get(@PathVariable("username") String username) throws UserException;

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an user with ID", nickname = "updateAnUserWithId")
    ResponseEntity<UserDto> update(@PathVariable("id") Long id, @RequestBody UpdateUserDto updateUserDto) throws UserException;

    @PutMapping("/username/{username}")
    @ApiOperation(value = "Update an user with username", nickname = "updateAnUserWithUsername")
    ResponseEntity<UserDto> update(@PathVariable("username") String username, @RequestBody UpdateUserDto updateUserDto) throws UserException;

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an user with ID", nickname = "deleteAnUserWithId")
    ResponseEntity delete(@PathVariable("id") Long id) throws UserException;

    @DeleteMapping("/username/{username}")
    @ApiOperation(value = "Delete an user with ID", nickname = "deleteAnUserWithId")
    ResponseEntity delete(@PathVariable("username") String username) throws UserException;
}
