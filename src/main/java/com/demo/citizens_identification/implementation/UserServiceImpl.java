package com.demo.citizens_identification.implementation;

import com.demo.citizens_identification.dao.UserDao;
import com.demo.citizens_identification.model.dto.UserDto;
import com.demo.citizens_identification.model.entity.UserEntity;
import com.demo.citizens_identification.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDto getUser(String hash) {
        Optional<UserEntity> user = userDao.getByHash(hash);
        return toDto(user.orElse(null));
    }

    @Override
    public UserDto crateUser(UserDto userDto) {
        Optional<UserEntity> createdUser = userDao.save(toEntity(userDto));
        return toDto(createdUser.orElse(null));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        Optional<UserEntity> user = userDao.update(toEntity(userDto));
        return toDto(user.orElse(null));
    }

    @Override
    public void deleteUserByHash(String hash) {
        userDao.deleteByHash(hash);
    }

    @Override
    public void deleteUserById(UUID id) {
        userDao.deleteById(id);
    }

    private UserDto toDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return UserDto.builder()
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .creditCards(userEntity.getCreditCards())
                .dateOfBirth(userEntity.getDateOfBirth())
                .emails(userEntity.getEmails())
                .phoneNumber(userEntity.getPhoneNumber())
                .additionalPhoneNumber(userEntity.getAdditionalPhoneNumber())
                .build();
    }


    private UserEntity toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return UserEntity.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .creditCards(userDto.getCreditCards())
                .dateOfBirth(userDto.getDateOfBirth())
                .emails(userDto.getEmails())
                .phoneNumber(userDto.getPhoneNumber())
                .additionalPhoneNumber(userDto.getAdditionalPhoneNumber())
                .build();
    }

}
