package ru.itmo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Setter
@Getter
public class Info {
    private long id;
    private OffsetDateTime beginningOfTheAscent;
    private OffsetDateTime endingOfTheAscent;
    private int countOfConqueredTheMountain;
    private Group group;
    private OffsetDateTime createdAt;
    private OffsetDateTime updateAt;
    private boolean enable;

    public Info() {
    }

    public Info(OffsetDateTime beginningOfTheAscent, OffsetDateTime endingOfTheAscent, int countOfConqueredTheMountain, Group group) {
        if (beginningOfTheAscent.isAfter(endingOfTheAscent)) {
            System.out.println("Некорректно указаны даты");
            return;
        }
        if (countOfConqueredTheMountain < 0 || group == null) {
            System.out.println("Некорректно указаны поля в классе инфо");
            return;
        }
        this.beginningOfTheAscent = beginningOfTheAscent;
        this.endingOfTheAscent = endingOfTheAscent;
        this.countOfConqueredTheMountain = countOfConqueredTheMountain;
        this.group = group;
        this.createdAt = OffsetDateTime.now();
        this.updateAt = this.createdAt;
        this.enable = true;
    }
}