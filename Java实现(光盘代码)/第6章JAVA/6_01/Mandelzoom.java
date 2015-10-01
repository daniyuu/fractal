//Mandelbrot集放大（分形频道：fractal.cn）2004
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class Mandelzoom extends Applet implements MouseListener, MouseMotionListener
{ 
        private final int MAX = 256;      
        private final double SX = -2.025; // 实部的最小值
        private final double SY = -1.125; // 虚部的最小值
        private final double EX = 0.6;    // 实部的最大值
        private final double EY = 1.125;  // 虚部的最大值
        private static int picX, picY, xs, ys, xe, ye;
        private static double x1, y1, x2, y2, p, q;
        private static boolean action, rechteck, fertig, stopit;
        private static float xy;
        private Image bild;
        private Graphics g1;
        private Cursor c1, c2;
         public void init() // 初始化所有实例
        {
                fertig = false;
                addMouseListener(this);
                addMouseMotionListener(this);
                c1 = new Cursor(Cursor.WAIT_CURSOR);
                c2 = new Cursor(Cursor.CROSSHAIR_CURSOR);
                picX = getSize().width;
                picY = getSize().height;
                xy = (float)picX / (float)picY;
                bild = createImage(picX, picY); 
                g1 = bild.getGraphics();
                fertig = true;
        }
         public void destroy() // 删除所有实例 
        {
                if (fertig)
                {
                        removeMouseListener(this);
                        removeMouseMotionListener(this);
                        bild = null;
                        g1 = null;
                        c1 = null;
                        c2 = null;
                        System.gc(); 
                }
        }
         public void start()
        {
                action = false;
                rechteck = false;
		stopit = false;
                startwerte();
                p = (x2 - x1) / (double)picX;
                q = (y2 - y1) / (double)picY;


                mandelbrot();
        }
         public void stop()
        {
        }
        
        public void paint(Graphics g)
        {
                update(g); 
        }
        
        public void update(Graphics g)
        {
                g.drawImage(bild, 0, 0, this);
                if (rechteck)
                {
                        g.setColor(Color.white);
                        if (xs < xe)
                        {
                                if (ys < ye) g.drawRect(xs, ys, (xe - xs), (ye - ys));
                                else g.drawRect(xs, ye, (xe - xs), (ys - ye));
                        }
                        else
                        {
                                if (ys < ye) g.drawRect(xe, ys, (xs - xe), (ye - ys));
                                else g.drawRect(xe, ye, (xs - xe), (ys - ye));
                        }
                }
        }
        
        private void mandelbrot() // 计算所有的点
        {
                int x, y;
                float h, b, alt = 0.0f;
                
                action = false;
                setCursor(c1);
                showStatus("正在计算Mandelbrot集，请等待...");

                for (x = 0; x < picX; x+=2) {
                        for (y = 0; y < picY; y++)
                        {
                                h = punktfarbe(x1 + p * (double)x, y1 + q * (double)y); // 颜色值
                                if (h != alt)
                                {
                                        b = 1.0f - h * h; 
                                        g1.setColor(Color.getHSBColor( h,1,b));
                                      
                                        alt = h;
                                }           
                                g1.drawLine(x, y, x + 1, y);
                        }
			showStatus( "At " + x + " of " + picX );
			if (stopit) x = picX;
		}

		if  (stopit) {
		    showStatus("Aborted");
		    stopit = false;
		}  else {
		    showStatus("Mandelbrot集已准备好，请用鼠标选择放大区域.");
		}
                setCursor(c2);
                action = true;
        }
        // 由迭代产生的0.0到1.0的颜色值
        private float punktfarbe(double x0, double y0) 
        {
                double r = 0.0, p0 = 0.0, q0 = 0.0, pk = 0, qk = 0;
                int j = 0;
                
                while ((j < MAX) && (r < 4.0))
                {
                        
                        pk = p0 * p0 - q0 * q0 + x0;
                        qk = 2.0 * p0 * q0 + y0;
                        j++;
                        r = qk * qk + pk * pk;
                        p0 = pk;
                        q0 = qk;
 
                }
                return (float)j / (float)MAX;
        }
        
        private void startwerte() // 重新开始值
        {
                x1 = SX;
                y1 = SY;
                x2 = EX;
                y2 = EY;
                if ((float)((x2 - x1) / (y2 - y1)) != xy ) 
                        x1 = x2 - (y2 - y1) * (double)xy;
        }
         public void mousePressed(MouseEvent e) 
        {
                e.consume();
                if (action)
                {
                        xs = e.getX();
                        ys = e.getY();
                }
        }
        
        public void mouseReleased(MouseEvent e)
        {
                int z, w;
                
                e.consume();
                if (action)
                {
                        xe = e.getX();
                        ye = e.getY();
                        if (xs > xe)
                        {
                                z = xs;
                                xs = xe;
                                xe = z;
                        }
                        if (ys > ye)
                        {
                                z = ys;
                                ys = ye;
                                ye = z;
                        }
                        w = (xe - xs);
                        z = (ye - ys);
                        if ((w < 2) && (z < 2)) startwerte();
                        else
                        {
                                if (((float)w > (float)z * xy)) ye = (int)((float)ys + (float)w / xy);
                                else xe = (int)((float)xs + (float)z * xy);
                                x2 = x1 + p * (double)xe;
                                y2 = y1 + q * (double)ye;
                                x1 += p * (double)xs;
                                y1 += q * (double)ys;
                        }
                        p = (x2 - x1) / (double)picX;
                        q = (y2 - y1) / (double)picY;
                        mandelbrot();
                        rechteck = false;
                        repaint();
                }  else {
		    stopit = true;
		}
        }
         public void mouseEntered(MouseEvent e)
        {
        }
         public void mouseExited(MouseEvent e)
        {
        }
         public void mouseClicked(MouseEvent e) 
        {
        }
        
        public void mouseDragged(MouseEvent e)
        {
                e.consume();
                if (action)
                {
                        xe = e.getX();
                        ye = e.getY();
                        rechteck = true;
                        repaint();
                }
        }
        
        public void mouseMoved(MouseEvent e)
        {
        }
        
}

