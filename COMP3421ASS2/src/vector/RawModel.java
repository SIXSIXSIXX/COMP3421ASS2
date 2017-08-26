package vector;

public class RawModel {
	private int[] vaoID;
	private int vertexCount;
	
	public RawModel(int[] vaoID, int vetexCount){
		
		this.vaoID = vaoID;
		this.vertexCount = vetexCount;
	}

	public int[] getVaoID() {
		return vaoID;
	}

	public void setVaoID(int[] vaoID) {
		this.vaoID = vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}
}
