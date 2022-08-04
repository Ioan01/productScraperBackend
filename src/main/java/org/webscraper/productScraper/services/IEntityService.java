package org.webscraper.productScraper.services;

import java.util.Optional;

public interface IEntityService<T> {

    T save(T entity);

    void delete(String name);

    Optional<T> getByName(String name);

    Optional<T> getByNameLike(String name);

    T getElseCreate(String name);

    T getLikeElseCreate(String name);

}
