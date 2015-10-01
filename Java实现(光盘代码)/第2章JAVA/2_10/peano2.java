import java.awt.*;
import java.applet.*;

public class peano2 extends Applet {
  private Image offScreenImage=null;
  private Graphics offScreenBuffer=null;

  private int width=800;
  private int height=600;

  int cx,cy;
  boolean times=true;
    int tpr=0;
    boolean srsj=false;
    float jiao=0;
  int n=4;
    int mx=744,my=176;
    int length=7;
  float q;
  float sinq;
  float max;
  float eda,edb,edc;
  int nx,ny;
  boolean chong;
  float len1,len2,len3;
  int CurrentX,CurrentY;

  public peano2(){}

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
          drawshpe(offScreenBuffer);
          g.drawImage(offScreenImage,0,0,this);
  }

  public void setplot(int nx,int ny){
          cx=nx;cy=ny;
          CurrentX=cx;CurrentY=cy;
  }

  public void plot(Graphics g,int cx,int cy){
          g.setColor(Color.black);
          g.drawLine(CurrentX, CurrentY,cx, cy);
          CurrentX=cx;
          CurrentY=cy;
  }

  public void a(Graphics g,int i){
            if( i > 0 ){
                     d(g,i - 1); cx = cx - (int)(len1 * Math.cos(q)); cy = cy - (int) (len1 *Math.sin(q));plot(g,cx,cy);
                     a(g,i - 1); cy = cy + (int)(len1 * Math.cos(q)); cx = cx - (int) (len1 *Math.sin(q));plot(g,cx,cy);
                     a(g,i - 1); cx = cx + (int)(len1 * Math.cos(q)); cy = cy + (int) (len1 *Math.sin(q));plot(g,cx,cy);
                     b(g,i - 1);
            }
    }

  public void b(Graphics g,int i){
            if( i > 0 ){
                     c(g,i - 1); cy = cy - (int) (len1 *Math.cos(q)); cx = cx + (int) (len1 *Math.sin(q));plot(g,cx,cy);
                     b(g,i - 1); cx = cx + (int) (len1 *Math.cos(q)); cy = cy + (int) (len1 *Math.sin(q));plot(g,cx,cy);
                     b(g,i - 1); cy = cy + (int) (len1 *Math.cos(q)); cx = cx - (int) (len1 *Math.sin(q));plot(g,cx,cy);
                     a(g,i - 1);
            }
    }

  public void c(Graphics g,int i){
            if( i > 0 ){
                     b(g,i - 1); cx = cx + (int) (len1 *Math.cos(q)); cy = cy + (int) (len1 *Math.sin(q));plot(g,cx,cy);
                     c(g,i - 1); cy = cy - (int) (len1 *Math.cos(q)); cx = cx + (int) (len1 *Math.sin(q));plot(g,cx,cy);
                     c(g,i - 1); cx = cx - (int) (len1 *Math.cos(q)); cy = cy - (int) (len1 *Math.sin(q));plot(g,cx,cy);
                     d(g,i - 1);
            }
   }

  public void d(Graphics g,int i){
          if( i > 0 ){
                  a(g,i - 1); cy = cy + (int) (len1 *Math.cos(q)); cx = cx - (int) (len1 *Math.sin(q));plot(g,cx,cy);
                     d(g,i - 1); cx = cx - (int) (len1 *Math.cos(q)); cy = cy - (int) (len1 *Math.sin(q));plot(g,cx,cy);
                     d(g,i - 1); cy = cy - (int) (len1 *Math.cos(q)); cx = cx + (int) (len1 *Math.sin(q));plot(g,cx,cy);
                     c(g,i - 1);
            }
  }

  public void drawshpe(Graphics g){
            int h1,h2;
            int trr;
          int x,y,dir;
          float tem1,tem2,tem3;
          eda=3.0f;edb=4.0f;edc=5.0f;
          len1=length;
          len2=len1*edb/eda;
          len3=len1*edc/eda;
          tem1=len1;
          max=0;
          if(len1>max) max=len1;
          if(len2>max) max=len2;
          if(len3>max) max=len3;
          sinq=(float)((Math.pow(eda,2)+Math.pow(edb,2)-Math.pow(edc,2))/(2*eda*edc));
          tem2=(int)Math.pow(2,n)-1;
          //q=(float)(Math.atan(sinq/Math.sqrt(1-sinq*sinq)));
          tem3=(int)q;
          x=(int)(max*Math.pow(2,n));
            y=x;
          ny=y;
          nx=x;
          q=jiao;
          setplot(nx,ny);
          a(g,n);

            q=tem3+jiao;
          nx=x;
          ny=y;
          len1=len2;
          setplot(nx,ny);
          b(g,n);

           len1=len3;
          nx=(int)(x-tem1*(Math.pow(2,n)-1)*Math.sin(jiao));
          ny=(int)(y+tem1*(Math.pow(2,n)-1)*Math.cos(jiao));
          sinq=(float)((Math.pow(eda,2)+Math.pow(edc,2)-Math.pow(edb,2))/(2*eda*edc));
                 q=(float)(Math.atan(sinq/Math.sqrt(1-sinq*sinq)));
                 q=(float)(3.1415/2-q+jiao);
                setplot(nx,ny);
          c(g,n);
    }
}
