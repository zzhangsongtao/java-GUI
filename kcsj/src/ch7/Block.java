package ch7;
import javax.swing.ImageIcon;
public class Block {
     String name;            //����,����"��"������
     int aroundMineNumber;         //��Χ�׵���Ŀ
     ImageIcon mineIcon;     //�׵�ͼ��
     boolean isMine=false;   //�Ƿ�����
     boolean isMark=false; //�Ƿ񱻱��
     boolean isOpen=false;   //�Ƿ��ڿ�
     public void setName(String name) { 
         this.name=name; 
     }
     public void setAroundMineNumber(int n) { 
         aroundMineNumber=n;
     }
     public int getAroundMineNumber() {
         return aroundMineNumber;
     }
     public String getName() { 
         return name;  
     }
     public boolean isMine() {
         return isMine;
     } 
     public void setIsMine(boolean b) {
         isMine=b;
     }
     public void setMineIcon(ImageIcon icon){
         mineIcon=icon;
     }
     public ImageIcon getMineicon(){
         return mineIcon;
     }
     public boolean getIsOpen() {
         return isOpen;
     } 
     public void setIsOpen(boolean p) {
         isOpen=p;
     }
     public boolean getIsMark() {
         return isMark;
     } 
     public void setIsMark(boolean m) {
         isMark=m;
     }
}
