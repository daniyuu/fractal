import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class c extends Applet implements ActionListener {

	int ordre = 1;

	public void init() {
              setBackground(new Color(255,255,255));//设置背景色为白色
        	Button prec = new Button("减少深度");
		prec.addActionListener(this);
		add(prec);
		Button suivant = new Button("增加深度");
		suivant.addActionListener(this);
		add(suivant);
	}

	


	private void fractal2(Graphics g, int nb, float x1, float y1, float x2, float y2) {
		if(nb > 0) {
			float x3 = (x1 + y1 + x2 - y2)/2;
			float y3 = (x2 + y2 + y1 - x1)/2;
			fractal2(g, nb-1, x1, y1, x3, y3);
			fractal2(g, nb-1, x3, y3, x2, y2);
                    g.drawLine(Math.round(x1), Math.round(y1), Math.round(x2), Math.round(y2));
		}
		
	}


	public void paint(Graphics g) {
                int largeur = getSize().width; 
                int hauteur = getSize().height; 
                fractal2(g, ordre, (float) largeur/3, (float) hauteur/3, (float) (2*largeur)/3, (float) hauteur/3);
		g.drawString("深度 = "+ordre,500,20);
        }

	public void actionPerformed(ActionEvent ev) {
		String label = ev.getActionCommand();
                if(label.equals("减少深度")) if(ordre > 0) ordre--;
                if(label.equals("增加深度")) ordre++;
		repaint(); 
	}

}
