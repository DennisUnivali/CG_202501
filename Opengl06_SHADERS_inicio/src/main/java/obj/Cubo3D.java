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

import Model.VboCube;

public class Cubo3D extends Object3D {
	//Sphere sphere = new Sphere();
	public Vector3f cor = new Vector3f();
	public VboCube vbocube = null;
	
	FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(16);
	
	public Cubo3D(float x, float y, float z, float r) {
		super(x, y, z);
		raio = r;
	}
	
	@Override
	public void DesenhaSe(ShaderProgram shader) {
//		glPushMatrix();
//		
//	    glDisable(GL_TEXTURE_2D);
//	    glColor3f(cor.x, cor.y, cor.z);
//	    
//		glTranslatef(x,y,z);
//		
//		sphere.draw(raio, 16, 16);
//		
//		glPopMatrix();
		
		Matrix4f modelm = new Matrix4f();
		modelm.setIdentity();
		
		//System.out.println(""+x+" "+y+" "+z);
		modelm.translate(new Vector3f(x,y,z));
		modelm.scale(new Vector3f(raio,raio,raio));
		
		int modellocation = glGetUniformLocation(shader.programID, "model");
		
		modelm.storeTranspose(matrixBuffer);
		matrixBuffer.flip();
		glUniformMatrix4fv(modellocation, false, matrixBuffer);	
		
		vbocube.draw();
	}
	
	@Override
	public void SimulaSe(long diftime) {
		super.SimulaSe(diftime);
		
		x += vx*diftime/1000.0f;
		y += vy*diftime/1000.0f;
		z += vz*diftime/1000.0f;
	}
}
