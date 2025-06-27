package org.example;

import picocli.CommandLine;

@CommandLine.Command(name="memora"
,subcommands = {
        TaskManager.class, ConfigCommand.class,
        NoteCommand.class, MeetingCommand.class,
        BookmarkCommand.class,

})
public class memoraCommand implements Runnable{


    @CommandLine.Option(names={"--info","--I"})
    private boolean info;

    public void run(){
        System.out.println("=================");
        System.out.println("|     MEMORA    |");
        System.out.println("=================");
        CommandLine.usage(this,System.out);
        if(info){
            System.out.println("Memora is a personal assistant within Command Line Interface (CLI) !!");
        }
        System.out.println("Start by giving command");
        System.out.println("              --help to get commands ");
    }
}
