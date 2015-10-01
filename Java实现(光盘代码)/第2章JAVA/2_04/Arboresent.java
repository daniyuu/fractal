//Arboresent·Î£¨·ÖÐÎÆµµÀ£ºfractal.cn£©2004
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import java.applet.*;
import java.lang.Math.*;

public class Arboresent extends Applet implements Serializable{
	private Image offScreenImage=null;
	private Graphics offScreenBuffer=null;
	
	private int width=800;
	private int height=500;
	
	private float prate=(float)1.5;
	private float theta=(float)(atn((float)(Math.sqrt(4/prate/prate-1))));
	private float t=(float)(Math.PI-4*theta);
	private float alfa=(float)(55*Math.PI/180);
	private float level=7;
	private float x=50;
	private float y=200;
	private float size=300;
	private double at;
	private double sum;
	
	public double atn(float x){
		double helper=x;
		for(int i=0;i<101;i++){
			sum=sum+helper/(2*i+1);
			helper=-helper*x*x;
		}
		at=sum;
		return at;
	}


	public Arboresent(){}
	
	public void init(){
		offScreenImage=this.createImage(width,height);
		offScreenBuffer=offScreenImage.getGraphics();
	}
	
	public void update(Graphics gc){
		paint(gc);
	}
	
	public void paint(Graphics gc){
			offScreenBuffer.setColor(Color.white);
			offScreenBuffer.fillRect(0,0,width,height);
			//Arboresent1(offScreenBuffer,x,y,size,prate,alfa,theta,t,(int)level);
			Arboresent2(offScreenBuffer,x,y,size,prate,alfa,theta,t,(int)level);
			gc.drawImage(offScreenImage,0,0,this);
	}

	public void Arboresent1(Graphics gc,float x,float y,float size,float prate,float alfa,float theta,float t,int level){
		float xe,ye,xa,ya,xb,yb,xc,yc,xd,yd,l; 
		l=size/prate;
		xe=x;
		ye=y;
		xa=(float)(x+size*Math.cos(alfa));
		ya=(float)(y+size*Math.sin(alfa));
		xb=(float)(x+l*Math.cos(alfa-theta));
		yb=(float)(y+l*Math.sin(alfa-theta));
		xc=(float)(x+l*Math.cos(alfa-theta-t));
		yc=(float)(y+l*Math.sin(alfa-theta-t));
		xd=(float)(x+size*Math.cos(alfa-theta*2-t));
		yd=(float)(y+size*Math.sin(alfa-theta*2-t));
		if(level<=1){
		gc.setColor(Color.red);
     		gc.drawLine((int)xa,(int)ya,(int)xe,(int)ye);
     		gc.drawLine((int)xe,(int)ye,(int)xd,(int)yd);
     		gc.drawLine((int)xd,(int)yd,(int)xc,(int)yc);
     		gc.drawLine((int)xc,(int)yc,(int)xe,(int)ye);
     		gc.drawLine((int)xe,(int)ye,(int)xb,(int)yb);
     		gc.drawLine((int)xb,(int)yb,(int)xa,(int)ya);
		}
		else{
			Arboresent1(gc,xb,yb,l,prate,(float)(alfa-theta+Math.PI),theta,t,level-1);
			Arboresent1(gc,xc,yc,l,prate,(float)(alfa-theta-t+Math.PI),-theta,-t,level-1);
		}
	}
	
	public void Arboresent2(Graphics gc,float x,float y,float size,float prate,float alfa,float theta,float t,int level){
		Arboresent1(gc,x,y,size,prate,alfa,theta,t,level);
		Arboresent1(gc,(float)(x+size*prate*Math.cos(alfa-theta-t)),(float)(y+size*prate*Math.sin(alfa-theta-t)),size,prate,alfa+2*theta-t,-theta,-t,level);
	}
}
