//Koch曲线（分形频道：fractal.cn）2004
import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.lang.Math.*;

public class koch extends JApplet{
  
 public void init(){
    new koch();
  }

  public void frameSet(){

   koch shapes = new koch();
   JFrame fra = new JFrame("welcome");
   fra.getContentPane().add(shapes,BorderLayout.CENTER);
         fra.setSize(new Dimension(670,400));
         fra.setResizable(false);
          fra.setVisible(true);
         
 }

  public static void main(String args[]){
    new koch().frameSet();
 
  }

// /////////////////////////////////////////////////////////////
   
   public void paint(Graphics g){
       g.setColor(Color.black);
       Kochv(g,100, 100, 800, 100);
   }

   public void Kochv(Graphics g, double aX, double aY, double bX, double bY){

       double c=10,
              cX = 0, cY = 0, 
              dX = 0, dY = 0,
              eX = 0, eY = 0,
              l  = 0, alpha = 0; 
       if((bX - aX) * (bX - aX) + (bY - aY) * (bY - aY) < c){

         g.drawLine((int) aX, 500-(int) aY, (int) bX, 500-(int) bY);

       }else{

        cX = aX + (bX - aX) / 3;
        cY = aY + (bY - aY) / 3;
        eX = bX - (bX - aX) / 3;
        eY = bY - (bY - aY) / 3;
         l = Math.sqrt((eX - cX) * (eX - cX) + (eY - cY) * (eY - cY));
        alpha = Math.atan((eY - cY) / (eX - cX));
        if( (alpha >= 0) && ((eX - cX) < 0) || (alpha <= 0) && ((eX - cX) < 0)){ 
            alpha = alpha + 3.14159;
        }

        dY = cY + Math.sin(alpha + 3.14159 / 3) * l;
        dX = cX + Math.cos(alpha + 3.14159 / 3) * l;
        
       Kochv(g,aX, aY, cX, cY);
       Kochv(g,eX, eY, bX, bY);
       Kochv(g,cX, cY, dX, dY);
       Kochv(g,dX, dY, eX, eY);

       
         
       }        
   }   
}
