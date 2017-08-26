package ass2.spec;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * COMMENT: Comment Tree 
 *
 * @author malcolmr
 */
public class Tree {

    private double[] myPos;
    
    public Tree(double x, double y, double z) {
        myPos = new double[3];
        myPos[0] = x;
        myPos[1] = y;
        myPos[2] = z;
    }
    
    public double[] getPosition() {
        return myPos;
    }
    
    
    
    public void draw(GL2 gl, MyTexture[] texture){
    	GLUT glut = new GLUT();
    	gl.glPushMatrix();
    	
   
    	gl.glTranslated(myPos[0], myPos[1],  myPos[2]); 
    	gl.glRotated(270, 1, 0, 0);
    	
    	gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    	
    	
        float trunkMatAmb[] = {0.2f, 0.2f, 0.2f, 1.0f};
        float trinkMatdiff[] = { 175/255f, 105/255f, 0f, 1.0f };
     	gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0].getTextureId()); 
        // Material properties of teapot
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, trunkMatAmb,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, trinkMatdiff,0);

        
        gl.glEnable(GL2.GL_TEXTURE_GEN_S);
	    gl.glEnable(GL2.GL_TEXTURE_GEN_T);

	    
	    
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[2].getTextureId());
	    gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_TEXTURE_CUBE_MAP);
        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_TEXTURE_CUBE_MAP);
        glut.glutSolidCylinder(0.1,0.9,20,20); 
        
	    	
    	gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
	    gl.glDisable(GL2.GL_TEXTURE_GEN_T);
	    
        gl.glEnable(GL2.GL_TEXTURE_GEN_S);
	    gl.glEnable(GL2.GL_TEXTURE_GEN_T);
        float leavesMatAmb[] = {0.2f, 0.2f, 0.2f, 1.0f};
        float leavesMatdiff[] = { 0.2f, 1f, 0f, 1.0f };
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, leavesMatAmb,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, leavesMatdiff,0);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[1].getTextureId());
        
    	gl.glRotated(-270, 1, 0, 0);
    	gl.glTranslated(0, 0.9, 0);
	    gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_SPHERE_MAP);
        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_SPHERE_MAP);
    	glut.glutSolidSphere(0.3, 10, 10);  
    	gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
	    gl.glDisable(GL2.GL_TEXTURE_GEN_T);
	    
	    
    	 gl.glPopMatrix();
    }
    

    public void draw2 (GL2 gl, MyTexture[] texture){
    	GLUT glut = new GLUT();
    	gl.glPushMatrix();
    	
   
    	gl.glTranslated(myPos[0], myPos[1]+0.9,  myPos[2]); 
    	gl.glRotated(270, 1, 0, 0);
    	gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glEnable(GL2.GL_TEXTURE_GEN_S);
	    gl.glEnable(GL2.GL_TEXTURE_GEN_T);

        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[2].getTextureId());
	    gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_TEXTURE_CUBE_MAP);
        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_TEXTURE_CUBE_MAP);
        render(gl);
       
	    	
    	gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
	    gl.glDisable(GL2.GL_TEXTURE_GEN_T);
	    
        gl.glEnable(GL2.GL_TEXTURE_GEN_S);
	    gl.glEnable(GL2.GL_TEXTURE_GEN_T);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[1].getTextureId());
        
    	gl.glRotated(-270, 1, 0, 0);
    	
    	gl.glTranslated(0, 0.9, 0);
	    gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_SPHERE_MAP);
        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_SPHERE_MAP);
    	//glut.glutSolidSphere(0.3, 10, 10);  
    	gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
	    gl.glDisable(GL2.GL_TEXTURE_GEN_T);
    	 gl.glPopMatrix();
    }
    
    
    public void render(GL2 gl) {
    	double z1 = 0;
    	double z2 = -0.9;
    	gl.glPolygonMode(GL.GL_BACK,GL2.GL_LINE);
    	//Front circle
    	int slices = 32;
    	gl.glBegin(GL2.GL_TRIANGLE_FAN);{
    	
    		 gl.glNormal3d(0,0,1);
    		 gl.glVertex3d(0,0,z1);
    		 double angleStep = 2*Math.PI/slices;
             for (int i = 0; i <= slices ; i++){
                 double a0 = i * angleStep;
                 double x0 = Math.cos(a0);
                 double y0 = Math.sin(a0);

                gl.glVertex3d(x0,y0,z1);     
             }
        
    	}gl.glEnd();
    	
    	//Back circle
    	gl.glBegin(GL2.GL_TRIANGLE_FAN);{
       
   		 gl.glNormal3d(0,0,-1);
   		 gl.glVertex3d(0,0,z2);
   		 double angleStep = 2*Math.PI/slices;
            for (int i = 0; i <= slices ; i++){
                
            	double a0 = 2*Math.PI - i * angleStep;
                            
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                gl.glVertex3d(x0,y0,z2);
            }
                
                
    	}gl.glEnd();

    	gl.glBegin(GL2.GL_QUADS);
        {
            double angleStep = 2*Math.PI/slices;
            for (int i = 0; i <= slices ; i++){
                double a0 = i * angleStep;
                double a1 = ((i+1) % slices) * angleStep;
                
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                double x1 = Math.cos(a1);
                double y1 = Math.sin(a1);


                gl.glNormal3d(x0, y0, 0);
                gl.glVertex3d(x0, y0, z1);
                gl.glVertex3d(x0, y0, z2);  
                

                gl.glNormal3d(x1, y1, 0);
                gl.glVertex3d(x1, y1, z2);
                gl.glVertex3d(x1, y1, z1);               
              
            }

        }
        gl.glEnd();
        
    	gl.glPolygonMode(GL.GL_FRONT_AND_BACK,GL2.GL_FILL);
    }

}