package vector;

import com.jogamp.opengl.GL2;

public class Renderer {

	
	
	public void  render(RawModel model, GL2 gl){
		
		gl.glBindVertexArray(model.getVaoID()[0]);
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glDrawArrays(GL2.GL_TRIANGLES,0,model.getVertexCount());  
		gl.glBindVertexArray(0);
	}
}
