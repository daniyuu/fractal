//分形树（分形频道：fractal.cn）2004
import java.awt.*;
import javax.swing.*;
import java.lang.*;

public class Tree2 extends JApplet{

  boolean bq;
  int st, r = 255, 
          g = 255,
          B = 255;

  public static final double PI = Math.PI / 180;
                            
 public void init(){

    new Tree2(); 
  }

  public void frameSet(){

   Tree2 shapes = new Tree2();
   JFrame fra = new JFrame("welcome");
   fra.getContentPane().add(shapes,BorderLayout.CENTER);
         fra.setSize(new Dimension(670,400));
         fra.setResizable(false);
          fra.setVisible(true);
         
 }

  public static void main(String args[]){
    new Tree2().frameSet(); 
  }

// /////////////////////////////////////////////////////////////

   public void paint(Graphics g){
     int x = 400,
         y = 300;

      g.drawLine(x - 50, y - 50, x - 50, y - 50 + 120);
        
          drawTree(g, x - 50, y - 50, 50, -90, 1);
   }
   
   public void drawTree(Graphics g, double x, double y,
                                       int L, int a, int tt){
         double x1,y1;

         for(int i = -1; i <= 1; i++){
 
            x1 = x + L * Math.cos((a + i * ((Math.random() * 40) + 20)) * PI);
            y1 = y + L * Math.sin((a + i * ((Math.random() * 40) + 20)) * PI);
              g.drawLine((int) x, (int) y, (int) x1, (int) y1);
   
              if(L > 5){

                 drawTree(g, x1, y1, L - 8, a + i * (int) (Math.random() * 40) + 10, tt);

              }else{

                   if(tt == 1){

                      g.drawArc((int) x1, (int) y1, 2, 2, 0, 180);

                   }else if(tt == 2){

                      g.drawLine((int) x1, (int) y1,
                                 (int) (x1 + Math.random() * 5 * i),
                                 (int) (y1 + Math.random() * 20 + 50));
                   }
              }
         }
   }
   
}
