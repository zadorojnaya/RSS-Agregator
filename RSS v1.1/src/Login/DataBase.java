package Login;

import java.sql.*;

/**
 * Created by Naya on 02.03.14.
 */
public class DataBase {
    public String dataBaseConnect;
    private Connection connect = null;
    private String lastLogin;
    public DataBase(){
        try{
            String user = "root";
            String pass = "123";
            String url = "jdbc:mysql://localhost/RSS";

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connect = DriverManager.getConnection(url,user,pass);
            dataBaseConnect = "ConnDB";
        } catch (Exception e) {
            dataBaseConnect = "FailDB";
        }
    }

    public boolean LogIn(String Log, String Pass) throws SQLException {
        Statement stmt;
        ResultSet rset;
        stmt = connect.createStatement();
        rset = stmt.executeQuery("select *from USER ");
        while(rset.next()){
            String login = rset.getString("login");
            String pass = rset.getString("pass");
            if(login.equals(Log) && pass.equals(Pass)) {
                lastLogin = Log;
                return true;
            }
        }
        rset.close();
        return false;
    }

    public boolean Register(String Log, String Pass) {

        try{
        Statement stmt = connect.createStatement();
        stmt.executeUpdate("insert into user (login,pass)values('"+Log+"','"+Pass+"');");
        lastLogin = Log;
        return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getLastLogin(){
        return lastLogin;
    }
    public Connection getCon(){return connect;}
}
