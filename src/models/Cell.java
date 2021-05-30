package models;

import common.Identity;

public class Cell extends Identity {
    private String name;
    private int wingId;

    public Cell() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWingId() {
        return wingId;
    }

    public void setWingId(int wingId) {
        this.wingId = wingId;
    }

    @Override
    public String toString() {
        return name;
    }
}
