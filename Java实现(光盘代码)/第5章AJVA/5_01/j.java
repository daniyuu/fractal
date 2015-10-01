//基于逃逸时间算法的Sierpinski三角形(分形频道：fractal.cn)2004
import java.awt.*;
import java.awt.Color;
import java.awt.Image;

public class j extends java.applet.Applet{
	public Graphics offScreenBuffer=null;
	public Image offScreenImage=null;
	
	private float a,b,c,d;
	private int n,m,r,p,q;
	int width=400;
	int height=400;
	private float x,y,x0,y0;
	
	public j(){}
	
	public void init(){
               setBackground(new Color(255,255,255));//设置背景色为白色
		offScreenImage=this.createImage(400,400);
		offScreenBuffer=offScreenImage.getGraphics();
	}
	
	public void update(Graphics gc){
		paint(gc);
	}
	
	public void paint(Graphics gc){
		offScreenBuffer.setColor(Color.black);
		serpirski(offScreenBuffer,0,0,1,1,12,200,200);
		gc.drawImage(offScreenImage,0,0,this);
	}
	
   public void serpirski(Graphics gc,float a,float b,float c,float d,float n,int m,int r){
	boolean flag=true;
	for(p=1;p<m;p++){
        	x0=a+(c-a)*p/m;
        	for(q=1;q<m;q++){
        		y0=b+(d-b)*q/m;
        		x=x0;
			y=y0;
        		for(int k=1;k<n+1;k++){
			        if(y>0.5){
        				x=2*x;
					y=2*y-1;
				}
        			else if(x>=0.5){
        				x=2*x-1;
					y=2*y;
        			}
				else{
					x=2*x;
					y=2*y;
				}
				if((x*x+y*y)>r){flag=false;}
			}
			if(flag) gc.drawRect(p+100,q+100,1,1);
			flag=true;
        		
		}
	}
   }
}
