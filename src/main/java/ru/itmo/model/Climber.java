package ru.itmo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Setter
@Getter
public class Climber {
    private long id;
    private String name;
    private String lastname;
    private String phone;
    private String email;
    private OffsetDateTime dateOfLastAscent;
    private OffsetDateTime createdAt;
    private OffsetDateTime updateAt;
    private boolean enable;

    public Climber() {
    }

    public Climber(String name, String lastname, String phone, String email, OffsetDateTime dateOfLastAscent) {
        if (name == null || lastname == null || phone == null || email == null) {
            System.out.println("Создание альпиниста невозможно с null полями");
            return;
        }
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.createdAt = OffsetDateTime.now();
        this.updateAt = this.createdAt;
        this.enable = true;
        this.dateOfLastAscent = dateOfLastAscent;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + lastname + " " + phone + " " + email + " " + dateOfLastAscent;
    }
}