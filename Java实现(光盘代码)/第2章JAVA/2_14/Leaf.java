//������������Ƶ����fractal.cn��
import java.awt.*;
import javax.swing.*;
import java.lang.*;

public class Leaf extends JApplet{

    boolean bq;
    int st, r = 255, 
            g = 255,
            b = 255;

    public static final double PI = Math.PI / 180;
                            
    public void init()
    {
        new Leaf(); 
    }

    public void frameSet()
    {
        Leaf shapes = new Leaf();
        JFrame fra = new JFrame("welcome");
        fra.getContentPane().add(shapes,BorderLayout.CENTER);
        fra.setSize(new Dimension(670,400));
        fra.setResizable(false);
        fra.setVisible(true);
         
    }

    public static void main(String args[])
    {
        new Leaf().frameSet(); 
    }

    /////////////////////////////////////////////////////////////

    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        drawLeaf(g,300, 500, 100,270);
    }
   
    public void drawLeaf(Graphics g,double x,double y,
                        double L, double a)
    {
        
        double x1,x2,x1L,x2L,x2R,x1R,
               y1,y2,y1L,y2L,y2R,y1R;
        float B = 50;
        float C =9;
        float s1 = 2;
        float s2 = 3 ;
        float s3 = 1.2f;

        if(L > s1)
        {
            x2 = x + L * Math.cos(a * PI);
            y2 = y + L * Math.sin(a * PI);
            x2R = x2 + L / s2 * Math.cos((a + B) * PI);
            y2R = y2 + L / s2 * Math.sin((a + B) * PI);
            x2L = x2 + L / s2 * Math.cos((a - B) * PI);
            y2L = y2 + L / s2 * Math.sin((a - B) * PI);
  
            x1 = x + L / s2 * Math.cos(a * PI);
            y1 = y + L / s2 * Math.sin(a * PI);
            x1L = x1 + L / s2 * Math.cos((a - B) * PI);
            y1L = y1 + L / s2 * Math.sin((a - B) * PI);
            x1R = x1 + L / s2 * Math.cos((a + B) * PI);
            y1R = y1 + L / s2 * Math.sin((a + B) * PI);
             
            g.drawLine((int) x, (int) y, (int) x2, (int) y2);
            g.drawLine((int) x2, (int) y2, (int) x2R, (int) y2R);
            g.drawLine((int) x2, (int) y2, (int) x2L, (int) y2L);
            g.drawLine((int) x1, (int) y1, (int) x1L, (int) y1L); 
            g.drawLine((int) x1, (int) y1, (int) x1R, (int) y1R);

               
            drawLeaf(g, x2, y2, L / s3, a + C);
            drawLeaf(g, x2R, y2R, L / s2, a + B);
            drawLeaf(g, x2L, y2L, L / s2, a - B);
            drawLeaf(g, x1L, y1L, L / s2, a - B);
            drawLeaf(g, x1R, y1R, L / s2, a + B);   
        }
    }
}
