package ch1;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class ShowRecordDialog extends JDialog implements ActionListener{
   File gradeFile;
   JButton clear;
   JTextArea showArea=null;
   TreeSet<People> treeSet;
   public ShowRecordDialog(){
      treeSet=new TreeSet<People>(); 
      showArea=new JTextArea(6,4);
      showArea.setFont(new Font("楷体",Font.BOLD,20));
      clear=new JButton("清空排行榜");
      clear.addActionListener(this);
      add(new JScrollPane(showArea),BorderLayout.CENTER);
      add(clear,BorderLayout.SOUTH);
      setBounds(100,100,320,185);
      setModal(true); 
      addWindowListener(new WindowAdapter(){
                           public void windwoClosing(WindowEvent e){
                              setVisible(false);
                           }
                        }); 
   }
   public void setGradeFile(File f){
      gradeFile=f;
      setTitle(f.getName());
   }
   public void showRecord(){
       showArea.setText(null);
       treeSet.clear(); 
       try{
           RandomAccessFile in=new RandomAccessFile(gradeFile,"rw");
           long fileLength=in.length();
           long readPosition=0; 
           while(readPosition<fileLength){
              String name=in.readUTF();  
              int time=in.readInt();
              readPosition=in.getFilePointer();
              People people=new People(name,time);
              treeSet.add(people);
           } 
           in.close();
           Iterator<People> iter=treeSet.iterator();
           while(iter.hasNext()){
              People p=iter.next();
              showArea.append("姓名:"+p.getName()+",成绩: "+p.getTime()+"秒");
              showArea.append("\n");
           }
      }
      catch(IOException exp){System.out.println(exp);}
   }
   public void actionPerformed(ActionEvent e){
      if(e.getSource()==clear){
        try{
             File f=gradeFile.getAbsoluteFile();
             gradeFile.delete();
             f.createNewFile();
             showArea.setText("排行榜被清空");
         }
        catch(Exception ee){}
     }    
  }
}
