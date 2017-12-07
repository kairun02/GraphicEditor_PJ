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
	
	/**좌표이동?*/
	public void moveCoordinate(Point moveP) {
		affineTransform.setToTranslation(moveP.getX(), moveP.getY());
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	/**도형을 이동*/
	public void move(Point resizeAnchor) {
		if(resizeAnchor == null) {
			System.out.println("resizeAnchor is null");
		}
		System.out.println("move");
		affineTransform.setToTranslation(resizeAnchor.x, resizeAnchor.y);
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void moveReverse(Point resizeAnchor) {
		affineTransform.setToTranslation(-resizeAnchor.x, -resizeAnchor.y);
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	/**도형의 크기변경 관련*/
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
	
	public Color getFillColor() {
		return fillColor;
	}
	
	public Color getLineColor() {
		return lineColor;
	}
	
	/**현재 선택되어있는지*/
	public void setSelected(boolean selected) {
		this.selected = selected;
		if(selected==true) {
			anchorList = new GEAnchorList();
			anchorList.setPosition(myShape.getBounds());
		}else {
			anchorList = null;
		}
	}
	
	/**앵커 선택상태?*/
	public boolean onShape(Point p) {
		if(anchorList != null) {
			selectedAnchor = anchorList.onAnchors(p);
			if(selectedAnchor != EAnchorTypes.NONE) {
				return true;
			}
		}
		return myShape.intersects(new Rectangle(p.x, p.y, 2, 2));
	}
	
	public Shape getPlace() {
		AffineTransform affineTransform = new AffineTransform();
		Shape p = affineTransform.createTransformedShape(myShape);
		return p;
	}
	
	public void setPlace(Shape shape) {
		setShape(shape);
	}
	
	/**그리기 시작할 때 시작점 지정*/
	public abstract void initDraw(Point startP);
	/**그리는 동안 어떻게 그려질지 미리 볼 수 있다*/
	public abstract void initend(Point endP);
	public abstract void setCoordinate(Point currentP);
	public abstract GEShape clone();
	/**클론 대신 사용할 자신을 복사하는 함수*/
	public abstract GEShape dup();
	public abstract GEShape drawPercentage(int percentage);
	public abstract void finish();
	public Point getsp() {
		return this.startP;
	}
	public Point getep() {
		return this.endP;
	}
	
	protected void setShape(Shape shape) {
		myShape = shape;
	}
	
	protected Shape myShape;
	protected Point startP;
	protected Point endP;
	protected boolean selected;
	protected GEAnchorList anchorList;
	protected EAnchorTypes selectedAnchor;
	protected Color fillColor;
	protected Color lineColor;
	protected AffineTransform affineTransform;
}
