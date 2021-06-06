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
        setTitle("中国象棋棋谱制作系统");
        board=new ChessBoard();
        add(board,BorderLayout.CENTER);
        bar=new JMenuBar();
        makeMenu=new JMenu("选择制作棋谱的方式");
        showMenu=new JMenu("棋谱");
        makeShiZhanManul=new JMenuItem("制作实战棋谱");
        makeCanJuManul=new JMenuItem("制作残局棋谱");
        openManulAndShow=new JMenuItem("打开一个棋谱并演示");
        makeMenu.add(makeShiZhanManul);
        makeMenu.add(makeCanJuManul);
        showMenu.add(openManulAndShow);
        bar.add(makeMenu);
        bar.add(showMenu);
        setJMenuBar(bar);
        makeShiZhanManul.addActionListener(this);
        makeCanJuManul.addActionListener(this);
        openManulAndShow.addActionListener(this);
        startMakeManul=new JButton("开始制作棋谱");
        startMakeManul.setVisible(false);
        startMakeManul.addActionListener(this);
        saveManul=new JButton("保存棋谱");
        saveManul.setVisible(false);
        saveManul.addActionListener(this);
        hintMessage=new JTextField(30);
        hintMessage.setHorizontalAlignment(JTextField.CENTER);
        hintMessage.setFont(new Font("细黑",Font.PLAIN,15));
        hintMessage.setEditable(false);
        hintMessage.setText("请单击菜单,选择制作棋谱的方式或演示已有的棋谱");
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
                      "请单击菜单,选择制作棋谱的方式或演示已有的棋谱","提示对话框",
                       JOptionPane.WARNING_MESSAGE);
   }
   public void actionPerformed(ActionEvent e){
       String message="单击\"开始制作棋谱\",然后拖动棋子走棋";
       if(e.getSource()==makeShiZhanManul) {
          board.setShizhanPlay();
          hintMessage.setText(message);
          startMakeManul.setVisible(true);
          saveManul.setVisible(false);
          JOptionPane.showMessageDialog(this,message,"提示对话框",                                                           JOptionPane.WARNING_MESSAGE);
       }   
       if(e.getSource()==makeCanJuManul){
          message="将棋盒中棋子拖入棋盘摆好残局,然后单击\"开始制作棋谱\"";
          hintMessage.setText(message);
          board.setCanjuPlay(); 
          startMakeManul.setVisible(true);
          saveManul.setVisible(false);
          JOptionPane.showMessageDialog(this,message,"提示对话框",                                                           JOptionPane.WARNING_MESSAGE);
       } 
       if(e.getSource()==startMakeManul){
          board.startMakeManul();
          hintMessage.setText("单击\"保存棋谱\"按钮可以保存棋谱");
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
