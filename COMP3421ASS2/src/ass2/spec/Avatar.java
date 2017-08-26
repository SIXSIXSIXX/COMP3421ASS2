package ass2.spec;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;

public class Avatar {
	private Terrain myTerrain;
	private double[] coordinate = {0,0,0};
	private double angle = 0;
	private int count =-1;
	private double walkAngle = 35;
	private double handAngle = 35;
	private RevSphereVBO  hat = null;
	private Portal portal =null;
	public Avatar(Terrain terrain, GL2 gl,Portal portal){
		myTerrain = terrain;
		hat = new RevSphereVBO(gl);
		this.portal = portal;
		
	}
	
	public void draw(GL2 gl,MyTexture[] texture){
		GLUT glut = new GLUT();
		gl.glPushMatrix();{
			
		
			//System.out.println("angle =: "+angle);
			
			
			gl.glTranslated(coordinate[0], myTerrain.altitude(coordinate[0], coordinate[2]), coordinate[2]);
			gl.glRotated(-angle, 0, 1, 0);
			//body(length:0.6)
			 gl.glEnable(GL2.GL_TEXTURE_GEN_S);
			 gl.glEnable(GL2.GL_TEXTURE_GEN_T);
			 gl.glBindTexture(GL.GL_TEXTURE_2D, texture[7].getTextureId());
			 gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_TEXTURE_CUBE_MAP);
		     gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_TEXTURE_CUBE_MAP);
			gl.glTranslated(0, 0.55, 0);
			glut.glutSolidCube(0.3f);
			gl.glTranslated(0, 0.3, 0);
			glut.glutSolidCube(0.3f);
			 gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
			 gl.glDisable(GL2.GL_TEXTURE_GEN_T);
			//head(0.2)
			 gl.glEnable(GL2.GL_TEXTURE_GEN_S);
			 gl.glEnable(GL2.GL_TEXTURE_GEN_T);
			 gl.glTranslated(0, 0.35, 0);
			 gl.glBindTexture(GL.GL_TEXTURE_2D, texture[5].getTextureId());
			 gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_SPHERE_MAP);
		     gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_SPHERE_MAP);
			 glut.glutSolidSphere(0.2, 10, 10);
			 gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
			 gl.glDisable(GL2.GL_TEXTURE_GEN_T);
			 gl.glTranslated(0, -0.35, 0);
			 //hat
			 gl.glEnable(GL2.GL_TEXTURE_GEN_S);
			 gl.glEnable(GL2.GL_TEXTURE_GEN_T);
			 gl.glTranslated(0, 0.45, 0);
			 gl.glRotated(180, 1, 0, 0);
			 gl.glBindTexture(GL.GL_TEXTURE_2D, texture[9].getTextureId());
			 hat.draw(gl);
			 gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
			 gl.glDisable(GL2.GL_TEXTURE_GEN_T);
			 gl.glRotated(-180, 1, 0, 0);
			 gl.glTranslated(0, -0.45, 0);
			 //light
			 gl.glTranslated(0, 0.575, 0);
			 gl.glTranslated(0.1, 0, 0);
			 gl.glRotated(90, 0,1, 0);
			 gl.glEnable(GL2.GL_TEXTURE_GEN_S);
			 gl.glEnable(GL2.GL_TEXTURE_GEN_T);
			 gl.glBindTexture(GL.GL_TEXTURE_2D, texture[10].getTextureId());
			 gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_TEXTURE_CUBE_MAP);
		     gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_TEXTURE_CUBE_MAP);
			 glut.glutSolidCylinder(0.1, 0.2, 10, 10);
			 gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
			 gl.glDisable(GL2.GL_TEXTURE_GEN_T);
			 gl.glRotated(-90, 0,1, 0);
			 gl.glTranslated(-0.1, 0, 0);
			 gl.glTranslated(0,- 0.575, 0);
			//nose(length:0.3)
			gl.glTranslated(0, 0.35, 0);
			gl.glTranslated(0.1, 0, 0);
			gl.glRotated(90, 0, 1, 0);
			gl.glTranslated(-0.1, 0, 0);
			gl.glEnable(GL2.GL_TEXTURE_GEN_S);
			 gl.glEnable(GL2.GL_TEXTURE_GEN_T);
			 gl.glBindTexture(GL.GL_TEXTURE_2D, texture[6].getTextureId());
			glut.glutSolidCone(0.05, 0.1, 10, 10);
			gl.glTranslated(0.2, 0, 0);
			glut.glutSolidCone(0.05, 0.1, 10, 10);
			gl.glTranslated(-0.1, 0, 0);
			glut.glutSolidCone(0.1, 0.2, 10, 10);
			 gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
			 gl.glDisable(GL2.GL_TEXTURE_GEN_T);
			gl.glRotated(-90, 0, 1, 0);
			gl.glTranslated(-0.1, 0, 0);
			gl.glTranslated(0, -0.35, 0);
			//two legs(length:0.4)
			gl.glEnable(GL2.GL_TEXTURE_GEN_S);
			 gl.glEnable(GL2.GL_TEXTURE_GEN_T);
			 gl.glBindTexture(GL.GL_TEXTURE_2D, texture[8].getTextureId());
			 gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_TEXTURE_CUBE_MAP);
		     gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_TEXTURE_CUBE_MAP);
			gl.glTranslated(0, -0.85, 0.1);
			
			//right leg
			gl.glTranslated(0, 0.4, 0);
			gl.glRotated(-270, 1, 0, 0);
			//rotate leg
			if(count%2==0){
			gl.glRotated(walkAngle, 0, 1, 0);
			
			glut.glutSolidCylinder(0.05,0.4,20,20); 
			//rotate leg
			gl.glRotated(-walkAngle, 0, 1, 0);
			}
			else{
				glut.glutSolidCylinder(0.05,0.4,20,20); 
			}
			gl.glRotated(270, 1, 0, 0);
			gl.glTranslated(0, -0.4, 0);
			gl.glTranslated(0, 0, -0.2);
			//left leg
			gl.glTranslated(0, 0.4, 0);
			gl.glRotated(-270, 1, 0, 0);
			if(count%2==1){
				gl.glRotated(walkAngle, 0, 1, 0);
				
				glut.glutSolidCylinder(0.05,0.4,20,20); 
				//rotate leg
				gl.glRotated(-walkAngle, 0, 1, 0);
				}
			else{
			glut.glutSolidCylinder(0.05,0.4,20,20);
			}
			gl.glRotated(270, 1, 0, 0);
			gl.glTranslated(0, -0.4, 0);
			//shoulder
			gl.glTranslated(0, 0.9, 0);
			//left shoulder
			gl.glRotated(115, 1, 0, 0);
			if(count%2==0){
			gl.glRotated(-handAngle, 0, 1, 0);
			glut.glutSolidCylinder(0.05,0.35,20,20); 
			gl.glRotated(handAngle, 0, 1, 0);
			}
			else if(count%2==1){
				gl.glRotated(handAngle, 0, 1, 0);
				glut.glutSolidCylinder(0.05,0.35,20,20); 
				gl.glRotated(-handAngle, 0, 1, 0);
			}
			else{
				glut.glutSolidCylinder(0.05,0.35,20,20); 
			}
			gl.glRotated(-115, 1, 0, 0);
			gl.glTranslated(0, 0, 0.20);
			//right shoulder
			gl.glRotated(75, 1, 0, 0);
			if(count%2==1){
				gl.glRotated(-handAngle, 0, 1, 0);
				glut.glutSolidCylinder(0.05,0.35,20,20); 
				gl.glRotated(handAngle, 0, 1, 0);
				}
				else if(count%2==0){
					gl.glRotated(handAngle, 0, 1, 0);
					glut.glutSolidCylinder(0.05,0.35,20,20); 
					gl.glRotated(-handAngle, 0, 1, 0);
				}
				else{
					glut.glutSolidCylinder(0.05,0.35,20,20); 
				}
			gl.glRotated(-75, 1, 0, 0);
			//glut.glutSolidCylinder(0.05,0.35,20,20); 
			 gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
			 gl.glDisable(GL2.GL_TEXTURE_GEN_T);
			gl.glTranslated(0, 0, -0.20);
			gl.glRotated(115, 1, 0, 0);
			gl.glTranslated(0, -0.9, 0);
			//gl.glRotated(150, 1, 0, 0);
			//			gl.glRotated(-angle, 0, 1, 0);
//			gl.glRotated(270, 1, 0, 0);
//			glut.glutSolidCylinder(0.1,0.9,20,20); 
//			gl.glRotated(-270, 1, 0, 0);
//			
//			gl.glTranslated(0, 0.9, 0);
//			//gl.glRotated(-90, 0, 1, 0);
//			glut.glutSolidTeapot(0.3);
//			gl.glRotated(90, 0, 1, 0);
			
			
		}
		gl.glPopMatrix();
	}
	
	
    public double[] getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(double[] coordinate) {
		this.coordinate = coordinate;
	}




	public void move(double increasement) {
		count++;
		double radian = Math.toRadians(angle);
		coordinate[0]= this.coordinate[0] + Math.cos(radian) * increasement;
		coordinate[2]= this.coordinate[2] + Math.sin(radian) * increasement;

		if(coordinate[0] < 0) {
    		coordinate[0] = 0;
    	}
    	if(coordinate[2] < 0){
    		coordinate[2] = 0;
    	}
    	if(coordinate[0] > myTerrain.size().getWidth() - 1){
    		coordinate[0] = myTerrain.size().getWidth() - 1;
    	}
    	if(coordinate[2] > myTerrain.size().getHeight() - 1){
    		coordinate[2] = myTerrain.size().getHeight() - 1;
    	}
		double[] now = this.getCoordinate();
    	double[] door1 = portal.getPortalCoordinate();
    	double[] door2 = portal.getTeleCoordinate();
    	increasement = 0.3;
    	if(0<=(now[0]-door1[0])&&(now[0]-door1[0])<=0.5&&Math.abs(now[2]-door1[2])<=0.2){
    		
    		this.makeAngle(normalizeAngle(this.getAngle()-180));
    		radian = Math.toRadians(this.angle);
    		coordinate[0] =door2[0] + Math.cos(radian) * increasement;
    		coordinate[2] =door2[2] + Math.sin(radian) * increasement;
    	}
    	else if(0<=(now[0]-door2[0])&&(now[0]-door2[0])<=0.5&&Math.abs(now[2]-door2[2])<=0.2){
    		System.out.println("lalalalal");
    		this.makeAngle(normalizeAngle(this.getAngle()-180));
    		radian = Math.toRadians(this.angle);
    		coordinate[0] =door1[0] + Math.cos(radian) * increasement;
    		coordinate[2] =door1[2] + Math.sin(radian) * increasement;
  	}
		
		
		//this.coordinate[0] += increasement;
		//setWordCoordinate();
	}
	
	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle += angle;
	}
	public void makeAngle(double angle){
		this.angle = angle;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getWalkAngle() {
		return walkAngle;
	}

	public void setWalkAngle(double walkAngle) {
		this.walkAngle = walkAngle;
	}

	public double getHandAngle() {
		return handAngle;
	}

	public void setHandAngle(double handAngle) {
		this.handAngle = handAngle;
	}
}
