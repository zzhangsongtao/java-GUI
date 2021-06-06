package ch5;
import javax.swing.*;
import java.awt.*;
public class PersonInMaze extends JTextField{
   MazePoint point;
   Toolkit tool;
   PersonInMaze(){
      tool=getToolkit();
      setEditable(false);
      setBorder(null);
      setOpaque(false);
      setToolTipText("单击我,然后按键盘方向键");
   }
   public void setAtMazePoint(MazePoint p){
      point=p;
   }
   public MazePoint getAtMazePoint(){
      return point;
   }
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      int w=getBounds().width;
      int h=getBounds().height; 
      Image image=tool.getImage("person.gif");  
      g.drawImage(image,0,0,w,h,this);
  } 
}