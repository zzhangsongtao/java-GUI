package ch8;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
public class ChessBoard extends JLayeredPane implements ActionListener{
     int m=10,n=9,distance=42;
     Point [][] playPoint;
     String [] redName={"帅","士","士","相","相","马","马","车","车","炮","炮",                                      "兵","兵","兵","兵","兵"};
     String [] blackName={"将","仕","仕","象","象","馬","馬","車","車","炮","炮",
                         "卒","卒","卒","卒","卒"};      
     ChessPiece [] redPiece;
     ChessPiece [] blackPiece;
     ChessBox redChessBox,blackChessBox;
     InitPieceLocation initPieceLocation;
     InitCanju initCanju;
     HandleMouse handleMouse;
     ArrayList<Point> step;
     JButton cancel;  
     public ChessBoard(){
          setLayout(null);
          step=new ArrayList<Point>();
          initPointAndPiece();
          redChessBox=new ChessBox(distance,11,7);
          blackChessBox=new ChessBox(distance,11,1);
          for(int i=0;i<redPiece.length;i++) 
              redChessBox.putPieceToBox(redPiece[i]);
          for(int i=0;i<blackPiece.length;i++) 
              blackChessBox.putPieceToBox(blackPiece[i]);  
          initPieceLocation=new InitPieceLocation();
          initPieceLocation.setPoint(playPoint);
          initPieceLocation.setRedChessPiece(redPiece);
          initPieceLocation.setBlackChessPiece(blackPiece);
          initPieceLocation.setRedChessBox(redChessBox);
          initPieceLocation.setBlackChessBox(blackChessBox);
          initCanju=new InitCanju();
          initCanju.setPoint(playPoint);
          initCanju.setRedChessBox(redChessBox);
          initCanju.setBlackChessBox(blackChessBox);
          handleMouse=new HandleMouse();
          handleMouse.setStep(step);
          handleMouse.setPoint(playPoint);
          handleMouse.setRedChessBox(redChessBox);
          handleMouse.setBlackChessBox(blackChessBox);
          handleMouse.initStep();
          cancel=new JButton("悔棋");
          add(cancel);
          cancel.setVisible(false);
          cancel.setSize(60,30);
          cancel.setLocation(13*distance,(int)(5*distance));
          cancel.addActionListener(this); 
     }
     private void initPointAndPiece() {
         removeAll();
         playPoint=new Point[m][n];
         int Hspace=distance,Vspace=distance;
         for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
               playPoint[i][j]=new Point(distance+Hspace,Vspace);
               playPoint[i][j].setIsPlayPoint(true);
               Hspace=Hspace+distance;
            }
            Hspace=distance;
            Vspace=Vspace+distance;
         }
         redPiece=new ChessPiece[redName.length];
         int size=distance;
         for(int i=0;i<redPiece.length;i++){
            redPiece[i]=new ChessPiece();
            add(redPiece[i],JLayeredPane.PALETTE_LAYER);
            redPiece[i].setSize(size,size);
            redPiece[i].setIsRed(true);
            redPiece[i].setName(redName[i]);
            redPiece[i].repaint();
         } 
         blackPiece=new ChessPiece[blackName.length];
         for(int i=0;i<blackPiece.length;i++){
            blackPiece[i]=new ChessPiece();
            add(blackPiece[i],JLayeredPane.PALETTE_LAYER);
            blackPiece[i].setSize(size,size);
            blackPiece[i].setIsBlack(true);
            blackPiece[i].setName(blackName[i]);
            blackPiece[i].repaint();
         } 
     }
     public ArrayList<Point> getStep(){
         return step;
     }
     public Point [][] getPoint(){
         return playPoint;
     }
     public void setShizhanPlay(){
         handleMouse.setRedFirstMove(true);
         handleMouse.initStep();
         cancel.setVisible(false);
         initPieceLocation.putAllPieceToPlayChessArea();
         for(int i=0;i<redPiece.length;i++){
             MouseListener [] listener=redPiece[i].getMouseListeners();
             for(int k=0;k<listener.length;k++)
                redPiece[i].removeMouseListener(listener[k]);
             listener=blackPiece[i].getMouseListeners();
             for(int k=0;k<listener.length;k++)
                blackPiece[i].removeMouseListener(listener[k]);
             MouseMotionListener [] mr=redPiece[i].getMouseMotionListeners();
             for(int k=0;k<mr.length;k++)
                redPiece[i].removeMouseMotionListener(mr[k]);
             mr=blackPiece[i].getMouseMotionListeners();
             for(int k=0;k<mr.length;k++)
                blackPiece[i].removeMouseMotionListener(mr[k]);
         }  
     }
     public void setCanjuPlay(){
         handleMouse.setRedFirstMove(true);
         handleMouse.initStep();
         cancel.setVisible(false);
         initPieceLocation.putAllPieceToChessBox();
         for(int i=0;i<redPiece.length;i++){
             MouseListener [] listener=redPiece[i].getMouseListeners();
             for(int k=0;k<listener.length;k++)
                redPiece[i].removeMouseListener(listener[k]);
             listener=blackPiece[i].getMouseListeners();
             for(int k=0;k<listener.length;k++)
                blackPiece[i].removeMouseListener(listener[k]);
             MouseMotionListener [] mr=redPiece[i].getMouseMotionListeners();
             for(int k=0;k<mr.length;k++)
                redPiece[i].removeMouseMotionListener(mr[k]);
             mr=blackPiece[i].getMouseMotionListeners();
             for(int k=0;k<mr.length;k++)
                blackPiece[i].removeMouseMotionListener(mr[k]);  
             redPiece[i].addMouseListener(initCanju);
             blackPiece[i].addMouseListener(initCanju);
             redPiece[i].addMouseMotionListener(initCanju);
             blackPiece[i].addMouseMotionListener(initCanju); 
         } 
        
     }
     public void startMakeManul(){
        cancel.setVisible(true);
        handleMouse.setRedFirstMove(true);
        handleMouse.initStep();
        for(int i=0;i<redPiece.length;i++){
             MouseListener [] listener=redPiece[i].getMouseListeners();
             for(int k=0;k<listener.length;k++)
                redPiece[i].removeMouseListener(listener[k]);
             listener=blackPiece[i].getMouseListeners();
             for(int k=0;k<listener.length;k++)
                blackPiece[i].removeMouseListener(listener[k]);
             MouseMotionListener [] mr=redPiece[i].getMouseMotionListeners();
             for(int k=0;k<mr.length;k++)
                redPiece[i].removeMouseMotionListener(mr[k]);
             mr=blackPiece[i].getMouseMotionListeners();
             for(int k=0;k<mr.length;k++)
                blackPiece[i].removeMouseMotionListener(mr[k]);
             redPiece[i].addMouseListener(handleMouse);
             blackPiece[i].addMouseListener(handleMouse);
             redPiece[i].addMouseMotionListener(handleMouse);
             blackPiece[i].addMouseMotionListener(handleMouse);
         } 
     }
     public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.cyan);
        g.fillRect(0,0,this.getBounds().width,this.getBounds().height);
        g.setColor(Color.orange);
        g.fillRect(playPoint[0][0].x,playPoint[0][0].y,8*distance,9*distance);
        g.setColor(Color.black);
        for(int i=0;i<=m-1;i++){   
          g.drawLine(playPoint[i][0].x,playPoint[i][0].y,
                     playPoint[i][n-1].x,playPoint[i][n-1].y); 
        }
        for(int j=0;j<=n-1;j++){ 
          if(j>0&&j<n-1){  
           g.drawLine(playPoint[0][j].x,playPoint[0][j].y,
           playPoint[4][j].x,playPoint[4][j].y);
           g.drawLine(playPoint[5][j].x,playPoint[5][j].y,
           playPoint[m-1][j].x,playPoint[m-1][j].y); 
          } 
          else{
           g.drawLine(playPoint[0][j].x,playPoint[0][j].y,
           playPoint[m-1][j].x,playPoint[m-1][j].y);
          }
        }
        g.drawLine(playPoint[0][3].x,playPoint[0][3].y,
                   playPoint[2][5].x,playPoint[2][5].y);
        g.drawLine(playPoint[0][5].x,playPoint[0][5].y,
                   playPoint[2][3].x,playPoint[2][3].y);
        g.drawLine(playPoint[7][3].x,playPoint[7][3].y,
                   playPoint[m-1][5].x,playPoint[m-1][5].y);
        g.drawLine(playPoint[7][5].x,playPoint[7][5].y,
                   playPoint[m-1][3].x,playPoint[m-1][3].y);
        g.setFont(new Font("宋体",Font.BOLD,18));
        int w=blackPiece[0].getBounds().width;
        int h=blackPiece[0].getBounds().height; 
        Point [] boxPoint=blackChessBox.getBoxPoint(); 
        g.drawString("黑棋盒",boxPoint[5].getX(),boxPoint[5].getY());
        g.drawRect(boxPoint[0].getX()-w/2-2,boxPoint[0].getY()-2*h/3,
                   4*distance+5,4*distance+8);  
        g.setColor(Color.red);
        boxPoint=redChessBox.getBoxPoint();
        w=redPiece[0].getBounds().width;
        h=redPiece[0].getBounds().height;
        g.drawString("红棋盒",boxPoint[5].getX(),boxPoint[5].getY());
        g.drawRect(boxPoint[0].getX()-w/2-2,boxPoint[0].getY()-2*h/3,
                   4*distance+5,4*distance+8);
        g.setColor(Color.black);
        for(int j=1;j<=n;j++){
            g.drawString(""+j,(1+j)*distance-5,2*distance/5+2);
            g.drawString(""+j,(1+j)*distance-5,11*distance-5);
        } 
        int t=1;
        for(char c='a';c<='j';c++,t++)
            g.drawString(""+c,distance+3,t*distance+distance/5);
     }
     public ChessPiece [] getRedPiece(){
        return redPiece;
     }
     public ChessPiece [] getBlackPiece(){
        return blackPiece;
     }
     public void actionPerformed(ActionEvent e){
         if(step.size()>0)
           handleMouse.cancelAnStep();
         else
          JOptionPane.showMessageDialog(this,"无法再悔棋","提示对话框",
                                        JOptionPane.WARNING_MESSAGE); 
     } 
} 
