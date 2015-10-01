//Julia���Ŵ󣨷���Ƶ����fractal.cn��2004
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class Juliazoom extends Applet implements MouseListener, MouseMotionListener
{ 
        private final int MAX = 256;      
        private final double SX = -1.0; // ʵ������Сֵ
        private final double SY = -1.5; // �鲿����Сֵ
        private final double EX = 2.0;    // ʵ�������ֵ
        private final double EY = 1.5;  // �鲿�����ֵ
        private static int picX, picY, xs, ys, xe, ye;
        private static double x1, y1, x2, y2, p, q,xb,yb;
        private static boolean action, rechteck, fertig, stopit;
        private static float xy;
        private Image bild;
        private Graphics g1;
        private Cursor c1, c2;
         public void init() // ��ʼ������ʵ��
        {
                p = -0.46;
                q = 0.57;

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
         public void destroy() // ɾ������ʵ�� 
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
                xb = (x2 - x1) / (double)picX;
                yb = (y2 - y1) / (double)picY;


                julia();
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
        
        private void julia() // �������еĵ�
        {
                int x, y;
                float h, b, alt = 0.0f;
                
                action = false;
                setCursor(c1);
                showStatus("���ڼ���julia������ȴ�...");
                 p = -0.46;
                 q = 0.57;
                for (x = 0; x < picX; x+=2) {
                        for (y = 0; y < picY; y++)
                        {
                                h = punktfarbe(x1 + xb * (double)x, y1 + yb * (double)y,p,q); // ��ɫֵ
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
		    showStatus("julia����׼���ã��������ѡ��Ŵ�����.");
		}
                setCursor(c2);
                action = true;
        }
        // �ɵ���������0.0��1.0����ɫֵ
        private float punktfarbe(double x0, double y0,double p,double q) 
        {
                double r = 0.0,  xk = 0, yk = 0;
                int j = 0;
                
                
                while ((j < MAX) && (r < 4.0))
                {
                        
                        xk = x0 * x0 - y0 * y0 + p;
                        yk = 2.0 * x0 * y0 + q;
                        j++;
                        r = yk * yk + xk * xk;
                        x0 = xk;
                        y0 = yk;

                    

                }
                return (float)j / (float)MAX;
        }
        
        private void startwerte() // ���¿�ʼֵ
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
                                x2 = x1 + xb* (double)xe;
                                y2 = y1 + yb * (double)ye;
                                x1 += xb * (double)xs;
                                y1 += yb * (double)ys;
                        }
                        xb = (x2 - x1) / (double)picX;
                        yb = (y2 - y1) / (double)picY;
                        julia();
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

