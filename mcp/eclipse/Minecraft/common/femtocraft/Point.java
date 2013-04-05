package femtocraft;

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
	
	public Point rotateOnXAxis(double rot, float yrotoffset, float zrotoffset) {
		Point p = new Point();
		p.x =  x;
		p.y = (float) ((y - yrotoffset)*Math.cos(rot) - (z - zrotoffset)*Math.sin(rot)) + yrotoffset;
		p.z = (float) ((y - yrotoffset)*Math.sin(rot) + (z - zrotoffset)*Math.cos(rot)) + zrotoffset;
		return p;
	}
	
	public Point rotateOnYAxis(double rot, float xrotoffset, float zrotoffset ) {
		Point p = new Point();
		p.x = (float) ((z - zrotoffset)*Math.cos(rot) - (x - xrotoffset)*Math.sin(rot)) + xrotoffset;
		p.y = y;
		p.z = (float) ((z - zrotoffset)*Math.sin(rot) + (x - xrotoffset)*Math.cos(rot)) + zrotoffset;
		return p;
	}
	
	public Point rotateOnZAxis(double rot, float xrotoffset, float yrotoffset) {
		Point p = new Point();
		p.x =  (float) (((x - xrotoffset)*Math.cos(rot) - (y - yrotoffset)*Math.sin(rot)) + xrotoffset);
		p.y = (float) (((x - xrotoffset)*Math.sin(rot) + (y - yrotoffset)*Math.cos(rot)) + yrotoffset);
		p.z = z;
		return p;
	}
}
