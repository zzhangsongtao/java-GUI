package ch11;
import java.net.*;
import java.util.*;
import java.io.*;
public class Server{
   ServerSocket server;
   Socket you;
   LinkedList<String> loginStudent;
   InetAddress yourAddress;
   public Server(){
      System.out.println("我是服务器端程序,负责处理用户的连接请求"); 
      loginStudent=new LinkedList<String>();
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
              new ServerThread(you,yourAddress,loginStudent).start();
          }  
          else 
              continue;
     }
   }
   public static void main(String args[]){
       Server server=new Server();
       server.startServer(6666);  
   }
}





