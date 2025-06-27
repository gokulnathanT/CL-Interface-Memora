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
    @CommandLine.Option(names={"--help"})
    private boolean help;

    public void run(){
        System.out.println("=================");
        System.out.println("|     MEMORA    |");
        System.out.println("=================");
        if(info){
            System.out.println("Memora is a personal assistant within Command Line Interface (CLI) !!");
        }
        else if(help){
            System.out.println("Commands:\n"+
            "   task      -Manage to-do list and get things done\n"+
            "   config    -Get or Set Configuration\n"+
            "   notes     -Capture ideas, thoughts, and important notes\n"+
            "   meeting   -Schedule and track meetings or events\n"+
            "   bookmark  -Save helpful links and websites");
            return;
        }
        System.out.println("Start by giving command");
        System.out.println("              -- to get commands ");
    }
}
