package com.example;

import io.vertx.core.json.JsonObject;
//main purpose is to take the fields of related to database
public class ConfigManager {
    //it is class where we take the variable which can store the main configuration of sql
    public static JsonObject mainConfiguration;
    //it will take all the fields of mySql database by key we get value
    public static JsonObject getSqlConfig(){
        //this getJsonObject function of JsonObject class takes key and return their value
        return mainConfiguration.getJsonObject("sql");
    }
}
