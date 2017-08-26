package ass2.spec;



import java.nio.FloatBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;



//Implements a surface of revolution
//Takes a 2D profile and sweeps it around the y-axis

public class SunVBO2{
	

	private int maxStacks = 20;
	private int maxSlices = 30;
	private int maxVertices = maxStacks*(maxSlices+1)*2;
	FloatBuffer verticesBuffer = FloatBuffer.allocate(maxVertices*3);
	FloatBuffer normalsBuffer = FloatBuffer.allocate(maxVertices*3);
	private int bufferIds[] = new int[2];
	private float colors[] = new float[maxVertices*3];
	private int shaderprogram;
	private FloatBuffer colorBuffer;
	private float[] center = new float[2];
	private int distance = 5;
    float lightPos0[] = new float[4];
	private static final String VERTEX_SHADER = "src/ass2/spec/ConstantColorVertex.glsl";
	private static final String FRAGMENT_SHADER = "src/ass2/spec/ConstantColorFragment.glsl";
	
	
  //  private boolean sunlight=true;
	private double angle = 0;
	private float[] amb = {1.0f, 1.0f, 1.0f, 1.0f};
	
	
	float matAmb[] = {0f, 0.56f, 0.0f, 1.0f}; 
	float matShininess = 27.8f;  	
	//private float[] lightColorDiffuse = {1.0f, 1.0f, 1.0f, 1f};
	//private float[] lightColorSpecular = {1.0f, 1.0f, 102/255f, 1f};

	public SunVBO2(GL2 gl,float x, float z){
		setColors();
		this.center[0] = x;
		this.center[1] = z;
	    lightPos0[0] = (float) (center[0]*2+distance);
	    lightPos0[1] = 0f;
	    lightPos0[2] = center[1];
	    lightPos0[3] = 1f;
		this.generateBuffers(gl);

	}
    
	
	public void updateCoordinateAndLight(GL2 gl){
		angle = normalizeAngle(angle+0.2);
		rotatePoint();
    	if(angle>=0)  gl.glEnable(GL2.GL_LIGHT0); 
    	else gl.glDisable(GL2.GL_LIGHT0);
	}
	
	
	public float[] rotatePoint(){
		float px= center[0]*2+distance;
		float py = 0;
    	float[] points = new float[2];
    	float redians = (float) Math.toRadians(angle);
    	lightPos0[0]= (float) (Math.cos(redians) * (px-center[0]) - Math.sin(redians) * (py-center[1]) + center[0]);
    	lightPos0[1]= (float) (Math.sin(redians) * (px-center[0]) + Math.cos(redians) * (py-center[1]) + center[1]);
		return points;
    	
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
    
    public void setColors(){
    	
    	for(int i = 0;i<maxVertices*3-3;i+=3){
    		colors[i] = 1;
    		colors[i+1] = 0;
    		colors[i+2] = 0;
    	}
    	colorBuffer = Buffers.newDirectFloatBuffer(colors);
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
    		updateCoordinateAndLight(gl);

    		
    		
        	float ambient[] = {0.33f, 0.22f, 0.03f, 1.0f}; 
        	float diffuse[] = {0.78f, 0.57f, 0.11f, 1.0f}; 
        	float specular[] = {0.99f, 0.91f, 0.81f, 1.0f}; 
        	float shininess = 27.8f;  	

        	gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);
        	gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, ambient,0); 
        	gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, diffuse,0); 
        	gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, specular,0); 
        	gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, shininess); 

        	gl.glPushMatrix();{
            	gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, amb, 0);
            	

	    	// material parameter set for metallic gold or brass 
//	
//	    	float ambient[] = {0.33f, 0.22f, 0.03f, 1.0f}; 
//	    	float diffuse[] = {0.78f, 0.57f, 0.11f, 1.0f}; 
//	    	float specular[] = {0.99f, 0.91f, 0.81f, 1.0f}; 
//	    	float shininess = 27.8f;  	
//	
//	    	gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);
//	    	gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, ambient,0); 
//	    	gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, diffuse,0); 
//	    	gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, specular,0); 
//	    	gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, shininess); 
	    	
	    	
        		//gl.glEnable(GL2.GL_CULL_FACE);
        		//gl.glCullFace(GL2.GL_BACK);
            	gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos0,0);
            	
            	//Also translate to draw to representation of the light
            	//Usually you would not do this if you did not
            	//want to actually draw the light.
            	gl.glTranslatef(lightPos0[0], lightPos0[1], lightPos0[2]);
   
		    	gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		    	
		        //Use the shader
		        gl.glUseProgram(shaderprogram);
		    	gl.glBindBuffer(GL.GL_ARRAY_BUFFER,bufferIds[0]);
		

		  
	    	
	    	   //	int vertexColLoc = gl.glGetAttribLocation(shaderprogram,"vertexCol");
	           	int vertexPosLoc = gl.glGetAttribLocation(shaderprogram,"vertexPos");
	                  
	            // Specify locations for the co-ordinates and color arrays.
	           	gl.glEnableVertexAttribArray(vertexPosLoc);
	           //	gl.glEnableVertexAttribArray(vertexColLoc);
				gl.glVertexAttribPointer(vertexPosLoc,3, GL.GL_FLOAT, false,0, 0); //last num is the offset
				//gl.glVertexAttribPointer(vertexColLoc,3, GL.GL_FLOAT, false,0,  maxVertices*3 *Float.SIZE/8);
	    	
				gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferIds[1]);
				gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
			    gl.glNormalPointer(GL.GL_FLOAT, 0, 0);
	
		    	for(int i=0; i < maxStacks; i++ ){
		    		gl.glDrawArrays(GL2.GL_TRIANGLE_STRIP,i*(maxSlices+1)*2,(maxSlices+1)*2);        
		    	}
		    	//gl.glDrawArrays(GL2.GL_TRIANGLE_STRIP,0,maxVertices);      
		    	
		    	gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL); 
		    	
		    	gl.glUseProgram(0);
		    	   
		     	//Un-bind the buffer. 
		     	//This is not needed in this simple example but good practice
		         gl.glBindBuffer(GL.GL_ARRAY_BUFFER,0);
		         gl.glBindBuffer(GL.GL_ARRAY_BUFFER,0);
			    	//gl.glDisable(GL2.GL_CULL_FACE);
	    }gl.glPopMatrix();
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
    	 gl.glGenBuffers(2,bufferIds,0);
         gl.glBindBuffer(GL.GL_ARRAY_BUFFER,bufferIds[0]);
    	        
         gl.glBufferData(GL2.GL_ARRAY_BUFFER,      
        	        maxVertices *3 * Float.SIZE/8 +  
        	        colors.length* Float.SIZE/8,
        	        null, GL2.GL_STATIC_DRAW);

         // load position data
         gl.glBufferSubData(GL2.GL_ARRAY_BUFFER, 0,
        		 maxVertices*3 *Float.SIZE/8,verticesBuffer);
         //load color data
         gl.glBufferSubData(GL2.GL_ARRAY_BUFFER,
        		 maxVertices*3*Float.SIZE/8, 
        		 colors.length* Float.SIZE/8,colorBuffer);

         
         gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, bufferIds[1]); 
         
         gl.glBufferData(GL2.GL_ARRAY_BUFFER,      
        		maxVertices*3*Float.SIZE/8,
        		normalsBuffer, GL2.GL_STATIC_DRAW);
         

    	 try {
    		 shaderprogram = Shader.initShaders(gl,VERTEX_SHADER,FRAGMENT_SHADER);
   		    		 
         }
         catch (Exception e) {
             e.printStackTrace();
             System.exit(1);
         }
    }
    
 
    
	double normalizeAngle(double angle)
	{
	    double newAngle = angle;
	    while (newAngle <= -180) newAngle += 360;
	    while (newAngle > 180) newAngle -= 360;
	    return newAngle;
	}
}
