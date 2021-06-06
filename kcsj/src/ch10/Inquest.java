package ch10;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
public class Inquest extends JDialog implements ActionListener{
   StudentPicture pic;
   PrintJob print; 
   Graphics g=null;
   HashMap<String,Student> ������Ϣ��; 
   JTextField ѧ��,����,רҵ,�꼶,����;  
   JRadioButton ��,Ů;
   JButton ��ѯ,��ӡ�ı�����Ƭ,��ӡ�ı�,��ӡ��Ƭ;
   ButtonGroup group=null;
   FileInputStream inOne=null;
   ObjectInputStream inTwo=null;
   File systemFile=null;
   JPanel messPanel;
   Student stu; 
   public Inquest(File file){
      setTitle("��ѯ�Ի���"); 
      setModal(false); 
      systemFile=file;
      pic=new StudentPicture();
      ѧ��=new JTextField(6);
      ��ѯ=new JButton("��ѯ");
      ѧ��.addActionListener(this);
      ��ѯ.addActionListener(this);
      ��ӡ�ı�����Ƭ=new JButton("��ӡ�ı�����Ƭ");
      ��ӡ�ı�=new JButton("��ӡ�ı�");
      ��ӡ��Ƭ=new JButton("��ӡ��Ƭ");
      ��ӡ�ı�����Ƭ.addActionListener(this);
      ��ӡ�ı�.addActionListener(this);
      ��ӡ��Ƭ.addActionListener(this);
      ��ӡ�ı�����Ƭ.setEnabled(false);
      ��ӡ�ı�.setEnabled(false);
      ��ӡ��Ƭ.setEnabled(false);     
      ����=new JTextField(5); 
      ����.setEditable(false);
      רҵ=new JTextField(5);
      רҵ.setEditable(false);
      �꼶=new JTextField(5);
      �꼶.setEditable(false);
      ����=new JTextField(5);
      ����.setEditable(false);
      ��=new JRadioButton("��",false);
      Ů=new JRadioButton("Ů",false);
      group=new ButtonGroup();
      group.add(��);
      group.add(Ů);
      Box box1=Box.createHorizontalBox(); 
      box1.add(new JLabel("ѧ��:",JLabel.CENTER));
      box1.add(ѧ��);
      box1.add(��ѯ);
      Box box2=Box.createHorizontalBox();  
      box2.add(new JLabel("����:",JLabel.CENTER));
      box2.add(����);
      Box box3=Box.createHorizontalBox(); 
      box3.add(new JLabel("�Ա�:",JLabel.CENTER));
      box3.add(��);
      box3.add(Ů);
      Box box4=Box.createHorizontalBox(); 
      box4.add(new JLabel("רҵ:",JLabel.CENTER));
      box4.add(רҵ); 
      Box box5=Box.createHorizontalBox(); 
      box5.add(new JLabel("�꼶:",JLabel.CENTER));
      box5.add(�꼶);
      Box box6=Box.createHorizontalBox();              
      box6.add(new JLabel("����:",JLabel.CENTER));
      box6.add(����);
      Box boxH=Box.createVerticalBox();  
      boxH.add(box1);
      boxH.add(box2);
      boxH.add(box3);
      boxH.add(box4);
      boxH.add(box5);
      boxH.add(box6);
      boxH.add(Box.createVerticalGlue()); 
      messPanel=new JPanel();
      messPanel.add(boxH);
      JSplitPane splitH=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,messPanel,pic);
      add(splitH,BorderLayout.CENTER); 
      JPanel pSouth=new JPanel();
      pSouth.add(��ӡ�ı�����Ƭ);
      pSouth.add(��ӡ�ı�);
      pSouth.add(��ӡ��Ƭ);
      add(pSouth,BorderLayout.SOUTH);  
      validate();
      setVisible(false);
      setSize(360,230);
      addWindowListener(new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                         setVisible(false);
                    }});
   }
   public void actionPerformed(ActionEvent e){
      if(e.getSource()==��ѯ||e.getSource()==ѧ��){
         String number="";
         number=ѧ��.getText();
         if(number.length()>0){
              try {
                    inOne=new FileInputStream(systemFile);
                    inTwo=new ObjectInputStream(inOne);
                    ������Ϣ��=(HashMap<String,Student>)inTwo.readObject();
                    inOne.close();
                    inTwo.close();
               }
               catch(Exception ee){}
              if(������Ϣ��.containsKey(number)){          
                   stu=������Ϣ��.get(number);
                   ����.setText(stu.getName());
                   רҵ.setText(stu.getDisciping());
                   �꼶.setText(stu.getGrade());
                   ����.setText(stu.getBorth()); 
                   if(stu.getSex().equals("��"))
                        ��.setSelected(true);
                   else
                        Ů.setSelected(true);
                   pic.setImage(stu.getImagePic());
                   pic.repaint();
                   ��ӡ�ı�����Ƭ.setEnabled(true);
                   ��ӡ�ı�.setEnabled(true);
                   ��ӡ��Ƭ.setEnabled(true);
              }
              else{
                  ��ӡ�ı�����Ƭ.setEnabled(false);
                  ��ӡ�ı�.setEnabled(false);
                  ��ӡ��Ƭ.setEnabled(false);
                  String warning="��ѧ�Ų�����!";
                  JOptionPane.showMessageDialog(this,warning,"����",
                                               JOptionPane.WARNING_MESSAGE);
                  clearMess();
              }
         }
         else{
              ��ӡ�ı�����Ƭ.setEnabled(false);
              ��ӡ�ı�.setEnabled(false);
              ��ӡ��Ƭ.setEnabled(false);
              String warning="����Ҫ����ѧ��!";
              JOptionPane.showMessageDialog(this,warning,"����",
                                            JOptionPane.WARNING_MESSAGE);
             }
      }
      else if(e.getSource()==��ӡ�ı�����Ƭ){
         try{
            print=getToolkit().getPrintJob(new JFrame(),"��ӡ",new Properties());
            g=print.getGraphics();
            g.translate(120,200);//�ڴ�ӡҳ������(120,200)��ʼ��ӡ�ı�����
            int w=messPanel.getBounds().width;
            messPanel.printAll(g);
            g.translate(w,0);   //�ڴ�ӡҳ������(120+w,200)����ӡ��Ƭ
            pic.printAll(g);
            ����.setText(stu.getName());
            g.dispose();          
            print.end();
         }
         catch(Exception exp){}    
      }
      else if(e.getSource()==��ӡ�ı�){
         try{ 
           print=getToolkit().getPrintJob(new JFrame(),"��ӡ",new Properties());
           g=print.getGraphics();
           g.translate(120,200);//�ڴ�ӡҳ������(120,200)��ʼ��ӡ�ı�����
           messPanel.printAll(g);
           g.dispose();          
           print.end();
         }
         catch(Exception exp){} 
      }  
      else if(e.getSource()==��ӡ��Ƭ){
         try{ 
           print=getToolkit().getPrintJob(new JFrame(),"��ӡ",new Properties());
           g=print.getGraphics();
           int w=messPanel.getBounds().width;
           g.translate(120+w,200);//�ڴ�ӡҳ������(120+w,200)����ӡ��Ƭ
           pic.printAll(g);
           g.dispose();          
           print.end();
         }
         catch(Exception exp){}
      } 
   }
   public void clearMess(){
       ѧ��.setText(null);
       ����.setText(null);
       �꼶.setText(null);
       ����.setText(null);
       רҵ.setText(null);
       pic.setImage(null);
       pic.repaint();
   }
}
