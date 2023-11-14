package com.training.OnlineTraining.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "equipment_needed")
    private String equipmentNeeded;

    @Column(name = "difficulty_level")
    private String difficultyLevel;
}
