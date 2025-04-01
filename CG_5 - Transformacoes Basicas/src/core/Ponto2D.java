package core;

public class Ponto2D {
	public float x;
	public float y;
	
	public Ponto2D(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public void translacao(float a,float b) {
		x+=a;
		y+=b;
	}
	public void escala(float a,float b) {
		x=x*a;
		y=y*b;
	}
	public void rotacao(float ang) {
		float rad = ang*0.017453f;
		float sin = (float)Math.sin(rad);
		float cos = (float)Math.cos(rad);
		float nx = x*cos+y*sin;
		float ny = -x*sin+y*cos;
		x = nx;
		y = ny;
	}
}
