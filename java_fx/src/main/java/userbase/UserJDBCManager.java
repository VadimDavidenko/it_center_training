package userbase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by v.davidenko on 03.02.2016.
 */
public class UserJDBCManager {

    public int create(User user) throws SQLException {
        String query = "insert into USERS \n" +
                "(USER_ID, LOGIN, PSW, REG_DATE)\n" +
                "values\n" +
                "(SEQ_USERS.nextval,?,?,?)";
        int result = 0;
        Connection conn = null;
        try{
            conn = connectToDB();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setDate(3, new Date(user.getDate().getTime()));
            ps.executeQuery();
            result = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                conn.close();
            }
        }
        return result;
    }

    public List<User> findAll() throws SQLException {
        String query = "select * from USERS";

        List<User> list = new ArrayList<User>();
        Connection conn = null;
        try {
            conn = connectToDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(query);

            while (res.next()) {
                User user = new User();
                user.setId(res.getInt("USER_ID"));
                user.setName(res.getString("LOGIN"));
                user.setPassword(res.getString("PSW"));
                user.setDate(res.getDate("REG_DATE"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                conn.close();
            }
        }
        return list;
    }

    public User readByNamePass(String login, String pass) throws SQLException {
        String query = "select * from USERS where LOGIN = ? and PSW = ?";

        Connection conn = null;
        User user = new User();
        try{
            conn = connectToDB();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, pass);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                user.setId(res.getInt("USER_ID"));
                user.setName(res.getString("LOGIN"));
                user.setPassword(res.getString("PSW"));
                user.setDate(res.getDate("REG_DATE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                conn.close();
            }
        }
        return user;
    }

    public Connection connectToDB() throws SQLException {
        final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
        final String DB_LOGIN = "notebooks";
        final String DB_PASSWORD = "notebooks";

        Locale.setDefault(Locale.ENGLISH);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
