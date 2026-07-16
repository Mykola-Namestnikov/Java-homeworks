package org.example.dto;

public class LongestProject {
    private String name; // В SQL у тебе: id AS name
    private int monthCount;

    public LongestProject(String name, int monthCount) {
        this.name = name;
        this.monthCount = monthCount;
    }

    @Override
    public String toString() {
        return "LongestProject{id='" + name + "', monthCount=" + monthCount + "}";
    }
}
