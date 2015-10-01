//随机L系统（分形频道：fractal.cn）2004
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
/* Applet of  L-sytem  */

class stapel{

 double x;
 double y;
 double alpha;
 stapel nach;
 stapel vor; 
}

class turtle{            
 
 double xpos;
 double ypos;
 double alpha;
 boolean pen;
 
 double bogenmass (double winkel){

  return (2*Math.PI*(winkel/360));
 }
 double winkel (double bogenmass){

  return (360*(bogenmass/(2*Math.PI)));	
 }
 void setx(double neux){

  xpos=neux;
 }
 double getx()
 {
  return xpos;	
 }
 void sety(double neuy)
 {
  ypos=neuy;	
 }
 double gety()
 {
  return ypos;	
 }
 void setalpha(double neualpha)
 {
  alpha = neualpha;	
 }
 double getalpha()
 {
  return alpha;	
 }
 void penup()
 {pen = false;}
 void pendown()
 {pen = true;}
 
 void turn (double winkel)
 {
 alpha = alpha + winkel;
 if (alpha > 360) {alpha = alpha-360;}
 if (alpha <   0) {alpha = 360 + alpha;}
 }
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
 }
}
public class Lsystem extends Applet
{
 String regel;
 stapel kopf = new stapel();
 stapel last = kopf;
StringBuffer zeichenkette = new StringBuffer("F");     //公理

double win =30;

 char ch;
 static private int hoehe;
 static private int breite;
 static private int laenge;
 static private final int a =5;                        //递归深度
 turtle myTurtle = new turtle();
	
 public void befehl (char ch, Graphics g){             // L系统规则
  switch (ch){
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
    }
 }

 public void paint(Graphics g){                        //从这画

    for (int z=0;z<zeichenkette.length();z++){

     	befehl(zeichenkette.charAt(z),g);
     }
    }
  public String selectFunction(double c){              //返回不同的规则

     if(c <= 0.3){
         regel = "F[+F]F[-F]F";
       return regel;
     }else if(c > 0.3 && c <= 0.6){
          regel = "F[+F]F[-F[+F]]";
       return regel;
     }else{
        regel = "FF-[-F+F+F]+[+F-F-F]";
       return regel;
     }
  }		
  
 public void init(){

     Dimension size = getSize();
     hoehe   = size.height;
     breite  = size.width;
     setBackground(new Color(255,255,255));//设置背景色为白色
     laenge =4;
     myTurtle.setx(300);
     myTurtle.sety(1100-hoehe);
  
     myTurtle.setalpha(-90);
     myTurtle.pendown();
     stapel kopf = new stapel();
     stapel last = kopf;
     kopf.vor = null;
     kopf.nach = null;
    
     for (int z=0;z<a;z++){

      for (int i=0;i<zeichenkette.length();i++) {

       if (zeichenkette.charAt(i)=='F'){

         zeichenkette=zeichenkette.deleteCharAt(i);         //删除一个字符，返回该字符的函数
         zeichenkette=zeichenkette.insert(i, selectFunction(Math.random()));
         
         i = i+regel.length();
         continue;
        }	
      }	
     }
      
 } 
}
