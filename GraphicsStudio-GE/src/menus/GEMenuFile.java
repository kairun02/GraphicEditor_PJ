package menus;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import constants.GEConstants.EFileMenuItems;

/**
 * 파일과 관련된 메뉴입니다.
 * @author HMCL_sy
 */
public class GEMenuFile extends JMenu {
	public GEMenuFile(String label) {
		super(label);
		for(EFileMenuItems btn : EFileMenuItems.values()) {
			JMenuItem menuItem = new JMenuItem(btn.toString());
			this.add(menuItem);
		}
		//단축키 설정
		this.getItem(0).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		this.getItem(1).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		this.getItem(2).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		this.getItem(3).setEnabled(false);
	}
	
}
