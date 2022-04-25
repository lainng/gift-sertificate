package com.piatnitsa.entity;

import java.util.Objects;

/**
 * This class represents tag entity.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class Tag {
    private long id;
    private String name;

    public Tag() {}

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return id == tag.id
                && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Tag{");
        result.append("id=").append(id);
        result.append(", name='").append(name).append('\'');
        result.append('}');
        return result.toString();
    }
}
