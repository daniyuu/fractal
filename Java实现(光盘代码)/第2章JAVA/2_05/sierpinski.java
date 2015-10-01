//sierpinski垫片（分形频道：fractal.cn）
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import java.applet.*;
import java.lang.Math;
import java.awt.event.*;

public class sierpinski extends Applet implements MouseListener{
	private final double pi = 3.14159265;
	int x;   //定义中心点坐标x
	int y;    //定义中心点坐标y
	int l;    //三角形边长
	int inti=0;
	
	public void init(){
		Graphics g=getGraphics();
		x = 300;
  		y = 300;
  		l = 300;
  		setBackground(new Color(255,255,255));//设置背景色为白色
             

		addMouseListener(this);
  		
	}
	
	public void mouseMoved(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
	public void update(Graphics g){paint(g);}
	
	public void paint(Graphics g){

              //绘制初始三角形
		g.setColor(Color.black);              
  		//g.drawLine((int)(x-l/2),(int)(y+l*Math.tan(pi/6)/2),(int)(x+l/2),(int)(y+l*Math.tan(pi/6)/2));
  		//g.drawLine((int)(x+l/2),(int)(y+l*Math.tan(pi/6)/2),x,(int)(y-l*Math.tan(pi/6)));
  		//g.drawLine(x,(int)(y-l*Math.tan(pi/6)),(int)(x-l/2),(int)(y+l*Math.tan(pi/6)/2));
		g.drawLine((int)(300-300/2),(int)(300+300*Math.tan(pi/6)/2),(int)(300+300/2),(int)(300+300*Math.tan(pi/6)/2));
  		g.drawLine((int)(300+300/2),(int)(300+300*Math.tan(pi/6)/2),300,(int)(300-300*Math.tan(pi/6)));
  		g.drawLine(300,(int)(300-300*Math.tan(pi/6)),(int)(300-300/2),(int)(300+300*Math.tan(pi/6)/2));
              if( inti == 1 ){ //绘制第一级中点连成的三角形
    			l = l / 2;
    			g.drawLine((int)(x-l/2),(int)(y-l*Math.tan(pi/6)/2),(int)(x+l/2),(int)(y-l*Math.tan(pi/6)/2));
    			g.drawLine((int)(x+l/2),(int)(y-l*Math.tan(pi/6)/2),(int)(x),(int)(y+l*Math.tan(pi/6)));
			g.drawLine(x,(int)(y+l*Math.tan(pi/6)),(int)(x-l/2),(int)(y-l*Math.tan(pi/6)/2));
		}
		else{
			sierpinski(x,y,l,inti);    //如果不是第一级，则调用sierpinski三角形递归过程
		}

  		
	}
	
	public void mousePressed(MouseEvent e){
		inti++;
              
		repaint();
	}
	
	public void sierpinski(int x,int y,int l,int i){
		Graphics g=getGraphics();
 		if( i == 1){
			g.drawLine((int)(x-l/2),(int)(y-l*Math.tan(pi/6)/2),(int)(x+l/2),(int)(y-l*Math.tan(pi/6)/2));
			g.drawLine((int)(x+l/2),(int)(y-l*Math.tan(pi/6)/2),(int)(x),(int)(y+l*Math.tan(pi/6)));
			g.drawLine(x,(int)(y+l*Math.tan(pi/6)),(int)(x-l/2),(int)(y-l*Math.tan(pi/6)/2));
		}
		else{
   			//递归调用
    			for(int j = 0;j<= 2;j++){
				sierpinski((int)(x+Math.sqrt(3)*l*Math.sin(j*2*pi/3)/3),(int)(y-Math.sqrt(3)*l*Math.cos(j*2*pi/3)/3),(int)(l/2),i-1);
    			}
		}
	}
}
