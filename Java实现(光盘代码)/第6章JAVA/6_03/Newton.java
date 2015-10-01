
import java.applet.Applet;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Rectangle;
import java.awt.Image;
import java.awt.image.ImageProducer;
import java.awt.image.ImageConsumer;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;

public class Newton extends Applet implements ImageProducer, Runnable 
{
    protected int maxt = 50;
    public int step = 1;
 
    public Cmplx c = null;
    public Cmplx tl = new Cmplx(-1.5,1.5), br = new Cmplx(1.5,-1.5);
    public Czoom czoom;

    Thread drawThread;
    boolean killThread = false;
    ColorModel cm = new DirectColorModel(9, 3, 16, 30);
    ImageConsumer ic;
    Rectangle imgSize;

    public void init() 
    {
        String s;
        double r = 0.0, i = 0.0;
        c = new Cmplx(r, i);
        czoom = new Czoom(this);
        czoom.updateImage();
        setLayout(new BorderLayout());
        add("Center", czoom);
   
    } 
  
    public boolean action(Event e) 
    {
        switch (e.id)
        {
            case Event.WINDOW_DESTROY:
            System.exit(0);
            return true;
            default:
            return false;
        }
    }
  
    public Cmplx transform(Cmplx z, Cmplx c) 
    {
        return new Cmplx().sqr(z).add(c);
    }
  
    public Cmplx initz() 
    {
        return new Cmplx(0.0, 0.0);
    }
  
  public int pixelColour(Cmplx pixel) {
    Cmplx z = null;
    int it = 0;
    
 
      z = initz();
      do {
        it++;
        z = transform(z, pixel);
      } while ( (z.modsq() <= 0.005) && (it <= maxt) );

      return it;
  }

  public synchronized void addConsumer(ImageConsumer ic) {
    this.ic = ic;
  }

  public synchronized boolean isConsumer(ImageConsumer ic) {
    return (ic == this.ic);
  }

  public synchronized void removeConsumer(ImageConsumer ic) {
    if (this.ic == ic) {
      this.ic = null;
    }
  }

  public void startProduction(ImageConsumer ic) {
    imgSize = czoom.bounds();
    this.ic = ic;
    this.ic.setDimensions(imgSize.width, imgSize.height);
    this.ic.setHints(ImageConsumer.TOPDOWNLEFTRIGHT |
                     ImageConsumer.COMPLETESCANLINES |
                     ImageConsumer.SINGLEFRAME);
    drawThread = new Thread(this, "Draw Fractal");
    killThread = false;
    drawThread.start();
  }

  public void requestTopDownLeftRightResend(ImageConsumer ic) {
   
  }

  public Cmplx translate(int x, int y) {
    return new Cmplx(
      tl.real() + (double)x / imgSize.width * (br.real() - tl.real()),
      tl.imag() + (double)y / imgSize.height * (br.imag() - tl.imag()));
  }

  public void run() {
    int scanLine[] = new int[imgSize.width];
    int i, j, clr, num;
    Cmplx pixel, z;

    for (j=0; j < imgSize.height; j+= step) {
      for (i=0; i < imgSize.width; i+= step) {
        pixel = translate(i, j);
        clr = pixelColour(pixel);
        if (clr == -1)
          for (num=0; num < step && i+num < imgSize.width; num++)
            scanLine[i + num] = 0;
        else
          for (num=0; num < step && i+num < imgSize.width; num++)
            scanLine[i + num] = (clr - 1) % 255 + 1;
        if (i%5 == 0) {
          drawThread.yield();
          if (killThread) {
            killThread = false;
            ic.imageComplete(ImageConsumer.STATICIMAGEDONE);
            return;
          }
        }
      }
      for (num=0; num < step && j+num < imgSize.height; num++)
        ic.setPixels(0, j+num, imgSize.width, 1,
                     cm, scanLine, 0, imgSize.width);
    }
    showStatus("·ÖÐÎÆµµÀ£ºwww.fractal.cn");
    ic.imageComplete(ImageConsumer.STATICIMAGEDONE);
  }

}

