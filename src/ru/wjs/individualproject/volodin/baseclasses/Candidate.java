package ru.wjs.individualproject.volodin.baseclasses;

public class Candidate {
    private int id;
    private final String surname;
    private final String name;
    private final String patronymic;
    private int age;
    private final String party;

    public Candidate(String surname, String name, String patronymic, int age, String party) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.age = age;
        this.party = party;
    }

    public Candidate(int id, String surname, String name, String patronymic, int age, String party) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.age = age;
        this.party = party;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public int getAge() {
        return age;
    }

    public String getParty() {
        return party;
    }

    @Override
    public String toString() {
        return String.format("Кандидат №%d: %s %s %s, возраст: %d, партия: %s. ", id, surname, name, patronymic, age, party);
    }
}
