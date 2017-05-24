package com.adowsky.service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "borrows", schema = "HOMELIBRARY")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class BorrowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private LibraryEntity book;
    @ManyToOne
    @JoinColumn(name = "borrower")
    private UserEntity borrower;
    private Long owner;
    private Timestamp borrowDate;
    private Timestamp returnDate;
}
