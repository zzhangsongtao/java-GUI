package ch12;
import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;
public class ServerThread extends Thread{
    InetAddress yourAddress;
    Socket socket=null;
    DataOutputStream out=null;
    DataInputStream  in=null;
    Connection con=null;
    Statement  stmt=null;
    ResultSet  rs;
    int number;
    ServerThread(Socket t){
        socket=t;
        try{  con=DriverManager.getConnection("jdbc:odbc:myDataSouce","","");
              stmt=con.createStatement();
        }
        catch(SQLException e){
              System.out.println(e);
        }
        try{ in=new DataInputStream(socket.getInputStream());
             out=new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException e){}
    }  
    public void run(){ 
        String s="";
        int N=0;       
        while(true){
           try{
                s=in.readUTF();
                System.out.println(s);
                if(s.startsWith("�ֶθ���:")){
                   String number=s.substring(s.lastIndexOf(":")+1);
                   N=Integer.parseInt(number);
                }
                else{
                   String sqlCondition=null;
                   String ����="",��ѯ����="",�ֶ�="",��ѯ��ʽ="";
                   StringTokenizer fenxi=new StringTokenizer(s,":");
                   if(fenxi.hasMoreTokens())
                      ����=fenxi.nextToken(); 
                   if(fenxi.hasMoreTokens())
                      ��ѯ����=fenxi.nextToken(); 
                   if(fenxi.hasMoreTokens())
                      �ֶ�=fenxi.nextToken();
                   if(fenxi.hasMoreTokens())
                      ��ѯ��ʽ=fenxi.nextToken(); 
                   if(��ѯ��ʽ.equals("��ȫһ��")){
                     sqlCondition=
                    "SELECT * FROM "+����+" WHERE "+�ֶ�+" LIKE "+"'"+��ѯ����+"' ";
                   }
                   else if(��ѯ��ʽ.equals("ǰ��һ��")){
                     sqlCondition=
                    "SELECT * FROM "+����+" WHERE "+�ֶ�+" LIKE "+"'"+��ѯ����+"%' ";
                   } 
                   else if(��ѯ��ʽ.equals("��һ��")){
                     sqlCondition=
                    "SELECT * FROM "+����+ " WHERE "+�ֶ�+" LIKE "+"'%"+��ѯ����+"' ";
                   } 
                   else if(��ѯ��ʽ.equals("�м����")){
                     sqlCondition=
                    "SELECT * FROM "+����+" WHERE "+�ֶ�+" LIKE "+"'%"+��ѯ����+"%' ";
                   }
                   try{  rs=stmt.executeQuery(sqlCondition);
                         number=0;
                         while(rs.next()){
                          number++;
                          StringBuffer buff=new StringBuffer();
                          for(int k=1;k<=N;k++){
                             buff.append(rs.getString(k)+"  ");
                          }
                          out.writeUTF("\n"+new String(buff));
                        }
                        if(number==0)
                          out.writeUTF("\nû�в�ѯ���κμ�¼\n");
                   }
                   catch(SQLException ee) {
                        out.writeUTF(""+ee);
                   }
                }
           }
           catch (IOException e){ 
               try{ socket.close();
                    con.close();
               }                    
               catch(Exception eee){}
               System.out.println("�ͻ��뿪��");
               break;                                  
           }
       
       }
   }
}

