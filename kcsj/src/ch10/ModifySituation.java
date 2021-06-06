package ch10;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.*;
public class ModifySituation extends JPanel implements ActionListener{
    StudentPicture pic;
    HashMap<String,Student> ������Ϣ��=null;                           
    JTextField ѧ��,����,�꼶,����; 
    JComboBox רҵ;
    JButton ѡ����Ƭ;                
    JRadioButton ��,Ů;
    ButtonGroup group=null;
    JButton ��ʼ�޸�,¼���޸�,����;
    FileInputStream inOne=null;
    ObjectInputStream inTwo=null;
    FileOutputStream outOne=null;
    ObjectOutputStream outTwo=null;
    File systemFile,imagePic;
    JComboBox oldMess;
    Student stu=null; 
    public ModifySituation(File file){
       systemFile=file;
       pic=new StudentPicture();
       ѧ��=new JTextField(6);
       ����=new JTextField(6);                                
       רҵ=new JComboBox();
       try{
           FileReader  inOne=new FileReader("רҵ.txt");
           BufferedReader inTwo= new BufferedReader(inOne);
           String s=null;
           int i=0;
           while((s=inTwo.readLine())!=null)
              רҵ.addItem(s);
           inOne.close();
           inTwo.close();
       }
       catch(IOException exp){
         רҵ.addItem("��ѧ");
         רҵ.addItem("�������ѧ�뼼��");
       } 
       �꼶=new JTextField(6);
       ����=new JTextField(6);
       ѡ����Ƭ=new JButton("ѡ��");
       group=new ButtonGroup();
       ��=new JRadioButton("��",true);
       Ů=new JRadioButton("Ů",false);
       group.add(��);
       group.add(Ů);
       oldMess=new JComboBox();
       ��ʼ�޸�=new JButton("��ʼ�޸�");
       ¼���޸�=new JButton("¼���޸�");
       ¼���޸�.setEnabled(false);
       ѡ����Ƭ.setEnabled(false);
       ����=new JButton("����");
       ѧ��.addActionListener(this);
       ��ʼ�޸�.addActionListener(this);
       ¼���޸�.addActionListener(this);
       ����.addActionListener(this);
       ѡ����Ƭ.addActionListener(this); 
       Box box1=Box.createHorizontalBox();              
       box1.add(new JLabel("����Ҫ�޸���Ϣ��ѧ��:",JLabel.CENTER));
       box1.add(ѧ��);
       box1.add(��ʼ�޸�);
       Box box2=Box.createHorizontalBox();              
       box2.add(new JLabel("(��)����:",JLabel.CENTER));
       box2.add(����);
       Box box3=Box.createHorizontalBox();              
       box3.add(new JLabel("(��)�Ա�:",JLabel.CENTER));
       box3.add(��);
       box3.add(Ů);
       Box box4=Box.createHorizontalBox();              
       box4.add(new JLabel("(��)רҵ:",JLabel.CENTER));
       box4.add(רҵ);
       Box box5=Box.createHorizontalBox();              
       box5.add(new JLabel("(��)�꼶:",JLabel.CENTER));
       box5.add(�꼶);
       Box box6=Box.createHorizontalBox();              
       box6.add(new JLabel("(��)����:",JLabel.CENTER));
       box6.add(����);
       Box box7=Box.createHorizontalBox();              
       box7.add(new JLabel("ѧ�����е�����:",JLabel.CENTER));
       box7.add(oldMess); 
       Box boxH=Box.createVerticalBox();              
       boxH.add(box1);
       boxH.add(box2);
       boxH.add(box3);
       boxH.add(box4);
       boxH.add(box5);
       boxH.add(box6);
       boxH.add(box7);
       boxH.add(Box.createVerticalGlue());
       JPanel picPanel=new JPanel(); 
       picPanel.setBackground(Color.green);             
       picPanel.add(new JLabel("ѡ����Ƭ:",JLabel.CENTER));
       picPanel.add(ѡ����Ƭ);
       JPanel putButton=new JPanel();
       putButton.setBackground(Color.yellow); 
       putButton.add(¼���޸�);
       putButton.add(����);          
       JPanel messPanel=new JPanel();
       messPanel.setBackground(Color.pink);
       messPanel.add(boxH);
       setLayout(new BorderLayout());
       JSplitPane splitV=
       new JSplitPane(JSplitPane.VERTICAL_SPLIT,picPanel,pic);
       JSplitPane splitH=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,messPanel,splitV);
       add(splitH,BorderLayout.CENTER);
       add(putButton,BorderLayout.SOUTH); 
       validate();
    }
    public void actionPerformed(ActionEvent e){
     if(e.getSource()==��ʼ�޸�||e.getSource()==ѧ��){
        oldMess.removeAllItems();
        String number="";
        imagePic=null;
        stu=null; 
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
               ¼���޸�.setEnabled(true);
               ѡ����Ƭ.setEnabled(true);
               stu=������Ϣ��.get(number);
               oldMess.addItem("����:"+stu.getName());
               oldMess.addItem("רҵ:"+stu.getDisciping());
               oldMess.addItem("�꼶:"+stu.getGrade());
               oldMess.addItem("��������:"+stu.getBorth()); 
               if(stu.getSex().equals("��"))
                   ��.setSelected(true);
               else
                   Ů.setSelected(true);
               imagePic=stu.getImagePic();
               pic.setImage(imagePic);
               pic.repaint();
               ����.setText(stu.getName());
               �꼶.setText(stu.getGrade());
               ����.setText(stu.getBorth());
          }
          else{
               ¼���޸�.setEnabled(false);
               ѡ����Ƭ.setEnabled(false);
               String warning="��ѧ�Ų�����!";
               JOptionPane.showMessageDialog(this,warning,"����",
                                             JOptionPane.WARNING_MESSAGE);
               clearMess();
          }
        }
        else{
          ¼���޸�.setEnabled(false); 
          ѡ����Ƭ.setEnabled(false);
          String warning="����Ҫ����ѧ��!";
          JOptionPane.showMessageDialog(this,warning,"����",JOptionPane.WARNING_MESSAGE);
          clearMess();
        }
     } 
     else if(e.getSource()==¼���޸�){
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
                  String question="����������Ϣ�Ѵ���,�����޸���(��)�Ļ�����Ϣ��?";
                  JOptionPane.showMessageDialog(this,question,"����",
                                                JOptionPane.QUESTION_MESSAGE);
                  String m="������Ϣ�����޸�!";
                  int ok=JOptionPane.showConfirmDialog(this,m,"ȷ��",
                        JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                  if(ok==JOptionPane.YES_OPTION){
                      String name=����.getText();
                      if(name.length()==0)
                         name=stu.getName();
                      String discipling=(String)רҵ.getSelectedItem();
                      if(discipling==null)
                         discipling=stu.getDisciping();  
                      String grade=�꼶.getText();
                      if(grade.length()==0)
                         grade=stu.getGrade(); 
                      String borth=����.getText();
                      if(borth.length()==0)
                         borth=stu.getBorth();
                      String sex=null;
                      if(��.isSelected())
                          sex=��.getText();
                      else
                          sex=Ů.getText();
                       if(imagePic==null) 
                         imagePic=stu.getImagePic();  
                      Student  ѧ��=new Student();
                      ѧ��.setNumber(number);
                      ѧ��.setName(name);
                      ѧ��.setDiscipling(discipling);
                      ѧ��.setGrade(grade);
                      ѧ��.setBorth(borth);
                      ѧ��.setSex(sex);
                      ѧ��.setImagePic(imagePic);
                      try{
                         outOne=new FileOutputStream(systemFile);
                         outTwo=new ObjectOutputStream(outOne);
                         ������Ϣ��.put(number,ѧ��);
                         outTwo.writeObject(������Ϣ��);  
                         outTwo.close();
                         outOne.close();
                         clearMess();
                      }
                      catch(Exception ee){}
                      ¼���޸�.setEnabled(false);
                      ѡ����Ƭ.setEnabled(false); 
                  }
                  else if(ok==JOptionPane.NO_OPTION){
                      ¼���޸�.setEnabled(true);
                      ѡ����Ƭ.setEnabled(true);
                  }
            }
            else{
              String warning="��ѧ��û�л�����Ϣ,�����޸�!";
              JOptionPane.showMessageDialog(this,warning,"����",
                                            JOptionPane.WARNING_MESSAGE);
              ¼���޸�.setEnabled(false); 
              ѡ����Ƭ.setEnabled(false);
              clearMess();
            }
        }
        else{
            String warning="����Ҫ����ѧ��!";
            JOptionPane.showMessageDialog(this,warning,"����",JOptionPane.WARNING_MESSAGE);
            ¼���޸�.setEnabled(false);
            clearMess();
            ¼���޸�.setEnabled(false); 
            ѡ����Ƭ.setEnabled(false);
        }
     }
     else if(e.getSource()==ѡ����Ƭ){
         JFileChooser chooser=new JFileChooser();
         FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                           "JPG & GIF Images", "jpg", "gif");
         chooser.setFileFilter(filter);
         int state=chooser.showOpenDialog(null);
         File choiceFile=chooser.getSelectedFile();
         if(choiceFile!=null&&state==JFileChooser.APPROVE_OPTION){
            ѡ����Ƭ.setText(choiceFile.getName());
            imagePic=choiceFile;
            pic.setImage(imagePic);
            pic.repaint();
         }
     } 
     else if(e.getSource()==����){
        clearMess();
        ¼���޸�.setEnabled(false);
        ѡ����Ƭ.setEnabled(false);
     }
   }
   public void clearMess(){
       ѧ��.setText(null);
       ����.setText(null);
       �꼶.setText(null);
       ����.setText(null);
       oldMess.removeAllItems();  
       ѡ����Ƭ.setText("ѡ��");
       imagePic=null;
       pic.setImage(imagePic);
       pic.repaint();
   }
}
