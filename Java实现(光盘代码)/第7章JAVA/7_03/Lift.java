//<title>随机元胞自动机（分形频道：fractal.cn）</title>
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class Lift extends Applet implements ActionListener{
	

	Button button=new Button("画图");
    int offset=1;
	int width=1;
	int a[][]=new int[200][200];
	int b[][]=new int[200][200];


	
	public void init() 
	  {

	    for(int i=0;i<200;++i)
		  for(int j=0;j<200;++j)
		  {
			a[i][j]=0;
			b[i][j]=0;
		  }
        a[100][100]=1;
	    b[100][100]=1; 
	    add(button);
	    button.addActionListener(this);

		setBackground(Color.black);
			
	  }
    void drawpixel(int m, int n,Graphics g)
    { 

	   int k; //行max
	   int t; //列max
 
	   if(offset-m>=0 ||offset-n>=0)
	   {
		    g.drawString("已经变换到最大。",100,120);
	        return;
	    }
	
       int i=0;
       int j=0;
 
      for(i=m-offset;i<=m+offset;++i)
	     for(j=n-offset;j<=n+offset;++j)
	      {
		      if(Math.abs(i-m)<width && Math.abs(j-n)<width)
			      continue;
		      if((a[i-1][j-1]+a[i-1][j]+a[i-1][j+1]
			        +a[i][j-1]+a[i][j+1]
			        +a[i+1][j-1]+a[i+1][j]+a[i+1][j+1])%2==1)
		       {   
		
			       b[i][j]=1;
		   
		       }
		      else 
			        b[i][j]=0;         
	      } 
	   
	   
	   
       for(i=m-offset;i<=m+offset;++i)
	     for(j=n-offset;j<=n+offset;++j)
		 {
		 	if(Math.abs(i-m)<width && Math.abs(j-n)<width)
			   continue;
	        a[i][j]=b[i][j];
		    if(a[i][j]==1)

		 {   
			 g.drawLine(i,j,i,j); //画点
		 }

		 }
	      offset++;

	      width++;

	   
}

	public void paint(Graphics g) {


        int red=(int)(Math.random()*160);
        int green=(int)(Math.random()*180);
        int blue=(int)(Math.random()*100);
        Color col=new Color(red,green,blue);
        g.setColor(col);
		drawpixel(100,100,g);


	}
	public void actionPerformed(ActionEvent e)
	{
		
		
		 
		repaint();
	}
	public void update(Graphics g)
	{
		paint(g);
	}
}
