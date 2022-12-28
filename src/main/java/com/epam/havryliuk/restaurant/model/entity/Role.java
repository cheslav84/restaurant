package com.epam.havryliuk.restaurant.model.entity;


//import com.epam.havryliuk.restaurant.model.entity.constants.UserRole;


public enum Role implements Entity {
    MANAGER (1),// todo set such id in database
    CLIENT (2);

    Role(final long id) {
        this.id = id;
    }

    private final long id;

    public long getId() {
        return id;
    }

    public static Role getRole (long id) {
        return Role.values()[(int) id - 1];
    }
}



//public class Role implements Entity{
//
//    private long id;
//    private UserRole userRole;
//
//    public static Role getInstance(UserRole userRole) {
//        Role role = new Role();
//        role.setUserRole(userRole);
//        return role;
//    }
//
//    public static Role getInstance(long id, UserRole userRole) {
//        Role role = getInstance(userRole);
//        role.setId(id);
//        return role;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public UserRole getUserRole() {
//        return userRole;
//    }
//
//    public void setUserRole(UserRole userRole) {
//        this.userRole = userRole;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Role role = (Role) o;
//
//        return userRole != null ? userRole.equals(role.userRole) : role.userRole == null;
//    }
//
//    @Override
//    public int hashCode() {
//        return userRole != null ? userRole.hashCode() : 0;
//    }
//
//    @Override
//    public String toString() {
//        return getUserRole().name();
//    }
//}
