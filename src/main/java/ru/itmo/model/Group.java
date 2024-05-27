package ru.itmo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class Group {
    private long id;
    private Mountain mountain;
    private String managersName;
    private OffsetDateTime nextAscentDate;
    private int countOfClimbers;
    private int maxCountClimbers;
    private int costOfParticipation;
    private OffsetDateTime createdAt;
    private OffsetDateTime updateAt;
    private boolean enable;

    public Group() {
    }

    public Group(long id, Mountain mountain, String managersName, OffsetDateTime nextAscentDate, int maxCountClimbers, int costOfParticipation) {
        if (id < 0 || mountain == null || maxCountClimbers < 1 || costOfParticipation < 1000) {
            System.out.println("Некорректно указаны поля группы");
            return;
        }
        this.id = id;
        this.mountain = mountain;
        this.managersName = managersName;
        this.nextAscentDate = nextAscentDate;
        countOfClimbers = (int) (Math.random() * 10);
        this.maxCountClimbers = maxCountClimbers;
        this.costOfParticipation = costOfParticipation;
        this.createdAt = OffsetDateTime.now();
        this.updateAt = this.createdAt;
        this.enable = true;
    }

    @Override
    public String toString() {
        return id + " " + managersName + " " + nextAscentDate;
    }
}