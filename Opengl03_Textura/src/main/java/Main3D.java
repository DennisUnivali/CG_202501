
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import obj.ObjModel;
import util.TextureLoader;

import java.awt.image.BufferedImage;

//import com.sun.org.apache.xerces.internal.dom.DeepNodeListImpl;

import java.nio.*;
import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main3D {

	// The window handle
	private long window;
	
	float angTankViewX = 0;
	float angTankViewY = 0;
	
	float angTowerViewY = 0;
	
	float angCanonViewZ = 0;
	
	public Random rnd = new Random();

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(800, 600, "Hello World!", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated
		// or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		
			if ( key == GLFW_KEY_W) {
				angTankViewX+=5;
			}
			if ( key == GLFW_KEY_S) {
				angTankViewX-=5;
			}
			if ( key == GLFW_KEY_A) {
				angTankViewY+=5;
			}
			if ( key == GLFW_KEY_D) {
				angTankViewY-=5;
			}
			
			if ( key == GLFW_KEY_N) {
				angTowerViewY+=5;
			}
			if ( key == GLFW_KEY_M) {
				angTowerViewY-=5;
			}
			
			if ( key == GLFW_KEY_G) {
				angCanonViewZ+=5;
			}
			if ( key == GLFW_KEY_B) {
				angCanonViewZ-=5;
			}
		
		});

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}

	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.

		GL.createCapabilities();

		
		BufferedImage imggato = TextureLoader.loadImage("texturaCamoCat.jpg");
		BufferedImage gatorgba = new BufferedImage(imggato.getWidth(), imggato.getHeight(), BufferedImage.TYPE_INT_ARGB);
		gatorgba.getGraphics().drawImage(imggato, 0, 0, null);
		int tgato = TextureLoader.loadTexture(imggato);
		System.out.println("tgato "+tgato);
		
		BufferedImage imgx35 = TextureLoader.loadImage("x35text.jpg");
		int tx35 = TextureLoader.loadTexture(imgx35);
		System.out.println("tx35 "+tx35);

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);



		ObjModel tankObj = new ObjModel();
		tankObj.loadObj("tank.obj");
		tankObj.desenhaTextureMapping();
		
		ObjModel mig29 = new ObjModel();
		mig29.loadObj("Mig_29_obj.obj");
		
		ObjModel x35 = new ObjModel();
		x35.loadObj("x-35_obj.obj");
		
		
		ArrayList<objeto3D> lista = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			objeto3D obj = new objeto3D(x35);
			obj.x = (rnd.nextFloat()*10)-5;
			obj.y = (rnd.nextFloat()*10)-5;
			obj.z = (-(rnd.nextFloat()*5))-4;
			lista.add(obj);
		}
		
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.

		int frame = 0;
		long lasttime = System.currentTimeMillis();

		int angle = 0;
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45, 600f / 800f, 0.5f, 100);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		while (!glfwWindowShouldClose(window)) {
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			glEnable(GL_LIGHTING);
			glShadeModel(GL_SMOOTH);

			//float senoang = (float) Math.sin(Math.toRadians(angle));
			//float cosang = (float) Math.cos(Math.toRadians(angle));
			glLoadIdentity();

			float[] lightAmbient = { 0.1f, 0.1f, 0.1f, 0.5f };
			float[] lightDiffuse = { 0.5f, 0.5f, 0.5f, 0.5f };
			float[] lightPosition = { 0.0f, 0.0f, 0.0f, 1.0f };
			//float[] lightPosition = { 0.0f, senoang * 600f, cosang * 600f, 1.0f };

			glLightfv(GL_LIGHT0, GL_AMBIENT, lightAmbient);
			glLightfv(GL_LIGHT0, GL_DIFFUSE, lightDiffuse);
			glLightfv(GL_LIGHT0, GL_POSITION, lightPosition);

			float[] lightAmbient2 = { 0.0f, 0.0f, 0.0f, 1.0f };
			float[] lightDiffuse2 = { 1.0f, 1.0f, 1.0f, 1.0f };
			float[] lightPosition2 = { -5f, -5f, 0f, 1.0f };

			glLightfv(GL_LIGHT1, GL_AMBIENT, lightAmbient2);
			glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuse2);
			glLightfv(GL_LIGHT1, GL_POSITION, lightPosition2);

			glEnable(GL_LIGHT0);
			//glEnable(GL_LIGHT1);
			
			glEnable(GL_COLOR_MATERIAL);
			//glColorMaterial(GL_FRONT, GL_DIFFUSE);

			glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

			angle += 1;
			glEnable(GL_DEPTH_TEST);
			
			glEnable (GL_TEXTURE_2D);
			
			glBindTexture ( GL_TEXTURE_2D, tgato );
			
			
			glPushMatrix();

			glTranslatef(0, 0, -4);
			glScalef(0.01f, 0.01f, 0.01f);
			//glTranslatef(0, 0, 50);
			glRotatef(angTankViewX, 1.0f, 0.0f, 0.0f);
			glRotatef(angTankViewY, 0.0f, 1.0f, 0.0f);
			//nomePlane04--
			//nomeBox01--
			//nomeBox02--

			tankObj.desenhaseGrupo("Box02");
			
			glPushMatrix();
			
			glTranslatef(0, 0, -10);
			glRotatef(angTowerViewY, 0.0f, 1.0f, 0.0f);
			glTranslatef(0, 0, 10);
			
			tankObj.desenhaseGrupo("Plane04");
			
	
			glPushMatrix();
			
			glTranslatef(20,40, 0);
			glRotatef(angCanonViewZ, 0.0f, 0.0f, 1.0f);
			glTranslatef(-20, -40, 0);
			
			tankObj.desenhaseGrupo("Box01");
			glPopMatrix();
			
			glPopMatrix();
			//glLoadIdentity();
			
			//glScalef(0.01f, 0.01f, 0.01f);
			//mig29.desenhaSe();
			glPopMatrix();
			
			glPushMatrix();
			
			glTranslatef(0, 2, -4);
			
			glColor3f(1f, 1f, 1f);
			glBegin(GL_TRIANGLE_STRIP);
			
			glTexCoord2f(1.0f, 1.0f);
			glNormal3f(0.0f, 0.0f, 1.0f);
			glVertex3f(-0.5f, -0.5f, -2.0f);
			
			glTexCoord2f(1.0f, 0.0f);
			glNormal3f(0.0f, 0.0f, 1.0f);
			glVertex3f(-0.5f, 0.5f, -2.0f);
			
			glTexCoord2f(0.0f, 1.0f);
			glNormal3f(0.0f, 0.0f, 1.0f);
			glVertex3f(0.5f, -0.5f, -2.0f);
			
			glTexCoord2f(0.0f, 0.0f);
			glNormal3f(0.0f, 0.0f, 1.0f);
			glVertex3f(0.5f, 0.5f, -2.0f);
			
			glEnd();
			
			glPopMatrix();
			
//			for(int i = 0; i < lista.size();i++) {
//				lista.get(i).desenha(tx35);
//			}
			//desenhax35(tx35, x35);
			

			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			frame++;
			long actualTime = System.currentTimeMillis();
			if ((lasttime / 1000) != (actualTime / 1000)) {
				System.out.println("FPS " + frame);
				frame = 0;
				lasttime = actualTime;
			}

		}
	}


	public static void main(String[] args) {
		new Main3D().run();
	}

	public static void gluPerspective(float fovy, float aspect, float near, float far) {
		float bottom = -near * (float) Math.tan(fovy / 2);
		float top = -bottom;
		float left = aspect * bottom;
		float right = -left;
		glFrustum(left, right, bottom, top, near, far);
	}

}
