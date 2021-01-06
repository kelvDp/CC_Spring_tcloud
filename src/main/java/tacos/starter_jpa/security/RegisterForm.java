package tacos.starter_jpa.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;
import tacos.starter_jpa.User;

@Data
public class RegisterForm {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(PasswordEncoder encoder) {

        return new User(fullname, username, encoder.encode(password), phone, city, street, zip, state);
    }
}
