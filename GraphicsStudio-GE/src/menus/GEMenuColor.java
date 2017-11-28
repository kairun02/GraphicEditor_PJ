package menus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GEConstants;
import constants.GEConstants.EColorMenuItems;
import frames.GEDrawingPanel;

/**
 * 색 설정과 관련된 메뉴입니다.
 * @author HMCL_sy
 */
public class GEMenuColor extends JMenu {
	public GEMenuColor(String label) {
		super(label);
		colorMenuHandler = new ColorMenuHandler();
		
		for(EColorMenuItems btn : EColorMenuItems.values()) {
			JMenuItem menuItem = new JMenuItem(btn.toString());
			menuItem.addActionListener(colorMenuHandler);
			menuItem.setActionCommand(btn.toString());
			this.add(menuItem);
		}
	}
	
	public void init(GEDrawingPanel panel) {
		this.drawingPanel = panel;
	}
	
	private void setLineColor() {
		Color lineColor = JColorChooser.showDialog(null, GEConstants.TITLE_LINECOLOR, null);
		drawingPanel.setLineColor(lineColor);
	}
	
	private void setFillColor() {
		Color fillColor = JColorChooser.showDialog(null, GEConstants.TITLE_FILLCOLOR, null);
		drawingPanel.setFillColor(fillColor);
	}
	
	private void clearLineColor() {
		drawingPanel.setLineColor(GEConstants.DEFAULT_LINE_COLOR);
	}
	
	private void clearFillColor() {
		drawingPanel.setFillColor(GEConstants.DEFAULT_FILL_COLOR);
	}
	
	private GEDrawingPanel drawingPanel;
	private ColorMenuHandler colorMenuHandler;
	class ColorMenuHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(EColorMenuItems.valueOf(e.getActionCommand())) {
			case SetLineColor:
				setLineColor();
				break;
			case SetFillColor:
				setFillColor();
				break;
			case ClearLinecolor:
				clearLineColor();
				break;
			case ClearFillColor:
				clearFillColor();
				break;
			}
		}
		
	}
}
