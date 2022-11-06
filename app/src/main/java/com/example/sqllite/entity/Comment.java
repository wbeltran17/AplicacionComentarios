package com.example.sqllite.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Comment {

    public int id;

    public String name;

    public String comment;

    public Comment(int id, String name, String comment) {
        this.id = id;
        this.name = name;
        this.comment = comment;
    }

    public Comment() {
    }

    @Override
    public String toString() {
        return name;
    }
}
