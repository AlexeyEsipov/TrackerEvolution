package ru.job4j;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String model;

    private String owner;

    private Timestamp created;

    public static Car of(String model, Timestamp created, String owner) {
        Car car = new Car();
        car.model = model;
        car.created = created;
        car.owner = owner;
        return car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Car)) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id
                && Objects.equals(model, car.model)
                && Objects.equals(owner, car.owner)
                && Objects.equals(created, car.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, owner, created);
    }
}
