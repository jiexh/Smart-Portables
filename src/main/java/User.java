import java.io.Serializable;

/*
	Users class contains class variables id,name,password,usertype.

	Users class has a constructor with Arguments name, String password, String usertype.

	Users class contains getters and setters for id,name,password,usertype.

*/

public class User implements Serializable {
    private String name;
    private String password;
    private String usertype;

    public User(String name, String password, String usertype) {
        this.name = name;
        this.password = password;
        this.usertype = usertype;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
