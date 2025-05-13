import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import obj.ObjModel;

public class objeto3D {
	ObjModel modelo;
	
	float x;
	float y;
	float z;
	
	float scale = 0.01f;
	
	public objeto3D(ObjModel modelo) {
		this.modelo = modelo;
	}
	public void desenha(int texture) {
		glPushMatrix();
		glBindTexture ( GL_TEXTURE_2D, texture );
		
		glTranslatef(x, y, z);
		glScalef(scale, scale, scale);
		modelo.desenhaSe();
		
		glPopMatrix();
	}
}
