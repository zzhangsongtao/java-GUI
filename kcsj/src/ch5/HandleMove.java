package ch5;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
public class HandleMove extends JPanel implements KeyListener,ActionListener {  
    MazePoint [][] p;
    int spendTime=0;
    javax.swing.Timer recordTime;
    JTextField showTime;
    Toolkit tool;
    HandleMove(){
       recordTime=new javax.swing.Timer(1000,this);
       showTime=new JTextField(16);
       tool=getToolkit(); 
       showTime.setEditable(false);
       showTime.setHorizontalAlignment(JTextField.CENTER);
       showTime.setFont(new Font("楷体_GB2312",Font.BOLD,16));
       JLabel hitMess=new JLabel("单击走迷宫者,按键盘方向键",JLabel.CENTER);
       hitMess.setFont(new Font("楷体_GB2312",Font.BOLD,18));      
       add(hitMess);
       add(showTime);
       setBackground(Color.cyan);
    } 
    public void setMazePoint(MazePoint [][] point){
        p=point;
    }
    public void initSpendTime(){
       recordTime.stop();
       spendTime=0;
       showTime.setText(null);
    }
    public void keyPressed(KeyEvent e){
        recordTime.start();
        PersonInMaze person=null;
        person=(PersonInMaze)e.getSource();
        int m=-1,n=-1;
        MazePoint startPoint=person.getAtMazePoint();
        for(int i=0;i<p.length;i++){
            for(int j=0;j<p[i].length;j++){
               if(startPoint.equals(p[i][j])){
                  m=i;
                  n=j;
                  break;
               }
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_UP){
           int k=Math.max(m-1,0);
           if(p[k][n].getWallOrRoad().getIsRoad()){
             tool.beep(); //发出嘟的一声
             person.setAtMazePoint(p[k][n]);
             person.setLocation(p[k][n].getX(),p[k][n].getY());
           }
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN){
           int k=Math.min(m+1,p.length-1);
           if(p[k][n].getWallOrRoad().getIsRoad()) {
             tool.beep();
             person.setAtMazePoint(p[k][n]);
             person.setLocation(p[k][n].getX(),p[k][n].getY());
           }
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT){
           int k=Math.max(n-1,0);
           if(p[m][k].getWallOrRoad().getIsRoad()){
             tool.beep();
             person.setAtMazePoint(p[m][k]);
             person.setLocation(p[m][k].getX(),p[m][k].getY());
           }
        } 
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
           int k=Math.min(n+1,p[0].length-1);
           if(p[m][k].getWallOrRoad().getIsRoad()){
             tool.beep();
             person.setAtMazePoint(p[m][k]);
             person.setLocation(p[m][k].getX(),p[m][k].getY());
           }
        }
    } 
    public void actionPerformed(ActionEvent e){
          spendTime++;
          showTime.setText("您的用时:"+spendTime+"秒");
    } 
    public void keyReleased(KeyEvent e){
        PersonInMaze person=(PersonInMaze)e.getSource();
        int m=-1,n=-1;
        MazePoint endPoint=person.getAtMazePoint();
        if(endPoint.getWallOrRoad().getIsOut()){
          recordTime.stop();
          JOptionPane.showMessageDialog(this,"您成功了!","消息框",
                                       JOptionPane.INFORMATION_MESSAGE );

       }
    }
    public void keyTyped(KeyEvent e) {}
}