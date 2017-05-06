package com.adowsky.service;

import com.adowsky.service.entities.LibraryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface LibraryRepository extends CrudRepository<LibraryEntity, Long> {
    List<LibraryEntity> getAllByLibraryOwner(Long ownerId);
    List<LibraryEntity> getAllByAuthorContains(String author);
    List<LibraryEntity> getAllByTitleContains(String title);
    List<LibraryEntity> getAllByTitleContainsAndAuthorContains(String title, String author);
}
