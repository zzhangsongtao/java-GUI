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
      System.out.println("���Ƿ������˳���,�������û�����������"); 
      loginStudent=new LinkedList<String>();
   } 
   public void startServer(int port){
      while(true){ 
          try{
               server=new ServerSocket(port);
          }
          catch(IOException e1){ 
               System.out.println("���ڼ���:");
          } 
          try{ System.out.println("�ȴ��û�����.");
               you=server.accept();          
               yourAddress=you.getInetAddress();
               System.out.println("�ͻ���IP:"+yourAddress);
                     
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





