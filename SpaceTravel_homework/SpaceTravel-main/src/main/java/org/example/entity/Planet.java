package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "planet")
public class Planet {
    @Id
    private String id;

    @Column(nullable = false, length = 500)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
