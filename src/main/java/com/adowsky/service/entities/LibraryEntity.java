package com.adowsky.service.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "libraries", schema = "HOMELIBRARY")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LibraryEntity {
    public LibraryEntity(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long libraryOwner;
    private String title;
    private String author;
    private boolean borrowed;
}
