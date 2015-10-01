//三维空间中的Cantor尘(分形频道：fractal.cn)2004
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


public class Boxes extends Applet {

	TransformGroup tg = null;
	BranchGroup newBG = null;
	TransformGroup objRotate = null;
	Appearance ap = null;
	Material mat = null;

       
	Vector v = new Vector();	//这是一个存放计算后的分形的存储器
	Random rnd = new Random();	//  随机数发生器

	static int fractDepth = 7;		//  分形的递归深度

	public BranchGroup createSceneGraph() {
		 // 构造一个分支生长的根
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


		tg.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		tg.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		
		v.add(new Fract(10.0f, 0));
		make();
		while(!v.isEmpty()) {
			tg.addChild(((Fract)v.firstElement()).getTransformGroup());
			v.remove(((Fract)v.firstElement()));
		}

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

		DirectionalLight al = new DirectionalLight(true, new Color3f(1f, 0f, 0f), new Vector3f(1f, -1f, -1f));
		al.setInfluencingBounds(new BoundingSphere(new Point3d(0d, 0d, 0d), 300d));
		Transform3D tr = new Transform3D();
		tr.set(new Vector3d(-30d, 30d, 30d)); 
		TransformGroup trg = new TransformGroup(tr);
		trg.addChild(al);
		objRoot.addChild(trg);

		
		objRoot.compile();
		 return objRoot;

	 } // end of CreateSceneGraph method


	 public Boxes() { }

	public static void main(String[] args) {
		if(args.length != 0) 
			fractDepth = new Integer(args[0]).intValue();
		Frame frame = new MainFrame(new Boxes(), 512, 512);
	} 

	BranchGroup scene = null;
	SimpleUniverse u;
	public void init() {

		initVars(); // call initialization of global elements

		setLayout(new BorderLayout());
		GraphicsConfiguration config =
		   SimpleUniverse.getPreferredConfiguration();

		Canvas3D c = new Canvas3D(config);
		add("Center", c);

		//  构造一个隶属于虚拟空间的简单场景
		scene = createSceneGraph();

	   			
		u = new SimpleUniverse(c);
	
		//移动观察平台使物体能够被观察
		u.getViewingPlatform().setNominalViewingTransform();

		u.addBranchGraph(scene);
	}
  
  	public void initVars() {
		tg = new TransformGroup();
		newBG = new BranchGroup();
		objRotate = new TransformGroup();
		ap = new Appearance();
		mat = new Material();
		mat.setLightingEnable(true);
		mat.setSpecularColor(new Color3f(0.7f, 0.7f, 0.7f));
		mat.setShininess(0.7f);
		ap.setMaterial(mat);
	}
	
	public class Fract {
		int depth;
		float size = 1;

		public com.sun.j3d.utils.geometry.Box getShape3D() {

			Box b = new Box(size, size, size, Box.GENERATE_NORMALS, ap);
		
			return b;
		} 

		public TransformGroup getTransformGroup() {

			TransformGroup tg2 = new TransformGroup();

			//t3d.set(v3d);

			if(t3d != null)
				tg2.setTransform(t3d);
			tg2.addChild(getShape3D());
			return tg2;
		}
		
		public Vector3d v3d = null;
		public Transform3D t3d = null;

		public Fract(float s, int i){
			size = s;
			depth = i;
			v3d=  new Vector3d();
		}
	}

	public void make() {
		//构造所有的分形物体，并存储到’v’中

		for (int i = 0; i < fractDepth; i++) {
			while(((Fract)v.firstElement()).depth < i) {
				v.add(makeFract((Fract)v.firstElement(), i, 1));
				v.add(makeFract((Fract)v.firstElement(), i, 2));
				v.add(makeFract((Fract)v.firstElement(), i, 3));
				v.add(makeFract((Fract)v.firstElement(), i, 4));
				v.add(makeFract((Fract)v.firstElement(), i, 5));
				v.add(makeFract((Fract)v.firstElement(), i, 6));
				v.add(makeFract((Fract)v.firstElement(), i, 7));
				v.add(makeFract((Fract)v.firstElement(), i, 8));

				v.remove(((Fract)v.firstElement()));
			}
		}
	}

	public Fract makeFract(Fract fr, int i, int j) {
		Fract ff = new Fract(fr.size/3, i+1);
		switch (j) {
			case 1: 
					ff.v3d.x = fr.v3d.x - fr.size/2;
					ff.v3d.y = fr.v3d.y - fr.size/2;
					ff.v3d.z = fr.v3d.z + fr.size/2;
					break;
			case 2:
				 	ff.v3d.x = fr.v3d.x + fr.size/2;
					ff.v3d.y = fr.v3d.y - fr.size/2;
					ff.v3d.z = fr.v3d.z + fr.size/2;
					break;
			case 3:
				 	ff.v3d.x = fr.v3d.x - fr.size/2;
					ff.v3d.y = fr.v3d.y + fr.size/2;
					ff.v3d.z = fr.v3d.z + fr.size/2;
					break;
			case 4:
					ff.v3d.x = fr.v3d.x + fr.size/2;
					ff.v3d.y = fr.v3d.y + fr.size/2;
					ff.v3d.z = fr.v3d.z + fr.size/2;
					break;
			case 5: 
					ff.v3d.x = fr.v3d.x - fr.size/2;
					ff.v3d.y = fr.v3d.y - fr.size/2;
					ff.v3d.z = fr.v3d.z - fr.size/2;
					break;
			case 6: 
					ff.v3d.x = fr.v3d.x - fr.size/2;
					ff.v3d.y = fr.v3d.y + fr.size/2;
					ff.v3d.z = fr.v3d.z - fr.size/2;
					break;
			case 7: 
					ff.v3d.x = fr.v3d.x + fr.size/2;
					ff.v3d.y = fr.v3d.y - fr.size/2;
					ff.v3d.z = fr.v3d.z - fr.size/2;
					break;
			case 8: 
					ff.v3d.x = fr.v3d.x + fr.size/2;
					ff.v3d.y = fr.v3d.y + fr.size/2;
					ff.v3d.z = fr.v3d.z - fr.size/2;
					break;
		}
		ff.t3d = new Transform3D();
		ff.t3d.set(ff.v3d);
		return ff;
	}
  


}


