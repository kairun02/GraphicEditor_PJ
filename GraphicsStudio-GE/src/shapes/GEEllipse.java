package shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

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
		Point sP = this.startP;
		Point eP = new Point(this.startP.x + (this.endP.x-this.startP.x)*percentage/100, this.startP.y + (this.endP.y-this.startP.y)*percentage/100);
		Ellipse2D r = new Ellipse2D.Double(this.startP.x, this.startP.y, (this.endP.x-this.startP.x)*percentage/100, (this.endP.y-this.startP.y)*percentage/100);
		shape.setShape(r);
		shape.initDraw(sP);
		shape.initend(eP);
		shape.setFillColor(fillColor);
		shape.setLineColor(lineColor);
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

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}
}
