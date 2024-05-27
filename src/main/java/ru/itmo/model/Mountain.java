package ru.itmo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Mountain {

    private long id;
    private String name;
    private List<String> countries = new ArrayList<>();
    private int height;
    private OffsetDateTime createdAt;
    private OffsetDateTime updateAt;
    private boolean enable;

    public Mountain() {
    }

    public Mountain(long id, String name, List<String> countries, int height) {
        if (id < 0 || name == null || countries == null || height < 10) {
            System.out.println("Некорректно указаны поля горы");
            return;
        }
        this.id = id;
        this.name = name;
        this.countries = countries;
        this.height = height;
        this.createdAt = OffsetDateTime.now();
        this.updateAt = this.createdAt;
        this.enable = true;
    }
}