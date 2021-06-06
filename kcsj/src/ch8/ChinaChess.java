package ch8;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class ChinaChess extends JFrame implements ActionListener{
    ChessBoard board;
    DemoManulDialog demoManul;
    JMenuBar bar;
    JMenu makeMenu,showMenu;
    JMenuItem makeShiZhanManul,makeCanJuManul,openManulAndShow;
    JButton startMakeManul,saveManul;
    JTextField hintMessage;
    public ChinaChess(){
        setTitle("�й�������������ϵͳ");
        board=new ChessBoard();
        add(board,BorderLayout.CENTER);
        bar=new JMenuBar();
        makeMenu=new JMenu("ѡ���������׵ķ�ʽ");
        showMenu=new JMenu("����");
        makeShiZhanManul=new JMenuItem("����ʵս����");
        makeCanJuManul=new JMenuItem("�����о�����");
        openManulAndShow=new JMenuItem("��һ�����ײ���ʾ");
        makeMenu.add(makeShiZhanManul);
        makeMenu.add(makeCanJuManul);
        showMenu.add(openManulAndShow);
        bar.add(makeMenu);
        bar.add(showMenu);
        setJMenuBar(bar);
        makeShiZhanManul.addActionListener(this);
        makeCanJuManul.addActionListener(this);
        openManulAndShow.addActionListener(this);
        startMakeManul=new JButton("��ʼ��������");
        startMakeManul.setVisible(false);
        startMakeManul.addActionListener(this);
        saveManul=new JButton("��������");
        saveManul.setVisible(false);
        saveManul.addActionListener(this);
        hintMessage=new JTextField(30);
        hintMessage.setHorizontalAlignment(JTextField.CENTER);
        hintMessage.setFont(new Font("ϸ��",Font.PLAIN,15));
        hintMessage.setEditable(false);
        hintMessage.setText("�뵥���˵�,ѡ���������׵ķ�ʽ����ʾ���е�����");
        hintMessage.setBackground(Color.green);
        JPanel north=new JPanel(); 
        north.add(hintMessage);
        north.add(startMakeManul);
        north.add(saveManul); 
        add(north,BorderLayout.NORTH); 
        setVisible(true);
        setBounds(120,10,710,580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        validate();
        JOptionPane.showMessageDialog(this,
                      "�뵥���˵�,ѡ���������׵ķ�ʽ����ʾ���е�����","��ʾ�Ի���",
                       JOptionPane.WARNING_MESSAGE);
   }
   public void actionPerformed(ActionEvent e){
       String message="����\"��ʼ��������\",Ȼ���϶���������";
       if(e.getSource()==makeShiZhanManul) {
          board.setShizhanPlay();
          hintMessage.setText(message);
          startMakeManul.setVisible(true);
          saveManul.setVisible(false);
          JOptionPane.showMessageDialog(this,message,"��ʾ�Ի���",                                                           JOptionPane.WARNING_MESSAGE);
       }   
       if(e.getSource()==makeCanJuManul){
          message="������������������̰ںòо�,Ȼ�󵥻�\"��ʼ��������\"";
          hintMessage.setText(message);
          board.setCanjuPlay(); 
          startMakeManul.setVisible(true);
          saveManul.setVisible(false);
          JOptionPane.showMessageDialog(this,message,"��ʾ�Ի���",                                                           JOptionPane.WARNING_MESSAGE);
       } 
       if(e.getSource()==startMakeManul){
          board.startMakeManul();
          hintMessage.setText("����\"��������\"��ť���Ա�������");
          saveManul.setVisible(true);
          startMakeManul.setVisible(false);
       } 
       if(e.getSource()==saveManul){
           startMakeManul.setVisible(false);
           JFileChooser chooser=new JFileChooser();
           int state=chooser.showSaveDialog(null);
           File file=chooser.getSelectedFile();
           if(file!=null&&state==JFileChooser.APPROVE_OPTION){
             try{ 
                  FileOutputStream out=new FileOutputStream(file);
                  ObjectOutputStream objectOut=new ObjectOutputStream(out);
                  objectOut.writeObject(board); 
                  out.close(); 
                  objectOut.close(); 
            }
            catch(Exception event){}
           } 
       } 
       if(e.getSource()==openManulAndShow){
           JFileChooser chooser=new JFileChooser();
           int state=chooser.showOpenDialog(null);
           File file=chooser.getSelectedFile();
           if(file!=null&&state==JFileChooser.APPROVE_OPTION){
              demoManul=new DemoManulDialog(file);
              demoManul.setVisible(true);
           }
       }
   }
   public static void main(String args[]){
       new ChinaChess();
   }
}
