import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import java.applet.*;


public class c extends Applet implements Serializable{
	private Image offScreenImage=null;
	private Graphics offScreenBuffer=null;
	private int xx=220,yy=100;
	private int width=800;
	private int height=500;

	public final double bs=0.7071068;   //Sqr(2) / 2
	public final double PI=3.1415926/180;
    //»æÍ¼±ÈÀý
	public int L=100;
	public int st = 1;
	
	public c(){}
	
	public void init(){
		offScreenImage=this.createImage(width,height);
		offScreenBuffer=offScreenImage.getGraphics();
	}
	
	public void update(Graphics g){
		paint(g);
	}
	
	public void paint(Graphics g){
		offScreenBuffer.setColor(Color.white);
		offScreenBuffer.fillRect(0,0,width,height);
		
		//g.drawRect(40,24,0,0);
		drawC(offScreenBuffer,xx,yy,L,90+45);
		g.drawImage(offScreenImage,0,0,this);
	}
	
    public void drawC(Graphics g,int x,int y,int L,int a){
		int d=45;
		int X1;
		int Y1;
  		if(L>=st){
    		X1=(int)(x+L*Math.cos(a*PI));
    		Y1=(int)(y+L*Math.sin(a*PI));
    		drawC(g,x,y,(int)(L*bs),a-d);
    		drawC(g,X1,Y1,(int)(L*bs),a+d);
    	}
  		else{
  			g.setColor(Color.red);
    		g.drawLine(xx,yy,x,y);
    		xx=x;yy=y;
    	}
    }
}
