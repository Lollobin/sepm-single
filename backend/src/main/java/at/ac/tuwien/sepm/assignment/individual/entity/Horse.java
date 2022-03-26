package at.ac.tuwien.sepm.assignment.individual.entity;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

import java.sql.Date;

/**
 * Class for horse entities
 * Contains all common properties
 */
public class Horse {
    private Long id;
    private String name;
    private String description;
    private java.sql.Date dateOfBirth;
    private Sex sex;
    private Owner owner;
    private Horse father;
    private Horse mother;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Horse getFather() {
        return father;
    }

    public void setFather(Horse father) {
        this.father = father;
    }

    public Horse getMother() {
        return mother;
    }

    public void setMother(Horse mother) {
        this.mother = mother;
    }
}
