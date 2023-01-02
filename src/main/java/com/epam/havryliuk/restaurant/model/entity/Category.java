package com.epam.havryliuk.restaurant.model.entity;

public enum Category implements Entity {
    COFFEE (1),// todo set such id in database
    LUNCH (2),
    DINER (3),
    DRINKS (4),
    SPECIALS (5),
    ALL (6);

    Category(final long id) {
        this.id = id;
    }

    private final long id;

    public long getId() {
        return id;
    }

    public static Category getCategory (long id) {

        return Category.values()[(int) id - 1];
    }
}


//public class Category implements Entity {
//    private Long id;
//    private CategoryName categoryName;
//
//    public static Category getInstance(CategoryName categoryName) {
//        Category category = new Category();
//        category.setCategoryName(categoryName);
//        return category;
//    }
//    public static Category getInstance(long id, CategoryName categoryName) {
//        Category category = getInstance(categoryName);
//        category.setId(id);
//        return category;
//    }
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//
//    public CategoryName getCategoryName() {
//        return categoryName;
//    }
//
//    public void setCategoryName(CategoryName categoryName) {
//        this.categoryName = categoryName;
//    }
//
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Category category = (Category) o;
//
//        return categoryName == category.categoryName;
//    }
//
//    @Override
//    public int hashCode() {
//        return categoryName != null ? categoryName.hashCode() : 0;
//    }
//
//    @Override
//    public String toString() {
//        return "Category{" +
//                "categoryName=" + categoryName.name() +
//                '}';
//    }
//}
