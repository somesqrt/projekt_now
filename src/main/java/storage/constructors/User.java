package storage.constructors;

import java.util.Date;

public class User {
    private Long idUser;
    private String Name;
    private String Surname;
    private Date DateOfBirth;
    private String Login;
    private String Password;
    private Roles role;

    public User(Long idUser, String name, String surname, Date dateOfBirth, String login, String password, Roles role) {
        this.idUser = idUser;
        Name = name;
        Surname = surname;
        DateOfBirth = dateOfBirth;
        Login = login;
        Password = password;
        this.role = role;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "\nUser{" +
                "idUser=" + idUser +
                ", Name='" + Name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", DateOfBirth=" + DateOfBirth +
                ", Login='" + Login + '\'' +
                ", Password='" + Password + '\'' +
                ", role=" + role +
                '}';
    }
}
