package ch1;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class ShowRecord extends JDialog implements ActionListener
{ 
   File gradeFile;
   JButton clear;
   JTextArea showArea=null;
   People people;
   TreeSet<People> treeSet;
   public ShowRecord(File f){
      treeSet=new TreeSet<People>(); 
      showArea=new JTextArea(6,4);
      clear=new JButton("������а�");
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
      try{
           RandomAccessFile in=new RandomAccessFile(gradeFile,"rw");
           String name=null;
           while((name=in.readUTF())!=null){
              int time=in.readInt();
              people =new People(name,time);
              treeSet.add(people);
           } 
           Iterator<People> iter=treeSet.iterator();
           while(iter.hasNext()){
              People p=iter.next();
              showArea.append("����:"+p.getName()+"�ɼ�: "+p.getTime());
              showArea.append("\n");
           }

      }
      catch(IOException exp){}
   }
   public void actionPerformed(ActionEvent e){
      if(e.getSource()==clear){
        try{
             File f=gradeFile.getAbsoluteFile();
             gradeFile.delete();
             f.createNewFile();
             showArea.setText("���а����");
         }
        catch(Exception ee){}
     }    
  }
}
