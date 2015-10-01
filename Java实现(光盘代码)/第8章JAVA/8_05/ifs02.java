//摇摆的sierpinski垫片(分形频道：fractal.cn)2004
import java.awt.*;
import javax.swing.*;

public class ifs02 extends JApplet implements Runnable 
{  
    public int lines = 0;
    public double map[][];
  
    private Thread t; // 线程
    boolean dstatus = true;
    float D=0;
    Image offscreenimage=null;    //缓冲区中的图形
    Graphics offscreenbuffer=null;  //缓存中的g
    int width=600;
    int height=600;

    int h, w;
    long dots = 10000;
    double t0 = 1.0, b = -1.0, l = -1.0, r = 1.0;
    
    public void init()
    {
        t = new Thread(this);
        t.start(); 
        //创建缓冲区内的图形
        offscreenimage=this.createImage(width,height);
        //得到g
        offscreenbuffer=offscreenimage.getGraphics();
    }

    int transX(double x)
    {
        return (int)((double)(x - l) / (r - l) * w-300);
    }
  
    int transY(double y)
    {
        return (int)((double)(y - t0) / (b - t0) * h+100);
    }
  
    public void run() 
    { 
        while(true) 
        {
            repaint();
            try 
            {
                t.sleep(10);
            } 
            catch(InterruptedException e) {}
        } 
    }

    public void paint (Graphics g)
    { 
        if (dstatus) 
        {
            D+=0.01f;
            if (D>=0.3) dstatus = false;
        } 
        else 
        {
            D -= 0.01f;
            if (D<=-0.3) dstatus = true;
        }    
        //清除缓冲内的图形
        offscreenbuffer.clearRect(0,0,600,600);
        //设置颜色
        offscreenbuffer.setColor(Color.black); 
        //在offscreenbuffer中画
	 ifs(offscreenbuffer,D);
        //将缓冲内的图形放到屏幕
	 g.drawImage(offscreenimage,0,0,this);
    }
  
    public void ifs(Graphics g,float k)
    {
        Rectangle r = bounds();
        long i;
  
        double u = 0.0, v = 0.0, newu, newv, sum = 0.0, rnd;
        int l = 0;

        h = r.height; w = r.width;

        map = new double[3][7];
        lines = 3;

        map[0][0] =  0.5;   map[1][0] =  0.5;   map[2][0] =  0.5;
        map[0][1] =  0.0;   map[1][1] =  0.0;   map[2][1] =  k;
        map[0][2] =  0.0;   map[1][2] =  0.0;   map[2][2] =  0.0;
        map[0][3] =  0.5;   map[1][3] =  0.5;   map[2][3] =  0.5;
        map[0][4] =  0.0;   map[1][4] =  0.5;   map[2][4] =  0.25;
        map[0][5] =  0.0;   map[1][5] =  0.0;   map[2][5] =  0.5;
        map[0][6] =  0.333; map[1][6] =  0.333; map[2][6] =  0.334;

        for (i = 1; i <= dots; i++) 
        {
            rnd = Math.random();
            l = 0; 
            sum = map[l][6];

            while ( (rnd > sum) && (l < lines) ) 
            {
                l++;
                sum += map[l][6];
            }

            if (l < lines) 
            {
                newu = map[l][0] * u + map[l][1] * v;
                newv = map[l][2] * u + map[l][3] * v;
                u = newu + map[l][4];
                v = newv + map[l][5];
            }
      
            g.drawLine(transX(u), transY(v), transX(u), transY(v));
        }
    }
}