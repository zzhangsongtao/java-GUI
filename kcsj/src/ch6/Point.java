package ch6;
public class Point{
    int x,y;
    boolean haveBlock;
    Block  block=null;                   
    public Point(int x,int y){
       this.x=x;
       this.y=y;
    }
    public boolean isHaveBlock(){
       return haveBlock;
    }
    public void setHaveBlock(boolean boo){
       haveBlock=boo;
    }
    public int getX(){
       return x;
    }
    public int getY(){
       return y;
    }
    public boolean equals(Point p){
       if(p.getX()==this.getX()&&p.getY()==this.getY())
           return true;
       else
           return false; 
    }
    public void setBlock(Block block){
       this.block=block;  
    }
    public Block getBlock(){
       return block;
    }
}
