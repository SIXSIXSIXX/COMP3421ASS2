package ass2.spec;



import java.nio.FloatBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;



//Implements a surface of revolution
//Takes a 2D profile and sweeps it around the y-axis

public class SunVBO{
	private float LOWESTSKYCOLOR=21;
	private float LOWESTEMM = 21;
	private int maxStacks = 20;
	private int maxSlices = 30;
	private float skyColor = LOWESTSKYCOLOR;
	private int maxVertices = maxStacks*(maxSlices+1)*2;
	FloatBuffer verticesBuffer = FloatBuffer.allocate(maxVertices*3);
	FloatBuffer normalsBuffer = FloatBuffer.allocate(maxVertices*3);
	private int bufferIds[] = new int[2];
	private float[] center = new float[2];
	private int distance = 0;
	private float emm = LOWESTEMM;
	private float colorChange = (float) ((255-LOWESTEMM)/(90/0.2));
	private float skyColorChange = (float) ((255-LOWESTSKYCOLOR)/(90/0.2));
    float lightPos0[] = new float[4];
	private double angle = 0;
	
  	float matAmbAndDifL[] = {1f, 1f, 1f, 1.0f};
	public SunVBO(GL2 gl,float x, float z,float[] pos){
		this.center[0] = x;
		this.center[1] = z;
	    lightPos0[0] = pos[0];
	    lightPos0[1] = pos[1];
	    lightPos0[2] = pos[2];
	    lightPos0[3] = 1f;
		this.generateBuffers(gl);

	}
    
	
	public void updateCoordinateAndLight(GL2 gl){
		angle = normalizeAngle(angle+0.2);
		rotatePoint();
		if(angle>0)     gl.glEnable(GL2.GL_LIGHT0);
		else gl.glDisable(GL2.GL_LIGHT0);
    	if(angle>=0 && angle <= 90)  {
    		emm= emm+colorChange;
    		skyColor += skyColorChange;
    		
    	}
    	if(angle > 90 && angle<180) {
    		emm = emm-colorChange;
      		skyColor -= skyColorChange;
    		
    	}
    	else if(angle<=0){
    		emm = LOWESTEMM;
    		skyColor = LOWESTSKYCOLOR;
    	};
	}
	
	
	public void rotatePoint(){

		
		float px =center[0]*2+distance;
    	float py = 0;	
    	float ox = center[0];
    	float oy = 0;
    	float redians = (float) Math.toRadians(angle);
    	lightPos0[0] = (float) ((Math.cos(redians) * (px-ox) - Math.sin(redians) * (py-oy) + ox));
    	lightPos0[1] =(float)( (Math.sin(redians) * (px-ox) + Math.cos(redians) * (py-oy) + oy));
		
    	
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
    
    
    public float[] getLight(){
    	float[] emmLi = {emm/255,emm/255,emm/255,1f};
    	return(emmLi);
    }
    
    public float[] getSkyColor(){
    	float[] emmLi = {1/111,skyColor/255,skyColor/255,1f};
    	return(emmLi);
    }
    	
    public float[] getLightPosition(){

    	return(lightPos0);
    }
    public void draw(GL2 gl, MyTexture[] texture) {
		updateCoordinateAndLight(gl);
		
		if(this.angle>=0){
	    	gl.glPushMatrix();{
	    		
	    		gl.glTranslatef(lightPos0[0], lightPos0[1], lightPos0[2]);
	    		gl.glBindTexture(GL.GL_TEXTURE_2D, texture[4].getTextureId()); 
	 
	    		
	    		
	    		//float matAmb[] = {1f, 1f, 1f, 1.0f};
	
	        	// Material properties of sphere.
	        	gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, getLight(),0);
	
		
		    	gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		    	
		    	gl.glBindBuffer(GL.GL_ARRAY_BUFFER,bufferIds[0]);
		
		    	// Enable two vertex arrays: co-ordinates and color.
		    	gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		    	gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
		
		    	// Specify locations for the co-ordinates and color arrays.
		    	gl.glVertexPointer(3, GL.GL_FLOAT, 0, 0); //last num is the offset
		    	gl.glNormalPointer(GL.GL_FLOAT, 0, maxVertices*3*Float.SIZE/8);
		        gl.glEnable(GL2.GL_TEXTURE_GEN_S);
			    gl.glEnable(GL2.GL_TEXTURE_GEN_T);
	
			    
			    gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_SPHERE_MAP);
		        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_SPHERE_MAP);
		    	for(int i=0; i < maxStacks; i++ ){
		    		gl.glDrawArrays(GL2.GL_TRIANGLE_STRIP,i*(maxSlices+1)*2,(maxSlices+1)*2);        
		    	}     
		    	
		    	gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
			    gl.glDisable(GL2.GL_TEXTURE_GEN_T);
		    	
		    	gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL); 
	    }gl.glPopMatrix();
    }
    }
   

	    public void generateData(){
	    
    	double deltaT;
    	deltaT = 0.5/maxStacks;
    	int ang;  
    	int delang = 360/maxSlices;
    	double x1,x2,z1,z2,y1,y2;
    	double radius = 0.5;
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
    
 
    
	double normalizeAngle(double angle)
	{
	    double newAngle = angle;
	    while (newAngle <= -180) newAngle += 360;
	    while (newAngle > 180) newAngle -= 360;
	    return newAngle;
	}
}
