package com.havryliuk.restaurant.db.entity;


public class Role implements Entity{
    public enum UserRole {CLIENT, MANAGER}
    private Long id;
    private UserRole userRole;

    public static Role getInstance(UserRole userRole) {
        Role role = new Role();
        role.setUserRole(userRole);
        return role;
    }

    public static Role getInstance(Long id, UserRole userRole) {
        Role role = getInstance(userRole);
        role.setId(id);
        return role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return userRole != null ? userRole.equals(role.userRole) : role.userRole == null;
    }

    @Override
    public int hashCode() {
        return userRole != null ? userRole.hashCode() : 0;
    }
}
