package com.amazon.mas.kiwi.util;

public class DeveloperInfo {

    private String ID;
    private String name;

    public DeveloperInfo() {
        ID = "";
        name = "";
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public boolean isValid() {
        return ID.length() != 0 && name.length() != 0;
    }

    public void setID(String s) {
        ID = s;
    }

    public void setName(String s) {
        name = s;
    }

    public String toString() {
        String s = (new StringBuilder()).append("ID: ").append(ID).append(", ")
                .toString();
        return (new StringBuilder()).append(s).append("name: ").append(name)
                .toString();
    }
}
