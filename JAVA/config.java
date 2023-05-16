package org.example;

import java.io.FileInputStream;
import java.util.Properties;

public class config {
    public static String API_token;
    public static String Searched_user;
    public static String db_url;
    public static String db_username;
    public static String db_password;
    public static String db_name;

    public static void conf(){
        try{
            String config = "src/config.properties";
            FileInputStream properties = new FileInputStream(config);
            Properties prop = new Properties();
            prop.load(properties);

            API_token = prop.getProperty("API_token");
            Searched_user = prop.getProperty("searched_user");
            db_url=prop.getProperty("db_url");
            db_username=prop.getProperty("db_username");
            db_password=prop.getProperty("db_password");
            db_name=prop.getProperty("db_name");

        }
        catch(Exception e){
            System.out.println(e);
        }


    }
}
