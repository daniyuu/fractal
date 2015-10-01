//分形树（分形频道：fractal.cn）2004
import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class Tree3 extends Applet
implements ActionListener {
	Dimension d;
	Image buffer;
    Graphics bufferg;
	int level = 8;//递归深度
	double L  = 120.0;//初始长度
	double t  = 45.0 * (Math.PI/180.0);//叉间角度
	double T0 = 90.0 * (Math.PI/180.0);//主干的生长角度
	double ratio_x = 0.8;
	double ratio_y = 0.8;
	double z  = 2.0/3.0;



	public void init( ) {
		d = getSize( );
		buffer = createImage(d.width, d.height);
		
    }



	public void actionPerformed(ActionEvent ae) 
    {
		level = Integer.parseInt(ae.getActionCommand( ));
		repaint( );
    }

	public void update(Graphics g){
		paint(g);
    }

	public void paint(Graphics g) {
		if(bufferg == null) {
			bufferg = buffer.getGraphics();
		}
		init_screen( );
		g.drawImage(buffer, 0, 0, this);
		write_node(g,level,L,T0,0,0);
    }

	public void init_screen( ) {
		bufferg.setColor(Color.white);
		bufferg.fillRect(0, 0, d.width, d.height);
	}

   	public void write_node(Graphics g,int n,double l,double arg,int x,int y) {
    	int xx,yy,i;
    	xx = (int)(l * Math.cos(arg) * ratio_x);
    	yy = (int)(l * Math.sin(arg) * ratio_y);
    	bufferg.setColor(Color.black);
		bufferg.drawLine(x + (int)(d.width * 0.5), d.height -  y,
			(x+xx) + (int)(d.width * 0.5), d.height - (y+yy));
    	g.drawImage(buffer, 0, 0, this);
		if(n>0){
	    	write_node(g,n-1,l*z,(arg-t/2.0) + 0.0 * t/1.0,x+xx,y+yy);
			write_node(g,n-1,l*z,(arg-t/2.0) + 1.0 * t/1.0,x+xx,y+yy);
		}
   	}
}
