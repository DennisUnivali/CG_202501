package core3d;

public class Mat4x4 {
	float mat[][] = new float[4][4];
	
	public Mat4x4() {
		setIdentity();
	}
	public void setIdentity() {
		zera();
		mat[0][0] = 1;
		mat[1][1] = 1;
		mat[2][2] = 1;
		mat[3][3] = 1;
	}
	
	public void zera() {
		for(int y = 0; y < 4;y++) {
			for(int x = 0; x < 4;x++) {
				mat[y][x] = 0;
			}
		}
	}
	
	public void setTranslate(float a,float b,float c) {
		zera();
		mat[0][0] = 1;
		mat[1][1] = 1;
		mat[2][2] = 1;
		
		mat[0][3] = a;
		mat[1][3] = b;
		mat[2][3] = c;
		mat[3][3] = 1;
	}
	
	public void setSacale(float a,float b,float c) {
		zera();
		mat[0][0] = a;
		mat[1][1] = b;
		mat[2][2] = c;
		mat[3][3] = 1;
	}
	
	public void setRotateY(float ang) {
		zera();
		float rad = ang*0.017453f;
		float sin = (float)Math.sin(rad);
		float cos = (float)Math.cos(rad);
		mat[0][0] = cos;
		mat[0][1] = 0;
		mat[0][2] = -sin;
		mat[0][3] = 0;
		
		mat[1][0] = 0;
		mat[1][1] = 1;
		mat[1][2] = 0;
		mat[1][3] = 0;
		
		mat[2][0] = sin;
		mat[2][1] = 0;
		mat[2][2] = cos;
		mat[2][3] = 0;
		
		mat[3][0] = 0;
		mat[3][1] = 0;
		mat[3][2] = 0;
		mat[3][3] = 1;
	}
//	public void setRotateAxis(float ang,float x0,float y0) {
//		zera();
//		float rad = ang*0.017453f;
//		float sin = (float)Math.sin(rad);
//		float cos = (float)Math.cos(rad);
//		mat[0][0] = cos;
//		mat[0][1] = -sin;
//		mat[0][2] = x0*(1-cos)+y0*sin;
//		
//		mat[1][0] = sin;
//		mat[1][1] = cos;
//		mat[1][2] = y0*(1-cos)-x0*sin;
//		
//		mat[2][0] = 0;
//		mat[2][1] = 0;
//		mat[2][2] = 1;
//	}	
}
