package com.demo.citizens_identification.dao;

import com.demo.citizens_identification.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserDao implements Dao<UserEntity> {

    //TODO need to be implemented

    @Override
    public Optional<UserEntity> getById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> getByHash(String hash) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> save(UserEntity userEntity) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> update(UserEntity userEntity) {
        return Optional.empty();
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void deleteByHash(String hash) {

    }
}
