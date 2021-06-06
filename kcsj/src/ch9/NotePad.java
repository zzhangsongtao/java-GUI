package ch9;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
public class NotePad extends JPanel implements ActionListener{
   JTextArea text;              
   JTextField showMessage;
   JPopupMenu menu;
   JMenuItem itemCopy,itemCut,itemPaste,itemClear; 
   public NotePad(){
       showMessage=new JTextField();
       showMessage.setHorizontalAlignment(JTextField.CENTER); 
       showMessage.setFont(new Font("TimesRoman",Font.BOLD,16));
       showMessage.setForeground(Color.blue);
       showMessage.setBackground(Color.pink);
       showMessage.setBorder(BorderFactory.createRaisedBevelBorder());
       showMessage.setEditable(false);
       menu=new JPopupMenu();   
       itemCopy=new JMenuItem("����");
       itemCut=new JMenuItem("����");
       itemPaste=new JMenuItem("ճ��");
       itemClear=new JMenuItem("���");
       itemCopy.addActionListener(this);
       itemCut.addActionListener(this);
       itemPaste.addActionListener(this);
       itemClear.addActionListener(this);
       menu.add(itemCopy);
       menu.add(itemCut);
       menu.add(itemPaste);
       menu.add(itemClear);
       text=new JTextArea(10,10);
       text.addMouseListener(new MouseAdapter(){
                                 public void mousePressed(MouseEvent e){
                                   if(e.getModifiers()==InputEvent.BUTTON3_MASK)
                                      menu.show(text,e.getX(),e.getY());
                                 }
                          });
       setLayout(new BorderLayout());
       add(showMessage,BorderLayout.NORTH);
       add(new JScrollPane(text),BorderLayout.CENTER);

   }
   public void setShowMessage(int year,int month,int day){
       showMessage.setText(""+year+"��"+month+"��"+day+"��");
   } 
   public void save(File dir,int year,int month,int day){
       String dailyContent=text.getText();
       String fileName=""+year+""+month+""+day+".txt";
       String key=""+year+""+month+""+day;
       String [] dayFile=dir.list();
       boolean boo=false;
       for(int k=0;k<dayFile.length;k++){
           if(dayFile[k].startsWith(key)){
             boo=true;
             break;
           }
       }
       if(boo){
          String m=""+year+"��"+month+"��"+day+"������־,���µ�������ӵ���־��?";
          int ok=JOptionPane.showConfirmDialog(this,m,"ѯ��",JOptionPane.YES_NO_OPTION,
                                             JOptionPane.QUESTION_MESSAGE);
          if(ok==JOptionPane.YES_OPTION){ 
            try{
                 File f=new File(dir,fileName);
                 RandomAccessFile out=new RandomAccessFile(f,"rw");
                 long fileEnd=out.length();
                 byte []bb=dailyContent.getBytes();    
                 out.seek(fileEnd);
                 out.write(bb);
                 out.close();
            }
            catch(IOException exp){}
         }
      }
      else{
          String m=""+year+"��"+month+"��"+day+"��û����־,������־��?";
          int ok=JOptionPane.showConfirmDialog(this,m,"ѯ��",JOptionPane.YES_NO_OPTION,
                                             JOptionPane.QUESTION_MESSAGE);
          if(ok==JOptionPane.YES_OPTION){ 
            try{
                 File f=new File(dir,fileName);
                 RandomAccessFile out=new RandomAccessFile(f,"rw");
                 long fileEnd=out.length();
                 byte []bb=dailyContent.getBytes();    
                 out.write(bb);
                 out.close();
            }
            catch(IOException exp){}
         }
      }  
  }
  public void delete(File dir,int year,int month,int day){
       String key=""+year+""+month+""+day;
       String [] dayFile=dir.list();
       boolean boo=false;
       for(int k=0;k<dayFile.length;k++){
           if(dayFile[k].startsWith(key)){
             boo=true;
             break;
           }
       } 
       if(boo){
           String m="ɾ��"+year+"��"+month+"��"+day+"�յ���־��?";
           int ok=JOptionPane.showConfirmDialog(this,m,"ѯ��",JOptionPane.YES_NO_OPTION,
                                             JOptionPane.QUESTION_MESSAGE);
           if(ok==JOptionPane.YES_OPTION){
             String fileName=""+year+""+month+""+day+".txt";
             File deleteFile=new File(dir,fileName);
             deleteFile.delete();
           }
          }
        else{
          String m=""+year+"��"+month+"��"+day+"����־��¼";
          JOptionPane.showMessageDialog(this,m,"��ʾ",JOptionPane.WARNING_MESSAGE);
       }
   }
   public void read(File dir,int year,int month,int day){
       String fileName=""+year+""+month+""+day+".txt";
       String key=""+year+""+month+""+day;
       String [] dayFile=dir.list();
       boolean boo=false;
       for(int k=0;k<dayFile.length;k++){
           if(dayFile[k].startsWith(key)){
             boo=true;
             break;
           }
       }
       if(boo){
          String m=""+year+"��"+month+"��"+day+"����־,��ʾ��־������?";
          int ok=JOptionPane.showConfirmDialog(this,m,"ѯ��",JOptionPane.YES_NO_OPTION,
                                             JOptionPane.QUESTION_MESSAGE);
          if(ok==JOptionPane.YES_OPTION){ 
            text.setText(null);
            try{
                 File f=new File(dir,fileName);
                 FileReader  inOne=new FileReader(f);
                 BufferedReader inTwo= new BufferedReader(inOne);
                 String s=null;
                 while((s=inTwo.readLine())!=null)
                    text.append(s+"\n");
                 inOne.close();
                 inTwo.close();
            }
            catch(IOException exp){} 
         }
      }
      else{
         String m=""+year+"��"+month+"��"+day+"����־��¼";
         JOptionPane.showMessageDialog(this,m,"��ʾ",JOptionPane.WARNING_MESSAGE);
      }
   }
   public void actionPerformed(ActionEvent e){
      if(e.getSource()==itemCopy)
         text.copy();
      else if(e.getSource()==itemCut)
         text.cut();
      else if(e.getSource()==itemPaste)
         text.paste();
      else if(e.getSource()==itemClear)
         text.setText(null); 
    }
}
