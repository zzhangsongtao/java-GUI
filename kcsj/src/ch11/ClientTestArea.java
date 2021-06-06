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
      handAnswer=new JButton("�ύ��"); 
      startTest=new JButton("��ʾ�Ծ�");
      lookScore=new JButton("�鿴����");
      handAnswer.setEnabled(false);
      lookScore.setEnabled(false);
      hintMess=new JLabel("����\"��ʾ�Ծ�\"��Ŧ�ӷ������õ��Ծ�");
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
                out.writeUTF("��ʾ�Ծ�:"+verifyMess+"#"+examinationFile.getName()); 
                String m="���������:"+examinationFile+"��ˢ�µ�ǰ������,�Ƿ�ȷ�ϼ���";
                int ok=JOptionPane.showConfirmDialog(this,m,"ȷ�϶Ի���",
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
                JOptionPane.showMessageDialog(this,"��δ��¼��ѡ���Ծ�","������ʾ",
                                              JOptionPane.PLAIN_MESSAGE); 
          } 
     } 
     if(e.getSource()==handAnswer){
         String m="ֻ��һ���ύ�𰸵Ļ���!,һ���ύ���޷���������,�Ƿ�ȷ���ύ��";
         int ok=JOptionPane.showConfirmDialog(this,m,"ȷ�϶Ի���",
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
                 out.writeUTF("�ύ�Ĵ�:"+verifyMess+"#"+myAnswer);
                 handAnswer.setEnabled(false);
                 lookScore.setEnabled(true);
            }
            catch(Exception exp){
                JOptionPane.showMessageDialog(this,"��δ��¼��ѡ���Ծ�","������ʾ",
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
          out.writeUTF("�鿴�÷�:"+verifyMess+"#"+myAnswer+"*"+examinationFile.getName());
              if(!(thread.isAlive())){
                 thread=new Thread(this); 
                 thread.start();
              } 
         }
         catch(Exception exp){
              JOptionPane.showMessageDialog(this,"��δ��¼��ѡ���Ծ�","������ʾ",
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
            if(s.startsWith("�Ծ�����:")){ 
                String content=s.substring(s.indexOf(":")+1);
                StringReader read=new StringReader(content);
                BufferedReader in= new BufferedReader(read);
                String str=null;
                int number=0;
                while((str=in.readLine())!=null){ //�������Ŀ����
                   if(str.trim().startsWith("���⿨"))
                     number++; 
                } 
                examineArea.setLayout(new GridLayout(number,2));//��2���Ϸ��ô��⿨
                JTextArea [] text=new JTextArea[number];
                for(int i=0;i<text.length;i++){
                   text[i]=new JTextArea(4,10);
                   text[i].setLineWrap(true);
                   text[i].setWrapStyleWord(true);
                   text[i].setFont(new Font("����",Font.BOLD,14));
                } 
                in.close();
                out.close();
                read=new StringReader(content);
                in= new BufferedReader(read);
                int i=0;
                while((str=in.readLine())!=null){
                  if(!(str.trim().startsWith("���⿨")))
                     text[i].append(str+"\n");            //text[i]����ʾ��i�������
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
                     pAddbox.add(new JLabel("���⿨:"));
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
                hintMess.setText("ף�����óɼ�");
                return;
            }
            if(s.startsWith("����")){
               JOptionPane.showMessageDialog(this,s,"��ѯ��ʾ",JOptionPane.PLAIN_MESSAGE);
               return;
            }
         } 
         catch(Exception e){
            JOptionPane.showMessageDialog(this,""+e,"�쳣��ʾ",JOptionPane.PLAIN_MESSAGE);
            return;
         }
     }
  }
} 
