package com.example.appspringhomework251.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Turnstile {
    @Id
    @GeneratedValue
    private UUID id;

    private Timestamp income;

    private Timestamp outcome;

    private boolean completed;

    @ManyToOne
    private User user;
}
