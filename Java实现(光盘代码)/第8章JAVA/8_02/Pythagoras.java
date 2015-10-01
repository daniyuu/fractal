// ���ֵıϴ����˹������(����Ƶ����fractal.cn)2004
import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.applet.*;

public class Pythagoras extends JApplet implements Runnable
{
     private Thread t;
     Point a1,b1,c1,d1, a2,b2,c2,d2;

     float D=0.3f;//���İڽ�
     int depth = 9; // �ݹ����

    boolean dstatus = true;
     public static final double PI = Math.PI / 180;
     Image offscreenimage=null;    //�������е�ͼ��
     Graphics offscreenbuffer=null;  //�����е�g
     int width=600;
     int height=600;
                       
     public void init()
     {
         t = new Thread(this);
         t.start(); 
         //�����������ڵ�ͼ��
         offscreenimage=this.createImage(width,height);
         //�õ�g
         offscreenbuffer=offscreenimage.getGraphics();
     }

     public void update(Graphics g)
     {	
         paint(g);
     }

     /////////////////////////////////////////////////////////////
     public void run() 
     {
         while(true) 
         {
             repaint();//�ػ�
         } 
     }

     public void paint(Graphics g)
     {        
         if (dstatus) 
         {
             D += 0.01f;
             if (D>=0.6) dstatus = false;
         }
         else 
         {
             D -= 0.01f;
             if (D<=0.3) dstatus = true;
         }

         //��������ڵ�ͼ��
         offscreenbuffer.clearRect(0,0,600,600);
         //������ɫ
         offscreenbuffer.setColor(Color.black); 
          //��offscreenbuffer�л���
	      drawTree(offscreenbuffer,new Point(230,-60), 
new Point(280,-60), new Point(280,0), 
new Point(230,0), depth,D);
         //�������ڵ�ͼ�ηŵ���Ļ
	      g.drawImage(offscreenimage, 0, 0, this);
     }
   
     public void drawTree(Graphics g2, Point a, 
Point b, Point c, Point d, 
int depth,float k) 
{
         if (depth==0) return; 
         depth -= 1;

         int xCoords[] = {a.x, b.x, c.x, d.x};
         int yCoords[] = {300-a.y, 300-b.y, 300-c.y, 300-d.y};

         g2.drawPolygon(xCoords, yCoords, 4);

         double r2 = Math.sqrt(0.5*0.5-(0.5-k)*(0.5-k));
         Point e = new Point((int)(d.x + k*(c.x-d.x) + r2*(a.y-b.y)),
 (int)(d.y + k*(c.y-d.y) + r2*(b.x-a.x)));

        a1 = d;
         b1 = e;
         c1 = new Point(b1.x+a1.y-b1.y, b1.y+b1.x-a1.x);
         d1 = new Point(a1.x+a1.y-b1.y, a1.y+b1.x-a1.x);
         drawTree(g2, a1, b1, c1, d1, depth,k);

         a2 = e;
         b2 = c;
         c2 = new Point(b2.x+a2.y-b2.y, b2.y+b2.x-a2.x);
         d2 = new Point(a2.x+a2.y-b2.y, a2.y+b2.x-a2.x);
         drawTree(g2, a2, b2, c2, d2, depth,k);
     }
}
