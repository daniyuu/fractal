//连续变化的Julia集(分形频道：fractal.cn)2004
import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.awt.event.*;


public class ActiveJulia extends JApplet implements Runnable{
    
    private Thread t;
    float b=0;      //cx的值
    float c=0;      //cy的值

    private boolean isForward = true;
    private String infoX, infoY, XY;
    Image offscreenimage=null;    //缓冲区中的图形
    Graphics offscreenbuffer=null;  //缓存中的g
    int width=600;
    int height=600;

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

    public void run() 
    {
        while(true) 
        {
            repaint();
            try 
            {
                t.sleep(0);
            }
            catch(InterruptedException e) {}
        } 
    }
   
    public void paint(Graphics g)
    {
                             
        if(isForward)
        {
            b += 0.004;
            c -= 0.004;
               
            if(b >= 1 || c <= -1)
            { 
                isForward = false;
            }               
        }
        else
        {
            b -= 0.004;
            c += 0.004;
             
            if(b <= -1 || c >= 1)
            {  
                isForward = true;
            }
        }
        //清除缓冲内的图形
        offscreenbuffer.clearRect(0,0,670,600);
        //设置颜色
        offscreenbuffer.setColor(Color.black); 
        //在offscreenbuffer中画Julia集
	julia(offscreenbuffer,200, 300, b,c);
        //将缓冲内的图形放到屏幕
	g.drawImage(offscreenimage, 0, 0, this);
    }

    public void julia(Graphics g,double x,double y,float cx,float cy)
    {
        double wx,wy,theta;
        double m, n;    //点的位置
        int m1, n1 ;
        double r;
       
        for(int c = 1;c < 7000; c++)
        {
            wx = x - cx; 
            wy = y - cy;

            if(wx>0)
            {
                theta = Math.atan(wy/wx);
            }
            else if(wx<0)
            {
                theta = Math.PI+Math.atan(wy/wx);
            }
            else
            {
                theta = Math.PI/2;
            }

            theta = theta/2;
            r = Math.sqrt(wx*wx+wy*wy);
       
            if(Math.random()<0.5)
            {
                r = -Math.sqrt(r);           
            }
            else
            {
                r = Math.sqrt(r);           
            }
            x =  r * Math.cos(theta);
            y =  r * Math.sin(theta);

            m = 100*x+325;
            n = 100*y+200;
            
            m1 = (int)m;
            n1 = (int)n;

            g.drawLine(m1, n1, m1, n1);    
        }
            
         infoX = new Double(cx).toString();
         infoY = new Double(cy).toString();
         XY = "cx = " + infoX + "   " + "cy = " + infoY;
         g.drawString(XY, 5, 12);
    }    

}
