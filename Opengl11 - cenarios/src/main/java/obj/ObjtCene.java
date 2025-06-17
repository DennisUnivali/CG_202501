package obj;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.*;

import shaders.ShaderProgram;
import util.Utils3D;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Model.Model;
import Model.VboCube;

public class ObjtCene extends Object3D {
	//Sphere sphere = new Sphere();
	public Vector3f cor = new Vector3f();
	public Model model = null;
	public Matrix4f modelAjust = null;
	
	FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(16);
	public float rotvel = 0;
	float ang = 0;
	public int texture = -1;
	public Vector4f Front = new Vector4f(0.0f, 0.0f, -1.0f,1.0f);
	public Vector4f UP = new Vector4f(0.0f, 1.0f, 0.0f,1.0f);
	public Vector4f Right = new Vector4f(1.0f, 0.0f, 0.0f,1.0f);
	
	public ObjtCene(float x, float y, float z, float r) {
		super(x, y, z);
		raio = r;
	}
	
	@Override
	public void DesenhaSe(ShaderProgram shader) {

		if(texture!=-1) {
			glBindTexture(GL_TEXTURE_2D, texture);
		}
		
		Matrix4f modelm1 = new Matrix4f();
		modelm1.translate(new Vector3f(x,y,z));
		Matrix4f modelm = Utils3D.positionMatrix(Front, UP, Right); //new Matrix4f();
		Matrix4f.mul(modelm1,modelm, modelm);

		modelm.scale(new Vector3f(raio,raio,raio));
		
		
		if(modelAjust!=null) {
			Matrix4f.mul(modelm,modelAjust, modelm);
		}
		
		
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
}
