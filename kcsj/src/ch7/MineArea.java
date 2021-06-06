package ch7;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;
public class MineArea extends JPanel implements ActionListener,MouseListener{
     JButton reStart;
     JButton teachGame;
     JButton randomBlockZo;
     JButton changeButton;
     Block [][] block;
     BlockView [][] blockView;
     LayMines lay;
     int row,colum,mineCount,markMount;//雷区的行数、列数以及地雷个数和用户给出的标记数
     ImageIcon mark;
     int grade; 
     JPanel pCenter,pNorth;
     JTextField showTime,showMarkedMineCount; //显示用时以及标记数
     Timer time;  //计时器
     int spendTime=0;
     Record record;
     public MineArea(int row,int colum,int mineCount,int grade) {
    	 
         reStart=new JButton("重新开始");
         teachGame = new JButton("教程");
         randomBlockZo = new JButton("随机生成雷区");
         changeButton = new JButton("更换为入门模式");
         mark=new ImageIcon("mark.gif");  //探雷标记
         time=new Timer(1000,this);
         showTime=new JTextField(5);
         showTime.setEditable(false);
         showMarkedMineCount=new JTextField(5);
         showMarkedMineCount.setEditable(false);
         showTime.setHorizontalAlignment(JTextField.CENTER);
         showMarkedMineCount.setHorizontalAlignment(JTextField.CENTER);
         showMarkedMineCount.setFont(new Font("Arial",Font.BOLD,8));
         showTime.setFont(new Font("Arial",Font.BOLD,8));         
         pCenter=new JPanel();
         pNorth=new JPanel();
         lay=new LayMines();             
         initMineArea(row,colum,mineCount,grade); //初始化雷区,见下面的LayMines()
         reStart.addActionListener(this);
         teachGame.addActionListener(this);
         randomBlockZo.addActionListener(this);
         changeButton.addActionListener(this);
         pNorth.add(showMarkedMineCount);
         pNorth.add(reStart);
         pNorth.add(teachGame);
         pNorth.add(randomBlockZo);
         pNorth.add(changeButton);
         pNorth.add(showTime);
         setLayout(new BorderLayout());
         add(pNorth,BorderLayout.NORTH);
         add(pCenter,BorderLayout.CENTER);
    }
    public void initMineArea(int row,int colum,int mineCount,int grade){
       pCenter.removeAll();
       spendTime=0;
       markMount=mineCount;
       this.row=row;
       this.colum=colum;
       this.mineCount=mineCount; 
       this.grade=grade; 
       block=new Block[row][colum];
       for(int i=0;i<row;i++){
         for(int j=0;j<colum;j++)
              block[i][j]=new Block();
       }
       lay.layMinesForBlock(block,mineCount);    
       blockView=new BlockView[row][colum];
       pCenter.setLayout(new GridLayout(row,colum));
       for(int i=0;i<row;i++) {
          for(int j=0;j<colum;j++) {
               blockView[i][j]=new BlockView(); 
               blockView[i][j].giveView(block[i][j]); //给block[i][j]提供视图
               pCenter.add(blockView[i][j]);
               blockView[i][j].getBlockCover().addActionListener(this);
               blockView[i][j].getBlockCover().addMouseListener(this);
               blockView[i][j].seeBlockCover();
               blockView[i][j].getBlockCover().setEnabled(true);
               blockView[i][j].getBlockCover().setIcon(null);
          }
       }
      showMarkedMineCount.setText(""+markMount); 
      validate();
    }
   public void setRow(int row){
       this.row=row;
   }
   public void setColum(int colum){
       this.colum=colum;
   }
   public void setMineCount(int mineCount){
       this.mineCount=mineCount;
   }
   public void setGrade(int grade) {
       this.grade=grade;
   }
   public void actionPerformed(ActionEvent e) {
	   if(e.getSource()==changeButton) {
		   initMineArea(10, 10, 16, 1);
	       JOptionPane.showMessageDialog(null, "入门模式切换成功！");
	   }
	    if(e.getSource()==randomBlockZo) {
	    	initMineArea(16, 16, 40, 1);
	    	JOptionPane.showMessageDialog(null, "随机雷区生成成功！");	    	
	    }
	    if(e.getSource()==teachGame) {
	    	JOptionPane.showMessageDialog(null, "1.找出空的方块和雷区。如果确定该方块为空，可以左键点击一下，如果认为是雷可以右键点击一下插上小旗，右击两次代表不确认。\n2.右上角代表游戏时间，左上角代表剩余雷的数量。\n3.首先开始我们都不知道哪里是雷，就需要任意选择一个。\n4.数字表示包围该区域的其他八个区域一共含有数字表示的个数的地雷。如果你确定某个区域是雷，就右击鼠标标记该区域。\n5.胜利条件：标记全部雷，成功点出所有非地雷区域。失败条件：鼠标左击出现地雷，游戏结束。");
	    }
        if(e.getSource()!=reStart&&e.getSource()!=time) {
          time.start();  
          int m=-1,n=-1; 
          for(int i=0;i<row;i++) {
             for(int j=0;j<colum;j++) {
               if(e.getSource()==blockView[i][j].getBlockCover()){
                  m=i;
                  n=j;
                  break;
               }
             }
          }
          if(block[m][n].isMine()) {
             for(int i=0;i<row;i++) {
                for(int j=0;j<colum;j++) {
                   blockView[i][j].getBlockCover().setEnabled(false);
                   if(block[i][j].isMine())
                      blockView[i][j].seeBlockNameOrIcon(); 
                }
             }
             JOptionPane.showMessageDialog(null, "游戏结束！");
             time.stop();
             spendTime=0;
             markMount=mineCount;
          }
         else { 
             show(m,n);          //见本类后面的show方法
          }
      }
      if(e.getSource()==reStart) {
         initMineArea(row,colum,mineCount,grade);
      }
      if(e.getSource()==time){
         spendTime++;
         showTime.setText(""+spendTime);
      }
      inquireWin();
    }
    
    public void show(int m,int n) {
      if(block[m][n].getAroundMineNumber()>0&&block[m][n].getIsOpen()==false){
          blockView[m][n].seeBlockNameOrIcon();
          block[m][n].setIsOpen(true);
          return;
      }
      else if(block[m][n].getAroundMineNumber()==0&&block[m][n].getIsOpen()==false){
          blockView[m][n].seeBlockNameOrIcon();
          block[m][n].setIsOpen(true);
          for(int k=Math.max(m-1,0);k<=Math.min(m+1,row-1);k++) {
             for(int t=Math.max(n-1,0);t<=Math.min(n+1,colum-1);t++)
                 show(k,t);
          } 
      }      
    }
    public void mousePressed(MouseEvent e){
        JButton source=(JButton)e.getSource();
        for(int i=0;i<row;i++) {
            for(int j=0;j<colum;j++) {
              if(e.getModifiers()==InputEvent.BUTTON3_MASK&&
                 source==blockView[i][j].getBlockCover()){
                 if(block[i][j].getIsMark()) {
                        source.setIcon(null);
                        block[i][j].setIsMark(false);
                        markMount=markMount+1;
                        showMarkedMineCount.setText(""+markMount);
                 }
                 else{
                        source.setIcon(mark);
                        block[i][j].setIsMark(true);
                        markMount=markMount-1;
                        showMarkedMineCount.setText(""+markMount);
                 }
              }    
            }
        }
   }
   public void inquireWin(){
        int number=0;
        for(int i=0;i<row;i++) {
            for(int j=0;j<colum;j++) {
              if(block[i][j].getIsOpen()==false)
                number++;
            }
        }
        if(number==mineCount){
           time.stop();
           record=new Record();
           switch(grade){
              case 1: record.setGrade("初级");
                      break;
              case 2: record.setGrade("中级");
                      break;
              case 3: record.setGrade("高级");
                      break;
           }
          record.setTime(spendTime);
          record.setVisible(true); 
        }
            
   
   }
   public void mouseReleased(MouseEvent e){}
   public void mouseEntered(MouseEvent e){}
   public void mouseExited(MouseEvent e){}
   public void mouseClicked(MouseEvent e){}
} 
