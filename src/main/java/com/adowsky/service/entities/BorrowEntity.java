package com.adowsky.service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "borrows")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class BorrowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long bookId;
    private Long borrower;
    private boolean returned;
}
