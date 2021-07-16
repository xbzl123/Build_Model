package databindingtest;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User{

    @Id
private int id;
private String name;
private String password;

@Generated(hash = 258842593)
public User(int id, String name, String password) {
    this.id = id;
    this.name = name;
    this.password = password;
}

@Generated(hash = 586692638)
public User() {
}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
