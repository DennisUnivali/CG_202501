package core3d;

public class Mat4x4 {
	double mat[][] = new double[4][4];
	
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
	
	public void setTranslate(double a,double b,double c) {
		zera();
		mat[0][0] = 1;
		mat[1][1] = 1;
		mat[2][2] = 1;
		mat[3][3] = 1;
		mat[0][3] = a;
		mat[1][3] = b;
		mat[2][3] = c;
	}
	
	public void setSacale(double a,double b,double c) {
		zera();
		mat[0][0] = a;
		mat[1][1] = b;
		mat[2][2] = c;
		mat[3][3] = 1;
	}
	
	public void setRotY(double ang) {
		double angrad = ang*0.01745329;
		zera();
		mat[0][0] = Math.cos(angrad);
		mat[0][1] = 0;
		mat[0][2] = -Math.sin(angrad);
		
		mat[1][1] = 1;
		
		mat[2][0] = Math.sin(angrad);
		mat[2][1] = 0;
		mat[2][2] = Math.cos(angrad);
		
		mat[3][3] = 1;
	}
	
	public void setRotX(double ang) {
		double angrad = ang*0.01745329;
		zera();
		
		mat[0][0] = 1;
		
		mat[1][0] = 0;
		mat[1][1] = Math.cos(angrad);
		mat[1][2] = -Math.sin(angrad);
		
		mat[2][0] = 0;
		mat[2][1] = Math.sin(angrad);
		mat[2][2] = Math.cos(angrad);
		
		mat[3][3] = 1;
	}
	
	public void setPerspectiva(double d) {

		zera();
		mat[0][0] = 1;
		mat[0][1] = 0;
		mat[0][2] = 0;
		mat[0][3] = 0;

		mat[1][0] = 0;
		mat[1][1] = 1;
		mat[1][2] = 0;
		mat[1][3] = 0;
		
		mat[2][0] = 0;
		mat[2][1] = 0;
		mat[2][2] = 0;
		mat[2][3] = 0;
		
		mat[3][2] = 1/d;
		mat[3][3] = 1;
	}
	
	public void set(Mat4x4 m) {
		for(int ym = 0; ym < 4; ym++ ) {
			for(int xm = 0; xm < 4; xm++ ) {;
				mat[ym][xm] = m.mat[ym][xm];
			}
		}
	}
	
	public Mat4x4 multiplicaMatrix(Mat4x4 m2) {
		Mat4x4 rmat = new Mat4x4();
		for(int ym1 = 0; ym1 < 4; ym1++ ) {
			for(int xm2 = 0; xm2 < 4; xm2++ ) {;
				double total = 0;
				for(int pos = 0; pos < 4;pos++) {
					total+= mat[ym1][pos]*m2.mat[pos][xm2];
				}
				rmat.mat[ym1][xm2] = total;
			}
		}
		
		return rmat;
	}
	public Ponto3D multiplicaVetor(Ponto3D vet) {
		double x1 = mat[0][0]*vet.x+mat[0][1]*vet.y+mat[0][2]*vet.z+mat[0][3]*vet.w;
		double y1 = mat[1][0]*vet.x+mat[1][1]*vet.y+mat[1][2]*vet.z+mat[1][3]*vet.w;
		double z1 = mat[2][0]*vet.x+mat[2][1]*vet.y+mat[2][2]*vet.z+mat[2][3]*vet.w;
		double w1 = mat[3][0]*vet.x+mat[3][1]*vet.y+mat[3][2]*vet.z+mat[3][3]*vet.w;
		
		if(w1!=1) {
			x1 = x1/w1;
			y1 = y1/w1;
			z1 = z1/w1;
			w1 = 1;
		}
		
		return new Ponto3D((float)x1,(float)y1,(float)z1,(float)w1);
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int ym = 0; ym < 4; ym++ ) {
			s+="[";
			for(int xm = 0; xm < 4; xm++ ) {;
				s+= mat[ym][xm]+"\t";
			}
			s+="]\n";
		}
		return s;
	}
	

}

