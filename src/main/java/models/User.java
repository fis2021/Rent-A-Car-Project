package models;

import org.dizitart.no2.objects.Id;

public class User {
    private String username;
    private String password;
    private String role;
    @Id
    private String email;
    private String phoneNumber;
    private String address;
    private String cnp;
    private boolean isBlocked;

    public User(String username, String password, String role, String email, String phoneNumber, String address, String cnp) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.cnp = cnp;
        this.isBlocked = false;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public boolean getIsBlocked() { return isBlocked; }

    public void setIsBlocked(boolean isBlocked) { this.isBlocked = isBlocked; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (user == null) return false;
        if (!cnp.equals(user.cnp) || !email.equals(user.email)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
