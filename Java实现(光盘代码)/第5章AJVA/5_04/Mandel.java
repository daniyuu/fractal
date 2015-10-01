//Newtonµü´ú·¨£¨fractal.cn£©
import java.awt.*;
import java.applet.*;
import java.awt.event.*;


class Fractal extends Canvas
{
  
     public Fractal()
     {
          this.setBackground(Color.white);
     }
     
     public void paint(Graphics g) {
              
	  	  int a=640;
                int b=480;

                int M=100;
                int K=100;

                double pMin=-2.2;
                double qMin=-1.2;
                double pMax=0.7;
                double qMax=1.2;

                double p1,q1;

                p1=(pMax-pMin)/(a-1);
                q1=(qMax-qMin)/(b-1);


		for(int i=0;i<a;i++)
                  for( int j=0;j<b;j++)
                  {
                     double p0=pMin+i*p1;
                     double q0=qMin+j*q1;

                     double x0=0;
                     double y0=0;

                     int k=0;
                     int color=0;

                     double x1;
                     double y1;
                     double r;

                     for(k=1;k<K;k++)
                     {
                        x1=x0;
                        y1=y0;

                        x0=x1*x1-y1*y1+p0;
                        y0=2*x1*y1+q0;
                  
                        r=x0*x0+y0*y0;

                        if(r>M){
                         color=200*k;
                         break;
                         }


                      }
                   
                    g.setColor(new Color(color));

                    if(k==K){
         
                       g.setColor(Color.white);
                     }

                    g.drawLine(i,j,i,j);


                  }//end for

          
	}//end of paint

}



public class Mandel extends Applet
{
	
	Fractal fractal;
      
	public void init()
	{
	   setLayout(new BorderLayout());

          fractal=new Fractal();
	
	   add("Center",fractal);


	}


}

