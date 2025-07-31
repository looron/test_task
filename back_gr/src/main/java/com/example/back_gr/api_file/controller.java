package com.example.back_gr.api_file;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.back_gr.com.TaskClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController

public class controller 
{
@Autowired
    private ObjectMapper om;

    private static Connection conn;
    

    static 
    {
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");  // установление связи с драйвером mysql
        }
        catch (ClassNotFoundException e)
        {e.printStackTrace();}

        String url = "jdbc:mysql://localhost/task_bd";
             String username = "root";
             String password = "cofeProg2@";

    try {
        conn = DriverManager.getConnection(url,username,password);  // установление подключения к бд
        System.out.println("Connection to Store DB succesfull!");
    } 
    catch (SQLException ex) 
    
    {
        System.out.println("Connection failed...");
    }
    }


// получение вссех задач в формате json
@CrossOrigin
@GetMapping("/api/tasks")
    public String listenning() throws SQLException
    {
        TaskClass task= new TaskClass();
        java.sql.Statement statement= conn.createStatement();

         String query_get_all_task = "select * from tasks"; // sql запрос
        
         ResultSet res_set = statement.executeQuery(query_get_all_task);    // выполнение запроса sql и полечение результатов в resulset

        String jsonData="{\"tasks\":[";

while(res_set.next())   // цикл формирования json ответа
{  
         task.setId_task(res_set.getInt("idTask"));
         task.setTask_name(res_set.getString("title"));
         task.setTask_text(res_set.getString("descriotion"));
         task.setDeadline(res_set.getString ("dedline"));
         task.setComplite(res_set.getBoolean("is_complite"));
        

          try {
             jsonData += om.writeValueAsString(task)+",";   // перевод объекта класса TaskClass в строку 
             
         } catch (JsonProcessingException e) {
            System.out.print("[eq]");
         }

                    }

        jsonData = jsonData.substring(0, jsonData.length() - 1);    // удаление лишней запятой
        jsonData+="]}"; // простановка закрывающих скобок

         
        
        return jsonData;
    }


// добавдение новой задачи
@CrossOrigin
@PostMapping ("/api/tasks")
public @ResponseBody String putMethodName( TaskClass task_new ) throws SQLException 
{
   String query_put_task = "insert tasks values(default,'"+ task_new.getTask_name() +"','"+ task_new.getTask_text()+"', '"+task_new.getDeadline()+"', default )"; // запрос на добавление записи в таблицу tasks

    java.sql.Statement statement = conn.createStatement();
    String res_op=null; 
    try {   
        statement.executeUpdate(query_put_task); 
        res_op="задача успешно добавлена";
    }
    catch (SQLException e)
    {   System.out.println(e);
        res_op="добавление задачи не удалось";
    }

    
    return res_op;
}

            // удаление задачи
@CrossOrigin
@DeleteMapping("api/tasks/{id}")
 public String delete_task(@PathVariable ("id") String id) throws SQLException 
{
    String query_delete_this_task = "delete from tasks where idTask = '" + id + "'"; // запрос на удаление задачи по id
    

    java.sql.Statement statement = conn.createStatement();
String res_op =null;
    try {
        statement.execute(query_delete_this_task);
        res_op = "задача успешно удалена";
        
    } catch (SQLException ex) {
        res_op = "удаление не удалось";
        
        System.out.println(ex);
    }
    return res_op;
}

//изменение статуса задачи
@CrossOrigin
@PutMapping("/api/tasks/{id}/toggle")
public String change_complete(@PathVariable String id) throws SQLException{

String query_post_this_task = "update tasks set is_complite= not is_complite where idTask = " + id;

java.sql.Statement statement = conn.createStatement();

    try
    {
        statement.executeUpdate(query_post_this_task);
    }
    
    catch (SQLException ex) {}

    String query_up_this_task = "select * from tasks where idTask = '" + id + "'";
    ResultSet res_set = statement.executeQuery(query_up_this_task);

    TaskClass task= new TaskClass();
    while(res_set.next())
    {
         task.setId_task(res_set.getInt("idTask"));
         task.setTask_name(res_set.getString("title"));
         task.setTask_text(res_set.getString("descriotion"));
         task.setComplite(res_set.getBoolean("is_complite"));
    }

    String jsonData=null;

    try {
            jsonData = om.writeValueAsString(task);
        }

    catch (JsonProcessingException e) 
    {
            System.out.print("[eq]");
    }
        
        return jsonData;
}

}


