package femtocraft.render;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.common.ForgeDirection;

public class Model {
	public List<Quad> faces;
	public Point center;
	public Point location;

	public Model() {
		this(new Point(0,0,0));
	}
	
	public Model(Point location) {
		this(location, new Point(0,0,0));
	}
	
	public Model (Point location, Point center)
	{
		this.center = center;
		this.location = location;
		faces = new ArrayList<Quad>();
	}

	public Model copy()
	{
		Model ret = new Model(center.copy(), location.copy());
		for(Quad quad : faces)
		{
			ret.addQuad(quad.copy());
		}
		return ret;
	}
	
	public void addQuad(Quad quad)
	{
		faces.add(quad);
	}
	
	public void removeQuad(Quad quad)
	{
		faces.remove(quad);
	}
	
	public Model rotateOnXAxis(double rot)
	{
		return rotateOnXAxis(rot, center.y, center.z);
	}
	
	public Model rotateOnXAxis(double rot, float yrotoffset, float zrotoffset) 
	{
		for(Quad quad : faces)
		{
			quad.rotateOnXAxis(rot, yrotoffset, zrotoffset);
		}
		return this;
	}
	
	public Model rotateOnYAxis(double rot)
	{
		return rotateOnYAxis(rot, center.x, center.z);
	}
	
	public Model rotateOnYAxis(double rot, float xrotoffset, float zrotoffset ) 
	{
		for(Quad quad : faces)
		{
			quad.rotateOnYAxis(rot, xrotoffset, zrotoffset);
		}
		return this;
	}
	
	public Model rotateOnZAxis(double rot)
	{
		return rotateOnZAxis(rot, center.x, center.y);
	}
	
	public Model rotateOnZAxis(double rot, float xrotoffset, float yrotoffset) 
	{
		for(Quad quad : faces)
		{
			quad.rotateOnZAxis(rot, xrotoffset, yrotoffset);
		}
		return this;
	}
	
	public Model rotatedOnXAxis(double rot)
	{
		return rotatedOnXAxis(rot, center.y, center.z);
	}
	
	public Model rotatedOnXAxis(double rot, float yrotoffset, float zrotoffset) 
	{
		return copy().rotateOnXAxis(rot, yrotoffset, zrotoffset);
	}
	
	public Model rotatedOnYAxis(double rot)
	{
		return rotatedOnYAxis(rot, center.x, center.z);
	}
	
	public Model rotatedOnYAxis(double rot, float xrotoffset, float zrotoffset ) 
	{
		return copy().rotateOnYAxis(rot, xrotoffset, zrotoffset);
	}
	
	public Model rotatedOnZAxis(double rot)
	{
		return rotatedOnZAxis(rot, center.x, center.y);
	}
	
	public Model rotatedOnZAxis(double rot, float xrotoffset, float yrotoffset) 
	{
		return copy().rotateOnZAxis(rot, xrotoffset, yrotoffset);
	}
	
	public Model rotate(double x, double y, double z)
	{
		return rotateOnXAxis(x).rotateOnYAxis(y).rotateOnZAxis(z);
	}
	
	public Model rotated(double x, double y, double z)
	{
		return this.copy().rotateOnXAxis(x).rotateOnYAxis(y).rotateOnZAxis(z);
	}
//	
//	public Model rotateFromTo(ForgeDirection from, ForgeDirection to)
//	{
//		if(from == to) return this;
//		
//		double xrot, yrot, zrot;
//		xrot = zrot = 0;
//		yrot = Math.PI/2.d;
//		
//		switch(from)
//		{
//		case UP:
//			switch(to)
//			{
//			case UP:
//				break;
//			case DOWN:
//				xrot += Math.PI;
//				break;
//			case NORTH:
//				xrot += -Math.PI/2.d;
//				break;
//			case SOUTH:
//				xrot += Math.PI/2.d;
//				break;
//			case EAST:
//				zrot += -Math.PI/2.d;
//				break;
//			case WEST:
//				zrot += Math.PI/2.d;
//				break;
//			default:
//				break;
//			}
//			break;
//		case DOWN:
//			switch(to)
//			{
//			case UP:
//				xrot += Math.PI;
//				break;
//			case DOWN:
//				break;
//			case NORTH:
//				xrot += Math.PI/2.d;
//				break;
//			case SOUTH:
//				xrot += -Math.PI/2.d;
//				break;
//			case EAST:
//				zrot += Math.PI/2.d;
//				break;
//			case WEST:
//				zrot += -Math.PI/2.d;
//				break;
//			default:
//				break;
//			}
//			break;
//		case NORTH:
//			switch(to)
//			{
//			case UP:
//				xrot += Math.PI/2.d;
//				break;
//			case DOWN:
//				xrot += -Math.PI/2.d;
//				break;
//			case NORTH:
//				break;
//			case SOUTH:
//				yrot += Math.PI;
//				break;
//			case EAST:
//				yrot += Math.PI/2.d;
//				break;
//			case WEST:
//				yrot += -Math.PI/2.d;
//				break;
//			default:
//				break;
//			}
//			break;
//		case SOUTH:
//			switch(to)
//			{
//			case UP:
//				xrot += -Math.PI/2.d;
//				break;
//			case DOWN:
//				xrot += Math.PI/2.d;
//				break;
//			case NORTH:
//				yrot += Math.PI;
//				break;
//			case SOUTH:
//				break;
//			case EAST:
//				yrot += -Math.PI/2.d;
//				break;
//			case WEST:
//				yrot += Math.PI/2.d;
//				break;
//			default:
//				break;
//			}
//			break;
//		case EAST:
//			switch(to)
//			{
//			case UP:
//				zrot += Math.PI/2.d;
//				break;
//			case DOWN:
//				zrot += -Math.PI/2.d;
//				break;
//			case NORTH:
//				yrot += -Math.PI/2d;
//				break;
//			case SOUTH:
//				yrot += Math.PI/2.d;
//				break;
//			case EAST:
//				break;
//			case WEST:
//				yrot += Math.PI;
//				break;
//			default:
//				break;
//			}
//			break;
//		case WEST:
//			switch(to)
//			{
//			case UP:
//				zrot += -Math.PI/2.d;
//				break;
//			case DOWN:
//				zrot += Math.PI/2.d;
//				break;
//			case NORTH:
//				yrot += Math.PI/2.d;
//				break;
//			case SOUTH:
//				yrot += -Math.PI/2.d;
//				break;
//			case EAST:
//				yrot += Math.PI;
//				break;
//			case WEST:
//				break;
//			default:
//				break;
//			}
//			break;
//		default:
//			break;
//		}
//		
//		return rotateOnXAxis(xrot).rotateOnYAxis(yrot).rotateOnZAxis(zrot);
//	}
//	
//	public Model rotatedFromTo(ForgeDirection from, ForgeDirection to)
//	{
//		return copy().rotateFromTo(from, to);
//	}
	
	public void draw()
	{
		Tessellator tes = Tessellator.instance;
		tes.addTranslation(location.x, location.y, location.z);
		
		for(Quad quad : faces)
		{
			quad.draw();
		}
		
		tes.addTranslation(-location.x, -location.y, -location.z);
	}

}
