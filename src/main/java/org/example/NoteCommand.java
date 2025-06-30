package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import picocli.CommandLine;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@CommandLine.Command(name="notes",description = "-Capture ideas, thoughts, and important notes",subcommands = {NoteCommand.AddNotes.class})
public class NoteCommand implements Runnable{

    class Note{
        int id;
        String title;
        String content;
        String createdAt;
    }
    @CommandLine.Option(names={"--info","--I"})
    private boolean info;
    @CommandLine.Option(names={"--getNote"})
    private int noteId;

    @CommandLine.Option(names = {"--allNotes"})
    private boolean allNotes;

    @CommandLine.Command(name = "add",description = "Add notes with title along with its content")
    static class AddNotes implements Runnable{

        @CommandLine.Option(names={"--title"},required = true)
        private String title;

        @CommandLine.Option(names={"--content"},required = true)
        private String content;
        @Override
        public void run() {
            try {
                URL url = new URL("http://localhost:8082/api/note/addNote");
                HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);
                String jsonInputString="{\"title\":\""+title+"\", \"content\":\""+content+"\"}";
                connection.getOutputStream().write(jsonInputString.getBytes());
                int responseCode=connection.getResponseCode();
                BufferedReader in=new BufferedReader((new InputStreamReader(connection.getInputStream())));
                String inputLine;
                StringBuilder response=new StringBuilder();
                while((inputLine=in.readLine())!=null){
                    response.append(inputLine);
                }
                in.close();
                Gson gson=new Gson();
                NoteCommand.Note note=gson.fromJson(response.toString(), NoteCommand.Note.class);
                if(responseCode==200){
                    System.out.println("Notes added : "+note.title);
                }
                else {
                    System.out.println("Unfortunately note cannot be added !");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }




    @Override
    public void run() {
        System.out.println();
        System.out.println("==================================");
        System.out.println("          NOTES KEEPER");
        System.out.println("==================================");
        System.out.println();

        if (info) {
            System.out.println("Keep your notes wise and organized!");
            System.out.println();
            System.out.println("Options:");
            System.out.println("  add         : Add a new note");
            System.out.println("  --allNotes  : View all saved notes");
            System.out.println("  --getNote   : Fetch a specific note by ID");
            System.out.println();
            System.out.println("Stay productive by managing your ideas effectively.");
            return;
        }

        else if(noteId!=0){
            try {
                System.out.println("Notes ");
                String baseUrl="http://localhost:8082/api/note/getNote";
                String urlString=String.format("%s/%d",baseUrl,noteId);
                URL url = new URL(urlString);
                HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","Application/json");
                connection.setDoOutput(true);
                int responseCode=connection.getResponseCode();
                BufferedReader in=new BufferedReader((new InputStreamReader(connection.getInputStream())));
                String inputLine;
                String response="";
                while((inputLine=in.readLine())!=null){
                    response=inputLine;
                }
                in.close();
                Gson gson=new Gson();
                NoteCommand.Note note=gson.fromJson(response.toString(), NoteCommand.Note.class);
                if(responseCode==200){
                    System.out.println("Title : "+note.title);
                    System.out.println("Content : "+note.content);
                }
                else {
                    System.out.println("Unfortunately note cannot be fetched !");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if(allNotes){
            try{
                URL url=new URL("http://localhost:8082/api/note/getAllNotes");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","Application/json");
                connection.setDoOutput(true);
                int responseCode=connection.getResponseCode();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                String allNotes="";
                while((inputLine=bufferedReader.readLine())!=null){
                    allNotes=inputLine;
                }
                Gson gson=new Gson();
                Type listType=new TypeToken<List<NoteCommand.Note>>(){}.getType();
                List<NoteCommand.Note> notes=gson.fromJson(allNotes,listType);
                System.out.println();
                System.out.println("   ---Id : Note Title---");
                System.out.println();
                for(NoteCommand.Note note:notes){
                    System.out.println("    "+note.id+"  : "+note.title);
                }
                bufferedReader.close();

            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("         --info for getting commands");

        }


    }


}
