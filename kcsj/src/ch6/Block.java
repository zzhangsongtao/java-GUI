package ch6;
import javax.swing.*;
import java.awt.*;
public class Block extends JTextField{
   Point point;
   Object object;
   Block(){
      setEditable(false);
      setHorizontalAlignment(JTextField.CENTER);
      setFont(new Font("Arial",Font.BOLD,16));
      setForeground(Color.blue);
   } 
   public void setAtPoint(Point p){
      point=p;
   }
   public Point getAtPoint(){
      return point;
   }
   public void setObject(Object object){
      this.object=object;
      if(object instanceof Integer){
         Integer number=(Integer)object;
         setText(""+number.intValue());
      }
      else if(object instanceof Image){
         repaint();
      }
   }
   public Object getObject(){
      return object;
   }
   public void paintComponent(Graphics g){
      super.paintComponent(g);  
      int w=getBounds().width;
      int h=getBounds().height;
      try{ 
           g.drawImage((Image)object,0,0,w,h,this);
      }
      catch(Exception exp){}
   }
}
