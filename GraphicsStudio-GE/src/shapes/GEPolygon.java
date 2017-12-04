package shapes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * 다각형
 */
public class GEPolygon extends GEShape {

	public GEPolygon() {
		super(new Polygon());
	}

	@Override
	public void initDraw(Point startP) {
		((Polygon)myShape).addPoint(startP.x, startP.y);
	}

	@Override
	public void setCoordinate(Point currentP) {
		Polygon polygon = (Polygon)myShape;
		polygon.xpoints[polygon.npoints - 1] = currentP.x;
		polygon.ypoints[polygon.npoints - 1] = currentP.y;
		if(anchorList != null) {
			anchorList.setPosition(myShape.getBounds());
		}
	}
	
	public void continueDrawing(Point currentP) {
		((Polygon)myShape).addPoint(currentP.x, currentP.y);
	}

	@Override
	public GEShape clone() {
		return new GEPolygon();
	}
	
	@Override
	public GEShape dup() {
		GEPolygon shape = new GEPolygon();
		Polygon my_p = (Polygon)myShape;
		Polygon p = new Polygon(my_p.xpoints, my_p.ypoints, my_p.npoints);
		shape.setShape(p);
		shape.setFillColor(fillColor);
		shape.setLineColor(lineColor);
		return shape;
	}
}
