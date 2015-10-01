//Newtond迭代法

import java.awt.Panel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
 
class Czoom extends Panel 
{
    Newton parent;
    boolean capture = true, zoom = true;
    Cmplx tmp1, tmp2;
    Image image;
    Graphics boxDraw;
  
    public Czoom(Newton f) 
    {
        parent = f;
        setBackground(Color.black);
    }

    public void updateImage() 
    {
        image = createImage(parent);
        repaint();
    }
  
    public void paint(Graphics g) 
    {
        g.drawImage(image, 0, 0, this);
        boxDraw = g.create();
        boxDraw.setXORMode(Color.white);
    }
  
    int tmpx1, tmpx2, tmpy1, tmpy2;
  
    //鼠标移动
    public boolean mouseMove(Event e, int x, int y) 
    {
        if (capture) 
        {
            tmp1 = parent.translate(x, y);
            parent.showStatus("(" + String.valueOf(tmp1.real()) + 
                             ", " + String.valueOf(tmp1.imag()) +
                             ")");
            return true;
        } 
        else
            return false;
    }      

    //鼠标落下  
    public boolean mouseDown(Event e, int x, int y) 
    {
        if (capture) 
        {
            tmp1 = parent.translate(x, y);
            
            if (zoom) 
            {
                tmpx1 = tmpx2 = x; tmpy1 = tmpy2 = y;
                boxDraw.drawRect(tmpx1, tmpy1, tmpx2 - tmpx1, tmpy2 - tmpy1);
            }

            return true;
        } 
        else
            return false;
    }

    //鼠标拖动  
    public boolean mouseDrag(Event e, int x, int y) 
    {
        if (capture) 
        {
            tmp2 = parent.translate(x, y);
            if (zoom) 
            {
                boxDraw.drawRect(tmpx1, tmpy1, tmpx2 - tmpx1, tmpy2 - tmpy1);
                tmpx2 = x; tmpy2 =y;
                boxDraw.drawRect(tmpx1, tmpy1, tmpx2 - tmpx1, tmpy2 - tmpy1);
                parent.showStatus("(" + String.valueOf(tmp1.real()) +
                                 ", " + String.valueOf(tmp1.imag()) +
                                ")-(" + String.valueOf(tmp2.real()) +
                                 ", " + String.valueOf(tmp2.imag()) +
                                ")");
            } 
            else
                parent.showStatus("(" + String.valueOf(tmp2.real()) + 
                                 ", " + String.valueOf(tmp2.imag()) +
                                 ")");
            return true;
        } 
        else
            return false;
    }
  
    //鼠标抬起
    public boolean mouseUp(Event e, int x, int y) 
    {
        if (capture) 
        {
            tmp2 = parent.translate(x, y);
   
            parent.tl = tmp1;
            parent.br = tmp2;
 
            updateImage();//重画
            return true;
        } 
        else
            return false;
    }
  
}
