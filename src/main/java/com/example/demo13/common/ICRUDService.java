package com.example.demo13.common;

import java.util.List;
import java.util.Optional;

public interface ICRUDService<E> {
    E save(E e);

    void delete(Long id);

    Optional<E> findById(Long id);

    List<E> findAll();
}
