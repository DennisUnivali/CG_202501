package core3d;

public class Ponto3D {
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Ponto3D(Ponto3D p) {
		x = p.x;
		y = p.y;
		z = p.z;
		w = p.w;
	}
	
	public Ponto3D(float x, float y,float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = 1;
	}
	
	

	public Ponto3D(float x, float y,float z, float w) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public void normalizaW() {
		this.x = x/w;
		this.y = y/w;
		this.z = z/w;
		this.w = w/w;
	}
}
