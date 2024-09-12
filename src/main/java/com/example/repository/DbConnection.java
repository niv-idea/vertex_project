package com.example.repository;


import com.example.ConfigManager;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import io.ebean.platform.mysql.MySqlPlatform;
import io.vertx.core.json.JsonObject;

//we are going create database connection here
public class DbConnection {
    public static Database sqlDb;

    private static DataSourceConfig dataSourceConfig(){
        // getting sql credential from config.json file
        JsonObject sqlConfig = ConfigManager.getSqlConfig();
        //thats why when we run the program it will show us database related  fields--- by key we are getting values
        System.out.println("sql :" +sqlConfig);
        DataSourceConfig config = new DataSourceConfig();
        config.setUrl(sqlConfig.getString("url"));
        config.setDriver(sqlConfig.getString("driver"));
        config.setPassword(sqlConfig.getString("password"));
        config.setUsername(sqlConfig.getString("username"));
        return config;
    }
    //in this  summary, this code snippet configures a database connection using a DatabaseConfig object.
// It sets up DDL generation and execution, specifies the database platform (MySQL), and returns a Database instance.
    public static Database connect(){
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.addPackage("com.example.entity");
        dbConfig.setDataSourceConfig(dataSourceConfig());
       //we can add here more task which we can perform on database
        dbConfig.setDdlGenerate(true);
        dbConfig.setDdlRun(true);
        dbConfig.setDefaultServer(true);
        dbConfig.setDdlCreateOnly(true);
        dbConfig.setRegister(true);

        //here you can use any database but i use here mySql database
        dbConfig.setDatabasePlatform(new MySqlPlatform());

        return DatabaseFactory.create(dbConfig);
    }

    public static void initSqlConnection() {
        if (sqlDb==null) {
            sqlDb = connect();
        }
        if (sqlDb!=null) {
            System.out.println("Sql Db connection ready");
        }
    }
}
