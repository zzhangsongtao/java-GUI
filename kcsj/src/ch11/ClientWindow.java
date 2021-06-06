package ch11;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
public class ClientWindow extends JFrame{
   ClientLogin clientLogin;          
   GetTestFile getTestFile;          
   ClientTestArea clientTestArea; 
   JLabel label=null;     
   JTabbedPane tabbedPane;
   public ClientWindow(){
     setTitle("标准化考试系统(客户端)");
     label=new JLabel();
     label.setText(getTitle());
     label.setForeground(Color.orange);
     label.setFont(new Font("隶书",Font.BOLD,22));
     label.setIcon(new ImageIcon("welcome.jpg"));
     label.setHorizontalTextPosition(SwingConstants.CENTER);
     label.setBackground(Color.green); 
     tabbedPane=new JTabbedPane(JTabbedPane.LEFT);
     clientLogin=new ClientLogin();
     getTestFile=new GetTestFile(); 
     clientTestArea=new ClientTestArea();
     tabbedPane.add("系统标题",label); 
     tabbedPane.add("登录",clientLogin);
     tabbedPane.add("选择试卷",getTestFile);
     tabbedPane.add("答卷",clientTestArea);
     add(tabbedPane,BorderLayout.CENTER);
     validate();
     setVisible(true);
     setBounds(100,50,460,280);
     setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
     addWindowListener(new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                      int n=JOptionPane.showConfirmDialog(null,"确认退出吗?","确认对话框",
                                               JOptionPane.YES_NO_OPTION );
                      if(n==JOptionPane.YES_OPTION)  
                         System.exit(0);
                    }});
     validate();   
   }
   public static void main(String args[]){
      new ClientWindow();
   }
}
