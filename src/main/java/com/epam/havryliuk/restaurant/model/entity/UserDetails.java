package com.epam.havryliuk.restaurant.model.entity;

import java.util.Date;

public class UserDetails {
    private Date birthDate;
    private String passport;
    private String bankAccount;

    private UserDetails(UserDetailsBuilder builder) {
        this.birthDate = builder.birthDate;
        this.passport = builder.passport;
        this.bankAccount = builder.bankAccount;
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


    @SuppressWarnings("EqualsReplaceableByObjectsCall")
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

    public static class UserDetailsBuilder {
        private Date birthDate;
        private String passport;
        private String bankAccount;

        public UserDetailsBuilder withBirthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public UserDetailsBuilder withPassport(String passport) {
            this.passport = passport;
            return this;
        }

        public UserDetailsBuilder withBankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
            return this;
        }

        public UserDetails build() {
            return new UserDetails(this);
        }
    }

}
