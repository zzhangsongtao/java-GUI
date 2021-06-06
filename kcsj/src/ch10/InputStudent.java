package ch10;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.*;
public class InputStudent extends JPanel implements ActionListener{
   Student  ѧ��=null;
   StudentPicture pic;
   HashMap<String,Student> ������Ϣ��=null;                           
   JTextField ѧ��,����,�꼶,����;
   JButton ѡ����Ƭ;
   JComboBox רҵ;                 
   JRadioButton ��,Ů;
   ButtonGroup group=null;
   JButton ¼��,����;
   FileInputStream inOne=null;
   ObjectInputStream inTwo=null;
   FileOutputStream outOne=null;
   ObjectOutputStream outTwo=null;
   File systemFile,imagePic;
   public InputStudent(File file){
      systemFile=file;
      pic=new StudentPicture();
      ѧ��=new JTextField(5);
      ����=new JTextField(5);
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
      �꼶=new JTextField(5);
      ����=new JTextField(5);
      ѡ����Ƭ=new JButton("ѡ��"); 
      group=new ButtonGroup();
      ��=new JRadioButton("��",true);
      Ů=new JRadioButton("Ů",false);
      group.add(��);
      group.add(Ů);
      ¼��=new JButton("¼��");
      ����=new JButton("����");
      ¼��.addActionListener(this);
      ѡ����Ƭ.addActionListener(this);
      ����.addActionListener(this);
      Box box1=Box.createHorizontalBox();              
      box1.add(new JLabel("ѧ��:",JLabel.CENTER));
      box1.add(ѧ��);
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
      JPanel picPanel=new JPanel();
      picPanel.setBackground(Color.green);              
      picPanel.add(new JLabel("ѡ����Ƭ:",JLabel.CENTER));
      picPanel.add(ѡ����Ƭ);
      JPanel putButton=new JPanel(); 
      putButton.add(¼��);
      putButton.add(����);            
      JPanel messPanel=new JPanel();
      messPanel.add(boxH);
      messPanel.setBackground(Color.cyan);
      putButton.setBackground(Color.red);
      setLayout(new BorderLayout());
      JSplitPane splitV=
      new JSplitPane(JSplitPane.VERTICAL_SPLIT,picPanel,pic);
      JSplitPane splitH=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,messPanel,splitV);
      add(splitH,BorderLayout.CENTER);
      add(putButton,BorderLayout.SOUTH);
      validate();
   }
   public void actionPerformed(ActionEvent e){
     if(e.getSource()==¼��){
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
                String warning="����������Ϣ�Ѵ���,�뵽�޸�ҳ���޸�!";
                JOptionPane.showMessageDialog(this,warning,"����",
                                              JOptionPane.WARNING_MESSAGE);
            }
            else{  
               String m="������Ϣ����¼��!";
               int ok=JOptionPane.showConfirmDialog(this,m,"ȷ��",
                           JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
               if(ok==JOptionPane.YES_OPTION){
                     String name=����.getText();
                     String discipling=(String)רҵ.getSelectedItem();
                     String grade=�꼶.getText();
                     String borth=����.getText();
                     String sex=null;
                        if(��.isSelected())
                            sex=��.getText();
                        else
                            sex=Ů.getText();
                     ѧ��=new Student();
                     ѧ��.setNumber(number);
                     ѧ��.setName(name);
                     ѧ��.setDiscipling(discipling);
                     ѧ��.setGrade(grade);
                     ѧ��.setBorth(borth);
                     ѧ��.setSex(sex);
                     ѧ��.setImagePic(imagePic);
                     try { outOne=new FileOutputStream(systemFile);
                           outTwo=new ObjectOutputStream(outOne);
                           ������Ϣ��.put(number,ѧ��);
                           outTwo.writeObject(������Ϣ��);
                           outTwo.close();
                           outOne.close();
                           clearMess();
                      }
                      catch(Exception ee){}
               }
            } 
        }
        else{
            String warning="����Ҫ����ѧ��!";
            JOptionPane.showMessageDialog(this,warning,"����",JOptionPane.WARNING_MESSAGE);
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
     }
   }
   public void clearMess(){
       ѧ��.setText(null);
       ����.setText(null);
       �꼶.setText(null);
       ����.setText(null);
       ѡ����Ƭ.setText("ѡ��");
       imagePic=null;
       pic.setImage(imagePic);
       pic.repaint();
   }
}
