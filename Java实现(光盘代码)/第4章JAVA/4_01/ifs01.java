//迭代函数系统（分形频道：fractal.cn）2004
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Event;
import java.awt.Color;
import java.applet.Applet;
import java.util.StringTokenizer;

public class ifs01 extends Applet implements Runnable {
  public final static int maxLines = 10;
  public int lines = 0;
  public double map[][];
  
  Thread drawThread = null;

  int h, w;
  long dots = 100000;
  double t = 1.0, b = -1.0, l = -1.0, r = 1.0;
    
  public void init()
  {
    String s;
    setBackground(new Color(255,255,255));//设置背景色为白色
    s =  getParameter("lines");  // 得到fractal消息
    if (s == null)
      setDemoMap();
    else {
      int i = Integer.valueOf(s).intValue(), j;
      StringTokenizer st;
      
      map = new double[i][7];
      for (lines = 0; lines < i; lines++) {
        s = getParameter("line" + String.valueOf(lines));
        if (s == null)
          for (j = 0; j <7; j++)
            map[lines][j] = 0.0;
        else {
          st = new StringTokenizer(s);
          for (j = 0; (j < 7) && st.hasMoreTokens(); j++)
            map[lines][j] = Double.valueOf(st.nextToken()).doubleValue();
        }
      }
    }

    s = getParameter("top");              // 得到顶部边界
    if (s != null) t = Double.valueOf(s).doubleValue();
    s = getParameter("bottom");           // 得到底部边界
    if (s != null) b = Double.valueOf(s).doubleValue();
    s = getParameter("left");             // 得到左边界
    if (s != null) l = Double.valueOf(s).doubleValue();
    s = getParameter("right");            // 得到右边界
    if (s != null) r = Double.valueOf(s).doubleValue();
    s = getParameter("dots");             // 得到画图点
    if (s != null) dots = Long.valueOf(s).longValue();

    s = getParameter("bgcolor");          // 得到背景颜色;
    if (s != null)
      setBackground(new Color(Integer.valueOf(s, 16).intValue()));
    s = getParameter("bgcolour");         // 得到背景颜色;
    if (s != null)
      setBackground(new Color(Integer.valueOf(s, 16).intValue()));

    s = getParameter("fgcolor");          // 得到前景色;
    if (s != null)
      setForeground(new Color(Integer.valueOf(s, 16).intValue()));
    s = getParameter("fgcolour");         // 得到前景色;
    if (s != null)
      setForeground(new Color(Integer.valueOf(s, 16).intValue()));
  }
  
  public void stop()
  {
    drawThread = null;
    gcont = null;
  }
  
  void setDemoMap()
  {
    map = new double[3][7];
    lines = 3;

    map[0][0] =  0.5;   map[1][0] =  0.5;   map[2][0] =  0.5;
    map[0][1] =  0.0;   map[1][1] =  0.0;   map[2][1] =  0.0;
    map[0][2] =  0.0;   map[1][2] =  0.0;   map[2][2] =  0.0;
    map[0][3] =  0.5;   map[1][3] =  0.5;   map[2][3] =  0.5;
    map[0][4] =  0.0;   map[1][4] =  0.5;   map[2][4] =  0.25;
    map[0][5] =  0.0;   map[1][5] =  0.0;   map[2][5] =  0.5;
    map[0][6] =  0.333; map[1][6] =  0.333; map[2][6] =  0.334;

  }


  public boolean action(Event e)
  {
    switch (e.id) {
      case Event.WINDOW_DESTROY:
        System.exit(0);
        return true;
      default:
        return false;
    }
  }
  
  int transX(double x)
  {
    return (int)((double)(x - l) / (r - l) * w);
  }
  
  int transY(double y)
  {
    return (int)((double)(y - t) / (b - t) * h);
  }
  
  Graphics gcont;
  
  public void paint (Graphics g)
  {
    gcont = g.create();
    drawThread = new Thread(this, "Draw it");
    drawThread.start();
  }
  
  public void run()
  {
    Rectangle r = bounds();
    long i;
    int x = r.width / 2, y = r.height / 2;
  
    double u = 0.0, v = 0.0, newu, newv, sum = 0.0, rnd;
    int l = 0;

    h = r.height; w = r.width;

    for (i = 1; i <= dots; i++) {
      rnd = Math.random();
      l = 0; sum = map[l][6];
      while ( (rnd > sum) && (l < lines) ) {
        l++;
        sum += map[l][6];
      }
      if (l < lines) {
        newu = map[l][0] * u + map[l][1] * v;
        newv = map[l][2] * u + map[l][3] * v;
        u = newu + map[l][4];
        v = newv + map[l][5];
      }
      
      gcont.drawLine(transX(u), transY(v), transX(u), transY(v));
      if (i % 5 == 0) drawThread.yield();
    }
  
    gcont = null;
  }

}