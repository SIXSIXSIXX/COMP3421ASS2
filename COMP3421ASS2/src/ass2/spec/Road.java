package ass2.spec;

import java.util.ArrayList;
import java.util.List;


import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

/**
 * COMMENT: Comment Road 
 *
 * @author malcolmr
 */
public class Road {

    private List<Double> myPoints;
    private double myWidth;
    private Terrain myTerrain = null;
    /** 
     * Create a new road starting at the specified point
     */
    public Road(double width, double x0, double y0) {
        myWidth = width;
        myPoints = new ArrayList<Double>();
        myPoints.add(x0);
        myPoints.add(y0);
    }

    /**
     * Create a new road with the specified spine 
     *
     * @param width
     * @param spine
     */
    public Road(double width, double[] spine) {
        myWidth = width;
        myPoints = new ArrayList<Double>();
        for (int i = 0; i < spine.length; i++) {
            myPoints.add(spine[i]);
        }
    }

    /**
     * The width of the road.
     * 
     * @return
     */
    public double width() {
        return myWidth;
    }

    /**
     * Add a new segment of road, beginning at the last point added and ending at (x3, y3).
     * (x1, y1) and (x2, y2) are interpolated as bezier control points.
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     */
    public void addSegment(double x1, double y1, double x2, double y2, double x3, double y3) {
        myPoints.add(x1);
        myPoints.add(y1);
        myPoints.add(x2);
        myPoints.add(y2);
        myPoints.add(x3);
        myPoints.add(y3);        
    }
    
    /**
     * Get the number of segments in the curve
     * 
     * @return
     */
    public int size() {
        return myPoints.size() / 6;
    }

    /**
     * Get the specified control point.
     * 
     * @param i
     * @return
     */
    public double[] controlPoint(int i) {
        double[] p = new double[2];
        p[0] = myPoints.get(i*2);
        p[1] = myPoints.get(i*2+1);
        return p;
    }
    
    /**
     * Get a point on the spine. The parameter t may vary from 0 to size().
     * Points on the kth segment take have parameters in the range (k, k+1).
     * 
     * @param t
     * @return
     */
    public double[] point(double t) {
        int i = (int)Math.floor(t);
        t = t - i;
        i *= 6;
        
        double x0 = myPoints.get(i++);
        double y0 = myPoints.get(i++);
        double x1 = myPoints.get(i++);
        double y1 = myPoints.get(i++);
        double x2 = myPoints.get(i++);
        double y2 = myPoints.get(i++);
        double x3 = myPoints.get(i++);
        double y3 = myPoints.get(i++);
        
        double[] p = new double[2];

        p[0] = b(0, t) * x0 + b(1, t) * x1 + b(2, t) * x2 + b(3, t) * x3;
        p[1] = b(0, t) * y0 + b(1, t) * y1 + b(2, t) * y2 + b(3, t) * y3;        
        
        return p;
    }
    
    /**
     * Calculate the Bezier coefficients
     * 
     * @param i
     * @param t
     * @return
     */
    private double b(int i, double t) {
        
        switch(i) {
        
        case 0:
            return (1-t) * (1-t) * (1-t);

        case 1:
            return 3 * (1-t) * (1-t) * t;
            
        case 2:
            return 3 * (1-t) * t * t;

        case 3:
            return t * t * t;
        }
        
        // this should never happen
        throw new IllegalArgumentException("" + i);
    }
    
    public double gradient(double[] from, double[] to){
    	return (from[1]-to[1])/(from[0]-to[0]); 	
    }
    
 //   public double intercept(double gradient, double[] point){
  //  	return (point[1]-gradient*point[0]);
   // }
    
    public double[] pointsOnLine(double[] from, double[] to){
    	double normalGradinet = -1/gradient(from, to);

    	double[] fromPoint = solution(normalGradinet,from);
    	double[] toPoint = solution(normalGradinet,to);
    	
    	double[] result = new double[8];
    	result[0] = fromPoint[0];
    	result[1] = fromPoint[1];
    	result[2] = fromPoint[2];
    	result[3] = fromPoint[3];
    	result[4] = toPoint[0];
    	result[5] = toPoint[1];
    	result[6] = toPoint[2];
    	result[7] = toPoint[3];
    	
    	return result;
    }
    
    public double[] solution(double gradient,double[] point){
    	double[] result = new double[4]; 
    	//x1
    	result[0] = point[0]+(this.myWidth/2)/Math.sqrt(1+gradient*gradient);
    	result[1] = gradient*(result[0]-point[0])+point[1];
    	result[2] = point[0]-(this.myWidth/2)/Math.sqrt(1+gradient*gradient);
    	result[3] = gradient*(result[2]-point[0])+point[1];
    	return result;	
    }
    
//   public void draw(GL2 gl, MyTexture[] texture){
//	   double increase = 0.01;
//	    for(double t = 0.0;t<this.size()-increase; t+=increase){
//		    double[] from = point(t);
//		    double[] to = point(t+increase);
//			double[] pointsTodraw = pointsOnLine(from, to);
//			if(roadAltitude(from[0],from[1])<roadAltitude(to[0],to[1])){
//				if(roadAltitude(from[0],Math.ceil(from[1]))==roadAltitude(to[0],Math.floor(to[1]))){
//					double[] newto = new double[2];
//					double angle = Math.toDegrees(Math.atan(gradient(from,to)));
//					newto[1] =Math.round(to[1]);
//					newto[0] = newto[1]/Math.tan(angle);
//					pointsTodraw = pointsOnLine(from, newto);
//					draww(gl,pointsTodraw,from,newto);
//					pointsTodraw = pointsOnLine(newto,to);
//					draww(gl,pointsTodraw,newto,to);
//				}
//				else if(roadAltitude(Math.ceil(from[0]),from[1])==roadAltitude(Math.floor(to[0]),to[1])){
//					double[] newto = new double[2];
//					double angle = Math.toDegrees(Math.atan(gradient(from,to)));
//					newto[0] =Math.round(from[0]);
//					newto[1] = newto[0]*Math.tan(angle);
//					pointsTodraw = pointsOnLine(from, newto);
//					draww(gl,pointsTodraw,from,newto);
//					pointsTodraw = pointsOnLine(newto,to);
//					draww(gl,pointsTodraw,newto,to);
//				}
//				else{
//					pointsTodraw = pointsOnLine(from, to);
//					draww(gl,pointsTodraw,from,to);
//				}
//			}			
//			else if(roadAltitude(from[0],from[1])>roadAltitude(to[0],to[1])){
//				System.out.println(from[0]+" "+from[1]+" "+to[0]+" "+to[1]);
//				if(roadAltitude(from[0],Math.ceil(from[1]))==roadAltitude(to[0],Math.floor(to[1]))){
//					double[] newto = new double[2];
//					double angle = Math.toDegrees(Math.atan(gradient(from,to)));
//					newto[1] =Math.round(to[1]);
//					newto[0] = newto[1]/Math.tan(angle);
//					pointsTodraw = pointsOnLine(from, newto);
//					draww(gl,pointsTodraw,from,newto);
//					pointsTodraw = pointsOnLine(newto,to);
//					draww(gl,pointsTodraw,newto,to);
//				}
//				else if(roadAltitude(Math.ceil(from[0]),from[1])==roadAltitude(Math.floor(to[0]),to[1])){
//					double[] newto = new double[2];
//					double angle = Math.toDegrees(Math.atan(gradient(from,to)));
//					newto[0] =Math.round(to[0]);
//					newto[1] = newto[0]*Math.tan(angle);
//					pointsTodraw = pointsOnLine(from, newto);
//					draww(gl,pointsTodraw,from,newto);
//					pointsTodraw = pointsOnLine(newto,to);
//					draww(gl,pointsTodraw,newto,to);
//				}
//				else{
//					pointsTodraw = pointsOnLine(from, to);
//					draww(gl,pointsTodraw,from,to);
//				}
//			}
//			else{
//				pointsTodraw = pointsOnLine(from, to);
//				draww(gl,pointsTodraw,from,to);
//			}
//	    }
//   }
//
//    
//    
//    
//   public void draww(GL2 gl,double[] pointsTodraw,double[] from,double[] to){
//	 
//		   		//gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
//		       // gl.glEnable(GL2.GL_TEXTURE_GEN_S);
//			   // gl.glEnable(GL2.GL_TEXTURE_GEN_T);
//		   		gl.glBegin(GL2.GL_QUADS);
//		   		{
//		   			gl.glTexCoord2d(0, 0);
//		   			gl.glVertex3d(pointsTodraw[0], roadAltitude(from[0],from[1]), pointsTodraw[1]);
//		   			//System.out.println("first   "+pointsTodraw[0]+"   "+pointsTodraw[1]);
//		   			//drawPoints(pointsTodraw[0],pointsTodraw[1],pointsTodraw[2], pointsTodraw[3]);
//		   			
//		   			gl.glTexCoord2d(this.width(), 0);
//		   			gl.glVertex3d(pointsTodraw[2], roadAltitude(from[0],from[1]), pointsTodraw[3]);
//		   			//drawPoints(pointsTodraw[2],pointsTodraw[1],pointsTodraw[2], pointsTodraw[3],gl);
//		   			
//		   			gl.glTexCoord2d(this.width(), 0.3);
//		   			gl.glVertex3d(pointsTodraw[6],roadAltitude(to[0],to[1]), pointsTodraw[7]);
//		   			//drawPoints(pointsTodraw[6],pointsTodraw[7],pointsTodraw[4], pointsTodraw[5]);
//		   			gl.glTexCoord2d(0, 0.3);
//		   			gl.glVertex3d(pointsTodraw[4],roadAltitude(to[0],to[1]), pointsTodraw[5]);
//		   		}
//		   		gl.glEnd();	
//		    	//gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
//			   // gl.glDisable(GL2.GL_TEXTURE_GEN_T);
//		    } 
//
    
//    public void draw(GL2 gl, MyTexture[] texture){
//	    double increase = 0.1;
//	    for(double t = 0.0;t<this.size()-increase; t+=increase){
//		    double[] from = point(t);
//		    double[] to = point(t+increase);
//			double[] pointsTodraw = pointsOnLine(from, to);
//	   		gl.glBindTexture(GL.GL_TEXTURE_2D, texture[3].getTextureId()); 
//	   		//gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
//	       // gl.glEnable(GL2.GL_TEXTURE_GEN_S);
//		   // gl.glEnable(GL2.GL_TEXTURE_GEN_T);
//	   		gl.glBegin(GL2.GL_POLYGON);
//	   		{
//	   			gl.glTexCoord2d(0, 0);
//	   			gl.glVertex3d(pointsTodraw[0], roadAltitude(from[0],from[1]), pointsTodraw[1]);
//	   			//System.out.println("first   "+pointsTodraw[0]+"   "+pointsTodraw[1]);
//	   			//drawPoints(pointsTodraw[0],pointsTodraw[1],pointsTodraw[2], pointsTodraw[3]);
//	   			
//	   			gl.glTexCoord2d(this.width(), 0);
//	   			gl.glVertex3d(pointsTodraw[2], roadAltitude(from[0],from[1]), pointsTodraw[3]);
//	   			//drawPoints(pointsTodraw[2],pointsTodraw[1],pointsTodraw[2], pointsTodraw[3],gl);
//	   			
//	   			gl.glTexCoord2d(this.width(), 0.3);
//	   			gl.glVertex3d(pointsTodraw[6],roadAltitude(to[0],to[1]), pointsTodraw[7]);
//	   			//drawPoints(pointsTodraw[6],pointsTodraw[7],pointsTodraw[4], pointsTodraw[5]);
//	   			gl.glTexCoord2d(0, 0.3);
//	   			gl.glVertex3d(pointsTodraw[4],roadAltitude(to[0],to[1]), pointsTodraw[5]);
//	   		}
//	   		gl.glEnd();	
//	    	//gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
//		   // gl.glDisable(GL2.GL_TEXTURE_GEN_T);
//	    }  
//    	
//   
//		}
    public double[] crossProduct(double[] a,double[] b){
    	double[] product = {a[1]*b[2]-a[2]*b[1],a[2]*b[0]-a[0]*b[2],a[0]*b[1]-a[1]*b[0]};

    	return product;
    }		
    public void draw(GL2 gl, MyTexture[] texture){
	    double increase = 0.1;
	    for(double t = 0.0;t<this.size()-increase; t+=increase){
		    double[] from = point(t);
		    double[] to = point(t+increase);
			double[] pointsTodraw = pointsOnLine(from, to);
	   		//gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
	       // gl.glEnable(GL2.GL_TEXTURE_GEN_S);
		   // gl.glEnable(GL2.GL_TEXTURE_GEN_T);
	   		double[] a = {pointsTodraw[0]-pointsTodraw[2], roadAltitude(from[0],from[1])-roadAltitude(from[0], -from[1]), pointsTodraw[1]-pointsTodraw[3]}  ;
	   		double[] b = {pointsTodraw[6]-pointsTodraw[2], roadAltitude(to[0],to[1])-roadAltitude(from[0], -from[1]), pointsTodraw[7]-pointsTodraw[3]};
	   		double[] normal =crossProduct(a,b);
	   		gl.glBindTexture(GL.GL_TEXTURE_2D, texture[3].getTextureId()); 
	   		gl.glBegin(GL2.GL_POLYGON);
	   		{   
	   			gl.glNormal3d(-normal[0], -normal[1], -normal[2]);
	   			gl.glTexCoord2d(0, 0);
	   			gl.glVertex3d(pointsTodraw[0], roadAltitude(from[0],from[1]), pointsTodraw[1]);
	   			//System.out.println("first   "+pointsTodraw[0]+"   "+pointsTodraw[1]);
	   			//drawPoints(pointsTodraw[0],pointsTodraw[1],pointsTodraw[2], pointsTodraw[3]);
	   			
	   			gl.glTexCoord2d(this.width(), 0);
	   			gl.glVertex3d(pointsTodraw[2], roadAltitude(from[0],from[1]), pointsTodraw[3]);
	   			//drawPoints(pointsTodraw[2],pointsTodraw[1],pointsTodraw[2], pointsTodraw[3],gl);
	   			
	   			gl.glTexCoord2d(this.width(), 0.3);
	   			gl.glVertex3d(pointsTodraw[6],roadAltitude(to[0],to[1]), pointsTodraw[7]);
	   			//drawPoints(pointsTodraw[6],pointsTodraw[7],pointsTodraw[4], pointsTodraw[5]);
	   			gl.glTexCoord2d(0, 0.3);
	   			gl.glVertex3d(pointsTodraw[4],roadAltitude(to[0],to[1]), pointsTodraw[5]);
	   		}
	   		gl.glEnd();	
	    	//gl.glDisable(GL2.GL_TEXTURE_GEN_S); 
		   // gl.glDisable(GL2.GL_TEXTURE_GEN_T);
	    }  
    	
   
		}
	   	
	   	
	   
   
   
	
	public List<Double> getPoints(double x0, double z0, double x1, double z1){
		List<Double> points = new ArrayList<Double>();
		//vertical line
		double radians = Math.atan((z1 - z0) / (x1 - x0));
		double distance = this.width();
		//System.out.println("angle="+Math.toDegrees(radius));
		
		
		if (x1 < x0){	   
			double tempX = x0; 
			double tempZ = z0;
			x0 = x1;
			z0 = z1;
			x1 = tempX;
			z1 = tempZ;
		}
		
		points.add(x0);
		points.add(z0);
		for (double i=0.02; i < distance ; i+=0.02){
																																																						
			double x = x0+i*Math.cos(radians);
			double z = z0+i*Math.sin(radians);
			//if(x>=x1 && z>=z1) break;
		
			points.add(x);
			points.add(z);
		}
		points.add(x1);
		points.add(z1);
		
		
		
		
		return points;
	}
   
   

   
   
    
    public double roadAltitude(double x, double z){
    	
    	if(x<=this.myTerrain.size().getWidth()-1&&x>=0 &&z<=this.myTerrain.size().getHeight()-1 && z>=0) return	(this.myTerrain.altitude(x,z))+0.01;
		return 0.0;
    	
    	
    }

	public Terrain getMyTerrain() {
		return myTerrain;
	}

	public void setMyTerrain(Terrain myTerrain) {
		this.myTerrain = myTerrain;
	}

}
