package models;

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 10.05.12
 * Time: 09:45
 * To change this template use File | Settings | File Templates.
 */

import play.data.validation.Constraints.*;

public class Login {

    @Required
    private
    String username;

    @Required
    private
    String password;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
