package femtocraft.render;

public class Point {
	public float x;
	public float y;
	public float z;
	
	public Point() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Point(float x_, float y_, float z_) {
		x = x_;
		y = y_;
		z = z_;
	}
	
	public Point copy()
	{
		return new Point(x,y,z);
	}
	
	public Point rotateOnXAxis(double rot, float yrotoffset, float zrotoffset) {
		if(rot == 0) return this;
		
		float y = this.y;
		float z = this.z;
		this.y = (float) ((y - yrotoffset)*Math.cos(rot) - (z - zrotoffset)*Math.sin(rot)) + yrotoffset;
		this.z = (float) ((y - yrotoffset)*Math.sin(rot) + (z - zrotoffset)*Math.cos(rot)) + zrotoffset;
		return this;
	}
	
	public Point rotateOnYAxis(double rot, float xrotoffset, float zrotoffset ) {
		if(rot == 0) return this;
		
		float x = this.x;
		float z = this.z;
		this.x = (float) ((z - zrotoffset)*Math.cos(rot) - (x - xrotoffset)*Math.sin(rot)) + xrotoffset;
		this.z = (float) ((z - zrotoffset)*Math.sin(rot) + (x - xrotoffset)*Math.cos(rot)) + zrotoffset;
		return this;
	}
	
	public Point rotateOnZAxis(double rot, float xrotoffset, float yrotoffset) {
		if(rot == 0) return this;
		
		float x = this.x;
		float y = this.y;
		this.x =  (float) (((x - xrotoffset)*Math.cos(rot) - (y - yrotoffset)*Math.sin(rot)) + xrotoffset);
		this.y = (float) (((x - xrotoffset)*Math.sin(rot) + (y - yrotoffset)*Math.cos(rot)) + yrotoffset);
		return this;
	}
	
	public Point rotatedOnXAxis(double rot, float yrotoffset, float zrotoffset) {
		return copy().rotateOnXAxis(rot, yrotoffset, zrotoffset);
	}
	
	public Point rotatedOnYAxis(double rot, float xrotoffset, float zrotoffset ) {
		return copy().rotateOnYAxis(rot, xrotoffset, zrotoffset);
	}
	
	public Point rotatedOnZAxis(double rot, float xrotoffset, float yrotoffset) {
		return copy().rotateOnZAxis(rot, xrotoffset, yrotoffset);
	}
}
