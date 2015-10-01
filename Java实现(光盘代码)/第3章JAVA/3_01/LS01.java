//L系统（分形频道：fractal.cn）2004
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;


class stapel
{
 double x;
 double y;
 double alpha;
 stapel nach;
 stapel vor; 
}//END stapel

class turtle
{
 double xpos;
 double ypos;
 double alpha;
 boolean pen;

 double bogenmass (double winkel)
 {
  return (2*Math.PI*(winkel/360));
 }//END bogenmass

 double winkel (double bogenmass)
 {
  return (360*(bogenmass/(2*Math.PI)));	
 }//END winkel

 void setx(double neux)
 {
  xpos=neux;
 }//END setx

 double getx()
 {
  return xpos;	
 }//END getx

 void sety(double neuy)
 {
  ypos=neuy;	
 }//END sety

 double gety()
 {
  return ypos;	
 }//END gety

 void setalpha(double neualpha)
 {
  alpha = neualpha;	
 }//END setalpha

 double getalpha()
 {
  return alpha;	
 }//END alpha

 void penup()
 {pen = false;}

 void pendown()
 {pen = true;}

 
 void turn (double winkel)
 {
 alpha = alpha + winkel;
 if (alpha > 360) {alpha = alpha-360;}
 if (alpha <   0) {alpha = 360 + alpha;}
 }//END turn

 void move(int laenge, Graphics g)
  {
  double neux;
  double neuy;
  double neualpha; 
  neux = xpos+(laenge*Math.cos(bogenmass(alpha)));
  neuy = ypos+(laenge*Math.sin(bogenmass(alpha)));
  if (pen){g.drawLine((int)xpos,(int)ypos,(int)neux,(int)neuy);}
  setx(neux);
  sety(neuy);
 }//END move
}//END turtle

public class LS01 extends Applet
{
 stapel kopf = new stapel();
 stapel last = kopf;

 StringBuffer zeichenkette = new StringBuffer("F");
 String regel = "F-F++F-F";
 double win = 60;

 char ch;
 static private int hoehe;
 static private int breite;
 static private int laenge;
 static private final int a =5;
 turtle myTurtle = new turtle();
	
 public void befehl (char ch, Graphics g)
 {
  switch (ch)
   {
   	case 'F':
            myTurtle.pendown();
      	myTurtle.move(laenge,g);
      	break;
   	case 'f':
      	myTurtle.penup();
            myTurtle.move(laenge,g);
      	break;
    	case '+':
      	myTurtle.turn(win);
      	break;
    	case '-':
      	myTurtle.turn(-win);
      	break;
    	case '[':
      	//push
      	last.nach = new stapel();
      	last.nach.vor = last;
      	last = last.nach;
      	last.x =myTurtle.getx();
      	last.y =myTurtle.gety();
      	last.alpha = myTurtle.getalpha();
      	break;
    	case ']':
      	//pop
      	myTurtle.setx(last.x);
      	myTurtle.sety(last.y);
      	myTurtle.setalpha(last.alpha);
      	last = last.vor;
      	last.nach = null;
      	break;
      default:
            {}
    }//END switch
 }//END befehl

 public void paint(Graphics g)
    {     
    for (int z=0;z<zeichenkette.length();z++)
     {
     	befehl(zeichenkette.charAt(z),g);
     }
    }//END paint
			
  
 public  void init()
 {
     setBackground(new Color(255,255,255));//设置背景色为白色
     Dimension size = getSize();
     hoehe   = size.height;
     breite  = size.width;
     laenge = 2;
     myTurtle.setx(100);
     myTurtle.sety(800-hoehe);
     myTurtle.setalpha(0);
     
     myTurtle.pendown();
     stapel kopf = new stapel();
     stapel last = kopf;
     kopf.vor = null;
     kopf.nach = null;
     
     for (int z=0;z<a;z++)
     {
      for (int i=0;i<zeichenkette.length();i++)
      {
       if (zeichenkette.charAt(i)=='F')
        {
         zeichenkette=zeichenkette.deleteCharAt(i);
         zeichenkette=zeichenkette.insert(i, regel);
         
         i = i+regel.length();
         continue;
        }//END if	
      }//END for 2	
     }//END for 1
      
 }//END init 
}//END lsystem