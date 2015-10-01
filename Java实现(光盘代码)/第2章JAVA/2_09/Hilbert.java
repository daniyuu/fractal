//Hilbert-Peano曲线（分形频道：fractal.cn）2004
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Hilbert extends Applet {
  private boolean isStandalone = false;
  Panel parameterPanel = new Panel();
  BorderLayout borderLayout1 = new BorderLayout();
  GridLayout gridLayout1 = new GridLayout(2,1);
  Panel nStepsPanel = new Panel();
  Panel buttonPanel = new Panel();
  Label label1 = new Label();
  TextField nStepsField = new TextField("5",5);
  Button drawButton = new Button();
  HilbertCurve hilbertCurve = new HilbertCurve();

  //得到参数值
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //创建一个applet
  public Hilbert() {
  }

  //初始化applet
  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //成员初始化
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    parameterPanel.setLayout(gridLayout1);
    label1.setText("递归深度 = ");
    nStepsField.setForeground(Color.black);
    drawButton.setLabel("绘制");
    drawButton.addMouseListener(new Hilbert_drawButton_mouseAdapter(this));
    this.add(parameterPanel,  BorderLayout.NORTH);
    parameterPanel.add(nStepsPanel, null);
    parameterPanel.add(buttonPanel, null);
    buttonPanel.add(drawButton, null);
    this.add(hilbertCurve, BorderLayout.CENTER);
    nStepsPanel.add(label1, null);
    nStepsPanel.add(nStepsField, null);
  }

  //得到applet信息
  public String getAppletInfo() {
    return "Applet Information";
  }

  //得到参数信息
  public String[][] getParameterInfo() {
    return null;
  }

  void drawButton_mousePressed(MouseEvent e) {
    hilbertCurve.setSteps(Integer.parseInt(nStepsField.getText()));
  }
}

class HilbertCurve extends Canvas
{
 private int x;//x,y为曲线初始坐标
 private int y;
 private int h;//单位线段长度
 private int n = 5;//递归深度
 private int len;//递归分解比率

 public HilbertCurve() { n = 5; }

 public void A()//Hilbert-Peano曲线四个基本元素之一的递归调用
 {
  if(n > 0)
  {
   Graphics g = getGraphics(); n--;
   D(); g.drawLine(x, y, x-h, y); x-=h;
   A(); g.drawLine(x, y, x, y-h); y-=h;
   A(); g.drawLine(x, y, x+h, y); x+=h;
   B(); n++;
  }
 }
 public void B()//Hilbert-Peano曲线四个基本元素之一的递归调用
 {
  if(n > 0)
  {
   Graphics g = getGraphics(); n--;
   C(); g.drawLine(x, y, x, y+h); y+=h;
   B(); g.drawLine(x, y, x+h, y); x+=h;
   B(); g.drawLine(x, y, x, y-h); y-=h;
   A(); n++;
  }
 }
 public void C()//Hilbert-Peano曲线四个基本元素之一的递归调用
 {
  if(n > 0)
  {
   Graphics g = getGraphics(); n--;
   B(); g.drawLine(x, y, x+h, y); x+=h;
   C(); g.drawLine(x, y, x, y+h); y+=h;
   C(); g.drawLine(x, y, x-h, y); x-=h;
   D(); n++;
  }
 }
 public void D()//Hilbert-Peano曲线四个基本元素之一的递归调用
 {
  if(n > 0)
  {
   Graphics g = getGraphics(); n--;
   A(); g.drawLine(x, y, x, y-h); y-=h;
   D(); g.drawLine(x, y, x-h, y); x-=h;
   D(); g.drawLine(x, y, x, y+h); y+=h;
   C(); n++;
  }
 }

 public void paint(Graphics g)
 {
  Dimension size = getSize();
  h = 4*Math.min(size.width,size.height)/5;
  x = size.width/2+h/2;
  y = size.height/2+h/2;

  for(int i=len=1;i<n;i++) len = 2*len+1;
  h/=len; A();
 }

 public void setSteps(int nSteps)
 { n = nSteps; repaint(); }
}

class Hilbert_drawButton_mouseAdapter extends java.awt.event.MouseAdapter {
  Hilbert adaptee;

  Hilbert_drawButton_mouseAdapter(Hilbert adaptee) {
    this.adaptee = adaptee;
  }
  public void mousePressed(MouseEvent e) {
    adaptee.drawButton_mousePressed(e);
  }
}
