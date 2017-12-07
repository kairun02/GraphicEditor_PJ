package shapes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

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
	public void initend(Point endP) {
		((Polygon)myShape).addPoint(endP.x, endP.y);
	}
	
	@Override
	public GEShape drawPercentage(int percentage) {
		GEPolygon shape = new GEPolygon();
		return shape;
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
		AffineTransform affineTransform = new AffineTransform();
		Shape p = affineTransform.createTransformedShape(myShape);
		GEPolygon shape = new GEPolygon();
		shape.setShape(p);
		shape.setFillColor(fillColor);
		shape.setLineColor(lineColor);
		return shape;
	}
}
