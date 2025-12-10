package com.cookbook.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Chef entity mapped to chefs table.
 */
public class Chef implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer chefId;
    private String name;

    public Chef() {}

    public Chef(Integer chefId, String name) {
        this.chefId = chefId;
        this.name = name;
    }

    public Chef(String name) {
        this(null, name);
    }

    public Integer getChefId() {
        return chefId;
    }

    public void setChefId(Integer chefId) {
        this.chefId = chefId;
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

        Chef chef = (Chef) o;
        return Objects.equals(chefId, chef.chefId) &&
               Objects.equals(name, chef.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chefId, name);
    }

    @Override
    public String toString() {
        return "Chef{" +
               "chefId=" + chefId +
               ", name='" + name + '\'' +
               '}';
    }
}
