package shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

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
		System.out.println(startP.x+","+startP.y);
	}
	
	
	
	@Override
	public void initend(Point endP) {
		this.endP = endP;
		System.out.println(endP.x+","+endP.y);
		
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
	
	@Override
	public GEShape drawPercentage(int percentage) {
		GERectangle shape = new GERectangle();
		Point P = new Point(this.startP.x + (this.endP.x-this.startP.x)*percentage/100, this.startP.y + (this.endP.y-this.startP.y)*percentage/100);
		Rectangle2D r = new Rectangle2D.Double(this.startP.x, this.startP.y, (this.endP.x-this.startP.x)*percentage/100, (this.endP.y-this.startP.y)*percentage/100);
		shape.setShape(r);
		//P.x = this.startP.x + (this.endP.x-this.startP.x)*percentage/100;
		//P.y = this.startP.y + (this.endP.y-this.startP.y)*percentage/100;
		shape.initend(P);
		shape.setFillColor(fillColor);
		shape.setLineColor(lineColor);
		return shape;
	}
}
