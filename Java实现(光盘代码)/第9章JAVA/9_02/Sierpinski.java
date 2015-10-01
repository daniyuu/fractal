//三维空间中的Sierpinski金字塔(分形频道：fractal.cn)2004
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.Font;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.applet.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.applet.Applet;
import com.sun.j3d.utils.applet.MainFrame; 
import java.util.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;


public class Sierpinski extends Applet {

	TransformGroup tg = new TransformGroup();
	BranchGroup newBG = new BranchGroup();
	TransformGroup objRotate = new TransformGroup();

	Vector v = new Vector();	// this one holds all the fractals after the calculation
	Random rnd = new Random();	// random number generator

	static int fractDepth = 4;		// recursive depth for fractals

	public BranchGroup createSceneGraph() {
		 // Create the root of the branch graph
	   BranchGroup objRoot = new BranchGroup();
	   objRoot.setCapability(BranchGroup.ALLOW_DETACH);
	   objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	   objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
	   objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);

	   newBG.setCapability(BranchGroup.ALLOW_DETACH);
	   newBG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	   newBG.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
	   newBG.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);

	   

		 Transform3D t3D = new Transform3D();

		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		objRotate.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		objRotate.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
	    objRotate.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);

/*		// create the alpha to trigger the animation frames
		Alpha alpha = new Alpha(-1, 100000);
		Moving mov = new Moving(alpha);
		mov.setSchedulingBounds(new BoundingSphere());
		objRotate.addChild(mov);
*/

		// cover all different angles in a box
		float bndry[][] = {
	        {-2f, -2f, -2f},
	        {-2f,  2f, -2f},
	        { 2f, -2f, -2f},
	        { 2f,  2f, -2f},
	        {-2f, -2f,  2f},
	        {-2f,  2f,  2f},
	        { 2f, -2f,  2f},
	        { 2f,  2f,  2f},
		};
	
		// build a wireframe box to aid visualisation
		LineArray la = new LineArray(24, LineArray.COORDINATES | LineArray.COLOR_3);
		la.setCoordinates(0, bndry[0]);
		la.setColor(0, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(1, bndry[1]);
		la.setColor(1, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(2, bndry[2]);
		la.setColor(2, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(3, bndry[3]);
		la.setColor(3, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(4, bndry[4]);
		la.setColor(4, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(5, bndry[5]);
		la.setColor(5, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(6, bndry[6]);
		la.setColor(6, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(7, bndry[7]);
		la.setColor(7, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(8, bndry[0]);
		la.setColor(8, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(9, bndry[4]);
		la.setColor(9, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(10, bndry[1]);
		la.setColor(10, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(11, bndry[5]);
		la.setColor(11, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(12, bndry[2]);
		la.setColor(12, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(13, bndry[6]);
		la.setColor(13, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(14, bndry[3]);
		la.setColor(14, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(15, bndry[7]);
		la.setColor(15, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(16, bndry[0]);
		la.setColor(16, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(17, bndry[2]);
		la.setColor(17, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(18, bndry[1]);
		la.setColor(18, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(19, bndry[3]);
		la.setColor(19, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(20, bndry[4]);
		la.setColor(20, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(21, bndry[6]);
		la.setColor(21, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(22, bndry[5]);
		la.setColor(22, new Color3f(0.4f, 0.4f, 0.4f));
		la.setCoordinates(23, bndry[7]);
		la.setColor(23, new Color3f(0.4f, 0.4f, 0.4f));

		Shape3D b = new Shape3D(la, new Appearance());
	//	objRotate.addChild(b);
		

	
		// add a pyramid at position 1, 1, 1


	//	Transform3D t3d = new Transform3D();
	//	t3d.set(new Vector3d(1.0, 1.0, 1.0), 0.5);
//		t3d.setType(TRANSFORM);
		
		tg.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		tg.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

	//	tg.setTransform(t3d);
		
		v.add(new Fract(10.0, 0));
		make();
		while(!v.isEmpty()) {
			tg.addChild(((Fract)v.firstElement()).getTransformGroup());
			v.remove(((Fract)v.firstElement()));
		}
//		tg.addChild(new Fract(1.0).getShape3D());
		objRotate.addChild(tg);

		MouseRotate myMouseRotate = new MouseRotate();
		myMouseRotate.setTransformGroup(objRotate);
		myMouseRotate.setSchedulingBounds(new BoundingSphere(new Point3d(), 100));
		objRotate.addChild(myMouseRotate);

		MouseTranslate myMouseTranslate = new MouseTranslate();
		myMouseTranslate.setTransformGroup(objRotate);
		myMouseTranslate.setSchedulingBounds(new BoundingSphere(new Point3d(), 100));
		objRotate.addChild(myMouseTranslate);

		MouseZoom myMouseZoom = new MouseZoom();
		myMouseZoom.setTransformGroup(objRotate);
		myMouseZoom.setSchedulingBounds(new BoundingSphere(new Point3d(), 100));
		objRotate.addChild(myMouseZoom);

		newBG.addChild(objRotate);


		objRoot.addChild(newBG);
		
		objRoot.compile();
		 return objRoot;

	 } // end of CreateSceneGraph method


	 public Sierpinski() { }

	//  The following allows this to be run as an application
	//  as well as an applet

	public static void main(String[] args) {
        if(args.length != 0) 
            fractDepth = new Integer(args[0]).intValue();
		Frame frame = new MainFrame(new Sierpinski(), 512, 512);
	} 

	BranchGroup scene = null;
	public void init() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config =
		   SimpleUniverse.getPreferredConfiguration();

		Canvas3D c = new Canvas3D(config);
		add("Center", c);

		// Create a simple scene and attach it to the virtual universe
		scene = createSceneGraph();

	   			
		SimpleUniverse u = new SimpleUniverse(c);
	
		// This will move the ViewPlatform back a bit so the
		// objects in the scene can be viewed.
		u.getViewingPlatform().setNominalViewingTransform();

		u.addBranchGraph(scene);
	}
  
  	public class Matrix {
		public double x1;
		public double x2;
		public double x3;
		public double y1;
		public double y2;
		public double y3;
		public double dx;
		public double dy;
		public double dz;
		
		public  Matrix(){}	
	}
	
	public class Fract {
		int depth;
		double size = 1;

		public Shape3D getShape3D() {

			TriangleArray ta = new TriangleArray(12, TriangleArray.COORDINATES 
												| TriangleArray.ALLOW_COLOR_WRITE 
												| TriangleArray.COLOR_3);

			ta.setCoordinates(0, p1);
			ta.setCoordinates(1, p2);
			ta.setCoordinates(2, p3);
	
			ta.setCoordinates(3, p4);
			ta.setCoordinates(4, p1);
			ta.setCoordinates(5, p3);
			
			ta.setCoordinates(6, p2);
			ta.setCoordinates(7, p4);
			ta.setCoordinates(8, p3);
	
			ta.setCoordinates(9, p1);
			ta.setCoordinates(10, p4);
			ta.setCoordinates(11, p2);
	
			// set the color for the different walls
			ta.setColor(0, new Color3f(1f, 0f, 0f));
//			ta.setColor(1, new Color3f(1f, 0f, 0f));
			ta.setColor(2, new Color3f(1f, 0f, 0f));
			ta.setColor(3, new Color3f(1f, 0f, 0f));
//			ta.setColor(4, new Color3f(1f, 0f, 0f));
//			ta.setColor(5, new Color3f(1f, 0f, 0f));
			ta.setColor(6, new Color3f(1f, 0f, 0f));
//			ta.setColor(7, new Color3f(1f, 0f, 0f));
			ta.setColor(8, new Color3f(1f, 0f, 0f));
//			ta.setColor(9, new Color3f(1f, 0f, 0f));
			ta.setColor(10, new Color3f(1f, 0f, 0f));
			ta.setColor(11, new Color3f(1f, 0f, 0f));

			Shape3D s3d = new Shape3D(ta, new Appearance());

			return s3d;
		} 

		public TransformGroup getTransformGroup() {

			TransformGroup tg2 = new TransformGroup();

			//t3d.set(v3d);

            if(t3d != null)
    			tg2.setTransform(t3d);
			tg2.addChild(getShape3D());
			return tg2;
		}
		
		public double[] p1;
		public double[] p2;
		public double[] p3;
		public double[] p4;
		public Vector3d v3d = null;
		public Transform3D t3d = null;

		public Fract(double s, int i){
			size = s;
			depth = i;
			p1 =  new double[]{-1*size, -1*size, 1*size};
			p2 =  new double[]{ 1*size, -1*size, 1*size}; 
			p3 =  new double[]{ 0*size,  1*size, 0*size};
			p4 =  new double[]{ 0*size, -1.25*size, -1.25*size }; 
			v3d=  new Vector3d();
		}
	}

	public void make() {
		//create all the fractal objects, store them in 'v'

		for (int i = 0; i < fractDepth; i++) {
			while(((Fract)v.firstElement()).depth < i) {
				v.add(makeFract((Fract)v.firstElement(), i, 1));
				v.add(makeFract((Fract)v.firstElement(), i, 2));
				v.add(makeFract((Fract)v.firstElement(), i, 3));
				v.add(makeFract((Fract)v.firstElement(), i, 4));

				v.remove(((Fract)v.firstElement()));
			}
		}
	}

	public Fract makeFract(Fract fr, int i, int j) {
		Fract ff = new Fract(fr.size/2, i+1);
		switch (j) {
			case 1: //ff.v3d = new Vector3d(fr.p1[0], fr.p1[1], fr.p1[2]);
					ff.v3d.x = fr.p1[0]/2 + fr.v3d.x;
					ff.v3d.y = fr.p1[1]/2 + fr.v3d.y;
					ff.v3d.z = fr.p1[2]/2 + fr.v3d.z;
					break;
			case 2: ff.v3d = new Vector3d(fr.p2[0],fr.p2[1], fr.p2[2]);
                 	ff.v3d.x = fr.p2[0]/2 + fr.v3d.x;
					ff.v3d.y = fr.p2[1]/2 + fr.v3d.y;
					ff.v3d.z = fr.p2[2]/2 + fr.v3d.z;
					break;
			case 3: ff.v3d = new Vector3d(fr.p3[0],fr.p3[1], fr.p3[2]);
                 	ff.v3d.x = fr.p3[0]/2 + fr.v3d.x;
					ff.v3d.y = fr.p3[1]/2 + fr.v3d.y;
					ff.v3d.z = fr.p3[2]/2 + fr.v3d.z;
					break;
			case 4: ff.v3d = new Vector3d(fr.p4[0],fr.p4[1], fr.p4[2]);
                	ff.v3d.x = fr.p4[0]/2 + fr.v3d.x;
					ff.v3d.y = fr.p4[1]/2 + fr.v3d.y;
					ff.v3d.z = fr.p4[2]/2 + fr.v3d.z;
					break;
		}
		ff.t3d = new Transform3D();
		ff.t3d.set(ff.v3d);
		return ff;
	}
  

}


