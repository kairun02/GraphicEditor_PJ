package transformer;

import java.awt.Graphics2D;
import java.awt.Point;

import shapes.GEPolygon;
import shapes.GEShape;

/**
 * shape와 DrawingPanel 사이에서 중간다리 역할을 함.
 * 이전의 animateDraw 역할을 함.
 */
public class GEDrawer extends GETransformer {
	
	public GEDrawer(GEShape shape) {
		super(shape);
	}

	@Override
	public void transformer(Graphics2D g2D, Point p) {
		g2D.setXORMode(g2D.getBackground());
		g2D.setStroke(dashedLineStroke);
		shape.draw(g2D);
		shape.setCoordinate(p);
		shape.draw(g2D);
	}

	@Override
	public void init(Point p) {
		shape.initDraw(p);
	}
	
	public void continueDrawing(Point p) {
		((GEPolygon)shape).continueDrawing(p);
	}

}
