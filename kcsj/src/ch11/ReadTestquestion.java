package ch11;
import java.io.*;
public class ReadTestquestion{
   public String getTestContent(File f){ 
        String testContent="";  
        try{ FileReader in=new FileReader(f);
             BufferedReader reader=new BufferedReader(in);            
             String correctAnswer=(reader.readLine()).trim(); 
             String lineContent=null;
             StringBuffer temp=new StringBuffer();
             while((lineContent=reader.readLine())!=null){
                    temp.append(lineContent+"\n") ; 
             }
             in.close();
             reader.close();
             testContent=new String(temp); 
         } 
        catch(IOException e){
           testContent="没有选择试题文件";
        }
        return testContent;
   }
   public String getCorrectAnswer(File f){
        String correctAnswer="";  
        try{ FileReader in=new FileReader(f);
             BufferedReader reader=new BufferedReader(in);            
             correctAnswer=(reader.readLine()).trim(); 
             in.close();
             reader.close();
        } 
        catch(IOException e){}
        return correctAnswer;
   }
}

