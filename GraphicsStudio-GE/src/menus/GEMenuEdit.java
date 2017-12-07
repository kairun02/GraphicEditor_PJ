package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import constants.GEConstants.EColorMenuItems;
import constants.GEConstants.EEditMenuItems;
import frames.GEDrawingPanel;
import menus.GEMenuColor.ColorMenuHandler;

/**
 * 그리기와 관련된 메뉴입니다.
 * @author HMCL_sy
 */
public class GEMenuEdit extends JMenu {
	public GEMenuEdit(String label) {
		super(label);
		
		editMenuHandler = new EditMenuHandler();
		
		for(EEditMenuItems btn : EEditMenuItems.values()) {
			JMenuItem menuItem = new JMenuItem(btn.toString());
			menuItem.addActionListener(editMenuHandler);
			menuItem.setActionCommand(btn.toString());
			menuItem.setEnabled(false); //초기엔 모든 기능이 비활성화 되어있음.
			this.add(menuItem);
		}
		//단축키
		this.getItem(0).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		this.getItem(3).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		this.getItem(4).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		this.getItem(5).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
	}
	
	public void init(GEMenuBar bar) {
		this.myBar = bar;
	}
	
	/** 도형의 선택 여부가 바뀔 경우 */
	public void selectedShape(boolean selected) {
		for(int i=2; i<5;i++) {
			this.getItem(i).setEnabled(selected);
		}
		this.getItem(8).setEnabled(true);
	}
	
	public void onHistory() {
		this.getItem(0).setEnabled(true);
		this.getItem(1).setEnabled(false);
	}
	
	// undo & redo
	private void undoHistory() {
		this.getItem(1).setEnabled(true);
		myBar.undoHistory();
	}
	
	private void redoHistory() {
		myBar.redoHistory();
	}
	
	private void copyShape() {
		this.getItem(5).setEnabled(true);
		myBar.copyShape();
	}
	
	private void cutShape() {
		this.getItem(5).setEnabled(true);
		myBar.cutShape();
	}
	
	// Grouping
	private void groupShape() {
		
	}
	
	private void ungroupShape() {
		
	}
	
	private void resizeShape() {
		myBar.resizeShape();
		this.getItem(8).setEnabled(false);
	}
	
	private GEMenuBar myBar;
	private EditMenuHandler editMenuHandler;
	
	class EditMenuHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(EEditMenuItems.valueOf(e.getActionCommand())) {
			case Undo:
				undoHistory();
				break;
			case Redo:
				redoHistory();
				break;
			case 삭제:
				myBar.deleteShape();
				break;
			case 잘라내기:
				cutShape();
				break;
			case 복사:
				copyShape();
				break;
			case 붙이기:
				myBar.pasteShape();
				break;
			case Group:
				groupShape();
				break;
			case Ungroup:
				ungroupShape();
				break;
			case Resize:
				resizeShape();
				break;
			}
		}
		
	}
}
