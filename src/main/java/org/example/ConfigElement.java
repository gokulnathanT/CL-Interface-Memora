package org.example;

import java.lang.reflect.Array;
import java.util.Arrays;

public enum ConfigElement {
    USERNAME("user.name"),
    EMAIL("user.email");

    private final String value;
    ConfigElement(String value){
        this.value=value;
    }
    public String value(){
        return value;
    }

    public static ConfigElement from(String value){                              // converting the parameter for finding the correct configuration element !
        return Arrays.stream(values()).
                filter(element->element.value.equals(value))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("The argument "+value+" doesn't match any ConfigElement"));
    }
}
