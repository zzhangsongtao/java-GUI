package ch8;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
public class DemoManulDialog extends JDialog implements ActionListener{
    ArrayList<Point> step=null;
    Stack<Point> stackStep,stackBack;
    ChessBoard demoOne=null;
    File file;
    JTextArea text;
    JButton previous,next;
    JSplitPane split;
    int stepNumber=1;
    DemoManulDialog(File f){
        file=f;
        setTitle("演示棋谱,棋谱文件:"+f.getName());
        split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); 
        stackStep=new Stack<Point>();
        stackBack=new Stack<Point>();
        next=new JButton("第"+stepNumber+"步");
        previous=new JButton("返回"); 
        next.addActionListener(this);
        previous.addActionListener(this);
        previous.setEnabled(false);
        text=new JTextArea();
        text.setEditable(false);
        text.setFont(new Font("宋体",Font.PLAIN,16));
        JPanel west=new JPanel();
        west.add(text);
        JPanel south=new JPanel();
        south.add(previous);
        south.add(next);
        split.setLeftComponent(new JScrollPane(text));
        add(south,BorderLayout.SOUTH);
        add(split,BorderLayout.CENTER); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showTextManul();
        showBoardManul();
        setBounds(10,10,700,570);
    }
    public void showBoardManul(){
        stepNumber=1;
        stackStep.clear();
        stackBack.clear(); 
        if(demoOne!=null)
           remove(demoOne);
        try{
               FileInputStream file_in=new FileInputStream(file);
               ObjectInputStream object_in=new ObjectInputStream(file_in);
               demoOne=(ChessBoard)object_in.readObject();
               file_in.close();
               object_in.close();
               ChessPiece [] redPiece=demoOne.getRedPiece();
               ChessPiece [] blackPiece=demoOne.getBlackPiece();
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
               step=demoOne.getStep();
               for(int i=step.size()-1;i>=0;i--){
                   stackStep.push(step.get(i)); 
               }
               demoOne.remove(demoOne.cancel);
               restoreChessBoard(step);
               split.setRightComponent(new JScrollPane(demoOne)); 
        }
        catch(Exception exp){
            add(new JButton("不是棋谱文件"),BorderLayout.CENTER);
        }
    }
    public void actionPerformed(ActionEvent e){
        JButton source=(JButton)e.getSource();
        Point startPoint=null,endPoint=null,boxPoint=null;
        if(source==next&&stackStep.empty()==false){
            stepNumber++;
            next.setText("第"+stepNumber+"步");
            startPoint=stackStep.pop();
            stackBack.push(startPoint);
            ChessPiece startPiece=startPoint.getChessPiece();
            int w=startPiece.getBounds().width;
            int h=startPiece.getBounds().height;
            endPoint=stackStep.pop();
            stackBack.push(endPoint);
            boxPoint=stackStep.pop();
            stackBack.push(boxPoint);
            if(boxPoint!=null){
                ChessPiece endPiece=endPoint.getChessPiece();
                endPiece.setLocation(boxPoint.getX()-w/2,boxPoint.getY()-h/2);
                startPiece.setLocation(endPoint.getX()-w/2,endPoint.getY()-h/2);
                endPiece.setAtPoint(boxPoint);
                boxPoint.setHaveChessPiece(true);
                endPoint.setChessPiece(startPiece);
                endPoint.setHaveChessPiece(true);
                startPoint.setHaveChessPiece(false); 
            } 
            else{
                startPiece.setLocation(endPoint.getX()-w/2,endPoint.getY()-h/2);
                startPiece.setAtPoint(endPoint);
                endPoint.setHaveChessPiece(true);
                endPoint.setChessPiece(startPiece);
                startPoint.setHaveChessPiece(false); 
            }
        }
        if(source==previous&&stackBack.empty()==false){
            stepNumber--;
            next.setText("第"+stepNumber+"步");
            boxPoint=stackBack.pop();
            stackStep.push(boxPoint); 
            endPoint=stackBack.pop();
            stackStep.push(endPoint);
            startPoint=stackBack.pop();
            stackStep.push(startPoint); 
            ChessPiece piece=null;
            if(boxPoint!=null){
                piece=endPoint.getChessPiece();
                int w=piece.getBounds().width;
                int h=piece.getBounds().height;
                piece.setLocation(startPoint.getX()-w/2,startPoint.getY()-h/2);
                piece.setAtPoint(startPoint);
                startPoint.setHaveChessPiece(true);
                startPoint.setChessPiece(piece);
                endPoint.setHaveChessPiece(false);
                piece=boxPoint.getChessPiece();
                piece.setLocation(endPoint.getX()-w/2,endPoint.getY()-h/2);
                piece.setAtPoint(endPoint);
                endPoint.setHaveChessPiece(true);
                endPoint.setChessPiece(piece);
                boxPoint.setHaveChessPiece(false);
            }
            else{
                piece=endPoint.getChessPiece();
                int w=piece.getBounds().width;
                int h=piece.getBounds().height;
                piece.setLocation(startPoint.getX()-w/2,startPoint.getY()-h/2);
                piece.setAtPoint(startPoint);
                startPoint.setHaveChessPiece(true);
                startPoint.setChessPiece(piece);
                endPoint.setHaveChessPiece(false);
            }
        }
        if(stackBack.empty())
             previous.setEnabled(false);
        else
             previous.setEnabled(true); 
        if(stackStep.empty()){
            int n=JOptionPane.showConfirmDialog(this,"演示完毕,重新演示吗?","确认对话框",
                                               JOptionPane.YES_NO_CANCEL_OPTION );
            if(n==JOptionPane.YES_OPTION){
               stepNumber=1; 
               next.setText("第"+stepNumber+"步");
               restoreChessBoard(step);
               stackStep.clear();
               stackBack.clear();
               previous.setEnabled(false);
               for(int i=step.size()-1;i>=0;i--){
                   stackStep.push(step.get(i)); 
               }  
            }
           else if(n==JOptionPane.NO_OPTION)
               dispose();
        }
    }
    private void restoreChessBoard(ArrayList<Point> step){
        for(int j=step.size()-1;j>=2;j=j-3){ 
              Point boxPoint=step.get(j);
              Point endPoint=step.get(j-1);
              Point startPoint=step.get(j-2); 
              ChessPiece piece=null;
              if(boxPoint!=null){
                 piece=endPoint.getChessPiece();
                 int w=piece.getBounds().width;
                 int h=piece.getBounds().height;
                 piece.setLocation(startPoint.getX()-w/2,startPoint.getY()-h/2);
                 startPoint.setHaveChessPiece(true);
                 startPoint.setChessPiece(piece);
                 endPoint.setHaveChessPiece(false);  
                 piece=boxPoint.getChessPiece();
                 piece.setLocation(endPoint.getX()-w/2,endPoint.getY()-h/2);
                 endPoint.setHaveChessPiece(true);
                 endPoint.setChessPiece(piece);
                 boxPoint.setHaveChessPiece(false);
              }
              else{
                 piece=endPoint.getChessPiece();
                 int w=piece.getBounds().width;
                 int h=piece.getBounds().height;
                 piece.setLocation(startPoint.getX()-w/2,startPoint.getY()-h/2);
                 startPoint.setHaveChessPiece(true);
                 startPoint.setChessPiece(piece);
                 endPoint.setHaveChessPiece(false); 
             }
        }
    }
    private void showTextManul(){
          ChessBoard demoTwo=null;
          try{
               FileInputStream file_in=new FileInputStream(file);
               ObjectInputStream object_in=new ObjectInputStream(file_in);
               demoTwo=(ChessBoard)object_in.readObject();
               file_in.close();
               object_in.close();
          }
          catch(Exception exp){
               add(new JButton("不是棋谱文件"),BorderLayout.CENTER);
          }
          if(demoTwo!=null){
               ArrayList<Point> step=demoTwo.getStep();
               step=demoTwo.getStep();
               restoreChessBoard(step);
               text.setText(null);
               StringBuffer buffer=new StringBuffer();
               text.append("棋谱"+file.getName()+":");
               Point [][] point=demoTwo.getPoint();
               Point startPoint=null,endPoint=null,boxPoint=null;
               ChessPiece startPiece=null;
               int m=-1,n=-1,p=-1,q=-1;
               int index=0,number=0;
               while(index<=step.size()-1){
                  startPoint=step.get(index);
                  startPiece=startPoint.getChessPiece();
                  String name=startPiece.getName();
                  endPoint=step.get(index+1);
                  boxPoint=step.get(index+2);
                  for(int i=0;i<point.length;i++){
                    for(int j=0;j<point[i].length;j++){
                        if(point[i][j].equals(startPoint)){
                           m=i+1;
                           n=j+1;
                           break;  
                        }
                    } 
                  }
                  for(int i=0;i<point.length;i++){
                    for(int j=0;j<point[i].length;j++){
                        if(point[i][j].equals(endPoint)){
                           p=i+1;
                           q=j+1;
                           break;  
                        }
                    } 
                  }
                  char c1=numberToLetter(m);
                  char c2=numberToLetter(p);
                  number++;
                  if(boxPoint!=null){
                     ChessPiece endPiece=endPoint.getChessPiece();
                     if(startPiece.getIsRed()){
                         buffer.append("\n("+number+")红"+name+":"+c1+""+n+"→"+c2+""+q);
                         buffer.append("(吃掉黑"+endPiece.getName()+")");
                     }
                     else if(startPiece.getIsBlack()){
                         buffer.append("\n("+number+")黑"+name+":"+c1+""+n+"→"+c2+""+q);
                         buffer.append("(吃掉红"+endPiece.getName()+")");
                     } 
                     endPiece.setAtPoint(boxPoint);
                     boxPoint.setHaveChessPiece(true);
                     endPoint.setChessPiece(startPiece);
                     endPoint.setHaveChessPiece(true);
                     startPoint.setHaveChessPiece(false);
                  }
                  else{
                     if(startPiece.getIsRed())
                         buffer.append("\n("+number+")红"+name+":"+c1+""+n+"→"+c2+""+q);
                     else if(startPiece.getIsBlack())
                         buffer.append("\n("+number+")黑"+name+":"+c1+""+n+"→"+c2+""+q);
                     startPiece.setAtPoint(endPoint);
                     endPoint.setHaveChessPiece(true);
                     endPoint.setChessPiece(startPiece);
                     startPoint.setHaveChessPiece(false);
                  }
                  index=index+3;
               }
               text.append(new String(buffer));
          }
    }
    public char numberToLetter(int n){   
        char c='\0';
        switch(n){
           case 1 : c='a'; break;
           case 2 : c='b'; break;
           case 3 : c='c'; break;
           case 4 : c='d'; break;
           case 5 : c='e'; break;
           case 6 : c='f'; break;
           case 7 : c='g'; break;
           case 8 : c='h'; break;
           case 9 : c='i'; break;
           case 10: c='j'; break;
        } 
        return c;
    }
}