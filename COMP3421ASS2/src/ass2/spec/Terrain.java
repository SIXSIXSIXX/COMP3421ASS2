package ass2.spec;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;



/**
 * COMMENT: Comment HeightMap 
 *
 * @author malcolmr
 */
public class Terrain {

    private Dimension mySize;
    private double[][] myAltitude;
    private List<Tree> myTrees;
    private List<Road> myRoads;
    private List<Portal> myPortals;
    private float[] mySunlight;
    private MyTexture myTexture[] = new MyTexture[1];



	public MyTexture[] getMyTexture() {
		return myTexture;
	}

	public void setMyTexture(MyTexture[] myTexture) {
		this.myTexture = myTexture;
	}

	/**
     * Create a new terrain
     *
     * @param width The number of vertices in the x-direction
     * @param depth The number of vertices in the z-direction
     */
    public Terrain(int width, int depth) {
        mySize = new Dimension(width, depth);
        myAltitude = new double[width][depth];
        myTrees = new ArrayList<Tree>();
        myRoads = new ArrayList<Road>();
        myPortals  = new ArrayList<Portal>();
        mySunlight = new float[3];
        
    }
    
    public Terrain(Dimension size) {
        this(size.width, size.height);
    }

    public Dimension size() {
        return mySize;
    }

    public List<Tree> trees() {
        return myTrees;
    }

    public List<Road> roads() {
        return myRoads;
    }

    public float[] getSunlight() {
        return mySunlight;
    }

    /**
     * Set the sunlight direction. 
     * 
     * Note: the sun should be treated as a directional light, without a position
     * 
     * @param dx
     * @param dy
     * @param dz
     */
    public void setSunlightDir(float dx, float dy, float dz) {
        mySunlight[0] = dx;
        mySunlight[1] = dy;
        mySunlight[2] = dz;    

    }
    
    /**
     * Resize the terrain, copying any old altitudes. 
     * 
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        mySize = new Dimension(width, height);
        double[][] oldAlt = myAltitude;
        myAltitude = new double[width][height];
        
        for (int i = 0; i < width && i < oldAlt.length; i++) {
            for (int j = 0; j < height && j < oldAlt[i].length; j++) {
                myAltitude[i][j] = oldAlt[i][j];
            }
        }
    }

    /**
     * Get the altitude at a grid point
     * 
     * @param x
     * @param z
     * @return
     */
    public double getGridAltitude(int x, int z) {
        return myAltitude[x][z];
    }

    
    public double[] crossProduct(double[] a,double[] b){
    	double[] product = {a[1]*b[2]-a[2]*b[1],a[2]*b[0]-a[0]*b[2],a[0]*b[1]-a[1]*b[0]};

    	return product;
    }
    
    public void draw(GL2 gl, MyTexture[] texture){
    	gl.glMatrixMode(GL2.GL_MODELVIEW);
    	
    	gl.glPushMatrix();
    	
        float matAmb[] = {0.2f, 0.2f, 0.2f, 1.0f};
        float matdiff[] = { 0.2f, 1f, 0f, 1.0f };

     	//gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0].getTextureId()); 
        // Material properties of teapot
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, matAmb,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, matdiff,0);

        
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0].getTextureId()); 
    	gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glBegin(GL2.GL_TRIANGLES);
        {
        	for(int x = 0;x < mySize.width - 1 ; x++){
        		for(int z = 0; z < mySize.height - 1;z++){        			
        			double[] j ={x, getGridAltitude(x,z), z};
        			double[] k = {x+1, getGridAltitude(x+1,z), z};
        			double[] l = {x, getGridAltitude(x,z+1), z+1};
        			double[] N = {k[0]-j[0],k[1]-j[1],k[2]-j[2]};
        			double[] m = {l[0]-j[0],l[1]-j[1],l[2]-j[2]};
        			double[] normal = crossProduct(N,m);
        			//System.out.println("normal:" + normal[0]+" "+normal[1]+" "+normal[2]);
        			gl.glNormal3d(-normal[0],-normal[1],-normal[2]);      
        			gl.glTexCoord2d(0, 0);
		            gl.glVertex3d(x, getGridAltitude(x,z), z);		            
		            gl.glTexCoord2d(1, 0);
		            gl.glVertex3d(x+1, getGridAltitude(x+1,z), z);
		            gl.glTexCoord2d(0, 1);
		            gl.glVertex3d(x, getGridAltitude(x,z+1), z+1);
		          
		            double[] j1 ={x+1, getGridAltitude(x+1,z), z};
        			double[] k1 = {x+1, getGridAltitude(x+1,z+1), z+1};
        			double[] l1 = {x, getGridAltitude(x,z+1), z+1};
        			double[] N1 = {k1[0]-j1[0],k1[1]-j1[1],k1[2]-j1[2]};
        			double[] m1 = {l1[0]-j1[0],l1[1]-j1[1],l1[2]-j1[2]};
        			double[] normal1 = crossProduct(N1,m1);
        			//System.out.println("normal1:" + normal1[0]+" "+normal1[1]+" "+normal1[2]);
        			gl.glNormal3d(-normal1[0],-normal1[1],-normal1[2]);      
		            gl.glTexCoord2d(1, 0);
		            gl.glVertex3d(x+1, getGridAltitude(x+1,z), z);
		            
		            gl.glTexCoord2d(1, 1);
		            gl.glVertex3d(x+1, getGridAltitude(x+1,z+1), z+1);  
		           
		            gl.glTexCoord2d(0, 1);
		            gl.glVertex3d(x, getGridAltitude(x,z+1), z+1);
        		}
        	}
        }
        gl.glEnd();
        
        for(Tree t:this.myTrees){
        	t.draw(gl, this.myTexture);
        }
        
        for(Road r:this.roads()){
        	r.draw(gl, texture);
        }
        //gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    	gl.glPopMatrix();
    	
    }
    

    /**
     * Set the altitude at a grid point
     * 
     * @param x
     * @param z
     * @return
     */
    public void setGridAltitude(int x, int z, double h) {
        myAltitude[x][z] = h;
    }

    /**
     * Get the altitude at an arbitrary point. 
     * Non-integer points should be interpolated from neighbouring grid points
     * 
     * TO BE COMPLETED
     * 
     * @param x
     * @param z
     * @return
     */
    
    public double[] XiexianshangdePoint(int x1,int z1,int x2,int z2,double z){
    	double[] point = new double[2];
    	//p1(x0,z1) p2(x1,z0)
    	double x = (z-z2)/(z1-z2)*(x1-x2)+x2;
 
    	point[0] = x;
    	point[1] = z;
    	return point;
    	
    }
    
    
    public double altitude(double x,double z){
		int x3 = 0;
		int z3 = 0;
		if(x<0||x>this.size().getWidth()-1||z<0||z>this.size().getHeight()-1)return 0;
    	double altitude =0;
    	int x1 = (int) Math.ceil(x);
    	int z1 = (int) Math.floor(z);
    	int z2 = (int) Math.ceil(z);
    	int x2 = (int) Math.floor(x);
     	if(x1==x2&&z1!=z2){
     		double t11 = getGridAltitude(x1,z1);
     		double t22 = getGridAltitude(x2,z2);
     		return (z-z1)/(z2-z1)*t22+(z2-z)/(z2-z1)*t11;
     	}
     	if(z1==z2&&x1!=x2){
     		double t11 = getGridAltitude(x1,z1);
     		double t22 = getGridAltitude(x2,z2);
     		return (x-x1)/(x2-x1)*t22+(x2-x)/(x2-x1)*t11;
     	}
     	if(z1==z2&&x1==x2){
     		return getGridAltitude(x2,z2);
     	}
		double[] point = XiexianshangdePoint(x1,z1,x2,z2,z);
    	double r2Att=0;
    	double r1Att=0;
		if(x<point[0]){
			x3 = (int) Math.floor(x);
			z3 = (int) Math.floor(z);
	    	point = XiexianshangdePoint(x1,z1,x2,z2,z);
	    	r1Att = (z-z1)/(z2-z1)*getGridAltitude(x2,z2)+(z2-z)/(z2-z1)*getGridAltitude(x1,z1);
			r2Att = (z-z3)/(z2-z3)*getGridAltitude(x2,z2)+(z2-z)/(z2-z3)*getGridAltitude(x3,z3);
			altitude = (x-point[0])/(x3-point[0])*r2Att+(x3-x)/(x3-point[0])*r1Att;
		}
		else{
 			x3 = (int) Math.ceil(x);
			z3 = (int) Math.ceil(z);
	    	point = XiexianshangdePoint(x1,z1,x3,z3,z);
	    	double[] p2 = XiexianshangdePoint(x1,z1,x2,z2,z);
	    	r1Att = (z-z1)/(z3-z1)*getGridAltitude(x3,z3)+(z3-z)/(z3-z1)*getGridAltitude(x1,z1);
			r2Att = (z-z1)/(z2-z1)*getGridAltitude(x2,z2)+(z2-z)/(z2-z1)*getGridAltitude(x1,z1);
			altitude = (x-point[0])/(p2[0]-point[0])*r2Att+(p2[0]-x)/(p2[0]-point[0])*r1Att;
		}

//	   	System.out.println("x1= "+x1+" z1= "+z1);
//    	System.out.println("x2= "+x3+" z2= "+z3);
//		System.out.println("x3= "+x2+" z3= "+z2);	
		System.out.println("r1Att= "+r1Att);
		System.out.println("r2Att= "+r2Att);
		System.out.println();
		return altitude;
    		
    
    }
  
        	
        	
        	
        	

    

    /**
     * Add a tree at the specified (x,z) point. 
     * The tree's y coordinate is calculated from the altitude of the terrain at that point.
     * 
     * @param x
     * @param z
     */
    public void addTree(double x, double z) {
        double y = altitude(x, z);
        System.out.println(y);
        Tree tree = new Tree(x, y, z);
        myTrees.add(tree);
    }


    /**
     * Add a road. 
     * 
     * @param x
     * @param z
     */
    public void addRoad(double width, double[] spine) {
        Road road = new Road(width, spine);
        road.setMyTerrain(this);
        myRoads.add(road);
 
    }





}
