package org.example.dto;

public class ProjectPrice {
    private String name;
    private long price;

    public ProjectPrice(String name, long price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProjectPrice{id='" + name + "', price=" + price + "}";
    }
}
