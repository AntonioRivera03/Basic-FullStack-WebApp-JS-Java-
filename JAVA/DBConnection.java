package org.example;
import java.sql.*;


public class DBConnection {

    private final config config = new config();
    private final String user_search = config.Searched_user;
    private final String url = config.db_url + config.db_name;
    private final String username = config.db_username;
    private final String password = config.db_password;
    private final Connection connection = DriverManager
            .getConnection(url, username, password);
    private final Statement statement = connection.createStatement();
    public DBConnection() throws SQLException {}
    private String dbConnect(String query) throws SQLException {

        /*
        This function connects to the database and writes a query. This can be an insert, or a select statement.
        If you want to query the database, It would be as follows
        ResultSet rs = statement.executeQuery("");
        Currently, this code uses the query built in dbWrite and pushes the statement to the database.

        Note: Might be better to reference dbWrite in this method with username and message as parameters instead of query.

         */

        statement.executeUpdate(query);

        return query;

    }
    public String dbWrite(String userID ,String user, String guild, String timeSent, String msg) throws SQLException {
        String dbName = config.db_name;
        String query = String
                .format("INSERT INTO `%s`.`message`(`userID`,`username`,`guild`, `timeSent`,`message`)VALUES('%s', '%s','%s','%s','%s');"
                        , dbName, userID, user, guild, timeSent, msg);
        return dbConnect(query);
    }
    public void uniqueUser(String userID, String username, String pfp ) throws SQLException {
        statement.executeUpdate(String.format("insert ignore into user values ('%s', '%s', '%s')", userID, username, pfp));
    }

    public String getUsername(){
        return user_search;
    }



}