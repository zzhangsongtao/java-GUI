package ch8;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
public class ChessPiece extends JPanel{
   String name;
   Point point;
   boolean isRed,isBlack;
   Color color;
   ChessPiece(){
      Cursor c=Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
      setCursor(c);
      setBorder(null);
      setOpaque(false);
   }
   public void setName(String s){
       name=s;
   }
   public String getName(){
       return name;
   }
   public void setIsRed(boolean boo){
       isRed=boo;
       color=Color.red;
   }
   public boolean getIsRed(){
       return isRed;
   }
   public void setIsBlack(boolean boo){
       isBlack=boo;
       color=Color.black;
   }
   public boolean  getIsBlack(){
       return isBlack;
   }
   public void setAtPoint(Point p){
      point=p;
   }
   public Point getAtPoint(){
      return point;
   }
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      int w=getBounds().width;
      int h=getBounds().height;
      Image image=null;
      g.setColor(color); 
      g.fillOval(1/2,1/2,w-1,h-1);
      g.setColor(Color.white);
      g.setFont(new Font("¡• È",Font.BOLD,22));
      g.drawString(name,7*w/32,2*h/3);
  }     
}
