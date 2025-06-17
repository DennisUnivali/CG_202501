package obj;

import shaders.ShaderProgram;

public class Object3D {
	public float x;
	public float y;
	public float z;
	
	public float vx = 0;
	public float vy = 0;
	public float vz = 0;
	
	public float raio = 0; 
	
	public boolean vivo = true;
	
	public Object3D(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void DesenhaSe(ShaderProgram shader) {
		
	};
	
	public void SimulaSe(long diftime) {
		
	};
	
}
