package ru.itmo;

import ru.itmo.model.*;

import java.time.*;
import java.util.List;

public class DataStorage { // класс для создания объектов
    private Climber climber01 = new Climber("Alex", "Scott", "7(3561)305-4940", "alex@gmail.ru", OffsetDateTime.parse("2023-01-10T15:30:00+01:00"));
    private Climber climber02 = new Climber("Elena", "Martin", "7(33)387-0409", "elena@gmail.ru", OffsetDateTime.parse("2022-02-09T15:30:00+01:00"));
    private Climber climber03 = new Climber("Misha", "Boyd", "7(29)147-2543", "misha@gmail.ru", OffsetDateTime.parse("2021-03-08T15:30:00+01:00"));
    private Climber climber04 = new Climber("Petr", "Jones", "7(502)208-4218", "petr@gmail.ru", OffsetDateTime.parse("2020-04-07T15:30:00+01:00"));
    private Climber climber05 = new Climber("Katya", "Kelly", "7(5665)751-9521", "katya@gmail.ru", OffsetDateTime.parse("2019-05-06T15:30:00+01:00"));
    private Climber climber06 = new Climber("Egor", "Estrada", "7(9344)097-3194", "egor@gmail.ru", OffsetDateTime.parse("2018-06-05T15:30:00+01:00"));
    private Climber climber07 = new Climber("Vlad", "Herrera", "7(92)014-4681", "vlad@gmail.ru", OffsetDateTime.parse("2019-07-04T15:30:00+01:00"));
    private Climber climber08 = new Climber("Anna", "Reed", "7(8819)582-2948", "anna@gmail.ru", OffsetDateTime.parse("2020-08-03T15:30:00+01:00"));
    private Climber climber09 = new Climber("Maria", "Goodwin", "7(308)055-9790", "maria@gmail.ru", OffsetDateTime.parse("2021-09-02T15:30:00+01:00"));
    private Climber climber10 = new Climber("Slava", "Gryzlik", "7(911)560-9190", "slava@gmail.ru", OffsetDateTime.parse("2023-10-01T15:30:00+01:00"));

    private List<String> everest = List.of("china", "nepal");
    private List<String> andes = List.of("colombia", "venezuela", "peru", "ecuador", "argentina", "chile", "bolivia");
    private List<String> kilimanjaro = List.of("tanzania");
    private Mountain mountain01 = new Mountain(1, "Everest", everest, 8849);
    private Mountain mountain02 = new Mountain(2, "Andes", andes, 6961);
    private Mountain mountain03 = new Mountain(3, "Kilimanjaro", kilimanjaro, 5895);

    private Group group01 = new Group(1, mountain01, "Петрович", OffsetDateTime.parse("2025-01-10T15:30:00+01:00"), 10, 100_000);
    private Group group02 = new Group(2, mountain02, "Василич", OffsetDateTime.parse("2026-06-05T15:30:00+01:00"), 10, 250_000);
    private Group group03 = new Group(3, mountain03, "Михалыч", OffsetDateTime.parse("2024-12-11T15:30:00+01:00"), 10, 400_000);
    private Group group04 = new Group(4, mountain01, "Вячеслав Алексеич", OffsetDateTime.parse("2028-09-28T15:30:00+01:00"), 10, 50_000);

    private Reserve reserve = new Reserve();

    private Info info01 = new Info(OffsetDateTime.parse("2023-01-10T15:30:00+01:00"), OffsetDateTime.parse("2023-01-22T15:30:00+01:00"), (int) (Math.random() * 10), group01);
    private Info info02 = new Info(OffsetDateTime.parse("2022-02-09T15:30:00+01:00"), OffsetDateTime.parse("2023-02-09T15:30:00+01:00"), (int) (Math.random() * 10), group02);
    private Info info03 = new Info(OffsetDateTime.parse("2019-07-04T15:30:00+01:00"), OffsetDateTime.parse("2019-07-06T15:30:00+01:00"), (int) (Math.random() * 10), group03);
    private Info info04 = new Info(OffsetDateTime.parse("2020-08-03T15:30:00+01:00"), OffsetDateTime.parse("2020-09-03T15:30:00+01:00"), (int) (Math.random() * 10), group04);
    private Info info05 = new Info(OffsetDateTime.parse("2019-12-10T15:30:00+01:00"), OffsetDateTime.parse("2020-01-01T15:30:00+01:00"), (int) (Math.random() * 10), group04);
    private Info info06 = new Info(OffsetDateTime.parse("2024-03-08T15:30:00+01:00"), OffsetDateTime.parse("2024-03-14T15:30:00+01:00"), (int) (Math.random() * 10), group03);
    private Info info07 = new Info(OffsetDateTime.parse("2023-07-25T15:30:00+01:00"), OffsetDateTime.parse("2023-07-30T15:30:00+01:00"), (int) (Math.random() * 10), group02);
    private Info info08 = new Info(OffsetDateTime.parse("2022-06-14T15:30:00+01:00"), OffsetDateTime.parse("2022-07-14T15:30:00+01:00"), (int) (Math.random() * 10), group01);

    public List<Climber> getClimbers() { // создание альпинистов
        List<Climber> climbers = List.of(climber01, climber02, climber03, climber04, climber05, climber06, climber07, climber08, climber09, climber10);
        return climbers;
    }

    public List<Mountain> getMountains() { // создание гор
        List<Mountain> mountains = List.of(mountain01, mountain02, mountain03);
        return mountains;
    }

    public Mountain getMountain01() {
        return mountain01;
    }

    public Mountain getMountain02() {
        return mountain02;
    }

    public Mountain getMountain03() {
        return mountain03;
    }

    public Group getGroup01() {
        return group01;
    }

    public Group getGroup02() {
        return group02;
    }

    public Group getGroup03() {
        return group03;
    }

    public Group getGroup04() {
        return group04;
    }

    public Info getInfo01() {
        return info01;
    }

    public Info getInfo02() {
        return info02;
    }

    public Info getInfo03() {
        return info03;
    }

    public Info getInfo04() {
        return info04;
    }

    public Info getInfo05() {
        return info05;
    }

    public Info getInfo06() {
        return info06;
    }

    public Info getInfo07() {
        return info07;
    }

    public Info getInfo08() {
        return info08;
    }
}
