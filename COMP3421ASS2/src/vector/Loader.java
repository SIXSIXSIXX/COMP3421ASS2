package vector;


import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;




public class Loader {
	private GLAutoDrawable drawable= null;
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	public RawModel loadToVAO(float[] positions,GLAutoDrawable drawable){
		this.drawable = drawable;
		int[] vaoID = createVAO();
		storeDataInAttributeList(0,positions);
		unbindVAO(drawable);
		return new RawModel(vaoID, positions.length/3);
	}
	
	public void cleanUP(GL2 gl){
		//for//(int vao:vaos){
			//gl.glDeleteVertexArrays(2,vao,0);
		//}
	}
	private int[] createVAO(){
		int vaoIds[] = new int[1];
		GL2 gl = drawable.getGL().getGL2();
		gl.glGenVertexArrays(2,vaoIds ,0);
		vaos.add(vaoIds[0]);
		gl.glBindVertexArray(vaoIds[0]);
		return vaoIds;
		
	}
	private void storeDataInAttributeList(int attributeNO, float[] data){
		GL2 gl = drawable.getGL().getGL2();
		int vboID[] = new int[1];
		gl.glGenBuffers(4,vboID,0);
		vbos.add(vboID[0]);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboID[0]);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
	//	gl.glBufferData(GL.GL_ARRAY_BUFFER, buffer, GL.GL_STATIC_DRAW);
		
	    gl.glBufferData(GL2.GL_ARRAY_BUFFER, //Type of buffer 
	    		data.length*Float.SIZE/8, //size needed
	    		buffer,  //The data to load
	    		GL2.GL_STATIC_DRAW); //We don't intend to modify this data after loading it
		
	    
	    gl.glVertexPointer(3, //3 coordinates per vertex 
		              GL.GL_FLOAT, //each co-ordinate is a float 
		              0, //There are no gaps in data between co-ordinates 
		              0); //Co-ordinates are at the start of the current array buffer
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
		}
	private void unbindVAO(GLAutoDrawable drawable){
	 	GL2 gl = drawable.getGL().getGL2();
		gl.glBindVertexArray(0);
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data){
		return Buffers.newDirectFloatBuffer(data);
	}
}
