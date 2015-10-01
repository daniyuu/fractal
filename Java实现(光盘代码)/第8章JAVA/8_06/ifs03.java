//旋转万花筒(分形频道：fractal.cn)2004
import java.awt.*;
import javax.swing.*;


public class ifs03 extends JApplet implements Runnable 
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
        return (int)((double)(x - l) / (r - l) * w/3);
    }
  
    int transY(double y)
    {
        return (int)((double)(y - t0) / (b - t0) * w/3);
    }
  
    public void run() 
    { 
        while(true) 
        {
            repaint();
        }
    }

    public void paint (Graphics g)
    {
        if (dstatus) 
        {
            D+=5;
            if (D>=245) dstatus = false;
        } 
        else 
        {
            D -= 5;
            if (D<=-245) dstatus = true;
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

        map = new double[5][7];
        lines = 5;

        map[0][0] =  0.2;   map[1][0] =  0.2;   map[2][0] =  0.2;    map[3][0] =  0.2;    map[4][0] =  0.85;
        map[0][1] =  0.2;   map[1][1] =  0.2;   map[2][1] =  0.2;    map[3][1] =  0.2;    map[4][1] =  0.85;
        map[0][2] =  0.0;   map[1][2] =  0.0;   map[2][2] =  0.0;    map[3][2] =  0.0;    map[4][2] =  k;
        map[0][3] =  0.0;   map[1][3] =  0.0;   map[2][3] =  0.0;    map[3][3] =  0.0;    map[4][3] =  k;
        map[0][4] =  0.7;   map[1][4] =  -0.7;  map[2][4] =  0.0;    map[3][4] =  0.0;    map[4][4] =  0.0;
        map[0][5] =  0.0;   map[1][5] =  0.0;   map[2][5] =  0.7;    map[3][5] =  -0.7;   map[4][5] =  0.0;
        map[0][6] =  0.2;   map[1][6] =  0.2;   map[2][6] =  0.2;    map[3][6] =  0.2;    map[4][6] =  0.2;

        for (i = 1; i <= dots; i++) 
        {
            rnd = Math.random();
            l = 0; sum = map[l][6];
            while ( (rnd > sum) && (l < lines) ) 
            {
                l++;
                sum += map[l][6];
            }

            if (l < lines) 
            {
                newu = map[l][0] * u * Math.cos(map[l][2]  / 180.0)- map[l][1] * v* Math.sin(map[l][3]  / 180.0);
                newv = map[l][0] * Math.sin(map[l][2]/180.0) * u + map[l][1] * Math.cos(map[l][3]/180.0) * v;
                u = newu + map[l][4];
                v = newv + map[l][5];
            }
     
            g.drawLine(transX(u), transY(v), transX(u), transY(v)); 
        }
    }
}