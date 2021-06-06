package ch5;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.*;
public class MazeWindow extends JFrame implements ActionListener{
    Maze maze;
   JMenuBar bar;
   JMenu menuChoice,menuImage;
   JMenuItem wallImage,roadImage,defaultImage;
   File mazeFile,wallImageFile,roadImageFile;
   JButton renew;
   MazeWindow(){
      wallImageFile=new File("wall.jpg");
      roadImageFile=new File("road.jpg");
      bar=new JMenuBar();
      menuChoice=new JMenu("选择迷宫");
      File dir=new File(".");
      File  file[]=dir.listFiles(new FilenameFilter(){
                                    public  boolean accept(File dir,String name){
                                      return name.endsWith("maze");
                                    }
                                 });
      for(int i=0;i< file.length;i++)
           {  JMenuItem item=new JMenuItem(file[i].getName());
              item.addActionListener(this);
              menuChoice.add(item);
           }
      mazeFile=new File(file[0].getName());
      init(); 
      menuImage=new JMenu("选择墙和路的图像(JPG,GIF)");
      wallImage=new JMenuItem("墙的图像");
      roadImage=new JMenuItem("路的图像"); 
      defaultImage=new JMenuItem("墙和路的默认图像");
      menuImage.add(wallImage);
      menuImage.add(roadImage);
      menuImage.add(defaultImage);
      bar.add(menuChoice);
      bar.add(menuImage);
      setJMenuBar(bar);
      wallImage.addActionListener(this);
      roadImage.addActionListener(this); 
      defaultImage.addActionListener(this);
      renew=new JButton("重新开始");
      renew.addActionListener(this);
      add(maze,BorderLayout.CENTER);
      add(renew,BorderLayout.SOUTH);      
      setVisible(true);
      setBounds(60,60,510,480);
      validate();
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
   }
   public void init(){
      if(maze!=null){
          remove(maze);
          remove(maze.getHandleMove());
      }
      maze=new Maze();
      maze.setWallImage(wallImageFile);
      maze.setRoadImage(roadImageFile);
      maze.setMazeFile(mazeFile);  
      add(maze,BorderLayout.CENTER);
      add(maze.getHandleMove(),BorderLayout.NORTH); 
      validate();
   }
   public void actionPerformed(ActionEvent e){
      if(e.getSource()==roadImage){
           JFileChooser chooser=new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & GIF Images", "jpg", "gif");
           chooser.setFileFilter(filter);
           int state=chooser.showOpenDialog(null);
           File file=chooser.getSelectedFile();
           if(file!=null&&state==JFileChooser.APPROVE_OPTION){
               roadImageFile=file;
               maze.setRoadImage(roadImageFile);
           }
      }
      else if(e.getSource()==wallImage){
          JFileChooser chooser=new JFileChooser();
          FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "JPG & GIF Images", "jpg", "gif");
           chooser.setFileFilter(filter);
           int state=chooser.showOpenDialog(null);
           File file=chooser.getSelectedFile();
           if(file!=null&&state==JFileChooser.APPROVE_OPTION){
               wallImageFile=file;
               maze.setWallImage(wallImageFile);
          }
      }
      else if(e.getSource()==defaultImage){
         wallImageFile=new File("wall.jpg"); 
         roadImageFile=new File("road.jpg");
         maze.setWallImage(wallImageFile); 
         maze.setRoadImage(roadImageFile);
      }
      else if(e.getSource()==renew){
         init();
      }
      else{
            JMenuItem item=(JMenuItem)e.getSource(); 
            mazeFile=new File(item.getText());
            init();  
      }
   }
   public static void main(String args[]){
      new MazeWindow();
   }
}


