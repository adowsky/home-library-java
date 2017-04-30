package com.adowsky.service.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "libraries")
@Data
@NoArgsConstructor
@Builder
public class LibraryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long libraryOwner;
    private String title;
    private String author;
    private boolean borrowed;
}
