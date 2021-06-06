package ch11;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
public class ClientLogin extends JPanel implements ActionListener{
    String verifyMess;
    JTextField inputNumber,inputName,inputServerIP;
    JLabel hintMess;
    JButton startCon;
    String number,name,IP;
    public ClientLogin(){ 
       inputNumber=new JTextField(10);
       inputName =new JTextField(10);
       hintMess=new JLabel("请输入学号、姓名和服务器的IP",JLabel.CENTER);
       hintMess.setFont(new Font("隶书",Font.BOLD,18));
       inputServerIP=new JTextField(10);
       startCon=new JButton("提交");
       startCon.addActionListener(this);
       Box box1=Box.createHorizontalBox(); 
       box1.add(new JLabel("输入学号:",JLabel.CENTER));
       box1.add(inputNumber);
       Box box2=Box.createHorizontalBox(); 
       box2.add(new JLabel("输入姓名:",JLabel.CENTER));
       box2.add(inputName);
       Box box3=Box.createHorizontalBox(); 
       box3.add(new JLabel("输入IP:"));
       box3.add(inputServerIP);
       Box box4=Box.createHorizontalBox(); 
       box4.add(new JLabel("单击提交按钮",JLabel.CENTER));
       box4.add(startCon);
       Box boxH=Box.createVerticalBox(); 
       boxH.add(box1);
       boxH.add(box2);
       boxH.add(box3);
       boxH.add(box4);
       JPanel pCenter=new JPanel();
       pCenter.add(boxH); 
       pCenter.setBackground(new Color(210,210,110));
       setLayout(new BorderLayout());
       add(pCenter,BorderLayout.CENTER);
       JPanel pNorth=new JPanel();
       pNorth.setBackground(Color.green);  
       pNorth.add(hintMess); 
       add(pNorth,BorderLayout.NORTH);
    }
    public void actionPerformed(ActionEvent e){
       if(e.getSource()==startCon){
         number=inputNumber.getText().trim();
         name=inputName.getText().trim();
         if(number.length()>0&&name.length()>0){
            IP=inputServerIP.getText().trim();
            try{  
               LoginThread thread=new LoginThread(hintMess);
               if(!(thread.isAlive())){
                   thread=new LoginThread(hintMess);
                   thread.makeConnection(number,name,IP);
                   thread.start(); 
               } 
            } 
            catch (Exception ee){  
               hintMess.setText("无法连接"+ee);
            }
         }
         else{
            JOptionPane.showMessageDialog(this,"必须输入学号和姓名","警告",
                                          JOptionPane.WARNING_MESSAGE);
         }
       } 
    }
}
class LoginThread extends Thread{
    Socket socket;
    InetSocketAddress socketAddress;
    DataInputStream in; 
    DataOutputStream out;
    JLabel hintMess;
    LoginThread(JLabel hint){
       hintMess=hint;
    }
    public void makeConnection(String number,String name,String IP) throws IOException {
       InetAddress address=InetAddress.getByName(IP);
       socketAddress=new InetSocketAddress(address,6666);
       socket=new Socket();
       socket.connect(socketAddress);
       in=new DataInputStream(socket.getInputStream());
       out=new DataOutputStream(socket.getOutputStream());
       out.writeUTF("学生"+number+""+name); 
    } 
    public void run(){
      String s=null;
      while(true){
        try{
             s=in.readUTF();          
             if(s.startsWith("成功登录:")){
                 String verifyMess=s.substring(s.indexOf(":")+1);
                 GetTestFile.setVerifyMess(verifyMess);
                 ClientTestArea.setVerifyMess(verifyMess);
                 GetTestFile.setSocketAddress(socketAddress);
                 ClientTestArea.setSocketAddress(socketAddress);
                 hintMess.setText(s);
                 String tishi="成功登录,请单击左侧的\"选择试卷界面\"";
                 JOptionPane.showMessageDialog(null,tishi,"操作提示",
                                               JOptionPane.PLAIN_MESSAGE);
                 if(socket.isClosed()) return;
             }
             if(s.startsWith("已经成功登录了")){
                 hintMess.setText(s);
                 String tishi="已经成功登录了,请单击左侧的\"选择试卷界面\"";
                 JOptionPane.showMessageDialog(null,tishi,"操作提示",
                                               JOptionPane.PLAIN_MESSAGE);
                 if(socket.isClosed()) return;
             }
        }
        catch (Exception ee){
            hintMess.setText("服务器暂时关闭了,稍后再请求连接");
            return;
        }
    }
  }
}
