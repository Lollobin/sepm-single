package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

import java.sql.Date;

public class SearchDto {
    private String name;
    private String description;
    private java.sql.Date dateOfBirth;
    private Sex sex;
    private String owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
