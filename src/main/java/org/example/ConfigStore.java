package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ConfigStore {
    private static final String CONFIG_FILE=System.getProperty("user.home")+"./memora-config.json";
    private static final Gson gson=new Gson();
    private static Map<String,String> config=new HashMap<>();

    static {
        load();
    }
    private static void load(){
        try (Reader reader=new FileReader(CONFIG_FILE)){
            Type type=new TypeToken<Map<String,String>>(){}.getType();
            config =gson.fromJson(reader,type);
            if(config==null) config=new HashMap<>();
        }
        catch (IOException e){
            config=new HashMap<>();
        }
    }
    public static void set(ConfigElement key,String value){
        config.put(key.value(),value);
        save();
    }
    public static String get(ConfigElement key){
        return config.get(key.value());
    }
    private static void save(){
        try(Writer writer=new FileWriter(CONFIG_FILE)){
            gson.toJson(config,writer);
        }
        catch (Exception e){
            System.out.println("Failed to save config : "+e.getMessage());
        }
    }
}
