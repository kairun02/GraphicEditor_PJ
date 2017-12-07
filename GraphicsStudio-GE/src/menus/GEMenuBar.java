package menus;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import constants.GEConstants;
import frames.GEDrawingPanel;

/**
 * 상단 메뉴들을 포함하고 있는 메뉴 바입니다.
 */
public class GEMenuBar extends JMenuBar {
	public GEMenuBar() {
		fileMenu = new GEMenuFile(GEConstants.TITLE_FILEMENU);
		editMenu = new GEMenuEdit(GEConstants.TITLE_EDITMENU);
		colorMenu = new GEMenuColor(GEConstants.TITLE_COLORMENU);
		this.add(fileMenu);
		this.add(editMenu);
		this.add(colorMenu);
	}

	public void init(GEDrawingPanel drawingPanel) {
		colorMenu.init(drawingPanel);
		panel = drawingPanel;
		editMenu.init(this);
	}

	public void selected(boolean sel) {
		editMenu.selectedShape(sel);
	}
	
	public void onHistory() {
		editMenu.onHistory();
	}
	
	public void undoHistory() {
		panel.Undo();
	}
	
	public void redoHistory() {
		panel.Redo();
	}

	public void deleteShape() {
		panel.deleteShape();
	}

	public void cutShape() {
		panel.copyShape();
		panel.deleteShape();
	}

	public void copyShape() {
		panel.copyShape();
	}

	public void pasteShape() {
		panel.pasteShape();
	}
	
	/**
	 * 알림창을 띄워서 퍼센티지를 입력받아 드로잉패널의 resizeShape를 실행시킨다.
	 */
	public void resizeShape() {
		int percentage;
		String _temp;
		
		_temp = JOptionPane.showInputDialog("크기의 퍼센티지를 입력하세요.(0이상, %생략)");
		if(_temp == null || Integer.parseInt(_temp)<0) {
			return;
		}
		
		percentage = Integer.parseInt(_temp);
		System.out.println(percentage);
		panel.resizeShape(percentage);
	}

	private GEMenuFile fileMenu;
	private GEMenuEdit editMenu;
	private GEMenuColor colorMenu;
	private GEDrawingPanel panel;
}
