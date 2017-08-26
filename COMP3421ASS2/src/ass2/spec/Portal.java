package ass2.spec;

import java.util.Random;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;

import vector.Vector3f;

public class Portal {
	private Terrain myTerrain;
	private double[] portalCoordinate = new double[3];
	private double[] teleCoordinate = new double[3];
	private double angle = 0;
	public Portal(Terrain terrain){
		myTerrain = terrain;
		this.portalCoordinate = randomCoordinate();
		this.teleCoordinate = rotatePoint(portalCoordinate[0],portalCoordinate[2]);
		
	}
	
	public double[] rotatePoint(double px, double py){
    	double ox = (float)((this.myTerrain.size().getWidth()-1)/2);
    	double oy = (float)((this.myTerrain.size().getHeight()-1)/2);
    	double[] points = new double[3];
    	double redians = (float) Math.toRadians(180);
    	points[0]= (float) (Math.cos(redians) * (px-ox) - Math.sin(redians) * (py-oy) + ox);
    	points[2]= (float) (Math.sin(redians) * (px-ox) + Math.cos(redians) * (py-oy) + oy);
		points[1] = this.myTerrain.altitude(points[0], points[2]);
    	return points;
    	
	}
	
	public double[] randomCoordinate(){
		double[] points = new double[3];
		points[0] = randomWithRange(1,myTerrain.size().getWidth()-2);
		points[2] = randomWithRange(1,myTerrain.size().getHeight()-3);
		points[1] = myTerrain.altitude(points[0], points[2]);	
		return points;		
	}
	
	public void draw(GL2 gl,MyTexture[] texture){
		gl.glPushMatrix();{
			//gl.glTranslated(this.portalCoordinate[0], portalCoordinate[1], portalCoordinate[2]);


			    
			    
			drawCube(gl,this.portalCoordinate,texture);
		
			drawCube(gl,this.teleCoordinate,texture);
		}
		gl.glPopMatrix();
	
	}
	
	public double randomWithRange(double min, double max)
	{
	   int range = (int)(max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}



		
		//this.coordinate[0] += increasement;
		//setWordCoordinate();
	
	 private void drawCube(GL2 gl,double[] point,MyTexture[] texture){
		 double[] a ={0.5,this.myTerrain.altitude(point[0]+0.5, point[2])-point[1],0};
		 double[] b = {0.5,0.5,0};
		 double[] normal = crossProduct(a,b);
		 gl.glBindTexture(GL.GL_TEXTURE_2D, texture[11].getTextureId()); 
	    	gl.glBegin(GL2.GL_QUADS);
	    	   // front
	    		gl.glNormal3d(-normal[0], -normal[1], -normal[2]);
	    		gl.glTexCoord2d(0, 0);
	    	   gl.glVertex3d(point[0], point[1], point[2]); 
	    		gl.glTexCoord2d(1, 0);
	    	   gl.glVertex3d(point[0]+0.5,this.myTerrain.altitude(point[0]+0.5, point[2]) , point[2]);   
	    	   gl.glTexCoord2d(1, 1);
	    	   gl.glVertex3d(0.5+point[0], this.myTerrain.altitude(point[0]+0.5, point[2])+1.5, point[2]);  
	    	   gl.glTexCoord2d(0, 1);
	    	   gl.glVertex3d(point[0], 1.5+point[1], point[2]); 
	    	   	      
	    	   gl.glEnd();    	   
	    }
	    public double[] crossProduct(double[] a,double[] b){
	    	double[] product = {a[1]*b[2]-a[2]*b[1],a[2]*b[0]-a[0]*b[2],a[0]*b[1]-a[1]*b[0]};

	    	return product;
	    }
//	public void drawDoor(GL2 gl){
//		gl.glPushMatrix();{
//			//front
//		gl.glBegin(GL2.GL_QUADS);
//		{
//			gl.glVertex3d(this.portalCoordinate[0]-0.3, this.myTerrain.altitude(portalCoordinate[0]-0.3, portalCoordinate[2]), this.portalCoordinate[2]);
//			gl.glVertex3d(this.portalCoordinate[0]-0.3, this.myTerrain.altitude(portalCoordinate[0]-0.3, portalCoordinate[2])+0.3, this.portalCoordinate[2]);
//			gl.glVertex3d(this.portalCoordinate[0]+0.3, this.myTerrain.altitude(portalCoordinate[0]-0.3, portalCoordinate[2])+0.3, this.portalCoordinate[2]);
//			gl.glVertex3d(this.portalCoordinate[0]+0.3, this.myTerrain.altitude(portalCoordinate[0]-0.3, portalCoordinate[2]), this.portalCoordinate[2]);
//		}		
//		gl.glEnd();
//		//left
//		gl.glBegin(GL2.GL_QUADS);
//		{
//			gl.glVertex3d(this.portalCoordinate[0]-0.3, this.myTerrain.altitude(portalCoordinate[0]-0.3, portalCoordinate[2]), this.portalCoordinate[2]);
//			gl.glVertex3d(this.portalCoordinate[0]-0.3, this.myTerrain.altitude(portalCoordinate[0]-0.3, portalCoordinate[2]-0.3), this.portalCoordinate[2]-0.3);
//			gl.glVertex3d(this.portalCoordinate[0]-0.3, this.myTerrain.altitude(portalCoordinate[0]-0.3, portalCoordinate[2]-0.3)+0.3, this.portalCoordinate[2]-0.3);
//			gl.glVertex3d(this.portalCoordinate[0]-0.3, this.myTerrain.altitude(portalCoordinate[0]-0.3, portalCoordinate[2])+0.3, this.portalCoordinate[2]);
//		}		
//		gl.glEnd();
//		//back
//		gl.glBegin(GL2.GL_QUADS);
//		{
//			gl.glVertex3d(this.portalCoordinate[0]-0.3, this.myTerrain.altitude(portalCoordinate[0]-0.3, portalCoordinate[2]-0.3), this.portalCoordinate[2]-0.3);
//			gl.glVertex3d(this.portalCoordinate[0]-0.3, this.myTerrain.altitude(portalCoordinate[0]-0.3, portalCoordinate[2]-0.3)+0.3, this.portalCoordinate[2]-0.3);
//			gl.glVertex3d(this.portalCoordinate[0]+0.3, this.myTerrain.altitude(portalCoordinate[0]+0.3, portalCoordinate[2]-0.3)+0.3, this.portalCoordinate[2]-0.3);
//			gl.glVertex3d(this.portalCoordinate[0]+0.3, this.myTerrain.altitude(portalCoordinate[0]+0.3, portalCoordinate[2]-0.3)-0.3, this.portalCoordinate[2]);
//		}		
//		gl.glEnd();
//		//right
//		gl.glBegin(GL2.GL_QUADS);
//		{
//			gl.glVertex3d(this.portalCoordinate[0]+0.3, this.myTerrain.altitude(portalCoordinate[0]+0.3, portalCoordinate[2]-0.3), this.portalCoordinate[2]-0.3);
//			gl.glVertex3d(this.portalCoordinate[0]+0.3, this.myTerrain.altitude(portalCoordinate[0]-0.3, portalCoordinate[2]-0.3)+0.3, this.portalCoordinate[2]-0.3);
//			gl.glVertex3d(this.portalCoordinate[0]+0.3, this.myTerrain.altitude(portalCoordinate[0]+0.3, portalCoordinate[2]+0.3)+0.3, this.portalCoordinate[2]+0.3);
//			gl.glVertex3d(this.portalCoordinate[0]+0.3, this.myTerrain.altitude(portalCoordinate[0]+0.3, portalCoordinate[2]+0.3)-0.3, this.portalCoordinate[2]);
//		}		
//		gl.glEnd();
//		gl.glPopMatrix();
//		}
//	}
//	
	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle += angle;
	}
	
	public void changeAngle(double increasement) {
		this.angle = normalizeAngle(this.angle + increasement);
	}

	
	public double normalizeAngle(double angle)
	{
	    double newAngle = angle;
	    while (newAngle <= -180) newAngle += 360;
	    while (newAngle > 180) newAngle -= 360;
	    return newAngle;
	}



	public double[] getTeleCoordinate() {
		return teleCoordinate;
	}



	public void setTeleCoordinate(double[] teleCoordinate) {
		this.teleCoordinate = teleCoordinate;
	}



	public double[] getPortalCoordinate() {
		return portalCoordinate;
	}



	public void setPortalCoordinate(double[] portalCoordinate) {
		this.portalCoordinate = portalCoordinate;
	}
}
