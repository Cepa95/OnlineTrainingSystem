package com.training.OnlineTraining.model;

import com.training.OnlineTraining.converter.ExerciseEquipmentConverter;
import com.training.OnlineTraining.converter.WorkoutStatusConverter;
import com.training.OnlineTraining.model.additional.Duration;
import com.training.OnlineTraining.model.enums.ExerciseDifficultyLevel;
import com.training.OnlineTraining.model.enums.WorkoutStatus;
import lombok.*;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private Contract contract;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<WorkoutSession> workoutSessions = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_workout")
    private Date dateOfWorkout;

    @Column(name = "ordinal_number_of_workout")
    private Integer ordinalNumberOfWorkout = 0;

    @Column(name = "number_of_exercises")
    private Integer numberOfExercises = 0;

    @Column(name = "warming_up_time_in_seconds")
    private Integer warmingUpTimeInSeconds = 0;

    @Column(name = "number_of_sets")
    private Integer numberOfSets = 0;

    @Column(name = "pause_between_sets_in_seconds")
    private Integer pauseBetweenSetsInSeconds = 0;

    @Column(name = "self_rating")
    private Integer selfRating;

    @Column(name = "workout_status")
    @Convert(converter = WorkoutStatusConverter.class)
    private WorkoutStatus workoutStatus = WorkoutStatus.WAITING;

    public Duration getDuration() {
        Duration newDuration = new Duration();

        newDuration.add(warmingUpTimeInSeconds);
        newDuration.add((numberOfSets - 1) * pauseBetweenSetsInSeconds);

        for(WorkoutSession workoutSession : workoutSessions)
            newDuration.add(workoutSession.getDuration().getDurationInSeconds() * numberOfSets);

        return newDuration;
    }

}
