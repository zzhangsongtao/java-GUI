package ch8;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
public class HandleMouse implements MouseListener,MouseMotionListener,Serializable{
    Point [][] point;
    ChessBox redChessBox,blackChessBox;
    MoveRule rule;
    ArrayList<Point> step;
    int a,b,x0,y0,x,y;
    boolean redFirstMove,blackFirstMove,isPlaypoint;
    Point startPoint=null,endPoint=null;
    HandleMouse(){
       redFirstMove=true;
       blackFirstMove=false;
       rule=new MoveRule();
    } 
    public void setStep(ArrayList<Point> step){
       this.step=step;
    }
    public void setRedFirstMove(boolean boo){
       redFirstMove=true;
       blackFirstMove=false;
    }
    public void setPoint(Point [][] point){
        this.point=point;
        rule.setPoint(point);
    }
    public void setRedChessBox(ChessBox redChessBox){
       this.redChessBox=redChessBox;  
    }
    public void setBlackChessBox(ChessBox blackChessBox){
       this.blackChessBox=blackChessBox;  
    }
    public void initStep(){
       step.clear();
    }
    public ArrayList<Point> getStep(){
       return step;
    }
     public void mousePressed(MouseEvent e){
        ChessPiece piece=null;
        piece=(ChessPiece)e.getSource();
        a=piece.getBounds().x;
        b=piece.getBounds().y;
        x0=e.getX();    
        y0=e.getY();
        startPoint=piece.getAtPoint(); 
        for(int i=0;i<point.length;i++){
           for(int j=0;j<point[i].length;j++){
               if(point[i][j].equals(startPoint))
                isPlaypoint=true;   
           } 
        }        
    } 
    public void mouseMoved(MouseEvent e){}
    public void mouseDragged(MouseEvent e){
        ChessPiece piece=null;
        piece=(ChessPiece)e.getSource();
        Container con=(Container)piece.getParent(); 
        a=piece.getBounds().x;
        b=piece.getBounds().y;
        x=e.getX();     
        y=e.getY();
        a=a+x;
        b=b+y;
        if(con instanceof JLayeredPane)
             ((JLayeredPane)con).setLayer(piece,JLayeredPane.DRAG_LAYER);
        if(piece.getIsRed()&&redFirstMove)
             piece.setLocation(a-x0,b-y0);
        if(piece.getIsBlack()&&blackFirstMove)
            piece.setLocation(a-x0,b-y0);
    }
    public void mouseReleased(MouseEvent e){
        ChessPiece piece=null;
        piece=(ChessPiece)e.getSource();
        int w=piece.getBounds().width;
        int h=piece.getBounds().height;
        Container con=(Container)piece.getParent();
        if(con instanceof JLayeredPane) 
           ((JLayeredPane)con).setLayer(piece,JLayeredPane.PALETTE_LAYER);
        Rectangle rect=piece.getBounds();
        for(int i=0;i<point.length;i++){
           for(int j=0;j<point[i].length;j++){
              if(rect.contains(point[i][j].getX(),point[i][j].getY())){
                 endPoint=point[i][j];
                 break;
              }
           }
        }
        if(endPoint!=null&&isPlaypoint){
            if(endPoint.isHaveChessPiece()){
                if(endPoint.getChessPiece().getIsRed()==
                   startPoint.getChessPiece().getIsRed())
                   piece.setLocation(startPoint.getX()-w/2,startPoint.getY()-h/2);
                else{
                   boolean ok=rule.movePieceRule(piece,startPoint,endPoint);
                   if(ok){
                       ChessPiece backToBoxPiece=endPoint.getChessPiece();
                       Point boxPoint=null;
                       if(backToBoxPiece.getIsRed())
                          boxPoint=redChessBox.putPieceToBox(backToBoxPiece);
                       else if(backToBoxPiece.getIsBlack())
                          boxPoint=blackChessBox.putPieceToBox(backToBoxPiece);
                       piece.setLocation(endPoint.getX()-w/2,endPoint.getY()-h/2);
                       piece.setAtPoint(endPoint);
                       endPoint.setHaveChessPiece(true);
                       endPoint.setChessPiece(piece);
                       startPoint.setHaveChessPiece(false);
                       step.add(startPoint);
                       step.add(endPoint);
                       step.add(boxPoint); 
                       if(piece.getIsRed()){
                          redFirstMove=false;
                          blackFirstMove=true;
                       }
                       else if(piece.getIsBlack()){
                          blackFirstMove=false;
                          redFirstMove=true;
                       } 
                   }  
                   else
                    piece.setLocation(startPoint.getX()-w/2,startPoint.getY()-h/2);                 }
            }
            else{
                boolean ok=rule.movePieceRule(piece,startPoint,endPoint);
                if(ok){
                       piece.setLocation(endPoint.getX()-w/2,endPoint.getY()-h/2);
                       piece.setAtPoint(endPoint);
                       endPoint.setHaveChessPiece(true);
                       endPoint.setChessPiece(piece);
                       startPoint.setHaveChessPiece(false);
                       step.add(startPoint);
                       step.add(endPoint);
                       step.add(null);  
                       if(piece.getIsRed()){
                          redFirstMove=false;
                          blackFirstMove=true;
                       }
                       else if(piece.getIsBlack()){
                          blackFirstMove=false;
                          redFirstMove=true;
                       }  
                   }  
                   else
                    piece.setLocation(startPoint.getX()-w/2,startPoint.getY()-h/2); 
            }
        }
        else
         piece.setLocation(startPoint.getX()-w/2,startPoint.getY()-h/2); 
    }
    public void cancelAnStep(){
         int length=step.size();
         if(length>=3){ 
            Point boxPoint=step.get(length-1);
            Point endPoint=step.get(length-2);
            Point startPoint=step.get(length-3); 
            ChessPiece piece=null;
            if(boxPoint!=null){
              piece=endPoint.getChessPiece();
              if(piece.getIsRed()){
                 redFirstMove=true;
                 blackFirstMove=false;
              }
              else if(piece.getIsBlack()){
                 blackFirstMove=true;
                 redFirstMove=false;
              }
              int w=piece.getBounds().width;
              int h=piece.getBounds().height;
              piece.setLocation(startPoint.getX()-w/2,startPoint.getY()-h/2);
              piece.setAtPoint(startPoint);
              startPoint.setHaveChessPiece(true);
              startPoint.setChessPiece(piece);
              endPoint.setHaveChessPiece(false);
              piece=boxPoint.getChessPiece();
              w=piece.getBounds().width;
              h=piece.getBounds().height;
              piece.setLocation(endPoint.getX()-w/2,endPoint.getY()-h/2);
              piece.setAtPoint(endPoint);
              endPoint.setHaveChessPiece(true);
              endPoint.setChessPiece(piece);
              boxPoint.setHaveChessPiece(false);
           }
           else{
              piece=endPoint.getChessPiece();
              if(piece.getIsRed()){
                 redFirstMove=true;
                 blackFirstMove=false;
              }
              else if(piece.getIsBlack()){
                 blackFirstMove=true;
                 redFirstMove=false;
              }
              int w=piece.getBounds().width;
              int h=piece.getBounds().height;
              piece.setLocation(startPoint.getX()-w/2,startPoint.getY()-h/2);
              piece.setAtPoint(startPoint);
              startPoint.setHaveChessPiece(true);
              startPoint.setChessPiece(piece);
              endPoint.setHaveChessPiece(false);
           }
           step.remove(step.size()-1);
           step.remove(step.size()-1);
           step.remove(step.size()-1);
       }     
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
}