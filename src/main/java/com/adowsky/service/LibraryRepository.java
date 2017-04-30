package com.adowsky.service;

import com.adowsky.service.entities.LibraryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends CrudRepository<LibraryEntity, Long> {
    List<LibraryEntity> getAllByLibraryOwner(Long ownerId);
}
