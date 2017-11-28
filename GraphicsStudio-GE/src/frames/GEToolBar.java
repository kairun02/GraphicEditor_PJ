package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import constants.GEConstants;
import constants.GEConstants.EToolBarButtons;
import shapes.*;
/**
 * Shape 툴바입니다.
 */
public class GEToolBar extends JToolBar {
	public GEToolBar(String label) {
		super(label);
		
		toolBarHandler = new GEToolBarHandler();
		buttonGroup = new ButtonGroup();
		JRadioButton rButton = null;
		
		for(EToolBarButtons btn : EToolBarButtons.values()) {
			rButton = new JRadioButton();
			rButton.setIcon(new ImageIcon(GEConstants.IMG_URL + btn.toString() + GEConstants.TOOLBAR_BTN));
			rButton.setSelectedIcon(new ImageIcon(GEConstants.IMG_URL + btn.toString() + GEConstants.TOOLBAR_BTN_SLT));
			rButton.setActionCommand(btn.toString());
			rButton.addActionListener(toolBarHandler);
			this.add(rButton);
			buttonGroup.add(rButton);
		}
	}
	
	public void init(GEDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.clickDefaultButton();
	}
	
	private void clickDefaultButton() {
		JRadioButton rButton = (JRadioButton)this.getComponent(
				EToolBarButtons.Rectangle.ordinal());
		rButton.doClick();
	}
	
	private GEDrawingPanel drawingPanel;
	private GEToolBarHandler toolBarHandler;
	private ButtonGroup buttonGroup;
	
	private class GEToolBarHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton rButton = (JRadioButton)e.getSource();
			if(rButton.getActionCommand().equals(
					EToolBarButtons.Rectangle.name())) {
				drawingPanel.setCurrentshape(new GERectangle());
			}else if(rButton.getActionCommand().equals(
					EToolBarButtons.Ellipse.name())) {
				drawingPanel.setCurrentshape(new GEEllipse());
			}else if(rButton.getActionCommand().equals(
					EToolBarButtons.Line.name())) {
				drawingPanel.setCurrentshape(new GELine());
			}else if(rButton.getActionCommand().equals(
					EToolBarButtons.Polygon.name())) {
				drawingPanel.setCurrentshape(new GEPolygon());
			}else if(rButton.getActionCommand().equals(
					EToolBarButtons.Select.name())) {
				drawingPanel.setCurrentshape(null);
			}
		}
		
	}
}
