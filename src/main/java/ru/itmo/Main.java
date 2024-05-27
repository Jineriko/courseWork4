package ru.itmo;

import ru.itmo.dao.*;

import java.time.OffsetDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Database");

        DataStorage dataStorage = new DataStorage();
        ClimberDao climberDao = new ClimberDao();
        MountainDao mountainDao = new MountainDao();
        GroupDao groupDao = new GroupDao();
        InfoDao infoDao = new InfoDao();
        ReserveDao reserveDao = new ReserveDao();

        climberDao.createTable();
        mountainDao.createTable();
        groupDao.createTable();
        infoDao.createTable();
        reserveDao.createTable();
        System.out.println("Таблицы созданы!");

        climberDao.insert(dataStorage.getClimbers());
        mountainDao.insert(dataStorage.getMountains());
        groupDao.insert(dataStorage.getGroup01(), dataStorage.getMountain01());
        groupDao.insert(dataStorage.getGroup02(), dataStorage.getMountain02());
        groupDao.insert(dataStorage.getGroup03(), dataStorage.getMountain03());
        groupDao.insert(dataStorage.getGroup04(), dataStorage.getMountain01());
        infoDao.insert(dataStorage.getGroup01(), dataStorage.getInfo01());
        infoDao.insert(dataStorage.getGroup02(), dataStorage.getInfo02());
        infoDao.insert(dataStorage.getGroup03(), dataStorage.getInfo03());
        infoDao.insert(dataStorage.getGroup04(), dataStorage.getInfo04());
        infoDao.insert(dataStorage.getGroup04(), dataStorage.getInfo05());
        infoDao.insert(dataStorage.getGroup03(), dataStorage.getInfo06());
        infoDao.insert(dataStorage.getGroup02(), dataStorage.getInfo07());
        infoDao.insert(dataStorage.getGroup01(), dataStorage.getInfo08());
        System.out.println("Все объекты добавлены");


        climberDao.getLastnameAndEmail().forEach(climber -> System.out.println(climber)); // первый select запрос
        infoDao.getId("Петрович", 6).forEach(integer -> System.out.println(integer)); // второй select запрос
        groupDao.getGroupsOpenForEntries().forEach(group -> System.out.println(group)); // третий select запрос
        infoDao.getInfoForPeriod(OffsetDateTime.parse("2019-01-10T15:30:00+01:00"), OffsetDateTime.parse("2023-01-10T15:30:00+01:00")).forEach(info -> System.out.println(info)); // четвертый select запрос
        mountainDao.getNameOfMountain(5).forEach(element -> System.out.println(element)); // пятый select запрос
    }
}
