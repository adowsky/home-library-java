package com.adowsky.service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reading", schema = "HOMELIBRARY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private LibraryEntity book;
    @ManyToOne
    @JoinColumn(name = "reader_id")
    private UserEntity reader;
    private Timestamp startDate;
    private Timestamp endDate;
}
