package ch11;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class ClientTestArea extends Panel implements ActionListener,Runnable{
   static String verifyMess;
   static InetSocketAddress socketAddress;
   static File examinationFile;
   ArrayList<JRadioButton> choiceList;
   Socket socket;                               
   DataInputStream in;                           
   DataOutputStream out;                         
   Thread thread;                         
   static JPanel examineArea;
   JLabel hintMess;                                   
   JButton handAnswer,startTest,lookScore;
   public ClientTestArea(){
      thread=new Thread(this);
      choiceList=new ArrayList<JRadioButton>();  
      examineArea=new JPanel();
      examineArea.setForeground(Color.blue);     
      handAnswer=new JButton("提交答案"); 
      startTest=new JButton("显示试卷");
      lookScore=new JButton("查看分数");
      handAnswer.setEnabled(false);
      lookScore.setEnabled(false);
      hintMess=new JLabel("单击\"显示试卷\"按纽从服务器得到试卷");
      startTest.addActionListener(this);
      handAnswer.addActionListener(this);
      lookScore.addActionListener(this);   
      setLayout(new BorderLayout());
      JPanel pCenter=new JPanel();
      pCenter.setBackground(Color.yellow);
      pCenter.setLayout(new BorderLayout());
      pCenter.add(new JScrollPane(examineArea),BorderLayout.CENTER);
      add(pCenter,BorderLayout.CENTER);
      JPanel pSouth=new JPanel();
      pSouth.setBackground(Color.blue);
      pSouth.add(handAnswer);
      pSouth.add(startTest);
      pSouth.add(lookScore);
      add(pSouth,BorderLayout.SOUTH);
      JPanel pNorth=new JPanel();
      pNorth.setBackground(Color.green);
      pNorth.add(hintMess);
      add(pNorth,BorderLayout.NORTH);
   }
   public static void setVerifyMess(String mess){
      verifyMess=mess;
   }
   public static void setSocketAddress(InetSocketAddress address){
      socketAddress=address;
   }
   public static void setExaminationFile(File f){
      examinationFile=f;
      examineArea.removeAll(); 
   }
   public void actionPerformed(ActionEvent e){
      if(e.getSource()==startTest){
           choiceList.clear();
           lookScore.setEnabled(false);
           try{ makeConnection();
                examineArea.removeAll();
                out.writeUTF("显示试卷:"+verifyMess+"#"+examinationFile.getName()); 
                String m="读入的试题:"+examinationFile+"将刷新当前的试题,是否确认继续";
                int ok=JOptionPane.showConfirmDialog(this,m,"确认对话框",
                              JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(ok==JOptionPane.YES_OPTION){
                  if(!(thread.isAlive())){
                    thread=new Thread(this); 
                    thread.start();
                  }
                  handAnswer.setEnabled(true);
                }
          }
          catch(Exception exp){
                JOptionPane.showMessageDialog(this,"您未登录或选择试卷","操作提示",
                                              JOptionPane.PLAIN_MESSAGE); 
          } 
     } 
     if(e.getSource()==handAnswer){
         String m="只有一次提交答案的机会!,一旦提交将无法继续答题,是否确认提交答案";
         int ok=JOptionPane.showConfirmDialog(this,m,"确认对话框",
                            JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
         if(ok==JOptionPane.YES_OPTION){
            StringBuffer str=new StringBuffer();
            for(int i=0;i<choiceList.size();i++){
               JRadioButton box=choiceList.get(i);
               if(box.isSelected())
                 str.append(box.getText());
            }
            String myAnswer=new String(str);
            try{ makeConnection();
                 out.writeUTF("提交的答案:"+verifyMess+"#"+myAnswer);
                 handAnswer.setEnabled(false);
                 lookScore.setEnabled(true);
            }
            catch(Exception exp){
                JOptionPane.showMessageDialog(this,"您未登录或选择试卷","操作提示",
                                              JOptionPane.PLAIN_MESSAGE);
            } 
         }
     }
     if(e.getSource()==lookScore){
         StringBuffer str=new StringBuffer();
         for(int i=0;i<choiceList.size();i++){
            JRadioButton box=choiceList.get(i);
            if(box.isSelected())
             str.append(box.getText());
         }
         String myAnswer=new String(str); 
         try{ makeConnection();
          out.writeUTF("查看得分:"+verifyMess+"#"+myAnswer+"*"+examinationFile.getName());
              if(!(thread.isAlive())){
                 thread=new Thread(this); 
                 thread.start();
              } 
         }
         catch(Exception exp){
              JOptionPane.showMessageDialog(this,"您未登录或选择试卷","操作提示",
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
     while(true){
         String s=null;
         try{ 
            s=in.readUTF();             
            if(s.startsWith("试卷内容:")){ 
                String content=s.substring(s.indexOf(":")+1);
                StringReader read=new StringReader(content);
                BufferedReader in= new BufferedReader(read);
                String str=null;
                int number=0;
                while((str=in.readLine())!=null){ //计算出题目总数
                   if(str.trim().startsWith("答题卡"))
                     number++; 
                } 
                examineArea.setLayout(new GridLayout(number,2));//第2列上放置答题卡
                JTextArea [] text=new JTextArea[number];
                for(int i=0;i<text.length;i++){
                   text[i]=new JTextArea(4,10);
                   text[i].setLineWrap(true);
                   text[i].setWrapStyleWord(true);
                   text[i].setFont(new Font("宋体",Font.BOLD,14));
                } 
                in.close();
                out.close();
                read=new StringReader(content);
                in= new BufferedReader(read);
                int i=0;
                while((str=in.readLine())!=null){
                  if(!(str.trim().startsWith("答题卡")))
                     text[i].append(str+"\n");            //text[i]中显示第i题的内容
                  else{
                     examineArea.add(new JScrollPane(text[i]));
                     i++;
                     JRadioButton [] box=new JRadioButton[4];
                     ButtonGroup group=new ButtonGroup();
                     String [] choiceChar={"A","B","C","D"};
                     for(int k=0;k<4;k++){
                          box[k]=new JRadioButton(choiceChar[k],true);
                          group.add(box[k]);
                     }
                     JPanel pAddbox=new JPanel();
                     pAddbox.setBackground(Color.yellow);
                     pAddbox.setBorder(BorderFactory.createRaisedBevelBorder());
                     FlowLayout flow=new FlowLayout();
                     flow.setAlignment(FlowLayout.LEFT);
                     flow.setHgap(5);
                     pAddbox.setLayout(flow);
                     pAddbox.add(new JLabel("答题卡:"));
                     for(int k=0;k<4;k++){
                        pAddbox.add(box[k]);
                        choiceList.add(box[k]);
                     }  
                     examineArea.add(pAddbox);
                  }
                }
                examineArea.repaint();
                validate();
                in.close();
                out.close();
                hintMess.setText("祝考出好成绩");
                return;
            }
            if(s.startsWith("分数")){
               JOptionPane.showMessageDialog(this,s,"查询提示",JOptionPane.PLAIN_MESSAGE);
               return;
            }
         } 
         catch(Exception e){
            JOptionPane.showMessageDialog(this,""+e,"异常提示",JOptionPane.PLAIN_MESSAGE);
            return;
         }
     }
  }
} 
