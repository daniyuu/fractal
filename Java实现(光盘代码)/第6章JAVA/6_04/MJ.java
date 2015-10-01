//��ΪJulia�ֵ��Mandelbrot��(����Ƶ����fractal.cn)2004
import java.applet.*;

import java.applet.*;
import java.awt.*;


public class MJ extends Applet implements Runnable
{
	// �ڲ��ࣺ��������
	public class ComplexPoint
	{
		// Ĭ�ϼ���
		public ComplexPoint()
		{
			set(0,0);
		};
		
		// ��դ�ϵļ���
		public ComplexPoint(double set_real, double set_imag)
		{
			set(set_real, set_imag);
		};
		
		// ���ϵ���ֵ
		public void set(double set_real, double set_imag)
		{
			real = set_real;
			imag = set_imag;
		};

		// ���ϵ��������ĸ�����
		public void set(ComplexPoint p)
		{
			real = p.real;
			imag = p.imag;
		};
		
		// ������������
		public void add(ComplexPoint p)
		{
			real += p.real;
			imag += p.imag;
		};
		
		// ������������
		public void subtract(ComplexPoint p)
		{
			real -= p.real;
			imag -= p.imag;
		};
		
		// �������㾵������
		public void negate()
		{
			real = -real;
			imag = -imag;
		};

		
		// ������㵽�ø�����ľ���
		public double distance_squared()
		{
			return real*real + imag*imag;
		};
		
		// �滻�ø�����ƽ��
		public void square()
		{
			double new_real = real*real - imag*imag;
			double new_imag = 2*real*imag;
			real = new_real;
			imag = new_imag;
		};
		
		// �滻�������ƽ����
		public void square_root()
		{
			toPolar();
			
			radius = Math.sqrt(radius);
			angle /= 2;
			
			toCartesian();
		};

		// ������ʵ�����鲿
		public double real, imag;
		
		// �����������˻�
		private void toPolar()
		{
			radius = Math.sqrt(real*real + imag*imag);
			
			if (real > 0)
				angle = Math.atan(imag/real);
			else if (real < 0)
				angle = Math.PI + Math.atan(imag/real);
			else
				angle = Math.PI;
		};

		// ���ķ��ص��Ͽ�������ϵ
		private void toCartesian()
		{
			real = radius * Math.cos(angle);
			imag = radius * Math.sin(angle);
		};

		// �洢��������ڲ�����
		private double radius, angle;
	};
	
	// ��M����J����ÿ�����������ֵ
	final int MAX_ITERATIONS = 200;

	// Ϊ�˿��ٳ��Եػ���J��������������ʼ�ĵ���
	final int JULIASKETCH_DISCARD = 10;
	
	// ������֮�󣬿��ٴ��Ե�J���������ġ������ĺ͸��ӻ������㷨��ȡ��
	final int JULIASKETCH_REFINE = 10000;
	
	// ��ʾM����һ����
	final double REAL_LO = -0.50, REAL_HI = 2.00;
	final double IMAG_LO = -1.25, IMAG_HI = 1.25;

	// J�������ߴ磨�����ļ��ࣩ
	final double JULIASIZE = 4.0;
	
	final int CROSSHAIR_SIZE = 10; // ʮ���߳ߴ�
	
	int SIZE = 250; 
	
	ComplexPoint step = new ComplexPoint();

	// ���ڵ�����ʽ�ϵı���
	ComplexPoint z = new ComplexPoint();
	ComplexPoint c = new ComplexPoint();
	ComplexPoint p = new ComplexPoint();
	ComplexPoint u = new ComplexPoint();
	
	Point crosshair = new Point(); // �����λ��

	boolean restart_julia;

	Color palette[] = new Color[MAX_ITERATIONS];
	
	Image mandel;	// M�����ⲿ
	Image julia;	// J�����ⲿ

	Graphics gm;
	Graphics gj;
	Graphics screen;
		
	Thread juliathread = null;

	public String getAppletInfo()
	{
		return "";
	}

	public void init() // Applet��ʼֵ
	{
		// applet������ʵ��
		String param = getParameter("size");
		if (param != null)
		{
			SIZE = Integer.parseInt(param);
		}

		// M����ļ���ֵ
		step.real = (REAL_HI - REAL_LO)/SIZE;
		step.imag = (IMAG_HI - IMAG_LO)/SIZE;

		// ʮ���ߵ����λ��
		crosshair.setLocation((int)(-REAL_LO / step.real),
							  (int)(-IMAG_LO / step.imag));

		// ����applet���Ʒ�Χ
		setBackground(Color.black);
		resize(2*SIZE, SIZE);

		// ����M����J������Ļλͼ
		mandel = createImage(SIZE, SIZE);
		julia  = createImage(SIZE, SIZE);

		// ������Ļλͼ����Ļλ��������
		gm = mandel.getGraphics();	initBitmap(gm);
		gj = julia.getGraphics();	initBitmap(gj);
		screen = getGraphics();
		
		// ����������������ƽ����ɫ��ɫ��
		double colfactor = 255.0 / Math.log((double)(MAX_ITERATIONS + 1));
		int level;
		for (int n = 0; n < MAX_ITERATIONS; n++)
		{
			level = (int)(colfactor * Math.log(n + 1));
			palette[n] = new Color(level, level, 255-level);
		}
	}

	public void start() // �����߳�
	{
		if (juliathread == null)
		{
			juliathread = new Thread(this);
			juliathread.start();
		}
	}
	
	public void stop() // �˳��߳�
	{
		if (juliathread != null)
		{
			juliathread.stop();
			juliathread = null;
		}
	}

	public void run() // �߳̽���㣬�ӷֽ����
	{
		// ����������M��ͼ��
		drawColoredMandel();

		// ����J���Ļ���ѭ��
		drawJuliaInteractively();
	}

	// ��ʱ������Ҫ���Ƶ�applet����
	// ���������ǲ���Ҫ��
	public void update(Graphics g)
	{
		paint(g);
	}

	// ��������Called from update()...
	public void paint(Graphics g)
	{
		// ����Ļ��ճ�����洢��ͼ����Ӧ��ʮ����
		g.drawImage(julia,  SIZE, 0, this);
		g.drawImage(mandel, 0,    0, this);
		drawCrosshairs(g);
	}

	// ��������
	void drawPixel(Graphics g, int xx, int yy)
	{
		g.drawLine(xx, yy, xx, yy);
	}

	// ��������
	void initBitmap(Graphics g)
	{
		
		g.setColor(Color.black); g.fillRect(1,1, SIZE-2,SIZE-2);
		g.setColor(Color.white); g.drawRect(0,0, SIZE-1,SIZE-1);
		repaint(); 
	}
	
	// ���ڴ��к���Ļ�ϻ���M��
	void drawJuliaInteractively()
	{
		final double JULIASCALE = (double)SIZE / JULIASIZE;
		final int    CENTRE_X   = SIZE + SIZE/2;
		final int    CENTRE_Y   = SIZE/2;

		int iter = 0; // ���ڵ�������Counter for iterations

		// �����ǽ���ѭ��ʱȷ�������Ѿ���ʼ��		
		restart_julia = true;
		
		// ѭ�����ÿ��ٺͳ��Եķ�������J��
		// �Ȼ��ƣ�Ȼ���������㷨����
		while (true)
		{
			// ��ʱʮ�����ƶ�ʱ����������J�����
			if (restart_julia)
			{
				initBitmap(gj);

				// ���µ�ʮ����λ������cֵ
				c.set(REAL_LO + step.real * crosshair.x,
					  IMAG_LO + step.imag * crosshair.y);

				// ���°���J���ĳ�ʼ��
				iter = 0;
				z.set(1,1);

				// ���°��ű��Reset flag
				restart_julia = false;
			}

			// ִ��һ�������Եء�J��������ʽ
			// ����Here, the "z := z^2 - c" formula is traced "backwards" using
			// its inverse "z := sqrt(z + c)". The calculation is separated
			// into two steps: "z := z + c" followed by z := sqrt(z).
			z.add(c); z.square_root();

			// Ȼ����ʵ���������ܵĽ�
			// �����ŵ�ʽ���������ѡ������֮һ
			if (Math.random() < 0.50)
			{
				z.negate(); // 50%ѡ������
			}
			
			// ���ڵĵ�����ȡ��
			if (iter > JULIASKETCH_DISCARD)
			{
				// ������֮��ͻȻ������ȷ��J��
				// ��ȷ��J��
				if (iter == JULIASKETCH_REFINE)
				{
					drawColoredJulia();
				}
			
				// ����ɫ��ͼ�񱻻�֮�����ֹͣ
				
				if (iter < JULIASKETCH_REFINE)
				{
					// Draw on screen. Please note: for each iteration, both of
					// the possible (+/-) solutions are drawn!
					screen.setColor(Color.yellow);
					drawPixel(screen, CENTRE_X + (int)(JULIASCALE*z.real),
							          CENTRE_Y + (int)(JULIASCALE*z.imag));
					drawPixel(screen, CENTRE_X - (int)(JULIASCALE*z.real),
							          CENTRE_Y - (int)(JULIASCALE*z.imag));
				}
			}
			
			// Increase iteration counter
			iter++;
		}
	}
	
	// ���ڴ��л�Julia�������Ժ�����Ļ����ʾ
	void drawColoredJulia()
	{
		final double STEPSIZE = JULIASIZE / (double)SIZE;
		
		int x, y;		// ��֮ƥ������ص�
		int iter;		// �����������

		
		
		u.imag = -JULIASIZE / 2.0 + STEPSIZE;
		for (y = 1; y < SIZE-1; y++)
		{
			u.real = -JULIASIZE / 2.0 + STEPSIZE;
			for (x = 1; x < SIZE-1; x++)
			{
				//�ڵ�ǰ�ĵ�(u)����ʼ����
				p.set(u);
				
				for (iter = 0; iter < MAX_ITERATIONS; iter++)
				{
					// Check if z is growing towards infinity: "|z| > 2" ==> "|z|^2 > 4"
					if (p.distance_squared() > 4.0)
					{
						// ����Julia������ĵ�.
						// ����Щ�㻭���ڴ��У���������������ɫ
					
						gj.setColor(palette[iter]);	//screen.setColor(palette[iter]);
						drawPixel(gj, x, y);	       //drawPixel(screen, SIZE + x, y);
						break;  // ��������һ����
					}
					
					// ���� "z := z^2 - c"
					p.square(); p.subtract(c);
				}
				// Julia���ڲ��ĵ�û�б����������ֺ�ɫ�ı���
				
				// ���ʹ�����ƶ����쳣�ж�
				if (restart_julia) return;
					
				u.real += STEPSIZE;
			}
			u.imag += STEPSIZE;
		}
		repaint(); // ������Ļ
	}

	// Draw an image of the Mandelbrot set in memory and on screen
	void drawColoredMandel()
	{
		int x, y;		// ��֮ƥ������ص�
		int y_mirrored;	// ����Y����
		int iter;		// �����������

		
		c.imag = IMAG_LO + step.imag;
		for (y = 1; y < SIZE/2; y++)
		{
			y_mirrored = SIZE - 1 - y;
			
			c.real = REAL_LO + step.real;
			for (x = 1; x < SIZE-1; x++)
			{
				// Start iterating at (0,0)
				z.set(0,0);
				
				for (iter = 0; iter < MAX_ITERATIONS; iter++)
				{
					// Check if z is growing towards infinity: "|z| > 2" ==> "|z|^2 > 4"
					if (z.distance_squared() > 4.0)
					{
						// ����Julia������ĵ�.
						// ����Щ�㻭���ڴ��У���������������ɫ
						gm.setColor(palette[iter]);		screen.setColor(palette[iter]);
						drawPixel(gm, x, y);			drawPixel(screen, x, y);
						drawPixel(gm, x, y_mirrored);	drawPixel(screen, x, y_mirrored);
						break; // ��������һ����
					}
					
					//���� "z := z^2 - c"
					z.square(); z.subtract(c);
				}
				// Mandelbrot���ڲ��ĵ�û�б����������ֺ�ɫ�ı���
				
				c.real += step.real;
			}
			c.imag += step.imag;
		}
	}

	// Draw crosshairs over the Mandelbrot image using XOR mode for simple animation
	void drawCrosshairs(Graphics g)
	{
		g.setXORMode(Color.white);
		
		g.drawLine(crosshair.x - CROSSHAIR_SIZE, crosshair.y,
				   crosshair.x + CROSSHAIR_SIZE, crosshair.y);
		g.drawLine(crosshair.x, crosshair.y - CROSSHAIR_SIZE,
				   crosshair.x, crosshair.y + CROSSHAIR_SIZE);
		
		g.setPaintMode();
	}

	// Called when mouse pointer is moving while mouse button is being pressed
	public boolean mouseDrag(Event evt, int x, int y)
	{
		// ������ָ���Ƿ���Mandelbrot���ڲ�
		if (x >= 0 && x < SIZE && y >= 0 && y < SIZE)
		{
			// ����ʮ����λ��
			crosshair.x = x;
			crosshair.y = y;

			// Julia���Ľ�������drawJuliaInteractively()����һ�ε��������� 
			restart_julia = true;
		}
		return true;
	}

	// ����������¼�
	public boolean mouseDown(Event evt, int x, int y)
	{
		// һЩ����϶�����
		return mouseDrag(evt, x, y);
	}
}


