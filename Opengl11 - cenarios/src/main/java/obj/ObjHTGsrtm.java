package obj;

import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import Model.Model;

public class ObjHTGsrtm extends Model{
	
	int vbo_vertex_handle;
	int vbo_color_handle;
	int vbo_texture_handle;
	int vbo_normal_handle;
	
	int vertices = 0;
	int vertex_size = 3; // X, Y, Z,
	int color_size = 3; // R, G, B,
	int normal_size = 3;
	int texture_size = 2;
	
	public int data[][];
	public int wh;

	@Override
	public void load() {
		File f = new File("S28W049.hgt");
		int l = (int)f.length();
		System.out.println("l "+l);
		wh = (int)Math.sqrt(l/2);
		
		System.out.println("wh "+wh);
		
		try {
			BufferedInputStream bin = new BufferedInputStream(new FileInputStream(f),1024000);
			DataInputStream din = new DataInputStream(bin);
			
			data = new int[wh][wh];
			for(int j = 0 ; j < wh;j++) {
				for(int i = 0 ; i < wh;i++) {
					data[j][i] = din.readShort();
					//System.out.println(""+data[j][i]);
				}
			}
			
			int dataB[][] = new int[wh/4][wh/4];
			for(int j = 0 ; j < (wh-4);j+=4) {
				for(int i = 0 ; i < (wh-4);i+=4) {
					int soma = 0;
					for(int jy = 0 ; jy < 4;jy++) {
						for(int ix = 0 ; ix < 4;ix++) {
							soma+=data[j+jy][i+ix];
						}
					}
					dataB[j/4][i/4] = (soma/16);
				}
			}
			
			data = dataB;
			wh = wh/4;
			//TIRAR DEPOIS
			//wh = 1000;
			
			vertices = (wh-1)*(wh-1)*6;
			FloatBuffer vertex_data = BufferUtils.createFloatBuffer(vertices * vertex_size);
			FloatBuffer texture_data = BufferUtils.createFloatBuffer(vertices * texture_size);
			FloatBuffer color_data = BufferUtils.createFloatBuffer(vertices * color_size);
			FloatBuffer normal_data = BufferUtils.createFloatBuffer(vertices * normal_size);
			
			int vcount = 0;
			for(int j = 0 ; j < wh-1;j++) {
				for(int i = 0 ; i < wh-1;i++) {
					int p1 = data[j][i];
					int p2 = data[j][i+1];
					int p3 = data[j+1][i+1];
					int p4 = data[j+1][i];
					
					//float passoxz = 0.01f;
					float passoxz = 0.04f;
					
					float v1[] = {i*passoxz, p1*0.001f, j*passoxz}; 
					float v2[] = {(i+1)*passoxz, p2*0.001f, j*passoxz}; 
					float v3[] = {(i+1)*passoxz, p3*0.001f, (j+1)*passoxz}; 
					float v4[] = {i*passoxz, p4*0.001f, (j+1)*passoxz};
					
					vertex_data.put(v1);
					vertex_data.put(v2);
					vertex_data.put(v3);
					
					vertex_data.put(v3);
					vertex_data.put(v4);
					vertex_data.put(v1);
					
					vcount+=6;
					
					float c[] = {1, 1, 1};
					
					color_data.put(c);
					color_data.put(c);
					color_data.put(c);
					
					float t1[] = {0,0}; 
					float t2[] = {1,0}; 
					float t3[] = {1,1}; 
					float t4[] = {0,1};
					
					if(p1<=0) {
						
						float t1b[] = {0.5f,0.0f}; 
						float t2b[] = {1.0f,0.0f}; 
						float t3b[] = {1.0f,0.5f}; 
						float t4b[] = {0.5f,0.5f};						
						
						t1 = t1b;
						t2 = t2b;
						t3 = t3b;
						t4 = t4b;
					}else if(p1 < 4) {
// areia						
						float t1b[] = {0.5f,0.5f}; 
						float t2b[] = {1.0f,0.5f}; 
						float t3b[] = {1.0f,1.0f}; 
						float t4b[] = {0.5f,1.0f};					
						
						t1 = t1b;
						t2 = t2b;
						t3 = t3b;
						t4 = t4b;
					}else if(p1 < 300) {
						float t1b[] = {0.0f,0.0f}; 
						float t2b[] = {0.5f,0.0f}; 
						float t3b[] = {0.5f,0.5f}; 
						float t4b[] = {0.0f,0.5f};						
						
						t1 = t1b;
						t2 = t2b;
						t3 = t3b;
						t4 = t4b;
					}else {
// pedra						
						float t1b[] = {0,0.5f}; 
						float t2b[] = {0.5f,0.5f}; 
						float t3b[] = {0.5f,1.0f}; 
						float t4b[] = {0,1.0f};					
						
						t1 = t1b;
						t2 = t2b;
						t3 = t3b;
						t4 = t4b;
					}
					
					texture_data.put(t1);
					texture_data.put(t2);
					texture_data.put(t3);
					
					texture_data.put(t3);
					texture_data.put(t4);
					texture_data.put(t1);
					
					float nt1[] = calculaVetorNormal(v1,v2,v3);
					float nt2[] = calculaVetorNormal(v3,v4,v1);
					
					//System.out.println("nt1 "+nt1[0]+" "+nt1[1]+" "+nt1[2]);
					
					normal_data.put(nt1);
					normal_data.put(nt1);
					normal_data.put(nt1);
					
					normal_data.put(nt2);
					normal_data.put(nt2);
					normal_data.put(nt2);
				}
			}
			System.out.println("vcount "+vcount);
			
			vertex_data.flip();
			color_data.flip();
			texture_data.flip();
			normal_data.flip();

			vbo_vertex_handle = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo_vertex_handle);
			glBufferData(GL_ARRAY_BUFFER, vertex_data, GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, 0);

			vbo_color_handle = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo_color_handle);
			glBufferData(GL_ARRAY_BUFFER, color_data, GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			vbo_texture_handle = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo_texture_handle);
			glBufferData(GL_ARRAY_BUFFER, texture_data, GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			vbo_normal_handle = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo_normal_handle);
			glBufferData(GL_ARRAY_BUFFER, normal_data, GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void draw() {
		glBindBuffer(GL_ARRAY_BUFFER, vbo_vertex_handle);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0,vertex_size,GL_FLOAT,false,0,0);

		glBindBuffer(GL_ARRAY_BUFFER, vbo_color_handle);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1,color_size,GL_FLOAT,false,0,0);
		//glColorPointer(color_size, GL_FLOAT, 0, 0l);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo_normal_handle);
		glEnableVertexAttribArray(2);
		glVertexAttribPointer(2,normal_size,GL_FLOAT,false,0,0);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo_texture_handle);
		glEnableVertexAttribArray(3);
		glVertexAttribPointer(3,texture_size,GL_FLOAT,false,0,0);
		//glColorPointer(color_size, GL_FLOAT, 0, 0l);

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);

		glDrawArrays(GL_TRIANGLES, 0, vertices);
		
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
	}
	
	public float[] calculaVetorNormal(float p1[],float p2[],float p3[]) {	
	    float v1[] = new float[3];
	    float v2[] = new float[3];
	    float n[] = new float[3];
	    
		v1[0] = p2[0] - p1[0];
	    v1[1] = p2[1] - p1[1];
	    v1[2] = p2[2] - p1[2];
	
	    /* Encontra vetor v2 */
	    v2[0] = p3[0] - p1[0];
	    v2[1] = p3[1] - p1[1];
	    v2[2] = p3[2] - p1[2];
	
	    /* Calculo do produto vetorial de v1 e v2 */
	    n[0] = (v1[1] * v2[2]) - (v1[2] * v2[1]);
	    n[1] = (v1[2] * v2[0]) - (v1[0] * v2[2]);
	    n[2] = (v1[0] * v2[1]) - (v1[1] * v2[0]);
	
	    /* normalizacao de n */
	    float len = (float)Math.sqrt(n[0]*n[0] + n[1]*n[1] + n[2]*n[2]);
	    n[0] /= -len;
	    n[1] /= -len;
	    n[2] /= -len;
	    
	    return n;
	}

}
