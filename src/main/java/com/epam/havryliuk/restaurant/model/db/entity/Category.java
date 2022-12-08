package com.epam.havryliuk.restaurant.model.db.entity;

import com.epam.havryliuk.restaurant.model.db.entity.constants.CategoryName;

public class Category implements Entity {
    private Long id;
    private CategoryName categoryName;

    public static Category getInstance(CategoryName categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        return category;
    }
    public static Category getInstance(long id, CategoryName categoryName) {
        Category category = getInstance(categoryName);
        category.setId(id);
        return category;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return categoryName == category.categoryName;
    }

    @Override
    public int hashCode() {
        return categoryName != null ? categoryName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName=" + categoryName.name() +
                '}';
    }
}
