//Julia集（分形频道：fractal.cn）2004
import java.awt.*;
import java.applet.*;

public class Julia extends Applet {

    double p, q;

    public void init() {
        setBackground(new Color(255,255,255));
    }

    //  Zn+1 = Zn^2 + C, C = const
    public int juliaZ(double x0,double y0) {
        double xk,yk;
        int i;
        for (i=0;i<47;i++) {

            xk=x0*x0-y0*y0+p;
            yk=2*x0*y0+q;

            if (xk*xk+yk*yk>4) return i;
            x0=xk;
            y0=yk;
        }
        return i;
    }

    // 绘制
    public void paint (Graphics g) {
        double reZ, imZ, ze0=0.0055; 
        int x, y;
        Color colJulia = new Color(100,0,100); // 设置颜色
        p = -0.65175;
        q = 0.41850;

        imZ=-1.5; 
        for (y=0;y<480;y++) {
            reZ=-1.5; 
            for (x=0;x<640;x++) {
                if (juliaZ(reZ,imZ)==47) {
                    g.setColor(colJulia);
                    g.drawLine(x,y,x,y);
                }
                reZ=reZ+ze0; 
            }
            imZ=imZ+ze0; 
        }
    }
}
