package com.havryliuk.restaurant.db.entity;

import java.util.Date;

public class Manager extends User implements Entity{
    private Date birthDate;
    private String passport;
    private String bankAccount;


    public static Manager getInstance(long id, String email, String password,
                                      String name, String surname, Date creationDate,
                                      String gender, boolean isOverEighteen, Date birthDate,
                                      String passport, String bankAccount) {
        Manager manager = new Manager();
        manager.setId(id);
        manager.setEmail(email);
        manager.setPassword(password);
        manager.setName(name);
        manager.setSurname(surname);
        manager.setAccountCreationDate(creationDate);
        manager.setRole(Role.getInstance(UserRole.MANAGER));
        manager.setGender(gender);
        manager.setOverEighteen(isOverEighteen);
        manager.setBirthDate(birthDate);
        manager.setPassport(passport);
        manager.setBankAccount(bankAccount);
        return manager;
    }

    public static Manager getInstance(User user, Date birthDate, String passport, String bankAccount ) {
        Manager manager = new Manager();
        manager.setId(user.getId());
        manager.setEmail(user.getEmail());
        manager.setPassport(user.getPassword());
        manager.setName(user.getName());
        manager.setSurname(user.getSurname());
        manager.setAccountCreationDate(new Date(user.getAccountCreationDate().getTime()));
        manager.setRole(Role.getInstance(UserRole.MANAGER));
        manager.setGender(user.getGender());
        manager.setOverEighteen(user.isOverEighteen());
        manager.setBirthDate(birthDate);
        manager.setPassport(passport);
        manager.setBankAccount(bankAccount);
        return manager;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Manager manager = (Manager) o;

        if (birthDate != null ? !birthDate.equals(manager.birthDate) : manager.birthDate != null) return false;
        return passport != null ? passport.equals(manager.passport) : manager.passport == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        return result;
    }
}
