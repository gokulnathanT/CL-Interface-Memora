package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import picocli.CommandLine;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@CommandLine.Command(name = "report",description = "Generate a weekly report on productivity")
public class WeeklyReport  implements  Runnable{

    @CommandLine.Option(names = {"--WeekSummary"})
    private String report;

    @CommandLine.Option(names={"--info"})
    private boolean info;

    @Override
    public void run() {
        System.out.println("==================================");
        System.out.println("|           Weekly Report        |");
        System.out.println("==================================");

        if(info){
            System.out.println("\n\nGet productivity report in ease!\n" +
                    "--WeekSummary  -> Weekly report on the activities \n\n" +

                    "Track and document your progress");
            return;
        }
        else if(report!=null){
            try{
                //  Content 1 :
                URL url=new URL("http://localhost:8082/api/task/Activity");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","Application/json");
                connection.setDoOutput(true);
                int responseCode=connection.getResponseCode();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                String weekActivity="";
                while((inputLine=bufferedReader.readLine())!=null){
                    weekActivity=inputLine;
                }
                Gson gson=new Gson();
                Type listType=new TypeToken<List<TaskManager.Task>>(){}.getType();
                List<TaskManager.Task> tasks=gson.fromJson(weekActivity,listType);

                //  Content 2 :
                URL url2=new URL("http://localhost:8082/api/note/Activity");
                HttpURLConnection connection2=(HttpURLConnection) url2.openConnection();
                connection2.setRequestMethod("GET");
                connection2.setRequestProperty("Content-Type","Application/json");
                connection2.setDoOutput(true);
                int responseCode2=connection.getResponseCode();
                BufferedReader bufferedReader2=new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                String inputLine2;
                String weekActivity2="";
                while((inputLine2=bufferedReader2.readLine())!=null){
                    weekActivity2=inputLine2;
                }
                Gson gson2=new Gson();
                Type listType2=new TypeToken<List<NoteCommand.Note>>(){}.getType();
                List<NoteCommand.Note> notes=gson2.fromJson(weekActivity2,listType2);

                //  Content 3 :
                URL url3=new URL("http://localhost:8082/api/bookmark/Activity");
                HttpURLConnection connection3=(HttpURLConnection) url3.openConnection();
                connection3.setRequestMethod("GET");
                connection3.setRequestProperty("Content-Type","Application/json");
                connection3.setDoOutput(true);
                int responseCode3=connection.getResponseCode();
                BufferedReader bufferedReader3=new BufferedReader(new InputStreamReader(connection3.getInputStream()));
                String inputLine3;
                String weekActivity3="";
                while((inputLine3=bufferedReader3.readLine())!=null){
                    weekActivity3=inputLine3;
                }
                Gson gson3=new Gson();
                Type listType3=new TypeToken<List<BookmarkCommand.Bookmark>>(){}.getType();
                List<BookmarkCommand.Bookmark> bookmarks=gson3.fromJson(weekActivity3,listType3);
                String fileName="D:\\Java_Fullstack\\MemoraReport\\"+report+".md";
                BufferedWriter fw = new BufferedWriter(new FileWriter(fileName));
                fw.write("# 📝 Weekly Activity Summary");
                fw.newLine();
                fw.write("**Generated on:** " + LocalDateTime.now());
                fw.newLine();
                fw.write("\n---\n");

                fw.write("## ✅ Tasks Completed");
                fw.newLine();
                if (tasks.stream().noneMatch(t -> "COMPLETED".equals(t.status))) {
                    fw.write("- No tasks marked as completed this week.");
                }
                for (TaskManager.Task task : tasks) {
                    if ("COMPLETED".equals(task.status)) {
                        fw.write("- **Task ID:** " + task.id + " — " + task.content);
                        fw.newLine();
                    }
                }
                fw.newLine();

                fw.write("## ⏳ Tasks Pending");
                fw.newLine();
                if (tasks.stream().noneMatch(t -> "PENDING".equals(t.status))) {
                    fw.write("- No pending tasks!");
                }
                for (TaskManager.Task task : tasks) {
                    if ("PENDING".equals(task.status)) {
                        fw.write("- **Task ID:** " + task.id + " — " + task.content);
                        fw.newLine();
                    }
                }
                fw.newLine();

                fw.write("## 🗒️ Notes Captured");
                fw.newLine();
                if (notes.isEmpty()) {
                    fw.write("- No notes recorded this week.");
                }
                for (NoteCommand.Note note : notes) {
                    fw.write("- **" + note.title + "**: " + note.content);
                    fw.newLine();
                }
                fw.newLine();

                fw.write("## 🔖 Bookmarks Added");
                fw.newLine();
                if (bookmarks.isEmpty()) {
                    fw.write("- No bookmarks saved.");
                }
                for (BookmarkCommand.Bookmark bookmark : bookmarks) {
                    fw.write("- [" + bookmark.title + "](" + bookmark.url + ")");
                    fw.newLine();
                }
                fw.newLine();

                fw.write("---\n");
                fw.write("_This report was generated by Memora CLI — your personal productivity assistant._");

                fw.flush();
                fw.close();

                System.out.println("Report created at : "+fileName);
                System.out.println("File Name : "+fileName.substring(31));

                System.out.println();
            } catch (IOException e) {
                System.out.println("Unfortunately report cannot be generated !");
            }
        }
    }
}
