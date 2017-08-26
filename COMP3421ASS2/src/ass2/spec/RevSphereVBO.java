package ass2.spec;




import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.nio.FloatBuffer;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

//Implements a surface of revolution
//Takes a 2D profile and sweeps it around the y-axis

public class RevSphereVBO{
	private double rotateX = 0;
    private double rotateY = 0;
    private Point myMousePoint = null;
    private static final int ROTATION_SCALE = 1;
    
    private int maxStacks = 20;
    private int maxSlices = 30;
    private int maxVertices = maxStacks*(maxSlices+1)*2;
	FloatBuffer verticesBuffer = FloatBuffer.allocate(maxVertices*3);
	FloatBuffer normalsBuffer = FloatBuffer.allocate(maxVertices*3);
      
    private int numStacks = 9;
    private int numSlices = 30;
    private int bufferIds[] = new int[1];
    
	    
    public  RevSphereVBO(GL2 gl){
    	
    	generateBuffers(gl);
    
    }   
    
    public void normalize(double v[])  
    {  
        double d = Math.sqrt(v[0]*v[0]+v[1]*v[1]+v[2]*v[2]);  
        if (d != 0.0) 
        {  
           v[0]/=d; 
           v[1]/=d;  
           v[2]/=d;  
        }  
    } 
    
    void normCrossProd(double v1[], double v2[], double out[])  
    {  
       out[0] = v1[1]*v2[2] - v1[2]*v2[1];  
       out[1] = v1[2]*v2[0] - v1[0]*v2[2];  
       out[2] = v1[0]*v2[1] - v1[1]*v2[0];  
       normalize(out);  
    } 
    
   
    
    double r(double t){
    	double x  = Math.cos(2 * Math.PI * t);
        return x;
    }
    
    double getY(double t){
    	
    	double y  = Math.sin(2 * Math.PI * t);
        return y;
    }
    

    public void draw(GL2 gl) {
    	float ambient[] = {0.33f, 0.22f, 0.03f, 1.0f}; 
    	float diffuse[] = {0.78f, 0.57f, 0.11f, 1.0f}; 
    	float specular[] = {0.99f, 0.91f, 0.81f, 1.0f}; 
    	float shininess = 27.8f;  	

//    	gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);
//    	gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, ambient,0); 
//    	gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, diffuse,0); 
//    	gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, specular,0); 
//    	gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, shininess); 
    	generateBuffers(gl);
    	
    	gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    	
    	gl.glBindBuffer(GL.GL_ARRAY_BUFFER,bufferIds[0]);

    	// Enable two vertex arrays: co-ordinates and color.
    	gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
    	gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);

    	// Specify locations for the co-ordinates and color arrays.
    	gl.glVertexPointer(3, GL.GL_FLOAT, 0, 0); //last num is the offset
    	gl.glNormalPointer(GL.GL_FLOAT, 0, maxVertices*3*Float.SIZE/8);
    	for(int i=0; i < numStacks; i++ ){
    		gl.glDrawArrays(GL2.GL_TRIANGLE_STRIP,i*(maxSlices+1)*2,(numSlices+1)*2);        
    	}
    	//gl.glDrawArrays(GL2.GL_TRIANGLE_STRIP,0,maxVertices);      
    	
    	gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);       
    }
    public void generateData(){
    
    	double deltaT;
    	deltaT = 0.5/maxStacks;
    	int ang;  
    	int delang = 360/maxSlices;
    	double x1,x2,z1,z2,y1,y2;
    	double radius = 0.25;
    	for (int i = 0; i < maxStacks; i++) 
    	{ 
    		double t = -0.25 + i*deltaT;

    		for(int j = 0; j <= maxSlices; j++)  
    		{  
    			ang = j*delang;
    			x1=radius * r(t)*Math.cos((double)ang*2.0*Math.PI/360.0); 
    			x2=radius * r(t+deltaT)*Math.cos((double)ang*2.0*Math.PI/360.0); 
    			y1 = radius * getY(t);

    			z1=radius * r(t)*Math.sin((double)ang*2.0*Math.PI/360.0);  
    			z2= radius * r(t+deltaT)*Math.sin((double)ang*2.0*Math.PI/360.0);  
    			y2 = radius * getY(t+deltaT);

    			double normal[] = {x1,y1,z1};


    			normalize(normal);   

    			verticesBuffer.put((float)x1);
    			verticesBuffer.put((float)y1);
    			verticesBuffer.put((float)z1);
    			normalsBuffer.put((float)normal[0]);
    			normalsBuffer.put((float)normal[1]);
    			normalsBuffer.put((float)normal[2]);


    			//gl.glNormal3dv(normal,0);         
    			//gl.glVertex3d(x1,y1,z1);
    			normal[0] = x2;
    			normal[1] = y2;
    			normal[2] = z2;

    			normalize(normal);    
    			//gl.glNormal3dv(normal,0); 
    			//gl.glVertex3d(x2,y2,z2); 

    			verticesBuffer.put((float)x2);
    			verticesBuffer.put((float)y2);
    			verticesBuffer.put((float)z2);
    			normalsBuffer.put((float)normal[0]);
    			normalsBuffer.put((float)normal[1]);
    			normalsBuffer.put((float)normal[2]);

    		}; 
    	}
    	verticesBuffer.rewind();
    	normalsBuffer.rewind();
}
    
    public void generateBuffers(GL2 gl){
    	
    	 generateData();
    	 gl.glGenBuffers(1,bufferIds,0);
         gl.glBindBuffer(GL.GL_ARRAY_BUFFER,bufferIds[0]);
    	        
         gl.glBufferData(GL2.GL_ARRAY_BUFFER,      
        	        maxVertices *3 * Float.SIZE/8 +  
        	        maxVertices *3 * Float.SIZE/8,
        	        null, GL2.GL_STATIC_DRAW);


         gl.glBufferSubData(GL2.GL_ARRAY_BUFFER, 0,
        		 maxVertices*3 *Float.SIZE/8,verticesBuffer);

         gl.glBufferSubData(GL2.GL_ARRAY_BUFFER,
        		 maxVertices*3*Float.SIZE/8, 
        		 maxVertices*3*Float.SIZE/8,normalsBuffer);
    }


    	 
}

 