package ch11;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
public class GetTestFile extends JPanel implements ActionListener,Runnable{
   static String verifyMess;
   static InetSocketAddress socketAddress;
   String fileName;
   JLabel hintMess;
   JButton getTestFile,submit;
   JComboBox list;
   Socket socket;
   DataInputStream in; 
   DataOutputStream out; 
   Thread thread;
   int n=0;
   public GetTestFile(){
      hintMess=new JLabel("单击\"列出试卷名字\"按纽从服务器得到试卷的名字");
      hintMess.setBackground(Color.green); 
      getTestFile=new JButton("列出试卷名字");
      getTestFile.setBackground(Color.green);
      submit=new JButton("提交");
      submit.setBackground(Color.orange);
      getTestFile.addActionListener(this);
      submit.addActionListener(this);
      list=new JComboBox();
      Box box1=Box.createHorizontalBox(); 
      box1.add(new JLabel("单击\"列出\"按纽:",JLabel.CENTER));
      box1.add(getTestFile);
      Box box2=Box.createHorizontalBox();
      box2.add(new JLabel("选择试卷:",JLabel.CENTER)); 
      box2.add(list);
      box2.add(submit);
      Box boxH=Box.createVerticalBox(); 
      boxH.add(box1);
      boxH.add(box2);
      thread=new Thread(this);
      JPanel pCenter=new JPanel();
      pCenter.add(boxH); 
      setLayout(new BorderLayout());
      pCenter.setBackground(Color.pink);
      add(pCenter,BorderLayout.CENTER);
      JPanel pNorth=new JPanel();
      pNorth.add(hintMess); 
      add(pNorth,BorderLayout.NORTH); 
   }
   public static void setVerifyMess(String mess){
      verifyMess=mess;
   }
   public static void setSocketAddress(InetSocketAddress address){
      socketAddress=address;
   }
   public void actionPerformed(ActionEvent e){
      if(e.getSource()==getTestFile){
         list.removeAllItems();
         try {  makeConnection();      //见后面的makeConnection()方法
                out.writeUTF("列出文件:"+verifyMess); 
                if(!(thread.isAlive())){
                    thread=new Thread(this);
                    thread.start();
                }
         }
         catch(Exception ee){
                JOptionPane.showMessageDialog(this,"请登录","操作提示",
                                              JOptionPane.PLAIN_MESSAGE);
         }
      }
      if(e.getSource()==submit){
         n=list.getItemCount();
         if(n>=1){
            fileName=(String)list.getSelectedItem();
            File file=new File(fileName);
            ClientTestArea.setExaminationFile(file);
            String tishi="您已经选择了"+fileName+"试卷,请单击左侧的\"答卷界面\"";
            JOptionPane.showMessageDialog(this,tishi,"操作提示",
                                               JOptionPane.PLAIN_MESSAGE);

         }
      }
   }
   private void makeConnection() throws IOException {
       socket=new Socket();
       socket.connect(socketAddress);
       in=new DataInputStream(socket.getInputStream());
       out=new DataOutputStream(socket.getOutputStream());
   }  
   public void run(){
      String s=null;
      while(true){
        try{
             s=in.readUTF();          
             if(s.startsWith("列出文件:")){
                  String listItem=s.substring(s.indexOf(":")+1);
                  list.addItem(listItem);
             }
             if(s.startsWith("已经全部列出:")){
                 hintMess.setText("试卷名字已经全部列出,请在列表中选择试卷");
                 hintMess.setBackground(Color.green);
                 break;
             }
             if(s.startsWith("请登录")){
                 JOptionPane.showMessageDialog(this,"请登录","操作提示",
                                              JOptionPane.PLAIN_MESSAGE);
                 break;
             }
        }
        catch (Exception ee){}
     }
  }
}
