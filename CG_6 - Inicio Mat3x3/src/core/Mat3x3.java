package core;

public class Mat3x3 {
	float mat[][] = new float[3][3];
	
	public Mat3x3() {
		setIdentity();
	}
	public void setIdentity() {
		zera();
		mat[0][0] = 1;
		mat[1][1] = 1;
		mat[2][2] = 1;
	}
	
	public void zera() {
		for(int y = 0; y < 3;y++) {
			for(int x = 0; x < 3;x++) {
				mat[y][x] = 0;
			}
		}
	}
	
	public void setTranslate(float a,float b) {
		zera();
		mat[0][0] = 1;
		mat[1][1] = 1;
		mat[2][2] = 1;
		mat[0][2] = a;
		mat[1][2] = b;
	}
	
	public void setSacale(float a,float b) {
		zera();
		mat[0][0] = a;
		mat[1][1] = b;
		mat[2][2] = 1;
	}
	
	public void setRotate(float ang) {
		zera();
		float rad = ang*0.017453f;
		float sin = (float)Math.sin(rad);
		float cos = (float)Math.cos(rad);
		mat[0][0] = cos;
		mat[0][1] = sin;
		mat[1][0] = -sin;
		mat[1][1] = cos;
		mat[2][2] = 1;
	}
	public void setRotateAxis(float ang,float x0,float y0) {
		zera();
		float rad = ang*0.017453f;
		float sin = (float)Math.sin(rad);
		float cos = (float)Math.cos(rad);
		mat[0][0] = cos;
		mat[0][1] = -sin;
		mat[0][2] = x0*(1-cos)+y0*sin;
		
		mat[1][0] = sin;
		mat[1][1] = cos;
		mat[1][2] = y0*(1-cos)-x0*sin;
		
		mat[2][0] = 0;
		mat[2][1] = 0;
		mat[2][2] = 1;
	}	
}
