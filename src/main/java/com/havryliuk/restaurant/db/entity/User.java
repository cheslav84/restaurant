package com.havryliuk.restaurant.db.entity;

import java.util.Date;

public class User implements Entity{
    private long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Date creationDate;
    private Role role;
    private String gender;
    private boolean isOverEighteen;

    public static User getInstance(String email, String password, String name, String surname) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        return user;
    }

    public static User getInstance(String email, String password, String name, String surname, String gender, boolean isOverEighteen) {
        User user = getInstance(email, password, name, surname);
        user.setGender(gender);
        user.setOverEighteen(isOverEighteen);
        user.setRole(Role.getInstance(UserRole.CLIENT));
        return user;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isOverEighteen() {
        return isOverEighteen;
    }

    public void setOverEighteen(boolean overEighteen) {
        isOverEighteen = overEighteen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || this.hashCode() != o.hashCode()){
            return false;
        }
        User user = (User) o;

        if (isOverEighteen != user.isOverEighteen) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        return gender != null ? gender.equals(user.gender) : user.gender == null;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (isOverEighteen ? 1 : 0);
        return result;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", isOverEighteen=" + isOverEighteen +
                '}';
    }
}
