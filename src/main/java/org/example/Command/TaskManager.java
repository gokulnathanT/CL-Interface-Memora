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

@CommandLine.Command(name="task",description = "-Manage to-do list and get things done")
public class TaskManager implements Runnable{

    static class Task{
        int id;
        String content;
        String status;
        String createdAt;
    }
    @CommandLine.Option(names={"--info","--I"})
    private boolean info;
    @CommandLine.Option(names={"--addTask"})
    private String task;
    @CommandLine.Option(names={"--Pending"})
    private boolean getPending;
    @CommandLine.Option(names={"--Completed"})
    private boolean getCompleted;

    @CommandLine.Option(names={"--Update"})
    private int completeTask;

    public void run() {
        System.out.println();
        System.out.println("==================================");
        System.out.println("|         TASK MANAGER           |");
        System.out.println("==================================");
        System.out.println();

        if (info) {
            System.out.println("Effortlessly organize your day!");
            System.out.println();
            System.out.println("Options:");
            System.out.println("  --addTask     : Add a new task");
            System.out.println("  --Pending     : List all pending tasks");
            System.out.println("  --Completed   : View completed tasks");
            System.out.println("  --Update      : Mark a task as completed by ID");
            System.out.println();
            System.out.println("~ Stay on track and boost productivity.");
            return;
        }

        else if (task != null) {
            try {
                URL url = new URL("http://localhost:8082/api/task/addTask");
                HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);

                String jsonInputString="{\"content\":\""+task+"\", \"status\":\"PENDING\"}";

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
                Task task=gson.fromJson(response.toString(),Task.class);
                if(responseCode==200){
                    System.out.println("Task added : \""+task.content);
                }
                else {
                    System.out.println("Unfortunately task cannot be added !");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        else if (getPending){
            try{
                System.out.println("pending");
                URL url=new URL("http://localhost:8082/api/task/getPending");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","Application/json");
                connection.setDoOutput(true);
                int responseCode=connection.getResponseCode();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                System.out.println("Tasks to Complete : ");
                String taskToComplete="";
                while((inputLine=bufferedReader.readLine())!=null){
                    taskToComplete=inputLine;
                }
                Gson gson=new Gson();
                Type listType=new TypeToken<List<Task>>(){}.getType();
                List<Task> tasks=gson.fromJson(taskToComplete,listType);
                System.out.println();
                System.out.println("   ---Id : TaskContent---");
                System.out.println();
                for(Task task:tasks){
                    System.out.println("    "+task.id+"  : "+task.content);
                }
                bufferedReader.close();

            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        else if (completeTask!=0){
            try{
                URL url=new URL("http://localhost:8082/api/task/updateTask");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","Application/json");
                connection.setDoOutput(true);
                String jsonInput="\""+completeTask+"\"";
                connection.getOutputStream().write(jsonInput.getBytes());

                int responseCode=connection.getResponseCode();
                if(responseCode==200){
                    System.out.println("Task marked as completed !");
                }
                else {
                    System.out.println("Unfortunately action failed ! Status Code : " + responseCode);
                }
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }else if (getCompleted){
            try{
                URL url=new URL("http://localhost:8082/api/task/getCompleted");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","Application/json");
                connection.setDoOutput(true);
                int responseCode=connection.getResponseCode();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                System.out.println("Tasks Completed : ");
                String taskToComplete="";
                while((inputLine=bufferedReader.readLine())!=null){
                    taskToComplete=inputLine;
                }
                Gson gson=new Gson();
                Type listType=new TypeToken<List<Task>>(){}.getType();
                List<Task> tasks=gson.fromJson(taskToComplete,listType);
                System.out.println();
                System.out.println("   ---Id : TaskContent---");
                System.out.println();
                for(Task task:tasks){
                    System.out.println("    "+task.id+"  : "+task.content);
                }
                bufferedReader.close();

            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        else{


            System.out.println("       '--info' to know commands");
        }
    }}
