package ch2;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.LinkedList;
import java.io.*;
public class CalculatorWindow extends JFrame implements ActionListener{
    NumberButton numberButton[];                  
    OperationButton operationButton[];             
    JButton С�������,�����Ų���,�˸����,�ȺŲ���,�������,sin;
    JTextField resultShow;          //��ʾ������
    JTextField showComputerProcess; //��ʾ��ǰ�������
    JTextArea  saveComputerProcess; //��ʾ���㲽��
    JButton saveButton,copyButton,clearButton;
    LinkedList<String> list;   //����������ŵ�һ����������������ź͵ڶ���������
    HandleDigit handleDigit;  //������ActionEvent�¼�
    HandleOperation handleOperation ;
    HandleBack handleBack;
    HandleClear handleClear;
    HandleEquality handleEquality;
    HandleDot handleDot;
    HandlePOrN handlePOrN;
    HandleSin handleSin;
    public CalculatorWindow(){
        setTitle("������");
        JPanel panelLeft,panelRight; 
        list=new LinkedList<String>();
        resultShow=new JTextField(10);
        resultShow.setHorizontalAlignment(JTextField.RIGHT);
        resultShow.setForeground(Color.blue);
        resultShow.setFont(new Font("TimesRoman",Font.BOLD,16));
        resultShow.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
        resultShow.setEditable(false);
        resultShow.setBackground(Color.white);
        showComputerProcess=new JTextField();
        showComputerProcess.setHorizontalAlignment(JTextField.CENTER);
        showComputerProcess.setFont(new Font("Arial",Font.BOLD,16));
        showComputerProcess.setBackground(Color.cyan);
        showComputerProcess.setEditable(false);
        saveComputerProcess=new JTextArea();
        saveComputerProcess.setEditable(false);
        saveComputerProcess.setFont(new Font("����",Font.PLAIN,16));
        numberButton=new NumberButton[10];
        handleDigit=new HandleDigit(list,resultShow,showComputerProcess);
        for(int i=0;i<=9;i++){
            numberButton[i]=new NumberButton(i);
            numberButton[i].setFont(new Font("Arial",Font.BOLD,20));
            numberButton[i].addActionListener(handleDigit);
        }
        operationButton=new OperationButton[4];
        handleOperation=new HandleOperation(list,resultShow,
                         showComputerProcess,saveComputerProcess);
        String �������[]={"+","-","*","/"}; 
        for(int i=0;i<4;i++){
           operationButton[i]=new OperationButton(�������[i]);
           operationButton[i].setFont(new Font("Arial",Font.BOLD,20));
           operationButton[i].addActionListener(handleOperation);
        }
       С�������=new JButton(".");
       handleDot=new HandleDot(list,resultShow,showComputerProcess);
       С�������.addActionListener(handleDot);
       �����Ų���=new JButton("+/-"); 
       handlePOrN=new HandlePOrN(list,resultShow,showComputerProcess);
       �����Ų���.addActionListener(handlePOrN);
       �ȺŲ���=new JButton("=");
       handleEquality=new HandleEquality(list,resultShow,
                                         showComputerProcess,saveComputerProcess);
       �ȺŲ���.addActionListener(handleEquality); 
       sin=new JButton("sin");
       handleSin=new HandleSin(list,resultShow,
                               showComputerProcess,saveComputerProcess);
       sin.addActionListener(handleSin);
       �˸����=new JButton("�˸�");
       handleBack=new HandleBack(list,resultShow,showComputerProcess);
       �˸����.addActionListener(handleBack);
       �������=new JButton("C");
       handleClear=new HandleClear(list,resultShow,showComputerProcess);
       �������.addActionListener(handleClear); 
       �������.setForeground(Color.red);
       �˸����.setForeground(Color.red);
       �ȺŲ���.setForeground(Color.red);
       sin.setForeground(Color.blue);
       �����Ų���.setForeground(Color.blue);
       С�������.setForeground(Color.blue);
       panelLeft=new JPanel(); 
       panelRight=new JPanel();
       panelLeft.setLayout(new BorderLayout()); 
       JPanel centerInLeft=new JPanel();
       panelLeft.add(resultShow,BorderLayout.NORTH);
       panelLeft.add(centerInLeft,BorderLayout.CENTER);
       centerInLeft.setLayout(new GridLayout(4,5)); 
       centerInLeft.add(numberButton[1]);
       centerInLeft.add(numberButton[2]);
       centerInLeft.add(numberButton[3]);
       centerInLeft.add(operationButton[0]);
       centerInLeft.add(�������);
       centerInLeft.add(numberButton[4]);
       centerInLeft.add(numberButton[5]);
       centerInLeft.add(numberButton[6]);
       centerInLeft.add(operationButton[1]);
       centerInLeft.add(�˸����);
       centerInLeft.add(numberButton[7]);
       centerInLeft.add(numberButton[8]);
       centerInLeft.add(numberButton[9]);
       centerInLeft.add(operationButton[2]);
       centerInLeft.add(sin);
       centerInLeft.add(numberButton[0]);
       centerInLeft.add(�����Ų���);
       centerInLeft.add(С�������);
       centerInLeft.add(operationButton[3]);
       centerInLeft.add(�ȺŲ���);
       panelRight.setLayout(new BorderLayout());
       panelRight.add(showComputerProcess,BorderLayout.NORTH);
       saveButton=new JButton("����");
       copyButton=new JButton("����");
       clearButton=new JButton("���");
       saveButton.setToolTipText("���������̵��ļ�");
       copyButton.setToolTipText("����ѡ�еļ������");
       clearButton.setToolTipText("����������");
       saveButton.addActionListener(this);
       copyButton.addActionListener(this);
       clearButton.addActionListener(this); 
       panelRight.add(new JScrollPane(saveComputerProcess),BorderLayout.CENTER);
       JPanel southInPanelRight=new JPanel();
       southInPanelRight.add(saveButton);
       southInPanelRight.add(copyButton);
       southInPanelRight.add(clearButton);
       panelRight.add(southInPanelRight,BorderLayout.SOUTH);
       JSplitPane split=new JSplitPane
                (JSplitPane.HORIZONTAL_SPLIT,panelLeft,panelRight);
       add(split,BorderLayout.CENTER);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setVisible(true);
       setBounds(100,50,528,258);
       validate();
   }
   public void actionPerformed(ActionEvent e){
       if(e.getSource()==copyButton)
          saveComputerProcess.copy();
       if(e.getSource()==clearButton)
          saveComputerProcess.setText(null);
       if(e.getSource()==saveButton){
           JFileChooser chooser=new JFileChooser();
           int state=chooser.showSaveDialog(null);
           File file=chooser.getSelectedFile();
           if(file!=null&&state==JFileChooser.APPROVE_OPTION){
             try{  String content=saveComputerProcess.getText();
                   StringReader read=new StringReader(content);
                   BufferedReader in= new BufferedReader(read);
                   FileWriter outOne=new FileWriter(file);
                   BufferedWriter out= new BufferedWriter(outOne);
                   String str=null;
                   while((str=in.readLine())!=null){
                      out.write(str);
                      out.newLine();
                   }
                  in.close();
                  out.close();
             } 
             catch(IOException e1){}
           }
       }
   }
   public static void main(String args[]){
       new CalculatorWindow();
   }  
}
