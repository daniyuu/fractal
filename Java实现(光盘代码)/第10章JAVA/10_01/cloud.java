//分形云（分形频道：fractal.cn）2004
import java.applet.Applet;
import java.awt.*;

public class cloud extends Applet
{
    Image Buffer;	//用于存储图像的缓冲器
    Graphics Context;	//用于绘制图像到缓冲器中
	
    //依赖于网格中央位置的随机位移颜色值
    float Displace(float num)
    {
        float max = num / (float)(getSize().width + getSize().height) * 3;
	return ((float)Math.random() - 0.5f) * max;
    }

    //返回一个基于颜色值的颜色c
    Color ComputeColor(float c)
    {		
        float Red = 0;
	float Green = 0;
	float Blue = 0;
	
	if (c < 0.5f)
	{
	    Red = c * 2;
	}
	else
	{
	    Red = (1.0f - c) * 2;
	}
		
	if (c >= 0.3f && c < 0.8f)
	{
	    Green = (c - 0.3f) * 2;
	}
	else if (c < 0.3f)
	{
	    Green = (0.3f - c) * 2;
	}
	else
	{
	    Green = (1.3f - c) * 2;
	}
		
	if (c >= 0.5f)
	{
	    Blue = (c - 0.5f) * 2;
	}
	else
	{
	    Blue = (0.5f - c) * 2;
	}
		
	return new Color(Red, Green, Blue);
    }
	
    //这是一个“助手”函数，用于在递归函数之前创造一个初始网格

    void drawcloud(Graphics g, int width, int height)
    {
        float r1, r2, r3, r4;
		
	//分配初始网格四个角点的随机颜色值
	//这将结束存在于applet中的四个角点的颜色	
	r1 = (float)Math.random();
	r2 = (float)Math.random();
	r3 = (float)Math.random();
	r4 = (float)Math.random();
			
	Midpoint(g, 0, 0, width , height , r1, r2, r3, r4);
    }

    //这是一个执行中点位移算法的递归函数，它将自己成为小于象素点的网格面片
    void Midpoint(Graphics g, float x, float y, float width, float height, float r1, float r2, float r3, float r4)
    {
        float M1, M2, M3, M4, Middle;
	float newWidth = width / 2;
	float newHeight = height / 2;

	if (width > 2 || height > 2)
	{	
	    Middle = (r1 + r2 + r3 + r4) / 4 + Displace(newWidth + newHeight);	//随机中点位移
	    M1 = (r1 + r2) / 2;	//计算网格两个角点中间的平均值
	    M2 = (r2 + r3) / 2;
	    M3 = (r3 + r4) / 2;
	    M4 = (r4 + r1) / 2;
			
            //保证中点不能在“随机位移”确定的范围之外
	        if (Middle < 0)
	    	{
		    Middle = 0;
		}
		else if (Middle > 1.0f)
		{
		    Middle = 1.0f;
		}
			
		//再次操作四个新的网格点			
		Midpoint(g, x, y, newWidth, newHeight, r1, M1, Middle, M4);
		Midpoint(g, x + newWidth, y, newWidth, newHeight, M1, r2, M2, Middle);
		Midpoint(g, x + newWidth, y + newHeight, newWidth, newHeight, Middle, M2, r3, M3);
		Midpoint(g, x, y + newHeight, newWidth, newHeight, M4, Middle, M3, r4);
	}
	else	//这是基本状况，一些网格面片小于象素点的尺寸
	{
	    //网格面片的四个角点求平均，并画这个单独的点
	    float c = (r1 + r2 + r3 + r4) / 4;
		
	    g.setColor(ComputeColor(c));
	    g.drawRect((int)x, (int)y, 1, 1);	//java没有画单独点的函数，所以用1到1的矩形来绘制点
	}
    }

    //随时画新的云
    public boolean mouseUp(Event evt, int x, int y)
    {
    	drawcloud(Context, getSize().width, getSize().height);
	repaint();	//画云图
	
	return false;
    }
	
    //随时随地使用这个applet，它必须是随机模式，在屏幕以外的缓存中存储分形，
    //这个函数只需要将画到缓存中的对象映射到屏幕上
    public void paint(Graphics g)
    {
	g.drawImage(Buffer, 0, 0, this);
    }
	
    public void init()
    {
	Buffer = createImage(getSize().width, getSize().height);//设置图形缓存和上下文
	Context = Buffer.getGraphics();
	drawcloud(Context, getSize().width, getSize().height);	//画第一朵云
    }
}