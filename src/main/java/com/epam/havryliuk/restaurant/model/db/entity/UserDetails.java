package com.epam.havryliuk.restaurant.model.db.entity;

import java.util.Date;

public class UserDetails{
    private Date birthDate;
    private String passport;
    private String bankAccount;



    public static UserDetails getInstance(Date birthDate, String passport, String bankAccount ) {
        UserDetails userDetails = new UserDetails();
        userDetails.setBirthDate(birthDate);
        userDetails.setPassport(passport);
        userDetails.setBankAccount(bankAccount);
        return userDetails;
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

        UserDetails that = (UserDetails) o;

        if (birthDate != null ? !birthDate.equals(that.birthDate) : that.birthDate != null) return false;
        if (passport != null ? !passport.equals(that.passport) : that.passport != null) return false;
        return bankAccount != null ? bankAccount.equals(that.bankAccount) : that.bankAccount == null;
    }

    @Override
    public int hashCode() {
        int result = birthDate != null ? birthDate.hashCode() : 0;
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        result = 31 * result + (bankAccount != null ? bankAccount.hashCode() : 0);
        return result;
    }
}
