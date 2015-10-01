//Koch雪花（分形频道：fractal.cn）
 import java.applet.*; 
 import java.awt.*; 
 import java.awt.event.*; 

public class  kochsnow extends Applet 

/*递归雪花分形*/
implements MouseListener  {

int  depth;

public void init()  {
 setBackground(Color .white);
 addMouseListener(this) ;
 depth=1;
} /*end init*/

/*鼠标方法*/
public void mouseMoved(MouseEvent e) {} 
public void mouseDragged(MouseEvent e) {} 
public void mouseReleased(MouseEvent e) {} 
public void mouseClicked(MouseEvent e) {} 
public void mouseEntered(MouseEvent e) {} 
public void mouseExited(MouseEvent e) {} 

void  snowflake(
double  x1, double  y1, double  x2, double  y2,
int  depth, Graphics  g){

if  (depth<=1)
{g.drawLine ((int )x1,(int )y1,(int )x2,(int )y2);}
else  {
/*计算额外的点*/
double  x4=x1*2/3 + x2*1/3;
double  y4=y1*2/3 + y2*1/3;
double  x5=x1*1/3 + x2*2/3;
double  y5=y1*1/3 + y2*2/3;
double  x6=(x4+x5)/2+(y4-y5)*Math.sqrt (3)/2;
double  y6=(y4+y5)/2+(x5-x4)*Math.sqrt (3)/2;
/*调用雪花递归*/
snowflake(x1,y1,x4,y4,depth-1,g);
snowflake(x4,y4,x6,y6,depth-1,g);
snowflake(x6,y6,x5,y5,depth-1,g);
snowflake(x5,y5,x2,y2,depth-1,g);

} /*end else*/
} /*end snowflake*/


public void paint(Graphics  g) {
g.setColor(Color .black);
snowflake(280.0,  10.0, 164.5, 210.0, depth, g);
snowflake(164.5, 210.0, 395.5, 210.0, depth, g);
snowflake(395.5, 210.0, 280.0,  10.0, depth, g);

} /*end paint*/

/*鼠标单击递归加深*/
public void mousePressed(MouseEvent  e) {
depth++;
repaint(); 
} /*end mousePressed*/

} /*end mouse1*/

