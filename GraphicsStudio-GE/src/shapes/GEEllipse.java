package shapes;

import java.awt.Point;
import java.awt.Rectangle;
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
		GEEllipse shape = new GEEllipse();
		Ellipse2D my_e = (Ellipse2D)myShape;
		Ellipse2D e = new Ellipse2D.Double();
		e.setFrame(my_e.getX(), my_e.getY(), my_e.getWidth(), my_e.getHeight());
		shape.setShape(e);
		shape.setFillColor(fillColor);
		shape.setLineColor(lineColor);
		return shape;
	}
}
