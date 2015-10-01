//Hilbert-Peano���ߣ�����Ƶ����fractal.cn��2004
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

  //�õ�����ֵ
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //����һ��applet
  public Hilbert() {
  }

  //��ʼ��applet
  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //��Ա��ʼ��
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    parameterPanel.setLayout(gridLayout1);
    label1.setText("�ݹ���� = ");
    nStepsField.setForeground(Color.black);
    drawButton.setLabel("����");
    drawButton.addMouseListener(new Hilbert_drawButton_mouseAdapter(this));
    this.add(parameterPanel,  BorderLayout.NORTH);
    parameterPanel.add(nStepsPanel, null);
    parameterPanel.add(buttonPanel, null);
    buttonPanel.add(drawButton, null);
    this.add(hilbertCurve, BorderLayout.CENTER);
    nStepsPanel.add(label1, null);
    nStepsPanel.add(nStepsField, null);
  }

  //�õ�applet��Ϣ
  public String getAppletInfo() {
    return "Applet Information";
  }

  //�õ�������Ϣ
  public String[][] getParameterInfo() {
    return null;
  }

  void drawButton_mousePressed(MouseEvent e) {
    hilbertCurve.setSteps(Integer.parseInt(nStepsField.getText()));
  }
}

class HilbertCurve extends Canvas
{
 private int x;//x,yΪ���߳�ʼ����
 private int y;
 private int h;//��λ�߶γ���
 private int n = 5;//�ݹ����
 private int len;//�ݹ�ֽ����

 public HilbertCurve() { n = 5; }

 public void A()//Hilbert-Peano�����ĸ�����Ԫ��֮һ�ĵݹ����
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
 public void B()//Hilbert-Peano�����ĸ�����Ԫ��֮һ�ĵݹ����
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
 public void C()//Hilbert-Peano�����ĸ�����Ԫ��֮һ�ĵݹ����
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
 public void D()//Hilbert-Peano�����ĸ�����Ԫ��֮һ�ĵݹ����
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
