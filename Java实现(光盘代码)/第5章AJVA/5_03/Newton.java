//Newton迭代法（fractal.cn）2004
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;

public class Newton extends Applet implements Serializable
{
    private Image offScreenImage=null;
    private Graphics offScreenBuffer=null;
	
    private int width=401;
    private int height=401;
	
    int n;
    int c;
    //下列数组用来存储通过牛顿方法迭代所产生的点
    int Fnewton[][]=new int[400][400];
	
    public Newton(){}
	
    public void init()
    {
		offScreenImage=this.createImage(width,height);
		offScreenBuffer=offScreenImage.getGraphics();
	}

	public void update(Graphics g){
		paint(g);
	}
	
	public void paint(Graphics g)
        {
			offScreenBuffer.setColor(Color.white);
			offScreenBuffer.fillRect(0,0,width,height);
         //绘制基于牛顿方法的分形  
			if (Fnewton[0][0] != 0){
	    for (int x=0; x<400; x++){
		for (int y=0; y<400; y++)
		    {
			if (Fnewton[x][y]<=5)
			    g.setColor(new Color(Fnewton[x][y]*50, 0 , 0));
			else if (Fnewton[x][y]<=10)
			    g.setColor(new Color(0,0,Fnewton[x][y]*20 ));
			else
			    g.setColor(new Color(0,Fnewton[x][y]*9,0));
			g.drawLine(x,y,x,y);
		    }
	   }
	      		}
			drawFract(offScreenBuffer,n,c);
			g.drawImage(offScreenImage,0,0,this);
	}
	

   public void drawFract(Graphics g, int n, int c)
   {
    	n=3;
	c=3;
	Complex root = new Complex();
	Complex hold = new Complex();
	Complex foo = new Complex(c,0);
	int tp = 0;
	
	for (double x=-2; x<=2; x+=.01)
	    {
		for (double y=-2; y<=2; y+=.01)
		    {
		        root.compEq(new Complex(x,y));
		        double dx = 1;
			tp=0;
			
			while ((dx>.1) && (tp < 25))
			    {
				
				hold = compDiv(compSubtr(root.compPwr(n),foo),doubleMult(n,root.compPwr(n-1)));
				dx=hold.compNorm();
				//设置根为新根 (root - f(x)/f'(x))
				root.compEq(compSubtr(root , compDiv(compSubtr(root.compPwr(n),foo),doubleMult(n,root.compPwr(n-1)))));
				tp++;
				
 
			    }
			
			
			Fnewton[(int)(x*100 + 200)][(int)(y*100 + 200)]=tp;
		    }
		
	   }
    }

    //复数运算类，有加、减、乘、除、指数等
    public class Complex
    {
	

	double real,imag;

	public Complex()
	{
	    real=0;
	    imag=0;
	}
	
	public Complex(double i, double j)
	{
	    real = i;
	    imag =j;
	}
	
	public double retReal()
	{
	    return real;
	}

	public double retImag()
	{
	    return imag;
	}

	public void compEq(Complex i)
	{
	    real = i.retReal();
	    imag = i.retImag();
	}
	
	public void compPrint()
	{
	    System.out.println(real);
	    System.out.println(imag);
	}
	public Complex compPwr(int i)
	{
	    
	    Complex foo = new Complex(real,imag);
	    Complex bar = new Complex(real,imag);
	    for (int j=0; j<(i-1); j++) 
		foo.compEq(compMult(foo, bar));
	    return foo;
	}

	public double compNorm()
	{
	    return Math.sqrt(real*real+imag*imag);
	}
    }
    public Complex compAdd(Complex i, Complex j)
    {
	return new Complex(i.retReal()+j.retReal(), i.retImag()+j.retImag());
    }
    
    public Complex compSubtr(Complex i, Complex j)
    {
	return new Complex(i.retReal()-j.retReal(), i.retImag()-j.retImag());
    }
    
    public Complex doubleMult(double i, Complex j)
    {
	return new Complex(i*j.retReal(),i*j.retImag());
    }
    
    public Complex compMult(Complex i, Complex j)
    {
	return new Complex(i.retReal()*j.retReal()-i.retImag()*j.retImag(),i.retReal()*j.retImag()+j.retReal()*i.retImag());
    }
    
    public Complex compDiv(Complex i, Complex j)
    {
	return new Complex((i.retReal()*j.retReal()+i.retImag()*j.retImag())/(j.retReal()*j.retReal()+j.retImag()*j.retImag()),(i.retImag()*j.retReal() - i.retReal()*j.retImag())/(j.retReal()*j.retReal()+j.retImag()*j.retImag()));
    }
    
}
