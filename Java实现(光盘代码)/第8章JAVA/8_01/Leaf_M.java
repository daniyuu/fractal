//ҡҷ�ķ�����������Ƶ����fractal.cn��2004
import java.awt.*;
import javax.swing.*;
import java.lang.*;

public class Leaf_M extends JApplet implements Runnable{
    private Thread t;

    float D=-10;//���������Ƕ�C
    float K=40;//��辵���չ�Ƕ�B
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
            D += 0.2f;
            if (D>=10) dstatus = false;
        }
        else 
        {
            D -= 0.2f;
            if (D<=-10) dstatus = true;
        }

        if (K<60) K=K+0.2f;

        //��������ڵ�ͼ��
        offscreenbuffer.clearRect(0,0,600,600);
        //������ɫ
        offscreenbuffer.setColor(Color.black); 
         //��offscreenbuffer�л���
	 drawLeaf(offscreenbuffer,200, 300, 30,270,K,D);
        //�������ڵ�ͼ�ηŵ���Ļ
	 g.drawImage(offscreenimage, 0, 0, this);
    }
   
    public void drawLeaf(Graphics g,double x,double y,
                   double L, double a,float B,float C)
    {    
        double x1,x2,x1L,x2L,x2R,x1R,
               y1,y2,y1L,y2L,y2R,y1R;
       
        float s1 = 2;
        float s2 = 3;
        float s3 = 1.1f;

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
          
            drawLeaf(g, x2, y2, L / s3, a + C,B,C);
            drawLeaf(g, x2R, y2R, L / s2, a + B,B,C);
            drawLeaf(g, x2L, y2L, L / s2, a - B,B,C);
            drawLeaf(g, x1L, y1L, L / s2, a - B,B,C);
            drawLeaf(g, x1R, y1R, L / s2, a + B,B,C);   
        }
    }
}
