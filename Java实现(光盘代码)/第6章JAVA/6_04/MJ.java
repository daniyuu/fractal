//作为Julia字典的Mandelbrot集(分形频道：fractal.cn)2004
import java.applet.*;

import java.applet.*;
import java.awt.*;


public class MJ extends Applet implements Runnable
{
	// 内部类：复数运算
	public class ComplexPoint
	{
		// 默认集合
		public ComplexPoint()
		{
			set(0,0);
		};
		
		// 光栅上的集合
		public ComplexPoint(double set_real, double set_imag)
		{
			set(set_real, set_imag);
		};
		
		// 集合的新值
		public void set(double set_real, double set_imag)
		{
			real = set_real;
			imag = set_imag;
		};

		// 集合等于其他的复数点
		public void set(ComplexPoint p)
		{
			real = p.real;
			imag = p.imag;
		};
		
		// 加其他复数点
		public void add(ComplexPoint p)
		{
			real += p.real;
			imag += p.imag;
		};
		
		// 减其他复数点
		public void subtract(ComplexPoint p)
		{
			real -= p.real;
			imag -= p.imag;
		};
		
		// 相对于起点镜像复数点
		public void negate()
		{
			real = -real;
			imag = -imag;
		};

		
		// 返回起点到该复数点的距离
		public double distance_squared()
		{
			return real*real + imag*imag;
		};
		
		// 替换该复数点平方
		public void square()
		{
			double new_real = real*real - imag*imag;
			double new_imag = 2*real*imag;
			real = new_real;
			imag = new_imag;
		};
		
		// 替换复数点的平方根
		public void square_root()
		{
			toPolar();
			
			radius = Math.sqrt(radius);
			angle /= 2;
			
			toCartesian();
		};

		// 复数的实部和虚部
		public double real, imag;
		
		// 从中心向两端画
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

		// 中心返回到迪卡尔坐标系
		private void toCartesian()
		{
			real = radius * Math.cos(angle);
			imag = radius * Math.sin(angle);
		};

		// 存储极坐标的内部变量
		private double radius, angle;
	};
	
	// 在M集合J集中每个点的最大迭代值
	final int MAX_ITERATIONS = 200;

	// 为了快速出略地绘制J集，丢弃了许多初始的迭代
	final int JULIASKETCH_DISCARD = 10;
	
	// 许多迭代之后，快速粗略的J集被较慢的、完整的和更加华丽的算法所取代
	final int JULIASKETCH_REFINE = 10000;
	
	// 显示M集的一部分
	final double REAL_LO = -0.50, REAL_HI = 2.00;
	final double IMAG_LO = -1.25, IMAG_HI = 1.25;

	// J集的最大尺寸（复数的极距）
	final double JULIASIZE = 4.0;
	
	final int CROSSHAIR_SIZE = 10; // 十字线尺寸
	
	int SIZE = 250; 
	
	ComplexPoint step = new ComplexPoint();

	// 用于迭代公式上的变量
	ComplexPoint z = new ComplexPoint();
	ComplexPoint c = new ComplexPoint();
	ComplexPoint p = new ComplexPoint();
	ComplexPoint u = new ComplexPoint();
	
	Point crosshair = new Point(); // 交叉点位置

	boolean restart_julia;

	Color palette[] = new Color[MAX_ITERATIONS];
	
	Image mandel;	// M集的外部
	Image julia;	// J集的外部

	Graphics gm;
	Graphics gj;
	Graphics screen;
		
	Thread juliathread = null;

	public String getAppletInfo()
	{
		return "";
	}

	public void init() // Applet初始值
	{
		// applet参数的实部
		String param = getParameter("size");
		if (param != null)
		{
			SIZE = Integer.parseInt(param);
		}

		// M集点的计算值
		step.real = (REAL_HI - REAL_LO)/SIZE;
		step.imag = (IMAG_HI - IMAG_LO)/SIZE;

		// 十字线的起点位置
		crosshair.setLocation((int)(-REAL_LO / step.real),
							  (int)(-IMAG_LO / step.imag));

		// 设置applet绘制范围
		setBackground(Color.black);
		resize(2*SIZE, SIZE);

		// 创建M集和J集的屏幕位图
		mandel = createImage(SIZE, SIZE);
		julia  = createImage(SIZE, SIZE);

		// 设置屏幕位图和屏幕位置区域句柄
		gm = mandel.getGraphics();	initBitmap(gm);
		gj = julia.getGraphics();	initBitmap(gj);
		screen = getGraphics();
		
		// 创建带有最大个数的平滑颜色调色板
		double colfactor = 255.0 / Math.log((double)(MAX_ITERATIONS + 1));
		int level;
		for (int n = 0; n < MAX_ITERATIONS; n++)
		{
			level = (int)(colfactor * Math.log(n + 1));
			palette[n] = new Color(level, level, 255-level);
		}
	}

	public void start() // 进入线程
	{
		if (juliathread == null)
		{
			juliathread = new Thread(this);
			juliathread.start();
		}
	}
	
	public void stop() // 退出线程
	{
		if (juliathread != null)
		{
			juliathread.stop();
			juliathread = null;
		}
	}

	public void run() // 线程进入点，从分解对象
	{
		// 构建并绘制M集图像
		drawColoredMandel();

		// 进入J集的绘制循环
		drawJuliaInteractively();
	}

	// 随时调用需要绘制的applet部分
	// 不考虑我们不需要的
	public void update(Graphics g)
	{
		paint(g);
	}

	// 调用修正Called from update()...
	public void paint(Graphics g)
	{
		// 在屏幕上粘贴被存储的图像，且应用十字线
		g.drawImage(julia,  SIZE, 0, this);
		g.drawImage(mandel, 0,    0, this);
		drawCrosshairs(g);
	}

	// 帮助函数
	void drawPixel(Graphics g, int xx, int yy)
	{
		g.drawLine(xx, yy, xx, yy);
	}

	// 帮助函数
	void initBitmap(Graphics g)
	{
		
		g.setColor(Color.black); g.fillRect(1,1, SIZE-2,SIZE-2);
		g.setColor(Color.white); g.drawRect(0,0, SIZE-1,SIZE-1);
		repaint(); 
	}
	
	// 在内存中和屏幕上绘制M集
	void drawJuliaInteractively()
	{
		final double JULIASCALE = (double)SIZE / JULIASIZE;
		final int    CENTRE_X   = SIZE + SIZE/2;
		final int    CENTRE_Y   = SIZE/2;

		int iter = 0; // 用于迭代计算Counter for iterations

		// 当我们进入循环时确定参数已经初始化		
		restart_julia = true;
		
		// 循环，用快速和出略的方法绘制J集
		// 先绘制，然后用完整算法计算
		while (true)
		{
			// 当时十字线移动时，重新启动J集标记
			if (restart_julia)
			{
				initBitmap(gj);

				// 用新的十字线位置修正c值
				c.set(REAL_LO + step.real * crosshair.x,
					  IMAG_LO + step.imag * crosshair.y);

				// 重新安排J集的初始点
				iter = 0;
				z.set(1,1);

				// 重新安排标记Reset flag
				restart_julia = false;
			}

			// 执行一个“粗略地”J集迭代公式
			// 这里Here, the "z := z^2 - c" formula is traced "backwards" using
			// its inverse "z := sqrt(z + c)". The calculation is separated
			// into two steps: "z := z + c" followed by z := sqrt(z).
			z.add(c); z.square_root();

			// 然而有实际两个可能的解
			// 开根号等式，我们随机选择其中之一
			if (Math.random() < 0.50)
			{
				z.negate(); // 50%选择负数解
			}
			
			// 早期的迭代被取消
			if (iter > JULIASKETCH_DISCARD)
			{
				// 许多迭代之后，突然制作精确的J集
				// 精确的J集
				if (iter == JULIASKETCH_REFINE)
				{
					drawColoredJulia();
				}
			
				// 在着色的图像被画之后迭代停止
				
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
	
	// 在内存中画Julia集，并稍后在屏幕上显示
	void drawColoredJulia()
	{
		final double STEPSIZE = JULIASIZE / (double)SIZE;
		
		int x, y;		// 与之匹配的象素点
		int iter;		// 计算迭代次数

		
		
		u.imag = -JULIASIZE / 2.0 + STEPSIZE;
		for (y = 1; y < SIZE-1; y++)
		{
			u.real = -JULIASIZE / 2.0 + STEPSIZE;
			for (x = 1; x < SIZE-1; x++)
			{
				//在当前的点(u)处开始迭代
				p.set(u);
				
				for (iter = 0; iter < MAX_ITERATIONS; iter++)
				{
					// Check if z is growing towards infinity: "|z| > 2" ==> "|z|^2 > 4"
					if (p.distance_squared() > 4.0)
					{
						// 这是Julia集外面的点.
						// 将这些点画到内存中，依靠迭代次数着色
					
						gj.setColor(palette[iter]);	//screen.setColor(palette[iter]);
						drawPixel(gj, x, y);	       //drawPixel(screen, SIZE + x, y);
						break;  // 继续画下一个点
					}
					
					// 计算 "z := z^2 - c"
					p.square(); p.subtract(c);
				}
				// Julia即内部的点没有被画出，保持黑色的背景
				
				// 如果使用者移动将异常中断
				if (restart_julia) return;
					
				u.real += STEPSIZE;
			}
			u.imag += STEPSIZE;
		}
		repaint(); // 更新屏幕
	}

	// Draw an image of the Mandelbrot set in memory and on screen
	void drawColoredMandel()
	{
		int x, y;		// 与之匹配的象素点
		int y_mirrored;	// 镜像Y坐标
		int iter;		// 计算迭代次数

		
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
						// 这是Julia集外面的点.
						// 将这些点画到内存中，依靠迭代次数着色
						gm.setColor(palette[iter]);		screen.setColor(palette[iter]);
						drawPixel(gm, x, y);			drawPixel(screen, x, y);
						drawPixel(gm, x, y_mirrored);	drawPixel(screen, x, y_mirrored);
						break; // 继续画下一个点
					}
					
					//计算 "z := z^2 - c"
					z.square(); z.subtract(c);
				}
				// Mandelbrot即内部的点没有被画出，保持黑色的背景
				
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
		// 检查鼠标指针是否在Mandelbrot集内部
		if (x >= 0 && x < SIZE && y >= 0 && y < SIZE)
		{
			// 修正十字线位置
			crosshair.x = x;
			crosshair.y = y;

			// Julia集的将被函数drawJuliaInteractively()的下一次迭代所计算 
			restart_julia = true;
		}
		return true;
	}

	// 调用鼠标点击事件
	public boolean mouseDown(Event evt, int x, int y)
	{
		// 一些鼠标拖动动作
		return mouseDrag(evt, x, y);
	}
}


