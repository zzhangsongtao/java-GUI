package ch10;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
public class Delete extends JPanel implements ActionListener{
    HashMap<String,Student> ������Ϣ��=null;                           
    JTextField ѧ��,����,רҵ,�꼶,����;                 
    JRadioButton ��,Ů;
    JButton ɾ��;
    ButtonGroup group=null;
    FileInputStream inOne=null;
    ObjectInputStream inTwo=null;
    FileOutputStream outOne=null;
    ObjectOutputStream outTwo=null;
    File systemFile=null;                                           
    public Delete(File file){
       systemFile=file;
       ѧ��=new JTextField(10);
       ɾ��=new JButton("ɾ��");
       ѧ��.addActionListener(this);
       ɾ��.addActionListener(this);
       ����=new JTextField(10);
       ����.setEditable(false);
       רҵ=new JTextField(10);
       רҵ.setEditable(false);
       �꼶=new JTextField(10);
       �꼶.setEditable(false);
       ����=new JTextField(10);
       ����.setEditable(false);
       ��=new JRadioButton("��",false);
       Ů=new JRadioButton("Ů",false);
       group=new ButtonGroup();
       group.add(��);
       group.add(Ů);
       Box box1=Box.createHorizontalBox();              
       box1.add(new JLabel("����Ҫɾ����ѧ��:",JLabel.CENTER));
       box1.add(ѧ��);
       box1.add(ɾ��);
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
       JPanel pCenter=new JPanel();
       pCenter.add(boxH);
       setLayout(new BorderLayout());
       add(pCenter,BorderLayout.CENTER);
       validate();
    }
    public void actionPerformed(ActionEvent e){
       if(e.getSource()==ɾ��||e.getSource()==ѧ��){
         String number="";
         number=ѧ��.getText();
         if(number.length()>0){
             try {
                    inOne=new FileInputStream(systemFile);
                    inTwo=new ObjectInputStream(inOne);
                    ������Ϣ��=(HashMap)inTwo.readObject();
                    inOne.close();
                    inTwo.close();
             }
             catch(Exception ee){}
             if(������Ϣ��.containsKey(number)){          
                 Student stu=(Student)������Ϣ��.get(number);
                 ����.setText(stu.getName());
                 רҵ.setText(stu.getDisciping());
                 �꼶.setText(stu.getGrade());
                 ����.setText(stu.getBorth()); 
                 if(stu.getSex().equals("��"))
                     ��.setSelected(true);
                 else
                     Ů.setSelected(true);
                 String m="ȷ��Ҫɾ����ѧ�ż�ȫ����Ϣ��?";
                 int ok=JOptionPane.showConfirmDialog(this,m,"ȷ��",
                 JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                 if(ok==JOptionPane.YES_OPTION){
                     ������Ϣ��.remove(number);
                     try{
                          outOne=new FileOutputStream(systemFile);
                          outTwo=new ObjectOutputStream(outOne);
                          outTwo.writeObject(������Ϣ��);
                          outTwo.close();
                          outOne.close();
                          ѧ��.setText(null);
                          ����.setText(null);                                
                          רҵ.setText(null);
                          �꼶.setText(null);
                          ����.setText(null);
                      }
                      catch(Exception ee){}
                 }
                 else if(ok==JOptionPane.NO_OPTION){
                      ѧ��.setText(null);
                      ����.setText(null);
                      רҵ.setText(null);
                      �꼶.setText(null);
                      ����.setText(null);
                 }
             }
             else{
                  String warning="��ѧ�Ų�����!";
                  JOptionPane.showMessageDialog(this,warning,"����",
                                                 JOptionPane.WARNING_MESSAGE);
                  ѧ��.setText(null);
             }
         }
         else{
             String warning="����Ҫ����ѧ��!";
             JOptionPane.showMessageDialog(this,warning,"����",
                                           JOptionPane.WARNING_MESSAGE);
         }
       } 
    }
}