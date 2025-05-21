package obj;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import obj.Vector2f;
import obj.Vector3f;

public class ObjModel {
	public ArrayList<Vector3f> v;
	public ArrayList<Vector3f> vn;
	public ArrayList<Vector2f> vt;
	public ArrayList<Face3D> f;
	public ArrayList<GrupoFaces> g;
	public ConcurrentHashMap<String, GrupoFaces> gname;

	public ObjModel() {
		// TODO Auto-generated constructor stubn
		v = new ArrayList<Vector3f>();
		vn = new ArrayList<Vector3f>();
		vt = new ArrayList<Vector2f>();
		f = new ArrayList<Face3D>();
		g = new ArrayList<GrupoFaces>();
		gname = new ConcurrentHashMap<String, GrupoFaces>();
	}

	public void loadObj(String file) {
		// System.out.println(" "+file);
		// InputStream in = this.getClass().getResourceAsStream(file);
		InputStream in;
		try {
			in = new FileInputStream(file);

			// System.out.println(""+in);

			BufferedReader dados = new BufferedReader(new InputStreamReader(in));

			String str;

			try {

				while ((str = dados.readLine()) != null) {
					if (str.length() > 0) {
						if (str.contains("#")) {
							continue;
						}
						if (str.contains("v ")) {
							decodeVertice(str);
						}
						if (str.contains("vn ")) {
							decodeVerticeNormal(str);
						}
						if (str.contains("vt ")) {
							decodeTextureMapping(str);
						}
						if (str.contains("f ")) {
							decodeFace(str);
						}
						if (str.contains("g ")) {
							decodeGrupo(str);
						}
					}
				}

				if (g.size() > 0) {
					g.get(g.size() - 1).ffinal = f.size() - 1;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			// System.out.println(" v " + v.size() + " vn " + vn.size() + " vt " +
			// vt.size());

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void decodeVertice(String str) {
		String s[] = str.split(" ");
		// System.out.print("V ");

		Vector3f vec = new Vector3f();

		int index = 0;
		for (int i = 0; i < s.length; i++) {
			// System.out.print(" s["+i+"] = "+s[i]);
			try {
				float numero = Float.parseFloat(s[i]);
				// System.out.print(" OK");

				if (index == 0) {
					vec.x = numero;
				}
				if (index == 1) {
					vec.y = numero;
				}
				if (index == 2) {
					vec.z = numero;
				}
				index++;
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		// System.out.println();
		v.add(vec);
	}

	public void decodeVerticeNormal(String str) {
		String s[] = str.split(" ");
		// System.out.print("Vn ");

		Vector3f vec = new Vector3f();

		int index = 0;
		for (int i = 0; i < s.length; i++) {
			// System.out.print(" s["+i+"] = "+s[i]);
			try {
				float numero = Float.parseFloat(s[i]);
				// System.out.print(" OK");

				if (index == 0) {
					vec.x = numero;
				}
				if (index == 1) {
					vec.y = numero;
				}
				if (index == 2) {
					vec.z = numero;
				}
				index++;
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		// System.out.println();
		vn.add(vec);
	}

	public void decodeTextureMapping(String str) {
		String s[] = str.split(" ");
		// System.out.print("Vt ");

		Vector2f vec = new Vector2f();

		int index = 0;
		for (int i = 0; i < s.length; i++) {
			// System.out.print(" s["+i+"] = "+s[i]);
			try {
				float numero = Float.parseFloat(s[i]);
				// System.out.print(" OK");

				if (index == 1) {
					vec.x = numero;
				}
				if (index == 0) {
					vec.y = numero;
				}

				index++;
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		// System.out.println();
		vt.add(vec);
	}

	int valorestmp[] = new int[3];

	public void decodeFace(String str) {
		String s[] = str.split(" ");
		// System.out.print("f ");

		Face3D face = new Face3D();

		int index = 0;

		for (int i = 0; i < s.length; i++) {
			// System.out.print(" s["+i+"] = "+s[i]);

			if (s[i].contains("/")) {
				String s2[] = s[i].split("/");

				valorestmp[0] = -1;
				valorestmp[1] = -1;
				valorestmp[2] = -1;

				for (int j = 0; j < s2.length; j++) {
					try {
						int numero = Integer.parseInt(s2[j]);
						valorestmp[j] = numero;
					} catch (Exception e) {
					}
				}

				face.v[index] = valorestmp[0];
				face.n[index] = valorestmp[2];
				face.t[index] = valorestmp[1];
				// System.out.println(" v " + valorestmp[0] + " n " + valorestmp[1] + " t " +
				// valorestmp[2]);

				index++;
			}

		}

		face.nvertices = (byte) index;
		// System.out.println();
		f.add(face);
	}

	public void decodeGrupo(String str) {
		// System.out.println("GRUPO _ __________--" + str);
		String sttmp[] = str.split(" ");
		String nome = "";
		if (sttmp.length >= 2) {
			nome = sttmp[1];
		}

		GrupoFaces gp = new GrupoFaces();
		gp.nome = nome;

		gp.finicial = f.size();

		if (g.size() > 0) {
			g.get(g.size() - 1).ffinal = f.size() - 1;
		}

		g.add(gp);
		gname.put(nome, gp);
		System.out.println("nome " + nome + "--");

	}

	public void desenhaSe() {

		for (int i = 0; i < f.size(); i++) {
			Face3D face = f.get(i);

			if (face.nvertices == 3) {
				Vector3f v1 = v.get(face.v[0] - 1);
				Vector3f v2 = v.get(face.v[1] - 1);
				Vector3f v3 = v.get(face.v[2] - 1);

				Vector3f vn1 = vn.get(face.n[0] - 1);
				Vector3f vn2 = vn.get(face.n[1] - 1);
				Vector3f vn3 = vn.get(face.n[2] - 1);
				vn1.Normalize();
				vn2.Normalize();
				vn3.Normalize();

				Vector2f vt1 = vt.get(face.t[0] - 1);
				Vector2f vt2 = vt.get(face.t[1] - 1);
				Vector2f vt3 = vt.get(face.t[2] - 1);

				// System.out.println(v1.x+","+v1.y+","+v1.z);

				glColor3f(1f, 1f, 1f);
				glBegin(GL_TRIANGLES);

				// glColor3f(0f, 1f, 0f);
				glTexCoord2f(vt1.x, vt1.y);
				glNormal3f(vn1.x, vn1.y, vn1.z);
				glVertex3f(v1.x, v1.y, v1.z);

				// glColor3f(1f, 0f, 0f);
				glTexCoord2f(vt2.x, vt2.y);
				glNormal3f(vn2.x, vn2.y, vn2.z);
				glVertex3f(v2.x, v2.y, v2.z);

				// glColor3f(0f, 0f, 1f);
				glTexCoord2f(vt3.x, vt3.y);
				glNormal3f(vn3.x, vn3.y, vn3.z);
				glVertex3f(v3.x, v3.y, v3.z);
				glEnd();

			} else if (face.nvertices == 4) {
				Vector3f v1 = v.get(face.v[0] - 1);
				Vector3f v2 = v.get(face.v[1] - 1);
				Vector3f v3 = v.get(face.v[2] - 1);
				Vector3f v4 = v.get(face.v[3] - 1);

				Vector3f vn1 = vn.get(face.n[0] - 1);
				Vector3f vn2 = vn.get(face.n[1] - 1);
				Vector3f vn3 = vn.get(face.n[2] - 1);
				Vector3f vn4 = vn.get(face.n[3] - 1);

				vn1.Normalize();
				vn2.Normalize();
				vn3.Normalize();
				vn4.Normalize();

				Vector2f vt1 = vt.get(face.t[0] - 1);
				Vector2f vt2 = vt.get(face.t[1] - 1);
				Vector2f vt3 = vt.get(face.t[2] - 1);
				Vector2f vt4 = vt.get(face.t[3] - 1);

				glColor3f(1f, 1f, 1f);
				glBegin(GL_TRIANGLES);

				glTexCoord2f(vt1.x, vt1.y);
				glNormal3f(vn1.x, vn1.y, vn1.z);
				glVertex3f(v1.x, v1.y, v1.z);

				glTexCoord2f(vt2.x, vt2.y);
				glNormal3f(vn2.x, vn2.y, vn2.z);
				glVertex3f(v2.x, v2.y, v2.z);

				glTexCoord2f(vt3.x, vt3.y);
				glNormal3f(vn3.x, vn3.y, vn3.z);
				glVertex3f(v3.x, v3.y, v3.z);

				glTexCoord2f(vt3.x, vt3.y);
				glNormal3f(vn3.x, vn3.y, vn3.z);
				glVertex3f(v3.x, v3.y, v3.z);

				glTexCoord2f(vt4.x, vt4.y);
				glNormal3f(vn4.x, vn4.y, vn4.z);
				glVertex3f(v4.x, v4.y, v4.z);

				glTexCoord2f(vt1.x, vt1.y);
				glNormal3f(vn1.x, vn1.y, vn1.z);
				glVertex3f(v1.x, v1.y, v1.z);
				glEnd();

			}
		}

	}

	public void desenhaseGrupo(String nome) {

		GrupoFaces gp = null;
		if (gname.containsKey(nome) == false) {
			System.out.println(" nao rolo");
			return;
		}

		gp = gname.get(nome);
		int inicial = gp.finicial;
		int ffinal = gp.ffinal;

		for (int i = inicial; i < ffinal; i++) {
			Face3D face = f.get(i);

			if (face.nvertices == 3) {
				Vector3f v1 = v.get(face.v[0] - 1);
				Vector3f v2 = v.get(face.v[1] - 1);
				Vector3f v3 = v.get(face.v[2] - 1);

				Vector3f vn1 = vn.get(face.n[0] - 1);
				Vector3f vn2 = vn.get(face.n[1] - 1);
				Vector3f vn3 = vn.get(face.n[2] - 1);
				vn1.Normalize();
				vn2.Normalize();
				vn3.Normalize();

				Vector2f vt1 = vt.get(face.t[0] - 1);
				Vector2f vt2 = vt.get(face.t[1] - 1);
				Vector2f vt3 = vt.get(face.t[2] - 1);

				// System.out.println(v1.x+","+v1.y+","+v1.z);

				glColor3f(1f, 1f, 1f);
				glBegin(GL_TRIANGLES);

				// glColor3f(0f, 1f, 0f);
				glTexCoord2f(vt1.x, vt1.y);
				glNormal3f(vn1.x, vn1.y, vn1.z);
				glVertex3f(v1.x, v1.y, v1.z);

				// glColor3f(1f, 0f, 0f);
				glTexCoord2f(vt2.x, vt2.y);
				glNormal3f(vn2.x, vn2.y, vn2.z);
				glVertex3f(v2.x, v2.y, v2.z);

				// glColor3f(0f, 0f, 1f);
				glTexCoord2f(vt3.x, vt3.y);
				glNormal3f(vn3.x, vn3.y, vn3.z);
				glVertex3f(v3.x, v3.y, v3.z);
				glEnd();

			} else if (face.nvertices == 4) {
				Vector3f v1 = v.get(face.v[0] - 1);
				Vector3f v2 = v.get(face.v[1] - 1);
				Vector3f v3 = v.get(face.v[2] - 1);
				Vector3f v4 = v.get(face.v[3] - 1);

				Vector3f vn1 = vn.get(face.n[0] - 1);
				Vector3f vn2 = vn.get(face.n[1] - 1);
				Vector3f vn3 = vn.get(face.n[2] - 1);
				Vector3f vn4 = vn.get(face.n[3] - 1);

				vn1.Normalize();
				vn2.Normalize();
				vn3.Normalize();
				vn4.Normalize();

				Vector2f vt1 = vt.get(face.t[0] - 1);
				Vector2f vt2 = vt.get(face.t[1] - 1);
				Vector2f vt3 = vt.get(face.t[2] - 1);
				Vector2f vt4 = vt.get(face.t[3] - 1);

				glColor3f(1f, 1f, 1f);
				glBegin(GL_TRIANGLES);

				glTexCoord2f(vt1.x, vt1.y);
				glNormal3f(vn1.x, vn1.y, vn1.z);
				glVertex3f(v1.x, v1.y, v1.z);

				glTexCoord2f(vt2.x, vt2.y);
				glNormal3f(vn2.x, vn2.y, vn2.z);
				glVertex3f(v2.x, v2.y, v2.z);

				glTexCoord2f(vt3.x, vt3.y);
				glNormal3f(vn3.x, vn3.y, vn3.z);
				glVertex3f(v3.x, v3.y, v3.z);

				glTexCoord2f(vt3.x, vt3.y);
				glNormal3f(vn3.x, vn3.y, vn3.z);
				glVertex3f(v3.x, v3.y, v3.z);

				glTexCoord2f(vt4.x, vt4.y);
				glNormal3f(vn4.x, vn4.y, vn4.z);
				glVertex3f(v4.x, v4.y, v4.z);

				glTexCoord2f(vt1.x, vt1.y);
				glNormal3f(vn1.x, vn1.y, vn1.z);
				glVertex3f(v1.x, v1.y, v1.z);
				glEnd();

			}
		}

	}

}
