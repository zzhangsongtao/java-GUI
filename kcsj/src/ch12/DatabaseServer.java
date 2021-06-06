package ch12;
import java.net.*;
import java.util.*;
import java.io.*;
public class DatabaseServer{
   ServerSocket server;
   Socket you;
   InetAddress yourAddress;
   public DatabaseServer(){
      try{ Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      }
      catch(ClassNotFoundException e){
           System.out.println(e);
      }
      
      System.out.println("我是服务器端程序,负责处理用户的连接请求"); 
   } 
   public void startServer(int port){
      while(true){ 
          try{
               server=new ServerSocket(port);
          }
          catch(IOException e1){ 
               System.out.println("正在监听:");
          } 
          try{ System.out.println("等待用户呼叫.");
               you=server.accept();          
               yourAddress=you.getInetAddress();
               System.out.println("客户的IP:"+yourAddress);
                     
          }
          catch (IOException e){}
          if(you!=null) {
              new ServerThread(you).start();
          }  
          else 
              continue;
     }
   }
   public static void main(String args[]){
       DatabaseServer server=new DatabaseServer();
       server.startServer(6666);  
   }
}
