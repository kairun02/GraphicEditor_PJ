package shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

/**
 * 원
 */
public class GEEllipse extends GEShape {
	public GEEllipse() {
		super(new Ellipse2D.Double());
	}
	
	@Override
	public void initDraw(Point startP) {
		this.startP = startP;
	}
	
	@Override
	public void initend(Point endP) {
		this.endP = endP;
	}

	@Override
	public GEShape drawPercentage(int percentage) {
		GEEllipse shape = new GEEllipse();
		
		return shape;
	}

	@Override
	public void setCoordinate(Point currentP) {
		Ellipse2D ellipse = (Ellipse2D)myShape;
		ellipse.setFrame(startP.x, startP.y,
				currentP.x - startP.x, currentP.y - startP.y);
		if(anchorList != null) {
			anchorList.setPosition(myShape.getBounds());
		}
	}

	@Override
	public GEShape clone() {
		return new GEEllipse();
	}
	
	@Override
	public GEShape dup() {
		AffineTransform affineTransform = new AffineTransform();
		Shape e = affineTransform.createTransformedShape(myShape);
		GEEllipse shape = new GEEllipse();
		shape.setShape(e);
		shape.setFillColor(fillColor);
		shape.setLineColor(lineColor);
		return shape;
	}
}
