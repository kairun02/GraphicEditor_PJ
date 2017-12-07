package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Shape들을 그룹화 하기위한 클래스
 * @author HMCL_sy
 */
public class GEShapeGroup extends GEShape {

	public GEShapeGroup() {
		super(null);
	}
	
	/**Group안에 들어갈 shape를 추가*/
	public void addShape(GEShape shape) {
		shapeList.add(shape);
	}
	
	@Override
	public void initDraw(Point startP) {}
	
	@Override
	public void initend(Point endP) {}
	
	@Override
	public GEShape drawPercentage(int percentage) {return null;}
	
	@Override
	public void draw(Graphics2D g2d) {
		for(GEShape shape : shapeList) {
			shape.draw(g2d);
		}
	}
	
	@Override
	public void moveCoordinate(Point moveP) {
		
	}

	@Override
	public void setCoordinate(Point currentP) {}

	@Override
	public GEShape clone() { return null; }

	@Override
	public GEShape dup() {
		return null;
	}
	
	private ArrayList<GEShape> shapeList;
}
