package org.example.Command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@CommandLine.Command(name="bookmark",subcommands = {BookmarkCommand.AddBookmark.class}, description = "-Save helpful links and websites")
public class BookmarkCommand implements Runnable{

    class Bookmark{
        int id;
        String title;
        String url;
        String createdAt;
    }
    @CommandLine.Option(names={"--info","--I"})
    private boolean info;
    @CommandLine.Option(names={"--getUrls"})
    private boolean getUrls;
    @CommandLine.Option(names = {"--delete"})
    private int delete;


    @CommandLine.Command(name = "addUrl",description = "Add URL as bookmark")
    static class AddBookmark implements Runnable{

        @CommandLine.Option(names = {"--title"},required = true)
        private String title;
        @CommandLine.Option(names={"--url"},required = true)
        private String link;

        @Override
        public void run() {
            try{
                URL url=new URL("http://localhost:8082/api/bookmark/addUrl");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);
                String jsonInput="{\"title\":\""+title+"\", \"url\":\""+link+"\"}";
                connection.getOutputStream().write(jsonInput.getBytes());
                int responseCode=connection.getResponseCode();
                BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                String response="";
                while((inputLine=br.readLine())!=null){
                    response=inputLine;
                }
                br.close();
                Gson gson=new Gson();
                BookmarkCommand.Bookmark bookmark=gson.fromJson(response,BookmarkCommand.Bookmark.class);
                if(responseCode==200){
                    System.out.println("Bookmark added : "+bookmark.title+" -> "+bookmark.url);
                }
                else{
                    System.out.println("Unfortunately bookmark canot be added !");
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        System.out.println();
        System.out.println("=====================================");
        System.out.println("        BOOKMARK URL MANAGER");
        System.out.println("=====================================");
        System.out.println();

        if (info) {
            System.out.println("Keep track of useful websites effortlessly!");
            System.out.println();
            System.out.println("Options:");
            System.out.println("  addUrl       : Save a website with a title and link");
            System.out.println("  --getUrls    : View all saved bookmarks");
            System.out.println("  --delete     : Delete a bookmark by ID");
            System.out.println();
            System.out.println("~ Never lose track of your favorite sites again.");
            return;
        }

        else if(getUrls){
            try{
                URL url=new URL("http://localhost:8082/api/bookmark/getUrls");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","Application/json");
                connection.setDoOutput(true);
                int responseCode=connection.getResponseCode();
                BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                String allUrls="";
                while((inputLine=br.readLine())!=null){
                    allUrls=inputLine;
                }
                Gson gson=new Gson();
                Type listType=new TypeToken<List<BookmarkCommand.Bookmark>>(){}.getType();
                List<BookmarkCommand.Bookmark> bookmarks=gson.fromJson(allUrls,listType);
                System.out.println();
                System.out.println("---Id : title -> URL---");
                System.out.println();
                for(BookmarkCommand.Bookmark bookmark:bookmarks){
                    System.out.println("    "+bookmark.id+" : "+bookmark.title+"      -> "+bookmark.url);
                }
                br.close();
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        else if(delete!=0){
            try{
                URL url=new URL("http://localhost:8082/api/bookmark/deleteUrl");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","Application/json");
                connection.setDoOutput(true);
                String jsonInput="\""+delete+"\"";
                connection.getOutputStream().write(jsonInput.getBytes());
                int responseCode=connection.getResponseCode();
                if(responseCode==200){
                    System.out.println("Bookmark deletion completed !");
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
