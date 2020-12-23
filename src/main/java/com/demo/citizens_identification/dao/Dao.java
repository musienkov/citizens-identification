package com.demo.citizens_identification.dao;

import java.util.Optional;
import java.util.UUID;

public interface Dao<T> {

    Optional<T> getById(UUID id);

    Optional<T> getByHash(String hash);

    Optional<T> save(T t);

    Optional<T> update(T t);

    void deleteById(UUID id);

    void deleteByHash(String hash);

}
