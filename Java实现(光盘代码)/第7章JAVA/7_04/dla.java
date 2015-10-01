//DLA模型（分形频道：fractal.cn）2004
import java.awt.*;
import java.applet.*;
import java.io.*;
import java.util.*;
import java.lang.String;

public class dla extends Applet implements Runnable 
{
    Thread     runner;
    int        WIDTH,HEIGHT;
    Graphics   g; 
    Random     nrand;
     
    int        MaxNum;
    int        YUAN,MAXYUAN,  Ox,Oy,num;
     
    int        P[][],p0[];
    boolean    moving=false;
	
    public void init() 
    {
        Date d=new Date();
	 nrand=new Random(d.getTime());
	 	 
	 WIDTH=this.size().width;
	 HEIGHT=this.size().height;
	 String s;
	    
	 g=this.getGraphics();
	 g.setColor(Color.black);
        g.fillRect(0,0,WIDTH,HEIGHT);
        
	 s=getParameter("NUM");
	 if (s==null) MaxNum=1000;
	 else MaxNum = Integer.parseInt(s);
	 
	 setBackground(Color.black);
	 setForeground(Color.white);
		
	 Ox=WIDTH/2;
	 Oy=HEIGHT/2;
	 	
	 P=new int[MaxNum][2];
	 p0=new int[2];
        
        P[0][0]=0;
        P[0][0]=0;
        num=1;
                 
        YUAN=5;
        MAXYUAN=10; 	 
    }
	
        public void start()
	 {
	     if (runner == null)
	     {
	         runner= new Thread(this);
		  runner.start();
	     }
	 }
	
        public void stop()
	 {
	     if (runner!=null)
	     {
	         runner.stop();
		  runner=null;
	     }
	 }
	
	 public void run()
	 {	
	     while (true) 
            {
	         try { Thread.sleep(0); } 
		  catch (InterruptedException e){};

		  if ( isVisible()  ) 
                {
	             RunMe();
                }
            }	  
        }
	
	 public void  RunMe()
	 {
	     if(!moving)
            {
                GP(p0);
                moving=true;
                drawBill(p0,true);
            }
         
            if(!DCK(p0,num))  drawBill(p0,false);
       
            switch(randomise(4))
            {
                case 0: 
                       p0[0]= p0[0]-1; 
                       break;
                case 1: 
                       p0[0]=p0[0]+1; 
                       break;
                case 2: 
                       p0[1]= p0[1]-1; 
                       break;
                case 3: 
                       p0[1]=p0[1]+1; 
                       break;
            }
         
            if(DCOut(p0)) { moving=false;}
            else
            {
                drawBill(p0,true);
                if(DC(p0,num))
                {
                    P[num][0]=p0[0];
                    P[num][1]=p0[1];
                    num++;
              
                    if(num>=MaxNum)  DomyInit();
                    else
                    {
                        moving=false;
                        doCalcMinRad(num);
                    }
                }
            }
        }
	 
	 public void paint(Graphics g)
	 {}
	
	 int myabs(int s)
	 {
 	     if(s<0) return -s;
	     return s;
	 }

        private void GP(int  dst[])
        {
            int xs,ys,s0,min,max;
            int  val;
  
            min=(YUAN*YUAN*3)/4;
            max=(YUAN*YUAN*4)/3;
   
            val=(YUAN*3)/2;
  
            while(true)
            {
                xs=randomise(val*2)-val;
                ys=randomise(val*2)-val;
                s0=xs*xs + ys*ys;
                if(s0<max && s0>min) break;
            }
 
            dst[0]=xs;  dst[1]=ys;
        }

        private boolean DC(int src[],int N)
        {
            int i;
            for(i=0;i<N;i++)
            {
                if(DCTooNear(src,P[i])) return false;
            }
 
            for(i=0;i<N;i++)
            {
                if(DCNear(src,P[i])) return true;
            }
            return false;
        }

        private boolean DCTooNear(int P0[],int P1[])
        {
            int  x,y;
 
            x=myabs(P0[0] - P1[0]);
            y=myabs(P0[1] - P1[1]);
            if(x==0 && y==0) return true;
    
            return false;
        }

        private boolean DCNear(int P0[],int P1[])
        {
            int x,y;
 
            x=myabs(P0[0] - P1[0]);
            y=myabs(P0[1] - P1[1]);
            if(x>1 || y>1) return false;
     
            return true;
        }

        private boolean DCOut(int src[])
        {
            int xs,ys,s0;
 
            xs=src[0];
            ys=src[1];
            s0=xs*xs + ys*ys;
            if(s0>MAXYUAN*MAXYUAN) return true;

            return false;
        }

        private void  drawBill(int srcP[],boolean draw)
        {
            if(draw) g.setColor(Color.cyan);
            else  g.setColor(Color.black);
		      
	     g.fillOval(srcP[0]+Ox,srcP[1]+Oy,1,1);

          
        }

        private void  doCalcMinRad(long N)
        {
            int    i,n0=0,xs,ys,val;
            double d0;

            for(i=0;i<N;i++)
            {
                xs=P[i][0];
                ys=P[i][1];
                val=xs*xs + ys*ys;
                if(val>n0) n0=val;
            }

            d0=n0;
            d0=Math.sqrt(d0);
    
            YUAN=(int)d0;
            if(YUAN<5) YUAN=5;
    
            MAXYUAN=YUAN*2;
            if(MAXYUAN - YUAN>100) MAXYUAN=YUAN+100;   
        }

        private boolean DCK(int src[],long N)
        {
            int i;
 
            for(i=0;i<N;i++)
            {   if(src[0]==P[i][0] && src[1]==P[i][1]) return true;  }
 
            return false;
        }

        private void DomyInit()
        {
            g.setColor(Color.black);
            g.fillRect(0,0,WIDTH,HEIGHT);

            P[0][0]=0;
            P[0][1]=0;
            num=1;
            drawBill(P[0],true);
        
            YUAN=5;
            MAXYUAN=10;
        }

	private int randomise(int range)
	{
	    return( java.lang.Math.abs(nrand.nextInt()) % range);
	}
    }
