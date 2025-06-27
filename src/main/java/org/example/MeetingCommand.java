package org.example;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@CommandLine.Command(name="meeting",subcommands = {MeetingCommand.FixMeeting.class}, description = "-Schedule and track meetings or events")
public class MeetingCommand implements Runnable{

    class Meet{
        int id;
        String date;
        String context;
    }
    @CommandLine.Option(names={"--info","--I"})
    private boolean info;

    @CommandLine.Option(names = {"--getMeets"})
    private boolean getMeets;

    @CommandLine.Option(names={"--cancel"})
    private int meetId;



    @CommandLine.Command(name = "fixMeet", description = "Fix a meeting with context")
    static class FixMeeting implements  Runnable{

        @CommandLine.Option(names={"--date"},required = true)
        private LocalDate date;
        @CommandLine.Option(names ={"--context"},required = true)
        private String context;

        @Override
        public void run() {
            try{
                URL url=new URL("http://localhost:8082/api/meet/addMeeting");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);
                String jsonInput="{\"date\":\""+date+"\", \"context\":\""+context+"\"}";
                connection.getOutputStream().write(jsonInput.getBytes());
                int responseCode=connection.getResponseCode();
                BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response=new StringBuilder();
                while((inputLine= br.readLine())!=null){
                    response.append(inputLine);
                }
                br.close();
                Gson gson=new Gson();
                MeetingCommand.Meet meet=gson.fromJson(response.toString(), MeetingCommand.Meet.class);
                if(responseCode==200){
                    System.out.println("Meeting Fixed : "+meet.date+" -- "+meet.context);
                }
                else{
                    System.out.println("Unfortunatels meeting cannot be fixed !");
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
    @Override
    public void run() {
        System.out.println("==================================");
        System.out.println("|           Meeting Tracker      |");
        System.out.println("==================================");
        if(info){
            System.out.println("\nNever miss a sync-up again\n" +
                    "fixMeet      -> Fix meetings with its context \n" +
                    "getMeets     -> Get meetings \n\n" +
                    "Track of meeting ");
            return;
        }
        else if(getMeets){
            try{
                URL url=new URL("http://localhost:8082/api/meet/getAllNotes");
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
                System.out.println("---Id : Date -> Context---");
                System.out.println();
                for(MeetingCommand.Meet meet:meets){
                    System.out.println("    "+meet.id+" : "+meet.date+" -> "+meet.context);
                }
                br.close();
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        else if(meetId!=0){
            try{
                URL url=new URL("http://localhost:8082/api/meet/updateMeet");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","Application/json");
                connection.setDoOutput(true);
                String jsonInput="\""+meetId+"\"";
                connection.getOutputStream().write(jsonInput.getBytes());

                int responseCode=connection.getResponseCode();
                if(responseCode==200){
                    System.out.println("Meeting cancellation completed !");
                }
                else {
                    System.out.println("Unfortunately action failed ! Status Code : " + responseCode);
                }
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("        --info for getting commands");
        }

    }
}
