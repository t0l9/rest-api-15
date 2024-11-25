package models.pojo;

public class LoginResponsePojoModel {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "models.pojo.LoginResponseModel{" +
                "token='" + token + '\'' +
                '}';
    }
}
