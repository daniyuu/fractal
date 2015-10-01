//显微镜下的雪花（分形频道：fractal.cn）2004
import java.awt.*;
import javax.swing.*;
import java.lang.*;

public class Snow_Z extends JApplet implements Runnable
{
    private Thread t;
    boolean dstatus = true;
    public static final double PI = Math.PI / 180;
    Image offscreenimage=null;    //缓冲区中的图形
    Graphics offscreenbuffer=null;  //缓存中的g
    int width=600;
    int height=600;
    double L=10.0;
                           
    public void init()
    {
        t = new Thread(this);
        t.start(); 
        //创建缓冲区内的图形
        offscreenimage=this.createImage(width,height);
        //得到g
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
            repaint();//重画
        } 
    }

    public void paint(Graphics g)
    {           
        int  i = 0,
             cx = 200,
             cy = 500;

        if (dstatus) 
        {
            L += 1.0;
            if (L>=1000.0) dstatus = false;
           i = i + 15; 
        }
        else 
        {
            L -= 1.0;
            if (L<=1.0) dstatus = true;
           i = i - 15; 
        }

                    
        //清除缓冲内的图形
        offscreenbuffer.clearRect(0,0,600,600);
        //设置颜色
        offscreenbuffer.setColor(Color.black); 
         //在offscreenbuffer中画树
	
        drawSnow(offscreenbuffer, cx, cy, L, 300 + i);
        drawSnow(offscreenbuffer, cx, cy, L, 240 + i);
        drawSnow(offscreenbuffer, cx + L * Math.cos((240 + i) * PI),
                                  cy + L * Math.sin((240 + i) * PI),
                                  L, 360 + i);
        //将缓冲内的图形放到屏幕
	 g.drawImage(offscreenimage, 0, 0, this);
            
    }
   
    public void drawSnow(Graphics g, double x, double y, double L, double a){
     double LL = L / 3,
            LLL = 2 * LL;
     double x1, x2, x3, x4, x5,
            y1, y2, y3, y4, y5;

        if(LL > 3){

           x1 = x + LL * Math.cos(a * PI);
           y1 = y + LL * Math.sin(a * PI);

           x2 = x + LLL * Math.cos(a * PI);
           y2 = y + LLL * Math.sin(a * PI);

           x3 = x + L * Math.cos(a * PI);
           y3 = y + L * Math.sin(a * PI);

           x4 = x1 + LL * Math.cos((a - 60) * PI);
           y4 = y1 + LL * Math.sin((a - 60) * PI);

           x5 = x1 + LL * Math.cos((a + 60) * PI);
           y5 = y1 + LL * Math.sin((a + 60) * PI);
                 

            g.setColor(Color.black);
            g.drawLine((int) x,  (int) y,  (int) x1, (int) y1);
            g.drawLine((int) x1, (int) y1, (int) x4, (int) y4);
            g.drawLine((int) x4, (int) y4, (int) x2, (int) y2);
            g.drawLine((int) x2, (int) y2, (int) x3, (int) y3);
            g.drawLine((int) x1, (int) y1, (int) x5, (int) y5);
            g.drawLine((int) x5, (int) y5, (int) x2, (int) y2);
              
            drawSnow(g, x, y, LL, a);
            drawSnow(g, x1, y1, LL, a - 60);
            drawSnow(g, x1, y1, LL, a + 60);
            drawSnow(g, x2, y2, LL, a - 120);
            drawSnow(g, x2, y2, LL, a + 120);
            drawSnow(g, x2, y2, LL, a);
        } 
    }

}
