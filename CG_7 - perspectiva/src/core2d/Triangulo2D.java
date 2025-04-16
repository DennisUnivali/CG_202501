package core2d;

import java.awt.Graphics2D;

public class Triangulo2D {
	Ponto2D pa;
	Ponto2D pb;
	Ponto2D pc;
	public Triangulo2D(Ponto2D a, Ponto2D b, Ponto2D c) {
		super();
		this.pa = a;
		this.pb = b;
		this.pc = c;
	}
	
	public void desenhase(Graphics2D dbg) {
		dbg.drawLine((int)pa.x,(int)pa.y,(int)pb.x,(int)pb.y);
		dbg.drawLine((int)pb.x,(int)pb.y,(int)pc.x,(int)pc.y);
		dbg.drawLine((int)pc.x,(int)pc.y,(int)pa.x,(int)pa.y);
	}
	
	public void translacao(float a,float b) {
		Mat3x3 m = new Mat3x3();
		m.setTranslate(a, b);
		pa.multiplicaMat(m);
		pb.multiplicaMat(m);
		pc.multiplicaMat(m);
	}
	public void escala(float a,float b) {
		Mat3x3 m = new Mat3x3();
		m.setSacale(a, b);
		pa.multiplicaMat(m);
		pb.multiplicaMat(m);
		pc.multiplicaMat(m);
	}	
	
	public void rotacao(float ang,float x0,float y0) {
		Mat3x3 m = new Mat3x3();
		m.setRotateAxis(ang,x0,y0);
		pa.multiplicaMat(m);
		pb.multiplicaMat(m);
		pc.multiplicaMat(m);
	}
}
