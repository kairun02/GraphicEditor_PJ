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
import constants.GEConstants.ACTION_LIST;
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
import utils.GEHistory;

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
		history = new GEHistory(this);
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
			history.Push(ACTION_LIST.Color, selectedShape, selectedShape);
			selectedShape.setFillColor(fillColor);
			repaint();
		}else {
			this.fillColor = fillColor;
		}
	}

	public void setLineColor(Color lineColor) {
		if(selectedShape != null) {
			history.Push(ACTION_LIST.Color, selectedShape, selectedShape);
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
		currentShape.finish();
		history.Push(ACTION_LIST.Create, currentShape, new GENull());
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
			if(shape == null) {
				System.out.println("shape is null");
			}else {
				shape.setSelected(false);
			}
		}
	}

	//ClipBoard 관련
	public void deleteShape() {
		System.out.println("도형 삭제");
		GEShape shape = new GENull();
		history.Push(ACTION_LIST.Delete, shape, selectedShape); //Delete. 
		shapeList.set(shapeList.indexOf(selectedShape), shape);
		selectedShape = null;
		repaint();
	}

	public void copyShape() {
		clipBoard.copyShape(selectedShape);
	}

	public void pasteShape() {
		GEShape shape = clipBoard.pasteShape();
		shapeList.add(shape);
		history.Push(ACTION_LIST.Create, shape, new GENull());
		selectedShape = shape;
		repaint();
	}
	/**
	 * GEHistory에서 사용.
	 * 기존에 존재하는 shape를 newshape(저장된 shape)로 교체
	 */
	public void changeShape(GEShape preshape, GEShape newshape) {
		int place = shapeList.indexOf(preshape);
		System.out.println("Drawing place : "+place);
		shapeList.set(place, newshape);
		clearSelectedShapes();
		menu.selected(false);
		repaint();
	}

	public void onHistory() {
		menu.onHistory();
	}

	public void Undo() {
		history.Undo();
	}

	public void Redo() {
		history.Redo();
	}
	
	public void resizeShape(int percentage) {
		GEShape percentShape;
		percentShape = selectedShape.drawPercentage(percentage);
		shapeList.remove(selectedShape);
		shapeList.add(percentShape);
		selectedShape = null;
		repaint();
		
	}
	
	
	private GEShape currentShape;
	private GEShape selectedShape;
	private GEShape prevState;
	private Color fillColor;
	private Color lineColor;
	private GETransformer transformer;
	private ArrayList<GEShape> shapeList;
	private EState currentState;
	private GECursorManager cursorManager;
	private MouseDrawingHandler drawingHandler;
	private GEClipBoard clipBoard;
	private GEMenuBar menu;
	private GEHistory history;

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
							prevState = selectedShape.dup();
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
				transformer = new GEDrawer(currentShape);
				((GEDrawer)transformer).initend(e.getPoint());
				finishDraw();
			}else if(currentState == EState.NPointsDrawing) {
				return;
			}else if(currentState == EState.Resizing) {
				((GEResizer)transformer).finalize(e.getPoint());
			}else if(currentState == EState.Moving) {
				history.Push(ACTION_LIST.Move, selectedShape, prevState);
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
