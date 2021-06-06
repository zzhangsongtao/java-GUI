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
               if(s.startsWith("ѧ��")){
                  verifyMess=s+""+yourAddress;
                  File file=new File(dirStudent,s+".txt");//�洢ѧ����Ϣ���ļ�
                  file.delete();  //ɾ���ɵ��ļ� 
                  byte [] bb=verifyMess.getBytes();
                  if(!(file.exists())){  
                    RandomAccessFile outNumber=new RandomAccessFile(file,"rw");
                    outNumber.write(bb);
                    outNumber.close();
                  }
                  if(loginStudent.contains(verifyMess)){
                     out.writeUTF("�Ѿ��ɹ���¼��");
                     break;  //����Ѿ���¼��,�����������µ�¼�������ĵ�ǰ�߳�
                  }
                  else{
                    loginStudent.add(verifyMess);
                    out.writeUTF("�ɹ���¼:"+verifyMess);
                   //�����״ε�¼ʱ���߳�,���û�ͻȻ����ʱ,���߳̽�����IOException
                  }
               }
               else if(s.startsWith("�г��ļ�:")){
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
                        out.writeUTF("�г��ļ�:"+fileName[i]); 
                     out.writeUTF("�Ѿ�ȫ���г�:");
                  }
                  else{  out.writeUTF("���¼");
                  }    
                  return;
               }
               else if(s.startsWith("��ʾ�Ծ�")){
                  verifyMess=s.substring(s.lastIndexOf(":")+1,s.lastIndexOf("#"));
                  System.out.println(verifyMess);
                  isLogin=loginStudent.contains(verifyMess);
                  String fileName=s.substring(s.lastIndexOf("#")+1);
                  File file=new File(fileName);
                  if(isLogin){
                     String contentTest=readTest.getTestContent(file);
                     out.writeUTF("�Ծ�����:"+contentTest);
                  }
                  else{  out.writeUTF("���¼");
                  }    
                  return;         
               }
               else if(s.startsWith("�ύ�Ĵ�:")){
                  verifyMess=s.substring(s.lastIndexOf(":")+1,s.lastIndexOf("#"));
                  System.out.println(verifyMess);
                  isLogin=loginStudent.contains(verifyMess);
                  if(isLogin){
                     String clientAnswer=s.substring(s.indexOf("#")+1);
                     String fileName=verifyMess.substring(0,verifyMess.indexOf("/"));   
                     File file=new File(dirStudent,fileName+".txt");
                     RandomAccessFile outAnswer=new RandomAccessFile(file,"rw");
                     outAnswer.seek(outAnswer.length());
                     byte [] bb="�����Ĵ�:".getBytes();
                     outAnswer.write(bb);
                     bb=clientAnswer.getBytes();
                     outAnswer.write(bb);
                     outAnswer.close();
                  }
                  else{ out.writeUTF("���¼");
                  }    
                  return;               
               }
               else if(s.startsWith("�鿴�÷�:")){
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
                       out.writeUTF("����:"+score);
                       fileName=verifyMess.substring(0,verifyMess.indexOf("/"));   
                       file=new File(dirStudent,fileName+".txt");
                       RandomAccessFile outAnswer=new RandomAccessFile(file,"rw");
                       outAnswer.seek(outAnswer.length());
                       byte [] bb=("�÷�:"+score).getBytes();
                       outAnswer.write(bb); 
                     }
                     else
                       out.writeUTF("����:"+"û�д�");
                  }
                  else{ out.writeUTF("���¼");
                  }    
                  return;               
              }
          }
          catch(IOException ee){
              try{ socket.close();
              }
              catch(Exception eee){}
              loginStudent.remove(verifyMess);
              System.out.println("�ͻ��뿪��");
              break;
          }             
       } 
    }
}