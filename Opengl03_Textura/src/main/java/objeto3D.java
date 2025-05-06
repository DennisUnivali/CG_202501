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
	
	public objeto3D(ObjModel modelo) {
		this.modelo = modelo;
	}
	public void desenha(int texture) {
		glPushMatrix();
		glBindTexture ( GL_TEXTURE_2D, texture );
		
		glTranslatef(x, y, z);
		glScalef(0.01f, 0.01f, 0.01f);
		modelo.desenhaSe();
		
		glPopMatrix();
	}
}
