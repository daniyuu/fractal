//分形树（分形频道：fractal.cn）
import java.awt.*;
import javax.swing.*;
import java.lang.*;
public class Tree extends JApplet{ 
 public static final int MINI_LENGTH = 3;
 public void init(){
    new Tree();
    }
public void frameSet(){
    Tree shapes = new Tree();
    JFrame fra = new JFrame("welcome");
    fra.getContentPane().add(shapes,BorderLayout.CENTER);
        fra.setSize(new Dimension(670,400));
        fra.setResizable(false);
        fra.setVisible(true);
     }
public static void main(String args[]){
   new Tree().frameSet();
    }
// /////////////////////////////////////////////////////////////
public void paint(Graphics g){
    g.setColor(Color.black);
    drawShape(g, 300, 550, -Math.PI / 2, 300);
   }
public void drawShape(Graphics g, double x, double y,
   double angle, int length){
   double x1,y1;
   if (length > MINI_LENGTH){
          x1 = Math.cos(angle) * length + x;
          y1 = Math.sin(angle) * length + y;
          g.drawLine((int) x1,(int) y1,   //the first point.
          (int) x, (int) y);   //the second point.
          drawShape(g, (x1 + x) / 2, (y1 + y) / 2,
          angle - Math.PI / 8, length / 2);
         drawShape(g, x1, y1, angle + Math.PI / 8, length / 2);
     } 
   }
}