package com.havryliuk.restaurant.db.entity;

public class Category implements Entity {
    private Long id;
    private String name;

    public static Category getInstance(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }
    public static Category getInstance(long id, String name) {
        Category category = getInstance(name);
        category.setId(id);
        return category;
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

        Category category = (Category) o;

        return name != null ? name.equals(category.name) : category.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
