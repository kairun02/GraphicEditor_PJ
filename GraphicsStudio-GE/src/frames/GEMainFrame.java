package frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import constants.GEConstants;
import menus.GEMenuBar;

/**
 * 그림판으로 사용할 메인 프레임입니다.
 */
public class GEMainFrame extends JFrame {
	/**
	 * 생성자입니다. 
	 * @param title : 창 맨 위에 표시할 타이틀입니다.
	 */
	private GEMainFrame(String title) {
		super(title);
		
		menuBar = new GEMenuBar();
		setJMenuBar(menuBar);
		
		drawingPanel = new GEDrawingPanel(menuBar);
		this.add(drawingPanel);
		
		toolBar = new GEToolBar(GEConstants.TITLE_TOOLBAR);
		this.add(BorderLayout.NORTH, toolBar);
	}
	
	/**
	 * Singleton Pattern 사용.
	 * @return
	 */
	public static GEMainFrame getInstance() {
		if(uniqueMainFrame == null) {
			uniqueMainFrame = new GEMainFrame(GEConstants.TITLE_MAINFRAME);
		}
		return uniqueMainFrame;
	}
	
	/**
	 * 창을 띄울때 사용. 크기, 닫기 기능 등 기본 세팅.
	 */
	public void init() {
		toolBar.init(drawingPanel);
		menuBar.init(drawingPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(GEConstants.WIDTH_MAINFRAME, GEConstants.HEIGHT_MAINFRAME);
		//개인적으로 추가
		this.setLocation(400, 100);
		this.setVisible(true);
	}
	
	/**Singleton패턴을 사용한 하나만 존재하는 프레임*/
	private static GEMainFrame uniqueMainFrame;
	
	/**그림 그리는 공간*/
	private GEDrawingPanel drawingPanel;
	
	/**메뉴 바*/
	private GEMenuBar menuBar;
	
	/**툴 바*/
	private GEToolBar toolBar;
}