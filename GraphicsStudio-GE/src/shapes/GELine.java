package shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
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
		System.out.println(startP.x+","+startP.y);
	}
	
	@Override
	public void initend(Point endP) {
		this.endP = endP;
		System.out.println(endP.x+","+endP.y);
	}
	
	@Override
	public GEShape drawPercentage(int percentage) {
		GELine shape = new GELine();
		Point sP = this.startP;
		Point eP = new Point(this.startP.x + (this.endP.x-this.startP.x)*percentage/100, this.startP.y + (this.endP.y-this.startP.y)*percentage/100);
		Line2D r = new Line2D.Double(this.startP.x, this.startP.y, (this.endP.x-this.startP.x)*percentage/100, (this.endP.y-this.startP.y)*percentage/100);
		shape.setShape(r);
		shape.initDraw(sP);
		shape.initend(eP);
		shape.setFillColor(fillColor);
		shape.setLineColor(lineColor);
		return shape;
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

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}
}
