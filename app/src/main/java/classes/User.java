package classes;




public class User {
    private Integer userId;

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public User(Integer userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
    public User(){

    }

    private String password;



    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}