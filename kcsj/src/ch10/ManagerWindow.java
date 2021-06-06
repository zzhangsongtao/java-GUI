package ch10;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;
public class ManagerWindow extends JFrame implements ActionListener{
   InputStudent ������Ϣ¼��;          
   ModifySituation ������Ϣ�޸�;          
   Inquest         ������Ϣ��ѯ���ӡ; 
   Delete          ������Ϣɾ��; 
   JMenuBar bar;
   JMenu fileMenu;
   JMenuItem ¼��,�޸�,��ѯ���ӡ,ɾ��,��ӭ����;      
   HashMap<String,Student> ������Ϣ=null;                    
   File file=null;                             
   CardLayout card=null;                       
   JLabel label=null;                          
   JPanel pCenter;
   public ManagerWindow(){
     ¼��=new JMenuItem("¼��ѧ��������Ϣ");
     �޸�=new JMenuItem("�޸�ѧ��������Ϣ");
     ��ѯ���ӡ=new JMenuItem("��ѯ���ӡѧ��������Ϣ");
     ɾ��=new JMenuItem("ɾ��ѧ��������Ϣ");
     ��ӭ����=new JMenuItem("��ӭ����");
     bar=new JMenuBar();
     fileMenu=new JMenu("�˵�ѡ��");
     fileMenu.add(¼��);
     fileMenu.add(�޸�);
     fileMenu.add(��ѯ���ӡ);
     fileMenu.add(ɾ��);
     fileMenu.add(��ӭ����);
     bar.add(fileMenu);
     setJMenuBar(bar);
     label=new JLabel("ѧ������ϵͳ",JLabel.CENTER);
     label.setIcon(new ImageIcon("welcome.jpg"));
     label.setFont(new Font("����",Font.BOLD,36));
     label.setHorizontalTextPosition(SwingConstants.CENTER);
     label.setForeground(Color.red);
     ������Ϣ=new HashMap<String,Student>();
     ¼��.addActionListener(this);
     �޸�.addActionListener(this);
     ��ѯ���ӡ.addActionListener(this);
     ɾ��.addActionListener(this);
     ��ӭ����.addActionListener(this);
     card=new CardLayout();
     pCenter=new JPanel();
     pCenter.setLayout(card); 
     file=new File("������Ϣ.txt");
     if(!file.exists()){
        try{
             FileOutputStream out=new FileOutputStream(file);
             ObjectOutputStream objectOut=new ObjectOutputStream(out);
             objectOut.writeObject(������Ϣ);
             objectOut.close();
             out.close();
        }
        catch(IOException e){}
     } 
     ������Ϣ¼��=new InputStudent(file);
     ������Ϣ�޸�=new ModifySituation(file);
     ������Ϣ��ѯ���ӡ=new Inquest(file);
     ������Ϣɾ��=new Delete(file);
     pCenter.add("��ӭ����",label);
     pCenter.add("¼�����",������Ϣ¼��);
     pCenter.add("�޸Ľ���",������Ϣ�޸�);
     pCenter.add("ɾ������",������Ϣɾ��);
     add(pCenter,BorderLayout.CENTER);
     validate();
     setVisible(true);
     setBounds(100,50,460,280);
     setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
     addWindowListener(new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                      int n=JOptionPane.showConfirmDialog(null,"ȷ���˳���?","ȷ�϶Ի���",
                                               JOptionPane.YES_NO_OPTION );
                      if(n==JOptionPane.YES_OPTION)  
                         System.exit(0);
                    }});
     validate();   
   }
   public void actionPerformed(ActionEvent e){
     if(e.getSource()==¼��){
         ������Ϣ¼��.clearMess();
         card.show(pCenter,"¼�����");
     }
     else if(e.getSource()==�޸�){
         ������Ϣ�޸�.clearMess();
         card.show(pCenter,"�޸Ľ���");
     }
     else if(e.getSource()==��ѯ���ӡ){
         ������Ϣ��ѯ���ӡ.clearMess();
         ������Ϣ��ѯ���ӡ.setLocation(getBounds().x+getBounds().width,getBounds().y);
         ������Ϣ��ѯ���ӡ.setVisible(true);
     }
     else if(e.getSource()==ɾ��)
         card.show(pCenter,"ɾ������");
     else if(e.getSource()==��ӭ����)
         card.show(pCenter,"��ӭ����");
   }
   public static void main(String args[]){
     new ManagerWindow();
   }
}
