package ch8;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
public class InitCanju implements MouseListener,MouseMotionListener,Serializable{  
    Point [][] point;
    ChessBox redChessBox,blackChessBox;
    Point [] redChessBoxPoint,blackChessBoxPoint;
    int a,b,x0,y0,x,y;
    Point startPoint=null,endPoint=null;
    public void setPoint(Point [][] point){
        this.point=point;
    }
    public void setRedChessBox(ChessBox redChessBox){
       this.redChessBox=redChessBox;
       redChessBoxPoint=redChessBox.getBoxPoint();  
    }
    public void setBlackChessBox(ChessBox blackChessBox){
       this.blackChessBox=blackChessBox; 
        blackChessBoxPoint=blackChessBox.getBoxPoint(); 
    }
    public void mousePressed(MouseEvent e){
        ChessPiece piece=null;
        piece=(ChessPiece)e.getSource();
        a=piece.getBounds().x;
        b=piece.getBounds().y;
        x0=e.getX();    
        y0=e.getY();
        startPoint=piece.getAtPoint();
    } 
    public void mouseMoved(MouseEvent e){}
    public void mouseDragged(MouseEvent e){
        ChessPiece piece=null;
        piece=(ChessPiece)e.getSource();
        Container con=(Container)piece.getParent();
        if(con instanceof JLayeredPane)
          ((JLayeredPane)con).setLayer(piece,JLayeredPane.DRAG_LAYER); 
        a=piece.getBounds().x;
        b=piece.getBounds().y;
        x=e.getX();     
        y=e.getY();
        a=a+x;
        b=b+y;
        piece.setLocation(a-x0,b-y0);
    }
    public void mouseReleased(MouseEvent e){
         ChessPiece piece=null;
         piece=(ChessPiece)e.getSource();
         int w=piece.getBounds().width;
         int h=piece.getBounds().height;
         Container con=(Container)piece.getParent();
         if(con instanceof JLayeredPane) 
            ((JLayeredPane)con).setLayer(piece,JLayeredPane.DEFAULT_LAYER);
         Rectangle rect=piece.getBounds();
         for(int i=0;i<point.length;i++){
            for(int j=0;j<point[i].length;j++){
              if(rect.contains(point[i][j].getX(),point[i][j].getY())){
                 endPoint=point[i][j];
                 break;
              }
            }
         }
         if(piece.getIsRed()){
            for(int i=0;i<redChessBoxPoint.length;i++){
              if(rect.contains(redChessBoxPoint[i].getX(),redChessBoxPoint[i].getY())){
                 endPoint=redChessBoxPoint[i];
                 break;
              } 
            } 
         }
         else if(piece.getIsBlack()){
            for(int i=0;i<blackChessBoxPoint.length;i++){
              if(rect.contains(blackChessBoxPoint[i].getX(),blackChessBoxPoint[i].getY())){
                 endPoint=blackChessBoxPoint[i];
                 break;
              } 
            } 
         }
         if(endPoint!=null&&endPoint.isHaveChessPiece()==false){
            piece.setLocation(endPoint.getX()-w/2,endPoint.getY()-h/2);
            piece.setAtPoint(endPoint);
            endPoint.setHaveChessPiece(true);
            endPoint.setChessPiece(piece);
            startPoint.setHaveChessPiece(false);
         }
         else
           piece.setLocation(startPoint.getX()-w/2,startPoint.getY()-h/2); 
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
}