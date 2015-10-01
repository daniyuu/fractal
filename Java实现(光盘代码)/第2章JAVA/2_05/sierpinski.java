//sierpinski��Ƭ������Ƶ����fractal.cn��
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import java.applet.*;
import java.lang.Math;
import java.awt.event.*;

public class sierpinski extends Applet implements MouseListener{
	private final double pi = 3.14159265;
	int x;   //�������ĵ�����x
	int y;    //�������ĵ�����y
	int l;    //�����α߳�
	int inti=0;
	
	public void init(){
		Graphics g=getGraphics();
		x = 300;
  		y = 300;
  		l = 300;
  		setBackground(new Color(255,255,255));//���ñ���ɫΪ��ɫ
             

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

              //���Ƴ�ʼ������
		g.setColor(Color.black);              
  		//g.drawLine((int)(x-l/2),(int)(y+l*Math.tan(pi/6)/2),(int)(x+l/2),(int)(y+l*Math.tan(pi/6)/2));
  		//g.drawLine((int)(x+l/2),(int)(y+l*Math.tan(pi/6)/2),x,(int)(y-l*Math.tan(pi/6)));
  		//g.drawLine(x,(int)(y-l*Math.tan(pi/6)),(int)(x-l/2),(int)(y+l*Math.tan(pi/6)/2));
		g.drawLine((int)(300-300/2),(int)(300+300*Math.tan(pi/6)/2),(int)(300+300/2),(int)(300+300*Math.tan(pi/6)/2));
  		g.drawLine((int)(300+300/2),(int)(300+300*Math.tan(pi/6)/2),300,(int)(300-300*Math.tan(pi/6)));
  		g.drawLine(300,(int)(300-300*Math.tan(pi/6)),(int)(300-300/2),(int)(300+300*Math.tan(pi/6)/2));
              if( inti == 1 ){ //���Ƶ�һ���е����ɵ�������
    			l = l / 2;
    			g.drawLine((int)(x-l/2),(int)(y-l*Math.tan(pi/6)/2),(int)(x+l/2),(int)(y-l*Math.tan(pi/6)/2));
    			g.drawLine((int)(x+l/2),(int)(y-l*Math.tan(pi/6)/2),(int)(x),(int)(y+l*Math.tan(pi/6)));
			g.drawLine(x,(int)(y+l*Math.tan(pi/6)),(int)(x-l/2),(int)(y-l*Math.tan(pi/6)/2));
		}
		else{
			sierpinski(x,y,l,inti);    //������ǵ�һ���������sierpinski�����εݹ����
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
   			//�ݹ����
    			for(int j = 0;j<= 2;j++){
				sierpinski((int)(x+Math.sqrt(3)*l*Math.sin(j*2*pi/3)/3),(int)(y-Math.sqrt(3)*l*Math.cos(j*2*pi/3)/3),(int)(l/2),i-1);
    			}
		}
	}
}
