package ch9;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
public class CalendarWindow extends JFrame implements ActionListener,
MouseListener,FocusListener{
    int year,month,day;
    CalendarMessage calendarMessage;
    CalendarPad calendarPad;
    NotePad notePad;
    JTextField showYear,showMonth;
    JTextField [] showDay;
    CalendarImage calendarImage;
    Clock clock; 
    JButton nextYear,previousYear,nextMonth,previousMonth;
    JButton saveDailyRecord,deleteDailyRecord,readDailyRecord;
    File dir;
    Color backColor=Color.white; 
    public  CalendarWindow(){
       dir=new File("./dailyRecord");
       dir.mkdir();  
       showDay=new JTextField[42];
       for(int i=0;i<showDay.length;i++){
          showDay[i]=new JTextField();
          showDay[i].setBackground(backColor);
          showDay[i].setLayout(new GridLayout(3,3)); 
          showDay[i].addMouseListener(this); 
          showDay[i].addFocusListener(this);
       }
       calendarMessage=new CalendarMessage();
       calendarPad=new CalendarPad();
       notePad=new NotePad();  
       Calendar calendar=Calendar.getInstance(); 
       calendar.setTime(new Date()); 
       year=calendar.get(Calendar.YEAR);
       month=calendar.get(Calendar.MONTH)+1;
       day=calendar.get(Calendar.DAY_OF_MONTH);
       calendarMessage.setYear(year);
       calendarMessage.setMonth(month);
       calendarMessage.setDay(day);
       calendarPad.setCalendarMessage(calendarMessage);
       calendarPad.setShowDayTextField(showDay);
       notePad.setShowMessage(year,month,day); 
       calendarPad.showMonthCalendar();
       doMark();  //给有日志的号码做标记,见后面的doMark()方法
       calendarImage=new CalendarImage();
       calendarImage.setImageFile(new File("flower.jpg"));
       clock=new Clock();
       JSplitPane splitV1=
       new JSplitPane(JSplitPane.VERTICAL_SPLIT,calendarPad,calendarImage);
       JSplitPane splitV2=
       new JSplitPane(JSplitPane.VERTICAL_SPLIT,notePad,clock); 
       JSplitPane splitH=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,splitV1,splitV2);
       add(splitH,BorderLayout.CENTER);
       showYear=new JTextField(""+year,6);
       showYear.setFont(new Font("TimesRoman",Font.BOLD,12));
       showYear.setHorizontalAlignment(JTextField.CENTER);
       showMonth=new JTextField(" "+month,4);
       showMonth.setFont(new Font("TimesRoman",Font.BOLD,12));
       showMonth.setHorizontalAlignment(JTextField.CENTER);
       nextYear=new JButton("下年");
       previousYear=new JButton("上年");
       nextMonth=new JButton("下月");
       previousMonth=new JButton("上月");
       nextYear.addActionListener(this);
       previousYear.addActionListener(this);
       nextMonth.addActionListener(this);
       previousMonth.addActionListener(this);
       showYear.addActionListener(this);
       JPanel north=new JPanel(); 
       north.add(previousYear); 
       north.add(showYear);
       north.add(nextYear);
       north.add(previousMonth);
       north.add(showMonth);
       north.add(nextMonth);
       add(north,BorderLayout.NORTH);
       saveDailyRecord=new JButton("保存日志") ;
       deleteDailyRecord=new JButton("删除日志");
       readDailyRecord=new JButton("读取日志");
       saveDailyRecord.addActionListener(this);
       deleteDailyRecord.addActionListener(this);
       readDailyRecord.addActionListener(this);
       JPanel pSouth=new JPanel();       
       pSouth.add(saveDailyRecord);
       pSouth.add(deleteDailyRecord);
       pSouth.add(readDailyRecord);
       add(pSouth,BorderLayout.SOUTH);
       setVisible(true);
       setBounds(60,60,660,480);
       validate();
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }
    public void actionPerformed(ActionEvent e){
       if(e.getSource()==nextYear){
          year++;
          showYear.setText(""+year);
          calendarMessage.setYear(year);
          calendarPad.setCalendarMessage(calendarMessage);
          calendarPad.showMonthCalendar();
          notePad.setShowMessage(year,month,day); 
          doMark(); 
       }
       else if(e.getSource()==previousYear){
          year--;
          showYear.setText(""+year);
          calendarMessage.setYear(year);
          calendarPad.setCalendarMessage(calendarMessage);
          calendarPad.showMonthCalendar();
          notePad.setShowMessage(year,month,day);  
          doMark(); 
       }
       else if(e.getSource()==nextMonth){
          month++;
          if(month>12) month=1;
          showMonth.setText(" "+month);
          calendarMessage.setMonth(month);
          calendarPad.setCalendarMessage(calendarMessage);
          calendarPad.showMonthCalendar(); 
          notePad.setShowMessage(year,month,day); 
          doMark();
       }
       else if(e.getSource()==previousMonth){
          month--;
          if(month<1) month=12;
          showMonth.setText(" "+month);
          calendarMessage.setMonth(month);
          calendarPad.setCalendarMessage(calendarMessage);
          calendarPad.showMonthCalendar();
          notePad.setShowMessage(year,month,day); 
          doMark(); 
       }
       else if(e.getSource()==showYear){
           String s=showYear.getText().trim();
           char a[]=s.toCharArray();
           boolean boo=false;
           for(int i=0;i<a.length;i++)
              if(!(Character.isDigit(a[i])))
                   boo=true;         
           if(boo==true)   //弹出“警告”消息对话框
               JOptionPane.showMessageDialog(this,"您输入了非法年份","警告",
                                             JOptionPane.WARNING_MESSAGE);
           else if(boo==false)
              year=Integer.parseInt(s);
           showYear.setText(""+year);
           calendarMessage.setYear(year);
           calendarPad.setCalendarMessage(calendarMessage);
           calendarPad.showMonthCalendar(); 
           notePad.setShowMessage(year,month,day); 
           doMark(); 
       }
       else if(e.getSource()==saveDailyRecord){
           notePad.save(dir,year,month,day);
           doMark();
       }
       else if(e.getSource()==deleteDailyRecord){
           notePad.delete(dir,year,month,day);
           doMark();
       } 
       else if(e.getSource()==readDailyRecord)
           notePad.read(dir,year,month,day);
    }
    public void mousePressed(MouseEvent e){
       JTextField text=(JTextField)e.getSource();
       String str=text.getText().trim();
       try{ day=Integer.parseInt(str);
       }
       catch(NumberFormatException exp){
       } 
       calendarMessage.setDay(day); 
       notePad.setShowMessage(year,month,day);  
    }
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e)  {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void focusGained(FocusEvent e){
        Component com=(Component)e.getSource();
        com.setBackground(Color.pink);
    }
    public void focusLost(FocusEvent e){
        Component com=(Component)e.getSource();
        com.setBackground(backColor);
    }
    public void doMark(){
       for(int i=0;i<showDay.length;i++){
          showDay[i].removeAll();
          String str=showDay[i].getText().trim();
          try{
               int n=Integer.parseInt(str);
               if(isHaveDailyRecord(n)==true){ //见后面的isHaveDailyRecord()方法
                  JLabel mess=new JLabel("有");
                  mess.setFont(new Font("TimesRoman",Font.PLAIN,11));
                  mess.setForeground(Color.blue) ; 
                  showDay[i].add(mess);
               }
          }
          catch(Exception exp){}
       }
       calendarPad.repaint(); 
       calendarPad.validate();   
    }
    public boolean isHaveDailyRecord(int n){
       String key=""+year+""+month+""+n;
       String [] dayFile=dir.list();
       boolean boo=false;
       for(int k=0;k<dayFile.length;k++){
           if(dayFile[k].equals(key+".txt")){
             boo=true;
             break;
           }
       } 
       return boo;
    } 
    public static void main(String args[]){
       new CalendarWindow();
    }
}  
