package ch11;
import java.io.*;
import java.net.*;
import java.util.*;
public class ServerThread extends Thread{
    LinkedList<String> loginStudent;
    String verifyMess; 
    InetAddress yourAddress;
    Socket socket=null;
    DataOutputStream out=null;
    DataInputStream  in=null;
    ReadTestquestion readTest=null;
    File currentDir,dirStudent;
    boolean isLogin=false;
    ServerThread(Socket t,InetAddress s,LinkedList<String> list){
        socket=t;
        yourAddress=s;
        loginStudent=list;  
        currentDir=new File(System.getProperty("user.dir"));
        dirStudent=new File(currentDir,"Student");
        dirStudent.mkdir();               
        try{ in=new DataInputStream(socket.getInputStream());
             out=new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException e){}
        readTest=new ReadTestquestion();
    }  
    public void run(){ 
        String s="";       
        while(true){
          try{ s=in.readUTF();
               if(s.startsWith("学生")){
                  verifyMess=s+""+yourAddress;
                  File file=new File(dirStudent,s+".txt");//存储学生信息的文件
                  file.delete();  //删除旧的文件 
                  byte [] bb=verifyMess.getBytes();
                  if(!(file.exists())){  
                    RandomAccessFile outNumber=new RandomAccessFile(file,"rw");
                    outNumber.write(bb);
                    outNumber.close();
                  }
                  if(loginStudent.contains(verifyMess)){
                     out.writeUTF("已经成功登录了");
                     break;  //如果已经登录过,就消灭因重新登录而创建的当前线程
                  }
                  else{
                    loginStudent.add(verifyMess);
                    out.writeUTF("成功登录:"+verifyMess);
                   //保留首次登录时的线程,当用户突然离线时,该线程将触发IOException
                  }
               }
               else if(s.startsWith("列出文件:")){
                  verifyMess=s.substring(s.lastIndexOf(":")+1);
                  System.out.println(verifyMess);
                  isLogin=loginStudent.contains(verifyMess);
                  if(isLogin){       
                     String fileName[]=currentDir.list(new FilenameFilter(){
                                              public boolean accept(File dir,String name){
                                                 return name.endsWith(".txt");
                                              }    
                                             });   
                     for(int i=0;i<fileName.length;i++) 
                        out.writeUTF("列出文件:"+fileName[i]); 
                     out.writeUTF("已经全部列出:");
                  }
                  else{  out.writeUTF("请登录");
                  }    
                  return;
               }
               else if(s.startsWith("显示试卷")){
                  verifyMess=s.substring(s.lastIndexOf(":")+1,s.lastIndexOf("#"));
                  System.out.println(verifyMess);
                  isLogin=loginStudent.contains(verifyMess);
                  String fileName=s.substring(s.lastIndexOf("#")+1);
                  File file=new File(fileName);
                  if(isLogin){
                     String contentTest=readTest.getTestContent(file);
                     out.writeUTF("试卷内容:"+contentTest);
                  }
                  else{  out.writeUTF("请登录");
                  }    
                  return;         
               }
               else if(s.startsWith("提交的答案:")){
                  verifyMess=s.substring(s.lastIndexOf(":")+1,s.lastIndexOf("#"));
                  System.out.println(verifyMess);
                  isLogin=loginStudent.contains(verifyMess);
                  if(isLogin){
                     String clientAnswer=s.substring(s.indexOf("#")+1);
                     String fileName=verifyMess.substring(0,verifyMess.indexOf("/"));   
                     File file=new File(dirStudent,fileName+".txt");
                     RandomAccessFile outAnswer=new RandomAccessFile(file,"rw");
                     outAnswer.seek(outAnswer.length());
                     byte [] bb="给出的答案:".getBytes();
                     outAnswer.write(bb);
                     bb=clientAnswer.getBytes();
                     outAnswer.write(bb);
                     outAnswer.close();
                  }
                  else{ out.writeUTF("请登录");
                  }    
                  return;               
               }
               else if(s.startsWith("查看得分:")){
                  int score=0;
                  verifyMess="";
                  verifyMess=s.substring(s.indexOf(":")+1,s.indexOf("#"));
                  System.out.println(verifyMess);
                  isLogin=loginStudent.contains(verifyMess);
                  if(isLogin){
                     String clientAnswer=s.substring(s.indexOf("#")+1,s.indexOf("*"));
                     String fileName=s.substring(s.indexOf("*")+1);  
                     File file=new File(fileName);
                     String str=readTest.getCorrectAnswer(file);
                     for(int k=0;k<str.length();k++){
                        if(str.charAt(k)==clientAnswer.charAt(k))
                          score++;
                     }
                     if(str.length()>0){
                       out.writeUTF("分数:"+score);
                       fileName=verifyMess.substring(0,verifyMess.indexOf("/"));   
                       file=new File(dirStudent,fileName+".txt");
                       RandomAccessFile outAnswer=new RandomAccessFile(file,"rw");
                       outAnswer.seek(outAnswer.length());
                       byte [] bb=("得分:"+score).getBytes();
                       outAnswer.write(bb); 
                     }
                     else
                       out.writeUTF("分数:"+"没有答案");
                  }
                  else{ out.writeUTF("请登录");
                  }    
                  return;               
              }
          }
          catch(IOException ee){
              try{ socket.close();
              }
              catch(Exception eee){}
              loginStudent.remove(verifyMess);
              System.out.println("客户离开了");
              break;
          }             
       } 
    }
}