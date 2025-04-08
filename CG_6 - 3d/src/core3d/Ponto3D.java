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



	public void multiplicaMat(Mat4x4 mat) {
		float x1 = mat.mat[0][0]*x +mat.mat[0][1]*y +mat.mat[0][2]*z +mat.mat[0][3]*w;
		float y1 = mat.mat[1][0]*x +mat.mat[1][1]*y +mat.mat[1][2]*z +mat.mat[1][3]*w;
		float z1 = mat.mat[2][0]*x +mat.mat[2][1]*y +mat.mat[2][2]*z +mat.mat[2][3]*w;
		float w1 = mat.mat[3][0]*x +mat.mat[3][1]*y +mat.mat[3][2]*z +mat.mat[3][3]*w;
		
		//System.out.println("x "+x+" "+x1);
		//System.out.println("y "+y+" "+y1);
		//System.out.println("z "+z+" "+z1);
		
		x = x1;
		y = y1;
		z = z1;
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
