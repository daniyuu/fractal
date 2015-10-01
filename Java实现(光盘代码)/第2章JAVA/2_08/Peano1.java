//Hilbert-Peano曲线(分形频道：fractal.cn)2004
import java.awt.*;
import javax.swing.*;
import java.lang.*;

public class Peano1 extends JApplet{
      
    final Color fg = Color.red;
    
    int n = 5, s;                                //控制递归深度
    double x1 = 1, y1 = 1, 
           x2 = 200, y2 = 200;
    boolean str = true;                          //控制第一条线的绘制
    public static final int STARTX = 103, 
                            STARTY = 196;        //图形的首点

    int startDrawX,startDrawY;
 
 public void frameSet(){
   Peano1 shapes = new Peano1();
   JFrame fra = new JFrame("welcome");
   fra.getContentPane().add(shapes,BorderLayout.CENTER);
         fra.setSize(new Dimension(550,500));
          fra.setVisible(true);
 }

  public static void main(String args[]){
    new Peano1().frameSet();
  }

// ////////////////////////////////////////////////////////////

  public void paint(Graphics g){
   // setBackground(fg);
    g.setColor(Color.red);
    drawShapes(g,n,s,x1,y1,x2,y2);
  }

  public void drawShapes(Graphics g,int n, int s,
                             double x1, double y1, 
                             double x2, double y2){

         double dx = x2 - x1,
                dy = y2 - y1;
         
      
     if(n == 1){
  
        lineTo(g, (int) (100 + Math.round(x1+dx/4)),(int) (200 - Math.round(y1+dy/4)));
        lineTo(g, (int) (100 + Math.round(x1+(2-s)*dx/4)), (int) (200 - Math.round(y1+(2+s)*dy/4)));
        lineTo(g, (int) (100 + Math.round(x1+3*dx/4)), (int) (200 - Math.round(y1+3*dy/4)));
        lineTo(g, (int) (100 + Math.round(x1+(2+s)*dx/4)),(int) (200 - Math.round(y1+(2-s)*dy/4)));
     
     }else{
        if(s>0){
           drawShapes(g,n-1,-1,x1,y1,(x1+x2)/2,(y1+y2)/2);
           drawShapes(g,n-1,1,x1,(y1+y2)/2,(x1+x2)/2,y2);
           drawShapes(g,n-1,1,(x1+x2)/2,(y1+y2)/2,x2,y2);
           drawShapes(g,n-1,-1,x2,(y1+y2)/2,(x1+x2)/2,y1); 
        }else{
            drawShapes(g,n-1,1,x1,y1,(x1+x2)/2,(y1+y2)/2);
            drawShapes(g,n-1,-1,(x1+x2)/2,y1,x2,(y1+y2)/2);
            drawShapes(g,n-1,-1,(x1+x2)/2,(y1+y2)/2,x2,y2);
            drawShapes(g,n-1,1,(x1+x2)/2,y2,x1,(y1+y2)/2);
        }
     }
  }
  
 public void lineTo(Graphics g, int endX, int endY){

   if(str){                                       // Draw the first line of the shape.
        g.drawLine(STARTX,STARTY,endX,endY);
     
        this.str = false;
          
   }else{   
    g.drawLine(startDrawX, startDrawY, endX, endY);
    }

   startDrawX = endX;
   startDrawY = endY;
 }
 
}