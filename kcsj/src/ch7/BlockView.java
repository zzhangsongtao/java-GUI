package ch7;
import javax.swing.*;
import java.awt.*;
public class BlockView extends JPanel{ 
     JLabel blockNameOrIcon; //������ʾBlock�����name��number��mineIcon����
     JButton blockCover;     //�����ڵ�blockNameOrIcon.
     CardLayout card;        //��Ƭʽ����
     BlockView(){
        card=new CardLayout();
        setLayout(card);
        blockNameOrIcon=new JLabel("",JLabel.CENTER);
        blockNameOrIcon.setHorizontalTextPosition(AbstractButton.CENTER);
        blockNameOrIcon.setVerticalTextPosition(AbstractButton.CENTER); 
        blockCover=new JButton(); 
        add("cover",blockCover);
        add("view",blockNameOrIcon);
     }
     public void giveView(Block block){
        if(block.isMine){
           blockNameOrIcon.setText(block.getName());
           blockNameOrIcon.setIcon(block.getMineicon());
        }
        else {
           int n=block.getAroundMineNumber();
           if(n>=1)
             blockNameOrIcon.setText(""+n);
           else
             blockNameOrIcon.setText(" ");
        }
     }
     public void seeBlockNameOrIcon(){
        card.show(this,"view");
        validate();
     }
     public void seeBlockCover(){
        card.show(this,"cover");
        validate();
     }
     public JButton getBlockCover(){
        return blockCover;
     } 
}
