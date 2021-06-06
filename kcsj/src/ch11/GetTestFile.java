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
      hintMess=new JLabel("����\"�г��Ծ�����\"��Ŧ�ӷ������õ��Ծ������");
      hintMess.setBackground(Color.green); 
      getTestFile=new JButton("�г��Ծ�����");
      getTestFile.setBackground(Color.green);
      submit=new JButton("�ύ");
      submit.setBackground(Color.orange);
      getTestFile.addActionListener(this);
      submit.addActionListener(this);
      list=new JComboBox();
      Box box1=Box.createHorizontalBox(); 
      box1.add(new JLabel("����\"�г�\"��Ŧ:",JLabel.CENTER));
      box1.add(getTestFile);
      Box box2=Box.createHorizontalBox();
      box2.add(new JLabel("ѡ���Ծ�:",JLabel.CENTER)); 
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
         try {  makeConnection();      //�������makeConnection()����
                out.writeUTF("�г��ļ�:"+verifyMess); 
                if(!(thread.isAlive())){
                    thread=new Thread(this);
                    thread.start();
                }
         }
         catch(Exception ee){
                JOptionPane.showMessageDialog(this,"���¼","������ʾ",
                                              JOptionPane.PLAIN_MESSAGE);
         }
      }
      if(e.getSource()==submit){
         n=list.getItemCount();
         if(n>=1){
            fileName=(String)list.getSelectedItem();
            File file=new File(fileName);
            ClientTestArea.setExaminationFile(file);
            String tishi="���Ѿ�ѡ����"+fileName+"�Ծ�,�뵥������\"������\"";
            JOptionPane.showMessageDialog(this,tishi,"������ʾ",
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
             if(s.startsWith("�г��ļ�:")){
                  String listItem=s.substring(s.indexOf(":")+1);
                  list.addItem(listItem);
             }
             if(s.startsWith("�Ѿ�ȫ���г�:")){
                 hintMess.setText("�Ծ������Ѿ�ȫ���г�,�����б���ѡ���Ծ�");
                 hintMess.setBackground(Color.green);
                 break;
             }
             if(s.startsWith("���¼")){
                 JOptionPane.showMessageDialog(this,"���¼","������ʾ",
                                              JOptionPane.PLAIN_MESSAGE);
                 break;
             }
        }
        catch (Exception ee){}
     }
  }
}
