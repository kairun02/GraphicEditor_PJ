package menus;

import javax.swing.JMenuBar;

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

	private GEMenuFile fileMenu;
	private GEMenuEdit editMenu;
	private GEMenuColor colorMenu;
	private GEDrawingPanel panel;
}
