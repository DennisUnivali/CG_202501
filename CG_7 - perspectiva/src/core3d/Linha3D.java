package core3d;

import java.awt.Graphics2D;

public class Linha3D {
	Ponto3D a;
	Ponto3D b;
	public Linha3D(Ponto3D a, Ponto3D b) {
		super();
		this.a = a;
		this.b = b;
	}
	public Linha3D(float x1,float y1, float z1, float x2, float y2, float z2) {
		this.a = new Ponto3D(x1, y1, z1);
		this.b = new Ponto3D(x2, y2, z2);
	}
	
	public void desenhase(Graphics2D dbg) {
		dbg.drawLine((int)a.x,(int)a.y,(int)b.x,(int)b.y);
	}

}
