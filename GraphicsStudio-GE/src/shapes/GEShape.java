package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import constants.GEConstants.EAnchorTypes;
import utils.GEAnchorList;

/**
 * 도형의 프로토 타입.
 */
public abstract class GEShape {
	public GEShape(Shape shape) {
		this.myShape = shape;
		anchorList = null;
		selected = false;
		affineTransform = new AffineTransform();
	}
	
	public GEAnchorList getAnchorList() {
		return anchorList;
	}
	
	public EAnchorTypes getSelectedAnchor() {
		return selectedAnchor;
	}
	
	/**현재 도형을 둘러싼 사각형의 크기를 받아옴.*/
	public Rectangle getBounds() {
		return myShape.getBounds();
	}
	
	/**선택 여부 확인*/
	public boolean isSelected() {
		return selected;
	}
	
	/**도형을 그림*/
	public void draw(Graphics2D g2D) {
		if(fillColor != null) {
			g2D.setColor(fillColor);
			g2D.fill(myShape);
		}
		if(lineColor != null) {
			g2D.setColor(lineColor);
			g2D.draw(myShape);
		}
		if(selected==true) {
			anchorList.setPosition(myShape.getBounds());
			anchorList.draw(g2D);
		}
	}
	
	/**앵커가 눌렸을 때 앵커를 건내줌*/
	public EAnchorTypes onAnchor(Point p) {
		selectedAnchor = anchorList.onAnchors(p);
		return selectedAnchor;
	}
	
	/***/
	public void moveCoordinate(Point moveP) {
		affineTransform.setToTranslation(moveP.getX(), moveP.getY());
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void move(Point resizeAnchor) {
		if(resizeAnchor == null) {
			System.out.println("resizeAnchor is null");
		}
		affineTransform.setToTranslation(resizeAnchor.x, resizeAnchor.y);
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void moveReverse(Point resizeAnchor) {
		affineTransform.setToTranslation(-resizeAnchor.x, -resizeAnchor.y);
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void resizeCoordinate(Point2D resizeFactor) {
		affineTransform.setToScale(resizeFactor.getX(), resizeFactor.getY());
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		if(selected==true) {
			anchorList = new GEAnchorList();
			anchorList.setPosition(myShape.getBounds());
		}else {
			anchorList = null;
		}
	}
	
	public boolean onShape(Point p) {
		if(anchorList != null) {
			selectedAnchor = anchorList.onAnchors(p);
			if(selectedAnchor != EAnchorTypes.NONE) {
				return true;
			}
		}
		return myShape.intersects(new Rectangle(p.x, p.y, 2, 2));
	}
	
	
	public abstract void initDraw(Point startP);
	public abstract void setCoordinate(Point currentP);
	public abstract GEShape clone();
	
	protected Shape myShape;
	protected Point startP;
	protected boolean selected;
	protected GEAnchorList anchorList;
	protected EAnchorTypes selectedAnchor;
	protected Color fillColor;
	protected Color lineColor;
	protected AffineTransform affineTransform;
}
