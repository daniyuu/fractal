//�����ƣ�����Ƶ����fractal.cn��2004
import java.applet.Applet;
import java.awt.*;

public class cloud extends Applet
{
    Image Buffer;	//���ڴ洢ͼ��Ļ�����
    Graphics Context;	//���ڻ���ͼ�񵽻�������
	
    //��������������λ�õ����λ����ɫֵ
    float Displace(float num)
    {
        float max = num / (float)(getSize().width + getSize().height) * 3;
	return ((float)Math.random() - 0.5f) * max;
    }

    //����һ��������ɫֵ����ɫc
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
	
    //����һ�������֡������������ڵݹ麯��֮ǰ����һ����ʼ����

    void drawcloud(Graphics g, int width, int height)
    {
        float r1, r2, r3, r4;
		
	//�����ʼ�����ĸ��ǵ�������ɫֵ
	//�⽫����������applet�е��ĸ��ǵ����ɫ	
	r1 = (float)Math.random();
	r2 = (float)Math.random();
	r3 = (float)Math.random();
	r4 = (float)Math.random();
			
	Midpoint(g, 0, 0, width , height , r1, r2, r3, r4);
    }

    //����һ��ִ���е�λ���㷨�ĵݹ麯���������Լ���ΪС�����ص��������Ƭ
    void Midpoint(Graphics g, float x, float y, float width, float height, float r1, float r2, float r3, float r4)
    {
        float M1, M2, M3, M4, Middle;
	float newWidth = width / 2;
	float newHeight = height / 2;

	if (width > 2 || height > 2)
	{	
	    Middle = (r1 + r2 + r3 + r4) / 4 + Displace(newWidth + newHeight);	//����е�λ��
	    M1 = (r1 + r2) / 2;	//�������������ǵ��м��ƽ��ֵ
	    M2 = (r2 + r3) / 2;
	    M3 = (r3 + r4) / 2;
	    M4 = (r4 + r1) / 2;
			
            //��֤�е㲻���ڡ����λ�ơ�ȷ���ķ�Χ֮��
	        if (Middle < 0)
	    	{
		    Middle = 0;
		}
		else if (Middle > 1.0f)
		{
		    Middle = 1.0f;
		}
			
		//�ٴβ����ĸ��µ������			
		Midpoint(g, x, y, newWidth, newHeight, r1, M1, Middle, M4);
		Midpoint(g, x + newWidth, y, newWidth, newHeight, M1, r2, M2, Middle);
		Midpoint(g, x + newWidth, y + newHeight, newWidth, newHeight, Middle, M2, r3, M3);
		Midpoint(g, x, y + newHeight, newWidth, newHeight, M4, Middle, M3, r4);
	}
	else	//���ǻ���״����һЩ������ƬС�����ص�ĳߴ�
	{
	    //������Ƭ���ĸ��ǵ���ƽ����������������ĵ�
	    float c = (r1 + r2 + r3 + r4) / 4;
		
	    g.setColor(ComputeColor(c));
	    g.drawRect((int)x, (int)y, 1, 1);	//javaû�л�������ĺ�����������1��1�ľ��������Ƶ�
	}
    }

    //��ʱ���µ���
    public boolean mouseUp(Event evt, int x, int y)
    {
    	drawcloud(Context, getSize().width, getSize().height);
	repaint();	//����ͼ
	
	return false;
    }
	
    //��ʱ���ʹ�����applet�������������ģʽ������Ļ����Ļ����д洢���Σ�
    //�������ֻ��Ҫ�����������еĶ���ӳ�䵽��Ļ��
    public void paint(Graphics g)
    {
	g.drawImage(Buffer, 0, 0, this);
    }
	
    public void init()
    {
	Buffer = createImage(getSize().width, getSize().height);//����ͼ�λ����������
	Context = Buffer.getGraphics();
	drawcloud(Context, getSize().width, getSize().height);	//����һ����
    }
}