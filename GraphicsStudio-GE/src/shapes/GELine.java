package shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

/**
 * 선
 */
public class GELine extends GEShape{
	public GELine() {
		 super(new Line2D.Double());
	}
	
	@Override
	public void initDraw(Point startP) {
		this.startP = startP;
	}
	
	@Override
	public void setCoordinate(Point currentP) {
		Line2D line = (Line2D)myShape;
		line.setLine(startP.x, startP.y, currentP.x, currentP.y);
		if(anchorList != null) {
			anchorList.setPosition(myShape.getBounds());
		}
	}

	@Override
	public GEShape clone() {
		return new GELine();
	}
	
	@Override
	public GEShape dup() {
		AffineTransform affineTransform = new AffineTransform();
		Shape l = affineTransform.createTransformedShape(myShape);
		GELine shape = new GELine();
		shape.setShape(l);
		shape.setFillColor(fillColor);
		shape.setLineColor(lineColor);
		return shape;
	}
}
