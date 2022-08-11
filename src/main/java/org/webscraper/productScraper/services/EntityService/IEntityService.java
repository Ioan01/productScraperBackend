package org.webscraper.productScraper.services.EntityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


public interface IEntityService<T,ID2> {

    @Async
    CompletableFuture<T> save(T entity);

    @Async
    CompletableFuture<ArrayList<T>> getByName(ID2 name, Pageable pageable);

    @Async
    CompletableFuture<ArrayList<T>>getAll(ID2 name,Pageable pageable);

    void delete(ID2 name);

}
