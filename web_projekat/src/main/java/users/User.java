package users;

public class User {
    private int id;
    private String username;
    private String password;
    private boolean type;
    private String token;

    public User(){
        this.id=0;
        this.username="";
        this.password="";
        this.type=false;
        this.token = "";
    }

    public User(int id, String username, String password, boolean type, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                '}';
    }
}
