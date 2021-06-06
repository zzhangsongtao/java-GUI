package ch6;
public class VerifySuccess{
   Point [][] point;
   Object [] object;
   public void setPoint(Point [][] point){
       this.point=point;
   }
   public void setObject(Object [] object){
       this.object=object;
   }
    public boolean isSuccess(){
       boolean boo=true;
       int k=0;
       for(int i=0;i<point.length;i++){
         if(i<point.length-1){
           for(int j=0;j<point[i].length;j++){ 
               if(!(point[i][j].getBlock().getObject()==object[k])){
                  boo=false;
                  break;
               }
               k++;
           } 
         }
         else{
           for(int j=0;j<point[i].length-1;j++){ 
               if(!(point[i][j].getBlock().getObject()==object[k])){
                  boo=false;
                  break;
               }
               k++;
           }  
         }  
       } 
       return boo;
    }  
}