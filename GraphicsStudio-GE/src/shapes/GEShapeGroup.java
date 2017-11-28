package shapes;

import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;

public class GEShapeGroup extends GEShape {

	public GEShapeGroup() {
		super(null);
	}
	
	@Override
	public void initDraw(Point startP) {
	}

	@Override
	public void setCoordinate(Point currentP) {
		// TODO Auto-generated method stub

	}

	@Override
	public GEShape clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
	ArrayList<GEShape> shapeList;
}
