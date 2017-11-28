package menus;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import constants.GEConstants.EEditMenuItems;

/**
 * 그리기와 관련된 메뉴입니다.
 * @author HMCL_sy
 */
public class GEMenuEdit extends JMenu {
	public GEMenuEdit(String label) {
		super(label);
		for(EEditMenuItems btn : EEditMenuItems.values()) {
			JMenuItem menuItem = new JMenuItem(btn.toString());
			this.add(menuItem);
		}
		//단축키
		this.getItem(0).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		this.getItem(3).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		this.getItem(4).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		this.getItem(5).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
	}
}
