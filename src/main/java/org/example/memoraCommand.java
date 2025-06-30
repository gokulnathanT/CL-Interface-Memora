package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Command.*;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@CommandLine.Command(name="load"
,subcommands = {
        TaskManager.class, ConfigCommand.class,
        NoteCommand.class, MeetingCommand.class,
        BookmarkCommand.class, WeeklyReport.class

})
public class memoraCommand implements Runnable{


    @CommandLine.Option(names={"--info","--I"})
    private boolean info;
    @CommandLine.Option(names={"--help"})
    private boolean help;

    public void run(){
        System.out.println();
        System.out.println("=================================");
        System.out.println("            MEMORA");
        System.out.println("=================================");
        System.out.println();

        if (info) {
            System.out.println("Memora is your personal assistant within the Command Line Interface (CLI).\n");
            System.out.println("It helps you organize tasks, notes, bookmarks, meetings, and more.");
            System.out.println();
            System.out.println("Tip:");
            System.out.println("  Use '--info' after each command to get its detailed usage.");
            System.out.println();
        }
        else if (help) {
            System.out.println("Available Commands:");
            System.out.println("---------------------------------");
            System.out.println("  task      : Manage to-do list and get things done");
            System.out.println("  config    : Get or Set configuration");
            System.out.println("  notes     : Capture ideas, thoughts, and important notes");
            System.out.println("  meeting   : Schedule and track meetings or events");
            System.out.println("  bookmark  : Save helpful links and websites");
            System.out.println();
            return;
        }

        try{
            URL url=new URL("http://localhost:8082/api/meet/getTodayMeetings");
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","Application/json");
            connection.setDoOutput(true);
            int responseCode=connection.getResponseCode();
            BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            String allMeetings="";
            while((inputLine=br.readLine())!=null){
                allMeetings=inputLine;
            }
            Gson gson=new Gson();
            Type listType=new TypeToken<List<MeetingCommand.Meet>>(){}.getType();
            List<MeetingCommand.Meet> meets=gson.fromJson(allMeetings,listType);
            System.out.println();
            System.out.println("Memora Ping Events (Today)");
            System.out.println("--------------------------");

            for(MeetingCommand.Meet meet:meets){
                System.out.println(" --> " + meet.context);
            }
            br.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        System.out.println();
        System.out.println("Start by giving command");
        System.out.println("              --help  to get commands ");
    }
}
