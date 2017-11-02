package neonyazilim.com.nrm.Models;

/**
 * Created by tuzlabim on 27.10.2017.
 */

public class UserResponse {

    private boolean login;
    private String _id;
    private String token;

    public UserResponse(boolean login, String message, String token) {
        this.login = login;
        this._id = message;
        this.token = token;
    }

    public UserResponse(boolean login, String token) {
        this.login = login;
        this.token = token;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String message) {
        this._id = message;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
