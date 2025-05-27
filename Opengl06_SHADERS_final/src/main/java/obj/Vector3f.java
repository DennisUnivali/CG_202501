package obj;

public class Vector3f {
	
	public float x,y,z;
	
	public Vector3f(){
		x = 0;
		y = 0;
		z = 0;
	}
			
	public Vector3f(float x,float y,float z) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f(Vector3f v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	Vector3f multiply(Vector3f V2)
	{
		return new Vector3f (x * V2.x,  y * V2.y,  z * V2.z);
	}	
	
	// Functions
	float Dot(Vector3f v1)
	{
		return v1.x*x + v1.y*y + v1.z*z;
	}

	
	Vector3f CrossProduct( Vector3f V2 )
		{
		return new Vector3f(
			y * V2.z  -  z * V2.y,
			z * V2.x  -  x * V2.z,
			x * V2.y  -  y * V2.x 	);
		}

	// Return vector rotated by the 3x3 portion of matrix m
	// (provided because it's used by bbox.cpp in article 21)
	Vector3f RotByMatrix( float m[] )
	{
	return new Vector3f( 
		x*m[0] + y*m[4] + z*m[8],
		x*m[1] + y*m[5] + z*m[9],
		x*m[2] + y*m[6] + z*m[10] );
 	}

	// These require math.h for the sqrtf function
	float Magnitude( )
	{
		return (float)Math.sqrt( x*x + y*y + z*z );
	}

	float Distance(Vector3f V1)
	{
		return multiply(V1).Magnitude();	
	}

	
	public void Normalize()
		{
		float fMag = ( x*x + y*y + z*z );
		if (fMag == 0) {return;}

		float fMult = (float)(1.0f/Math.sqrt(fMag));            
		x *= fMult;
		y *= fMult;
		z *= fMult;
		return;
	}
}
