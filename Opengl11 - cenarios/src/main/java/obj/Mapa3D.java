package obj;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.*;

import shaders.ShaderProgram;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Model.Model;
import Model.VboCube;

public class Mapa3D extends Object3D {
	//Sphere sphere = new Sphere();
	public Vector3f cor = new Vector3f();
	public ObjHTGsrtm model = null;
	
	FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(16);
	public float rotvel = 0;
	float ang = 0;
	
	public Mapa3D(float x, float y, float z, float r) {
		super(x, y, z);
		raio = r;
	}
	
	@Override
	public void DesenhaSe(ShaderProgram shader) {

		Matrix4f modelm = new Matrix4f();
		modelm.setIdentity();
		
		modelm.translate(new Vector3f(x,y,z));
		modelm.scale(new Vector3f(raio,raio,raio));
		modelm.rotate(ang,new Vector3f(0.0f,1.0f,0.0f));
		
		int modellocation = glGetUniformLocation(shader.programID, "model");
		
		modelm.storeTranspose(matrixBuffer);
		matrixBuffer.flip();
		glUniformMatrix4fv(modellocation, false, matrixBuffer);	
		
		model.draw();
	}
	
	@Override
	public void SimulaSe(long diftime) {
		super.SimulaSe(diftime);
		
		x += vx*diftime/1000.0f;
		y += vy*diftime/1000.0f;
		z += vz*diftime/1000.0f;
		
		ang+=rotvel*diftime/1000.0f;
	}
	
	public boolean testaColisao(float xt, float yt, float zt, float rt) {
		float prx = xt - x;
		float pry = yt - y;
		float prz = zt - z;
		
		float postilex = prx/(0.04f*raio);
		float postilez = prz/(0.04f*raio);
		
		if(postilex<0) {
			return false;
		}
		if(postilez<0) {
			return false;
		}
		if(postilex>=(model.wh-1)) {
			return false;
		}
		if(postilez>=(model.wh-1)) {
			return false;
		}
		
		float a1 = model.data[((int)postilez)][((int)postilex)]*0.001f*raio;
		float a2 = model.data[((int)postilez)][((int)postilex)+1]*0.001f*raio;
		float a3 = model.data[((int)postilez)+1][((int)postilex)]*0.001f*raio;
		float a4 = model.data[((int)postilez)+1][((int)postilex)+1]*0.001f*raio;
		
		float a = (a1+a2+a3+a4)/4.0f;
		
		if(a > (yt-rt) && a < (yt + rt) ) {
			//System.out.println(" COLIDIU "+a+" "+yt+" "+rt);
			return true;
		}
		else {
			return false;
		}
		
		
	}
}
