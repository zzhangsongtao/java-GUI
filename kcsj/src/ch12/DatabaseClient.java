package ch12;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
public class DatabaseClient extends Applet implements Runnable,ActionListener{
   String formName;      //数据库中的表名
   TextField 输入查询内容;                             
   Choice choice;                                 
   Checkbox  完全一致,前方一致,后方一致,中间包含;      
   CheckboxGroup group=null;
   Button 查询;
   TextArea 显示查询结果;
   Label 提示条;
   Socket socket=null;
   DataInputStream in=null;
   DataOutputStream out=null;
   Thread thread;
   boolean ok=false;
   int N=0;                //字段个数 
   String []ziduanName;    //存放字段名字的数组
   String []ziduanExplain; //存放字段解释的数组
   public void init(){
      thread=new Thread(this);
      输入查询内容=new TextField(19);
      查询=new Button("查询");
      提示条=new Label("正在连接到服务器,请稍等...",Label.CENTER);
      choice=new Choice();
      formName=getParameter("0"); //从index.html得到"0"的值
      String number=getParameter("ziduanAmount");
      N=Integer.parseInt(number);
      ziduanName=new String[N];
      ziduanExplain=new String[N];
      for(int i=1,k=0;i<=N;i++,k++){
        String str=getParameter(""+i); //从index.html得到字段名称及解释
        ziduanName[k]=str.substring(0,str.indexOf(":")).trim();
        ziduanExplain[k]=str.substring(str.indexOf(":")+1).trim();
      }
      for(int k=0;k<ziduanExplain.length;k++){
        choice.add(ziduanExplain[k]);
      }
      choice.select(0);
      group=new CheckboxGroup();
      完全一致=new Checkbox("完全一致",true,group);
      前方一致=new Checkbox("前方一致",false,group);
      后方一致=new Checkbox("后方一致",false,group);
      中间包含=new Checkbox("中间包含",false,group);
      显示查询结果=new TextArea(8,43);
      提示条.setForeground(Color.red);
      提示条.setFont(new Font("TimesRoman",Font.BOLD,24));
      Panel box1=new Panel();                 
      box1.add(new Label("输入查询内容:",Label.CENTER));
      box1.add(输入查询内容); 
      box1.add(choice); 
      box1.add(查询);
      Panel box2=new Panel();                
      box2.add(new Label("选择查询条件:",Label.CENTER));
      box2.add(完全一致);
      box2.add(前方一致);
      box2.add(后方一致);
      box2.add(中间包含); 
      Panel box3=new Panel();             
      box3.add(new Label("查询结果:",Label.CENTER));
      box3.add(显示查询结果);
      add(提示条);
      add(box1);
      add(box2);
      add(box3); 
      查询.addActionListener(this);
      setBackground(Color.cyan);
   }
   public void start(){
      ok=true;
      if(socket!=null&&in!=null&&out!=null){
         try{ socket.close();
              in.close(); 
              out.close();
         }
          catch(Exception ee){}
      }
      try{ socket=new Socket(this.getCodeBase().getHost(), 6666);
           in=new DataInputStream(socket.getInputStream());
           out=new DataOutputStream(socket.getOutputStream());
      }
      catch (IOException ee){
           提示条.setText("连接失败");
      }
      if(socket!=null){
           InetAddress address=socket.getInetAddress();
           提示条.setText("连接:"+address+"成功");
      } 
      if(!(thread.isAlive())){
           thread=new Thread(this);
           thread.start();
      }
   }
   public void stop(){
      ok=false;
      try{
           socket.close();
      }
      catch(IOException e){
           this.showStatus(e.toString());
      }
   }
   public void run(){
      String s=null;
      while(true){
        try{ s=in.readUTF();
        }
        catch (IOException e){
            提示条.setText("与服务器已断开");
            break;
        }
        显示查询结果.append(s);
        if(ok==false)
          break;
     }
  }
  public void actionPerformed(ActionEvent e){
     if(e.getSource()==查询){
        显示查询结果.setText(null);
        String 查询内容="";
        查询内容=输入查询内容.getText();
        int index=choice.getSelectedIndex();
        String 字段=ziduanName[index];       
        String 查询方式=group.getSelectedCheckbox().getLabel();
        if(查询内容.length()>0){
          try{  out.writeUTF("字段个数:"+N);
                out.writeUTF(formName+":"+查询内容+":"+字段+":"+查询方式);
          }
          catch(IOException e1){
                提示条.setText("与服务器已断开");
          } 
        } 
        else
          输入查询内容.setText("请输入内容"); 
     }
  }
}
