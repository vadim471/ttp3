package org.example.model;

public class UserProfile {
    String login;
    String password;
    String email;

    public UserProfile(String login, String password, String email){
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin(){
        return this.login;
    }
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }

}
