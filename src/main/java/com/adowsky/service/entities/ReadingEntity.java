package com.adowsky.service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reading", schema = "HOMELIBRARY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long bookId;
    private Long readerId;
    private Date startDate;
    private Date endDate;
}
