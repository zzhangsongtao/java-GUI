package ch1;
import javax.swing.*; 
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
public class MemoryTestArea extends JPanel implements ActionListener,Runnable {
  int row,col;
  File gradeFile; 
  ArrayList<Block> allBlockList;                         
  String  imageFileName[]; 
  LinkedList<ImageIcon> openIconList;             
  LinkedList<Block>    openBlockList;
  int success=0; 
  Thread hintThead;
  JButton hintButton;
  int usedTime=0;
  JTextField showUsedTime,hintMessage;
  javax.swing.Timer timer;
  Record record;
  JPanel center,south;
  MemoryTestArea(){
     setLayout(new BorderLayout());
     allBlockList=new ArrayList<Block>();
     openIconList=new LinkedList<ImageIcon>();
     openBlockList=new LinkedList<Block>();
     hintThead=new Thread(this);
     hintMessage=new JTextField();
     hintMessage.setHorizontalAlignment(JTextField.CENTER);
     hintMessage.setEditable(false);
     hintMessage.setFont(new Font("宋体",Font.BOLD,18));
     center=new JPanel();
     south=new JPanel();
     hintButton=new JButton("提示");
     hintButton.addActionListener(this);
     showUsedTime=new JTextField(8);
     showUsedTime.setEditable(false);
     showUsedTime.setHorizontalAlignment(JTextField.CENTER);
     south.add(new JLabel("用时:")) ;
     south.add(showUsedTime);
     south.add(new JLabel("提示图标位置(导致用时增加):")) ;
     south.add(hintButton);
     add(south,BorderLayout.SOUTH);
     add(hintMessage,BorderLayout.NORTH); 
     timer=new javax.swing.Timer(1000,this);
     record=new Record(); 
  }
  public void initBlock(int m,int n,String name[],File f){
     row=m;
     col=n;
     gradeFile=f;
     center.removeAll();
     imageFileName=name;
     ImageIcon icon[]=new ImageIcon[imageFileName.length];
     for(int i=0;i<icon.length;i++){
        icon[i]=new ImageIcon(imageFileName[i]); 
     } 
     if(allBlockList.isEmpty()){
          for(int i=0;i<row*col;i++){
             allBlockList.add(new Block());
          }
     }
     else{
           allBlockList.clear(); 
           for(int i=0;i<row*col;i++){
             allBlockList.add(new Block());
           }
     }
     for(int i=0;i<allBlockList.size();i++){
        allBlockList.get(i).addActionListener(this);
        allBlockList.get(i).setOpenStateIcon(icon[i%row]); 
     } 
     Collections.shuffle(allBlockList);       
     center.setLayout(new GridLayout(row,col));
     for(int i=0;i<allBlockList.size();i++){
        center.add(allBlockList.get(i));
     }
     add(center,BorderLayout.CENTER);
     if(timer.isRunning()){
         timer.stop();
     }
     hintMessage.setText("您需要用鼠标单击出"+col+"个同样图标的方块");
     usedTime=0;
     showUsedTime.setText(null);
     validate(); 
  }
  public void setImageName(String name[]){
     imageFileName=name;
  }
  public void actionPerformed(ActionEvent e){
    if(e.getSource() instanceof Block){
        if(!timer.isRunning())
           timer.start();
        Block block=(Block)e.getSource();
        ImageIcon openStateIcon=block.getOpenStateIcon();
        block.setIcon(openStateIcon);
        if(openIconList.size()==0) {                                 
          openIconList.add(openStateIcon);
          openBlockList.add(block);
          success=1;                                            
        }
        else{
           ImageIcon temp=openIconList.getLast();        
           if(temp==openStateIcon&&!(openBlockList.contains(block))){  
                success=success+1;                              
                openIconList.add(openStateIcon);                     
                openBlockList.add(block);
                if(success==col){ 
                     for(int i=0;i<allBlockList.size();i++){           
                        allBlockList.get(i).setEnabled(false);
                     }
                     for(int j=0;j<openBlockList.size();j++){
                        Block b=openBlockList.get(j);
                        b.setDisabledIcon(b.getOpenStateIcon());
                     } 
                     timer.stop();
                     record.setTime(usedTime);
                     record.setGradeFile(gradeFile);
                     record.setVisible(true);
                  }    
           }
           else if((temp!=openStateIcon)&&(!(openBlockList.contains(block)))){ 
                openIconList.clear();                          
                openBlockList.clear();
                openIconList.add(openStateIcon);                
                openBlockList.add(block);
                success=1;                                 
                for(int i=0;i<allBlockList.size();i++){            
                   if(allBlockList.get(i)!=block)
                      allBlockList.get(i).setIcon(null);
                }                
           }  
        }
    }
   if(e.getSource()==hintButton){ 
       if(!hintThead.isAlive())
           hintThead=new Thread(this);
       for(int i=0;i<allBlockList.size();i++)           
           allBlockList.get(i).removeActionListener(this);
       usedTime=usedTime+10;
       try{
           hintThead.start();  
       }
       catch(IllegalThreadStateException ex){}
    }
   if(e.getSource()==timer){ 
       usedTime++;
       showUsedTime.setText("您的用时:"+usedTime+"秒");  
    }
  }
  public void run(){
     for(int i=0;i<allBlockList.size();i++)           
        allBlockList.get(i).setIcon(allBlockList.get(i).getOpenStateIcon());
     try{ Thread.sleep(1200);
     }
     catch(InterruptedException exp){}
     for(int i=0;i<allBlockList.size();i++)           
        allBlockList.get(i).addActionListener(this);
     for(int i=0;i<allBlockList.size();i++)
       if(!openBlockList.contains(allBlockList.get(i)))           
         allBlockList.get(i).setIcon(null);
  }
}