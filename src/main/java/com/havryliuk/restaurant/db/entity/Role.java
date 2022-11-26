package com.havryliuk.restaurant.db.entity;


public class Role implements Entity{
    private Long id;
    private String name;

    public static Role getInstance(String name) {
        Role role = new Role();
        role.setName(name);
        return role;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return name != null ? name.equals(role.name) : role.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
