package ch12;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
public class DatabaseClient extends Applet implements Runnable,ActionListener{
   String formName;      //���ݿ��еı���
   TextField �����ѯ����;                             
   Choice choice;                                 
   Checkbox  ��ȫһ��,ǰ��һ��,��һ��,�м����;      
   CheckboxGroup group=null;
   Button ��ѯ;
   TextArea ��ʾ��ѯ���;
   Label ��ʾ��;
   Socket socket=null;
   DataInputStream in=null;
   DataOutputStream out=null;
   Thread thread;
   boolean ok=false;
   int N=0;                //�ֶθ��� 
   String []ziduanName;    //����ֶ����ֵ�����
   String []ziduanExplain; //����ֶν��͵�����
   public void init(){
      thread=new Thread(this);
      �����ѯ����=new TextField(19);
      ��ѯ=new Button("��ѯ");
      ��ʾ��=new Label("�������ӵ�������,���Ե�...",Label.CENTER);
      choice=new Choice();
      formName=getParameter("0"); //��index.html�õ�"0"��ֵ
      String number=getParameter("ziduanAmount");
      N=Integer.parseInt(number);
      ziduanName=new String[N];
      ziduanExplain=new String[N];
      for(int i=1,k=0;i<=N;i++,k++){
        String str=getParameter(""+i); //��index.html�õ��ֶ����Ƽ�����
        ziduanName[k]=str.substring(0,str.indexOf(":")).trim();
        ziduanExplain[k]=str.substring(str.indexOf(":")+1).trim();
      }
      for(int k=0;k<ziduanExplain.length;k++){
        choice.add(ziduanExplain[k]);
      }
      choice.select(0);
      group=new CheckboxGroup();
      ��ȫһ��=new Checkbox("��ȫһ��",true,group);
      ǰ��һ��=new Checkbox("ǰ��һ��",false,group);
      ��һ��=new Checkbox("��һ��",false,group);
      �м����=new Checkbox("�м����",false,group);
      ��ʾ��ѯ���=new TextArea(8,43);
      ��ʾ��.setForeground(Color.red);
      ��ʾ��.setFont(new Font("TimesRoman",Font.BOLD,24));
      Panel box1=new Panel();                 
      box1.add(new Label("�����ѯ����:",Label.CENTER));
      box1.add(�����ѯ����); 
      box1.add(choice); 
      box1.add(��ѯ);
      Panel box2=new Panel();                
      box2.add(new Label("ѡ���ѯ����:",Label.CENTER));
      box2.add(��ȫһ��);
      box2.add(ǰ��һ��);
      box2.add(��һ��);
      box2.add(�м����); 
      Panel box3=new Panel();             
      box3.add(new Label("��ѯ���:",Label.CENTER));
      box3.add(��ʾ��ѯ���);
      add(��ʾ��);
      add(box1);
      add(box2);
      add(box3); 
      ��ѯ.addActionListener(this);
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
           ��ʾ��.setText("����ʧ��");
      }
      if(socket!=null){
           InetAddress address=socket.getInetAddress();
           ��ʾ��.setText("����:"+address+"�ɹ�");
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
            ��ʾ��.setText("��������ѶϿ�");
            break;
        }
        ��ʾ��ѯ���.append(s);
        if(ok==false)
          break;
     }
  }
  public void actionPerformed(ActionEvent e){
     if(e.getSource()==��ѯ){
        ��ʾ��ѯ���.setText(null);
        String ��ѯ����="";
        ��ѯ����=�����ѯ����.getText();
        int index=choice.getSelectedIndex();
        String �ֶ�=ziduanName[index];       
        String ��ѯ��ʽ=group.getSelectedCheckbox().getLabel();
        if(��ѯ����.length()>0){
          try{  out.writeUTF("�ֶθ���:"+N);
                out.writeUTF(formName+":"+��ѯ����+":"+�ֶ�+":"+��ѯ��ʽ);
          }
          catch(IOException e1){
                ��ʾ��.setText("��������ѶϿ�");
          } 
        } 
        else
          �����ѯ����.setText("����������"); 
     }
  }
}
