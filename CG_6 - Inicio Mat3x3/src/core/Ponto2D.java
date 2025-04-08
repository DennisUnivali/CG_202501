package core;

public class Ponto2D {
	public float x;
	public float y;
	public float w;
	
	public Ponto2D(float x, float y) {
		super();
		this.x = x;
		this.y = y;
		this.w = 1;
	}
	
	

	public Ponto2D(float x, float y, float w) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
	}



	public void multiplicaMat(Mat3x3 mat) {
		float x1 = mat.mat[0][0]*x+mat.mat[0][1]*y+mat.mat[0][2]*w;
		float y1 = mat.mat[1][0]*x+mat.mat[1][1]*y+mat.mat[1][2]*w;
		float w1 = mat.mat[2][0]*x+mat.mat[2][1]*y+mat.mat[2][2]*w;
		
		x = x1;
		y = y1;
		w = w1;
	}
	
//	public void translacao(float a,float b) {
//		x+=a;
//		y+=b;
//	}
//	public void escala(float a,float b) {
//		x=x*a;
//		y=y*b;
//	}
//	public void rotacao(float ang) {
//		float rad = ang*0.017453f;
//		float sin = (float)Math.sin(rad);
//		float cos = (float)Math.cos(rad);
//		float nx = x*cos+y*sin;
//		float ny = -x*sin+y*cos;
//		x = nx;
//		y = ny;
//	}
}
