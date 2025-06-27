package org.example;

import picocli.CommandLine;

@CommandLine.Command(name = "config",
subcommands = {
        ConfigCommand.Set.class,
        ConfigCommand.Get.class
},
description = "-Get or Set Configuration ")
public class ConfigCommand implements Runnable {


    public void run(){
        System.out.println("Configuration of User Information");
    }

    @CommandLine.Command(name="set" , description = "Set a configuration key")
    static class Set implements Runnable{

        @CommandLine.Parameters(index = "0",description = "The config key !")
        private ConfigElement element;
        @CommandLine.Parameters(index = "1",description = "The value to set !")
        private String value;

        @Override
        public void run() {
            ConfigStore.set(element, value);
            System.out.println("Set " + element.value() + " to '" + value + "'");
        }
    }

    @CommandLine.Command(name="get" , description = "Get a configuration value")
    static class Get implements Runnable{

        @CommandLine.Parameters(index = "0" , description = " The Config Key")
        private ConfigElement element;

        public void run(){
            String value=ConfigStore.get(element);
            if(value!=null){
                System.out.println(element.value()+"="+value);
            }
            else{
                System.out.println(element.value()+" is not set.");
            }
        }

    }

}
