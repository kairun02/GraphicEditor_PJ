package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import constants.GEConstants;
import constants.GEConstants.EAnchorTypes;
import constants.GEConstants.EState;
import menus.GEMenuBar;
import shapes.GENull;
import shapes.GEPolygon;
import shapes.GEShape;
import transformer.GEDrawer;
import transformer.GEMover;
import transformer.GEResizer;
import transformer.GETransformer;
import utils.GEClipBoard;
import utils.GECursorManager;

public class GEDrawingPanel extends JPanel {
	
	public GEDrawingPanel() {
		super();
		shapeList = new ArrayList<GEShape>();
		currentState = EState.Idle;
		drawingHandler = new MouseDrawingHandler();
		lineColor = GEConstants.DEFAULT_LINE_COLOR;
		fillColor = GEConstants.DEFAULT_FILL_COLOR;
		cursorManager = new GECursorManager();
		clipBoard = new GEClipBoard();
		this.addMouseListener(drawingHandler);
		this.addMouseMotionListener(drawingHandler);
		this.setBackground(GEConstants.BACKGROUND_COLOR);
		this.setForeground(GEConstants.FOREGROUND_COLOR);
	}
	
	public GEDrawingPanel(GEMenuBar menu) {
		this();
		this.menu = menu;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		for(GEShape shape : shapeList) {
			shape.draw(g2D);
		}
	}
	
	//Color 관련
	public void setFillColor(Color fillColor) {
		if(selectedShape != null) {
			selectedShape.setFillColor(fillColor);
			repaint();
		}else {
			this.fillColor = fillColor;
		}
	}
	
	public void setLineColor(Color lineColor) {
		if(selectedShape != null) {
			selectedShape.setLineColor(lineColor);
			repaint();
		}else {
			this.lineColor = lineColor;
		}
	}
	
	//Draw 관련
	public void setCurrentshape(GEShape currentShape) {
		this.currentShape = currentShape;
	}
	
	private void initDraw(Point startP) {
		currentShape = currentShape.clone();
		currentShape.setFillColor(fillColor);
		currentShape.setLineColor(lineColor);
	}
	
	private void continueDrawing(Point currentP) {
		((GEDrawer)transformer).continueDrawing(currentP);
	}
	
	private void finishDraw() {
		shapeList.add(currentShape);
		
	}
	
	//Select 관련
	private GEShape onShape(Point p) {
		for(int i = shapeList.size() -1; i >=0; i--) {
			GEShape shape = shapeList.get(i);
			if(shape.onShape(p)) {
				return shape;
			}
		}
		return null;
	}
	
	private void clearSelectedShapes() {
		for(GEShape shape : shapeList) {
			shape.setSelected(false);
		}
	}
	
	//ClipBoard 관련
	public void deleteShape() {
		System.out.println("도형 삭제");
		shapeList.set(shapeList.indexOf(selectedShape), new GENull());
		selectedShape = null;
		repaint();
	}
	
	public void copyShape() {
		clipBoard.copyShape(selectedShape);
	}
	
	public void pasteShape() {
		GEShape shape = clipBoard.pasteShape();
		shapeList.add(shape);
		selectedShape = shape;
		repaint();
	}
	
	/**
	 * GEHistory에서 사용.
	 * 기존에 존재하는 shape를 newshape(저장된 shape)로 교체
	 */
	public void changeShape(int place, GEShape newshape) {
		if(newshape == null) { //create를 undo 시킬 때
			shapeList.remove(place);
		} else if(place == -1) { //create undo -> redo 할 경우
			shapeList.add(newshape);
		} else {
			shapeList.set(place, newshape);
		}
		repaint();
	}
	
	private GEShape currentShape;
	private GEShape selectedShape;
	private Color fillColor;
	private Color lineColor;
	private GETransformer transformer;
	private ArrayList<GEShape> shapeList;
	private EState currentState;
	private GECursorManager cursorManager;
	private MouseDrawingHandler drawingHandler;
	private GEClipBoard clipBoard;
	private GEMenuBar menu;
	
	/**
	 * 마우스 이벤트를 받아오는 핸들러
	 */
	private class MouseDrawingHandler extends MouseInputAdapter{
		@Override
		public void mouseDragged(MouseEvent e) {
			if(currentState != EState.Idle) {
				transformer.transformer((Graphics2D)getGraphics(), e.getPoint());
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(currentState == EState.Idle) {
				if(currentShape != null) {
					clearSelectedShapes();
					selectedShape = null;
					initDraw(e.getPoint());
					transformer = new GEDrawer(currentShape);
					transformer.init(e.getPoint());
					if (currentShape instanceof GEPolygon) {
						currentState = EState.NPointsDrawing;
					}else {
						currentState = EState.TwoPointsDrawing;
					}
				}else {
					selectedShape = onShape(e.getPoint());
					clearSelectedShapes();
					if(selectedShape != null) {
						menu.selected(true);
						selectedShape.setSelected(true);
						if(selectedShape.onAnchor(e.getPoint()) == EAnchorTypes.NONE) {
							transformer = new GEMover(selectedShape);
							currentState = EState.Moving;
							transformer.init(e.getPoint());
						}else {
							transformer = new GEResizer(selectedShape);
							currentState = EState.Resizing;
							transformer.init(e.getPoint());
						}
						
					}
					else {
						menu.selected(false);
					}
				}
				
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(currentState == EState.TwoPointsDrawing) {
				finishDraw();
			}else if(currentState == EState.NPointsDrawing) {
				return;
			}else if(currentState == EState.Resizing) {
				((GEResizer)transformer).finalize(e.getPoint());
			}
			currentState = EState.Idle;
			repaint();
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				if(currentState == EState.NPointsDrawing) {
					if(e.getClickCount() == 1) {
						continueDrawing(e.getPoint());
					}else if(e.getClickCount() == 2) {
						finishDraw();
						currentState = EState.Idle;
						repaint();
					}
				}
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if(currentState == EState.NPointsDrawing) {
				transformer.transformer((Graphics2D)getGraphics(), e.getPoint());
			}else if(currentState == EState.Idle) {
				GEShape shape = onShape(e.getPoint());
				if(shape == null) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}else if(shape.isSelected()) {
					EAnchorTypes anchorTypes = shape.onAnchor(e.getPoint());
					setCursor(cursorManager.get(anchorTypes.ordinal()));
				}
			}
		} 
	}
}
