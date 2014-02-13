package femtocraft.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.common.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class RenderModel {
	public List<RenderQuad> faces;
	public RenderPoint center;
	public RenderPoint location;

	public RenderModel() {
		this(new RenderPoint(0, 0, 0));
	}

	public RenderModel(RenderPoint location) {
		this(location, new RenderPoint(0, 0, 0));
	}

	public RenderModel(RenderPoint location, RenderPoint center) {
		this.center = center;
		this.location = location;
		faces = new ArrayList<RenderQuad>();
	}

	public RenderModel copy() {
		RenderModel ret = new RenderModel(location.copy(), center.copy());
		for (RenderQuad quad : faces) {
			ret.addQuad(quad.copy());
		}
		return ret;
	}

	public void addQuad(RenderQuad quad) {
		faces.add(quad);
	}

	public void removeQuad(RenderQuad quad) {
		faces.remove(quad);
	}

	public RenderModel rotateOnXAxis(double rot) {
		return rotateOnXAxis(rot, center.y, center.z);
	}

	public RenderModel rotateOnXAxis(double rot, float yrotoffset, float zrotoffset) {
		for (RenderQuad quad : faces) {
			quad.rotateOnXAxis(rot, yrotoffset, zrotoffset);
		}
		return this;
	}

	public RenderModel rotateOnYAxis(double rot) {
		return rotateOnYAxis(rot, center.x, center.z);
	}

	public RenderModel rotateOnYAxis(double rot, float xrotoffset, float zrotoffset) {
		for (RenderQuad quad : faces) {
			quad.rotateOnYAxis(rot, xrotoffset, zrotoffset);
		}
		return this;
	}

	public RenderModel rotateOnZAxis(double rot) {
		return rotateOnZAxis(rot, center.x, center.y);
	}

	public RenderModel rotateOnZAxis(double rot, float xrotoffset, float yrotoffset) {
		for (RenderQuad quad : faces) {
			quad.rotateOnZAxis(rot, xrotoffset, yrotoffset);
		}
		return this;
	}

	public RenderModel rotatedOnXAxis(double rot) {
		return rotatedOnXAxis(rot, center.y, center.z);
	}

	public RenderModel rotatedOnXAxis(double rot, float yrotoffset, float zrotoffset) {
		return copy().rotateOnXAxis(rot, yrotoffset, zrotoffset);
	}

	public RenderModel rotatedOnYAxis(double rot) {
		return rotatedOnYAxis(rot, center.x, center.z);
	}

	public RenderModel rotatedOnYAxis(double rot, float xrotoffset, float zrotoffset) {
		return copy().rotateOnYAxis(rot, xrotoffset, zrotoffset);
	}

	public RenderModel rotatedOnZAxis(double rot) {
		return rotatedOnZAxis(rot, center.x, center.y);
	}

	public RenderModel rotatedOnZAxis(double rot, float xrotoffset, float yrotoffset) {
		return copy().rotateOnZAxis(rot, xrotoffset, yrotoffset);
	}

	public RenderModel rotate(double x, double y, double z) {
		return rotateOnXAxis(x).rotateOnYAxis(y).rotateOnZAxis(z);
	}

	public RenderModel rotated(double x, double y, double z) {
		return this.copy().rotateOnXAxis(x).rotateOnYAxis(y).rotateOnZAxis(z);
	}

	//
	// public RenderModel rotateFromTo(ForgeDirection from, ForgeDirection to)
	// {
	// if(from == to) return this;
	//
	// double xrot, yrot, zrot;
	// xrot = zrot = 0;
	// yrot = Math.PI/2.d;
	//
	// switch(from)
	// {
	// case UP:
	// switch(to)
	// {
	// case UP:
	// break;
	// case DOWN:
	// xrot += Math.PI;
	// break;
	// case NORTH:
	// xrot += -Math.PI/2.d;
	// break;
	// case SOUTH:
	// xrot += Math.PI/2.d;
	// break;
	// case EAST:
	// zrot += -Math.PI/2.d;
	// break;
	// case WEST:
	// zrot += Math.PI/2.d;
	// break;
	// default:
	// break;
	// }
	// break;
	// case DOWN:
	// switch(to)
	// {
	// case UP:
	// xrot += Math.PI;
	// break;
	// case DOWN:
	// break;
	// case NORTH:
	// xrot += Math.PI/2.d;
	// break;
	// case SOUTH:
	// xrot += -Math.PI/2.d;
	// break;
	// case EAST:
	// zrot += Math.PI/2.d;
	// break;
	// case WEST:
	// zrot += -Math.PI/2.d;
	// break;
	// default:
	// break;
	// }
	// break;
	// case NORTH:
	// switch(to)
	// {
	// case UP:
	// xrot += Math.PI/2.d;
	// break;
	// case DOWN:
	// xrot += -Math.PI/2.d;
	// break;
	// case NORTH:
	// break;
	// case SOUTH:
	// yrot += Math.PI;
	// break;
	// case EAST:
	// yrot += Math.PI/2.d;
	// break;
	// case WEST:
	// yrot += -Math.PI/2.d;
	// break;
	// default:
	// break;
	// }
	// break;
	// case SOUTH:
	// switch(to)
	// {
	// case UP:
	// xrot += -Math.PI/2.d;
	// break;
	// case DOWN:
	// xrot += Math.PI/2.d;
	// break;
	// case NORTH:
	// yrot += Math.PI;
	// break;
	// case SOUTH:
	// break;
	// case EAST:
	// yrot += -Math.PI/2.d;
	// break;
	// case WEST:
	// yrot += Math.PI/2.d;
	// break;
	// default:
	// break;
	// }
	// break;
	// case EAST:
	// switch(to)
	// {
	// case UP:
	// zrot += Math.PI/2.d;
	// break;
	// case DOWN:
	// zrot += -Math.PI/2.d;
	// break;
	// case NORTH:
	// yrot += -Math.PI/2d;
	// break;
	// case SOUTH:
	// yrot += Math.PI/2.d;
	// break;
	// case EAST:
	// break;
	// case WEST:
	// yrot += Math.PI;
	// break;
	// default:
	// break;
	// }
	// break;
	// case WEST:
	// switch(to)
	// {
	// case UP:
	// zrot += -Math.PI/2.d;
	// break;
	// case DOWN:
	// zrot += Math.PI/2.d;
	// break;
	// case NORTH:
	// yrot += Math.PI/2.d;
	// break;
	// case SOUTH:
	// yrot += -Math.PI/2.d;
	// break;
	// case EAST:
	// yrot += Math.PI;
	// break;
	// case WEST:
	// break;
	// default:
	// break;
	// }
	// break;
	// default:
	// break;
	// }
	//
	// return rotateOnXAxis(xrot).rotateOnYAxis(yrot).rotateOnZAxis(zrot);
	// }
	//
	// public RenderModel rotatedFromTo(ForgeDirection from, ForgeDirection to)
	// {
	// return copy().rotateFromTo(from, to);
	// }

	public void draw() {
		Tessellator tes = Tessellator.instance;
		tes.addTranslation(location.x, location.y, location.z);

		for (RenderQuad quad : faces) {
			quad.draw();
		}

		tes.addTranslation(-location.x, -location.y, -location.z);
	}

	public RenderModel rotatedToDirection(ForgeDirection dir) {
		switch (dir) {
		case SOUTH:
			return rotatedOnXAxis(Math.PI);
		case EAST:
			return rotatedOnYAxis(-Math.PI / 2.d);
		case WEST:
			return rotatedOnYAxis(Math.PI / 2.d);
		case UP:
			return rotatedOnXAxis(Math.PI / 2.d);
		case DOWN:
			return rotatedOnXAxis(-Math.PI / 2.d);
		default:
			return this.copy();
		}
	}

}
