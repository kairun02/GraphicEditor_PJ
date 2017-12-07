package shapes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * 다각형
 */
public class GEPolygon extends GEShape {
	int pointsc = 0;
	int sx, sy;
	int ex, ey;
	Point fsp;
	Point fep;

	public GEPolygon() {
		super(new Polygon());
	}

	@Override
	public void initDraw(Point startP) {
		((Polygon)myShape).addPoint(startP.x, startP.y);
		System.out.println(startP.x+","+startP.y);
	}
	
	@Override
	public void initend(Point endP) {
		((Polygon)myShape).addPoint(endP.x, endP.y);
		System.out.println(endP.x+","+endP.y);
	}
	
	@Override
	public GEShape drawPercentage(int percentage) {
		GEPolygon shape = new GEPolygon();
		Polygon r = new Polygon();
		r.addPoint(((Polygon)myShape).xpoints[0], ((Polygon)myShape).ypoints[0]);
		for(int i=1; i<((Polygon)myShape).npoints;i++) {
			int x, y;
			if(((Polygon)myShape).xpoints[0] < ((Polygon)myShape).xpoints[i]) {
				x = ((Polygon)myShape).xpoints[0]+(((Polygon)myShape).xpoints[i]-((Polygon)myShape).xpoints[0])*percentage/100;
			}else if(((Polygon)myShape).xpoints[0] > ((Polygon)myShape).xpoints[i]){
				x = ((Polygon)myShape).xpoints[0]-(((Polygon)myShape).xpoints[0]-((Polygon)myShape).xpoints[i])*percentage/100;
			}else {
				x = ((Polygon)myShape).xpoints[0];
			}
			
			if(((Polygon)myShape).ypoints[0] < ((Polygon)myShape).ypoints[i]) {
				y = ((Polygon)myShape).ypoints[0]+(((Polygon)myShape).ypoints[i]-((Polygon)myShape).ypoints[0])*percentage/100;
			}else if(((Polygon)myShape).ypoints[0] > ((Polygon)myShape).ypoints[i]){
				y = ((Polygon)myShape).ypoints[0]-(((Polygon)myShape).ypoints[0]-((Polygon)myShape).ypoints[i])*percentage/100;
			}else {
				y = ((Polygon)myShape).ypoints[0];
			}
			r.addPoint(x, y);
		}
		shape.setShape(r);
		shape.fsp = this.fsp;
		shape.fep = new Point(this.fsp.x+(this.fep.x-this.fsp.x)*percentage/100, this.fsp.y+(this.fep.y-this.fsp.y)*percentage/100);
		shape.setFillColor(fillColor);
		shape.setLineColor(lineColor);
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
		if(pointsc == 0) {
			//this.fsp = currentP;
			pointsc += 1;
			this.sx=currentP.x;
			this.sy=currentP.y;
			this.ex=currentP.x;
			this.ey=currentP.y;
			System.out.println(this.sx+","+this.sy);
			System.out.println(this.ex+","+this.ey);
			return;
		}
		
		if(this.sx > currentP.x) {
			this.sx=currentP.x;
			System.out.println(this.sx);
		}
		if(this.sy > currentP.y) {
			this.sy=currentP.y;
			System.out.println(this.sy);
		}
		
		if(this.ex < currentP.x) {
			this.ex=currentP.x;
			System.out.println(this.ex);
		}
		if(this.ey < currentP.y) {
			this.ey=currentP.y;
			System.out.println(this.ey);
		}
	}
	
	@Override
	public void finish() {
		Point ep = new Point(this.ex, this.ey);
		Point sp = new Point(this.sx, this.sy);
		this.fsp = sp;
		this.fep = ep;
		System.out.println("fsp: "+fsp.x+","+fsp.y);
		System.out.println("fep: "+fep.x+","+fep.y);
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
