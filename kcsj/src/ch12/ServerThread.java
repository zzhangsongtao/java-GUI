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
                if(s.startsWith("字段个数:")){
                   String number=s.substring(s.lastIndexOf(":")+1);
                   N=Integer.parseInt(number);
                }
                else{
                   String sqlCondition=null;
                   String 表名="",查询内容="",字段="",查询方式="";
                   StringTokenizer fenxi=new StringTokenizer(s,":");
                   if(fenxi.hasMoreTokens())
                      表名=fenxi.nextToken(); 
                   if(fenxi.hasMoreTokens())
                      查询内容=fenxi.nextToken(); 
                   if(fenxi.hasMoreTokens())
                      字段=fenxi.nextToken();
                   if(fenxi.hasMoreTokens())
                      查询方式=fenxi.nextToken(); 
                   if(查询方式.equals("完全一致")){
                     sqlCondition=
                    "SELECT * FROM "+表名+" WHERE "+字段+" LIKE "+"'"+查询内容+"' ";
                   }
                   else if(查询方式.equals("前方一致")){
                     sqlCondition=
                    "SELECT * FROM "+表名+" WHERE "+字段+" LIKE "+"'"+查询内容+"%' ";
                   } 
                   else if(查询方式.equals("后方一致")){
                     sqlCondition=
                    "SELECT * FROM "+表名+ " WHERE "+字段+" LIKE "+"'%"+查询内容+"' ";
                   } 
                   else if(查询方式.equals("中间包含")){
                     sqlCondition=
                    "SELECT * FROM "+表名+" WHERE "+字段+" LIKE "+"'%"+查询内容+"%' ";
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
                          out.writeUTF("\n没有查询到任何记录\n");
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
               System.out.println("客户离开了");
               break;                                  
           }
       
       }
   }
}

