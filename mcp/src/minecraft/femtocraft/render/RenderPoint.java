package femtocraft.render;

public class RenderPoint {
	public float x;
	public float y;
	public float z;

	public RenderPoint() {
		x = 0;
		y = 0;
		z = 0;
	}

	public RenderPoint(float x_, float y_, float z_) {
		x = x_;
		y = y_;
		z = z_;
	}

	public RenderPoint copy() {
		return new RenderPoint(x, y, z);
	}

	public RenderPoint rotateOnXAxis(double rot, float yrotoffset, float zrotoffset) {
		if (rot == 0)
			return this;

		float ym = this.y;
		float zm = this.z;
		this.y = (float) ((ym - yrotoffset) * Math.cos(rot) - (zm - zrotoffset)
				* Math.sin(rot))
				+ yrotoffset;
		this.z = (float) ((ym - yrotoffset) * Math.sin(rot) + (zm - zrotoffset)
				* Math.cos(rot))
				+ zrotoffset;
		return this;
	}

	public RenderPoint rotateOnYAxis(double rot, float xrotoffset, float zrotoffset) {
		if (rot == 0)
			return this;

		float xm = this.x;
		float zm = this.z;
		this.z = (float) ((zm - zrotoffset) * Math.cos(rot) - (xm - xrotoffset)
				* Math.sin(rot))
				+ xrotoffset;
		this.x = (float) ((zm - zrotoffset) * Math.sin(rot) + (xm - xrotoffset)
				* Math.cos(rot))
				+ zrotoffset;
		return this;
	}

	public RenderPoint rotateOnZAxis(double rot, float xrotoffset, float yrotoffset) {
		if (rot == 0)
			return this;

		float xm = this.x;
		float ym = this.y;
		this.x = (float) (((xm - xrotoffset) * Math.cos(rot) - (ym - yrotoffset)
				* Math.sin(rot)) + xrotoffset);
		this.y = (float) (((xm - xrotoffset) * Math.sin(rot) + (ym - yrotoffset)
				* Math.cos(rot)) + yrotoffset);
		return this;
	}

	public RenderPoint rotatedOnXAxis(double rot, float yrotoffset, float zrotoffset) {
		return copy().rotateOnXAxis(rot, yrotoffset, zrotoffset);
	}

	public RenderPoint rotatedOnYAxis(double rot, float xrotoffset, float zrotoffset) {
		return copy().rotateOnYAxis(rot, xrotoffset, zrotoffset);
	}

	public RenderPoint rotatedOnZAxis(double rot, float xrotoffset, float yrotoffset) {
		return copy().rotateOnZAxis(rot, xrotoffset, yrotoffset);
	}
}
