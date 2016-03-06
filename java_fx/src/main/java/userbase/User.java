package userbase;

import java.util.Date;

/**
 * Created by v.davidenko on 03.02.2016.
 * id, имя, пароль, дата
 */
public class User {
    private Integer id;
    private String name;
    private String password;
    private Date date;

    public User(String name, String password, Date date) {
        this.name = name;
        this.password = password;
        this.date = date;
    }

    public User() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        if (!id.equals(user.id)) return false;
        if (!name.equals(user.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", date=" + date +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
