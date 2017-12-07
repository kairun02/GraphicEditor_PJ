package shapes;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 * 도형을 제거했을 때 undo하기 위해 해당 위치만을 남겨 두기 위함.
 * @author HMCL_sy
 */
public class GENull extends GEShape {

	public GENull() {
		super(null);
	}
	
	

	@Override
	public void initDraw(Point startP) { }

	@Override
	public void setCoordinate(Point currentP) { }

	@Override
	public GEShape clone() { return null; }
	
	@Override
	public void draw(Graphics2D g2d) { }
	
	@Override
	public boolean onShape(Point p) { return false; }

	@Override
	public GEShape dup() {
		return this;
	}



	@Override
	public void initend(Point endP) {}



	@Override
	public GEShape drawPercentage(int percentage) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void finish() {}
}
