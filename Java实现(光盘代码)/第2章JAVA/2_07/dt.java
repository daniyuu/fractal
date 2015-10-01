//sierpinski地毯（分形频道：fractal.cn）2004

import java.awt.*;
import java.applet.*;



public class dt extends Applet {
	
        public void init() {
           setBackground(new Color(255,255,255));//设置背景色为白色
        }

	public void paint(Graphics g) {
		int n = 5;
        int 
        x1 = 20,
        y1 = 20,
        x2 = 420,
        y2 = 420;
        
        g.setColor(Color.black);
 
        g.fillRect(x1, y1,x2-x1, y2-y1);

        
		sier(x1, y1, x2, y2, n,g);
	}
	public void sier(float x1,float y1,float x2,float y2,float n,Graphics g){
		float L=0 , W=0;
        if (n > 1)
        {
            L = x2 - x1;
            W = y2 - y1;
        
        g.setColor(Color.white);
        g.fillRect( (int)(x1 + L / 3), (int)(y1 + W / 3),(int)(x2 - L / 3)-(int)(x1 + L / 3), (int)(y2 - W / 3)-(int)(y1 + W / 3));
     
          
	    sier(x1, y1, (float)(x1 + L / 3), (float)(y1 + W / 3), n - 1,g);
        sier((float)(x1 + L / 3), y1,(float)( x2 - L / 3),(float)( y1 + W / 3), n - 1,g);
        sier((float)(x2 - L / 3), y1, x2, (float)(y1 + W / 3), n - 1,g);
        sier(x1, (float)(y1 + W / 3), (float)(x1 + L / 3),(float)( y2 - W / 3), n - 1,g);
        sier((float)(x2 - L / 3),(float)( y1 + W / 3), x2,(float)( y2 - W / 3), n - 1,g);
        sier(x1,(float)( y2 - W / 3), (float)(x1 + L / 3), y2, n - 1,g);
        sier((float)(x1 + L / 3),(float)( y2 - W / 3),(float) (x2 - L / 3), y2, n - 1,g);
        sier((float)(x2 - L / 3),(float)( y2 - W / 3), x2, y2, n - 1,g);
		
		}
	}
}
