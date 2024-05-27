package ru.itmo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Reserve {
    private long id;
    private Climber climber;
    private Group group;
    private OffsetDateTime nextAscentDate;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private boolean enable;

    public Reserve() {
    }

    public Reserve(Climber climber, Group group, OffsetDateTime nextAscentDate) {
        if (climber == null || group == null || nextAscentDate == null) {
            System.out.println("Некорректно указаны поля в резерве");
            return;
        }
        this.climber = climber;
        this.group = group;
        this.nextAscentDate = nextAscentDate;
        this.createdAt = LocalDateTime.now();
        this.updateAt = this.createdAt;
        this.enable = true;
    }
}