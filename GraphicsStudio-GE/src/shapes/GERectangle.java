package shapes;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * 사각형
 */
public class GERectangle extends GEShape {
	public GERectangle() {
		super(new Rectangle());
	}
	
	@Override
	public void initDraw(Point startP) {
		this.startP = startP;
	}
	
	@Override
	public void setCoordinate(Point currentP) {
		Rectangle rectangle = (Rectangle)myShape;
		rectangle.setFrame(startP.x, startP.y, currentP.x - startP.x, currentP.y - startP.y);
		if(anchorList != null) {
			anchorList.setPosition(myShape.getBounds());
		}
	}

	@Override
	public GEShape clone() {
		return new GERectangle();
	}
	
	@Override
	public GEShape dup() {
		GERectangle shape = new GERectangle();
		Rectangle r = new Rectangle(myShape.getBounds());
		shape.setShape(r);
		shape.setFillColor(fillColor);
		shape.setLineColor(lineColor);
		return shape;
	}
}
