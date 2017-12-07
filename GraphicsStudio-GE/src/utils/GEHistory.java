package utils;

import java.util.ArrayList;

import frames.GEDrawingPanel;
import shapes.GEShape;

/**
 * Redo, Undo를 위한 행동 저장소입니다.
 * @author HMCL_sy
 *
 */
public class GEHistory {
	public GEHistory(GEDrawingPanel panel) {
		historyList = new ArrayList<GEHistorySave>();
		maxAction = 0;
		currentAction = 0;
		this.panel = panel;
	}
	
	/**
	 * history data를 기록합니다.
	 */
	public void Push(int place, GEShape shape) {
		/*undo상태에서 push할 경우*/
		while(historyList.size() > currentAction) {
			historyList.remove(--maxAction);
		}
		historyList.add(new GEHistorySave(place, shape));
		currentAction = maxAction = historyList.size();
	}
	
	/**
	 * 바로 직전상태로 undo합니다.
	 */
	public void Undo(){
		if(currentAction <=0) {
			System.out.println("undo할 데이터가 없습니다.");
		}else {
			GEHistorySave data = historyList.get(--currentAction);
			panel.changeShape(data.place, data.shape_state);
		}
	}
	
	/**
	 * undo했을 경우 다시 복구합니다.
	 */
	public void Redo() {
		if(currentAction >= maxAction) {
			System.out.println("redo할 데이터가 없습니다.");
		}
		else {
			GEHistorySave data = historyList.get(++currentAction);
			panel.changeShape(data.place, data.shape_state);
		}
	}
	
	private GEDrawingPanel panel;
	private ArrayList<GEHistorySave> historyList;
	private int maxAction; /*쌓여있는 최대액션 번호*/
	private int currentAction;/*현재 액션 위치 번호*/
	
	/**
	 * 대상의 당시 상태와, 현재 대상이 어딨는지에 대한 데이터를 가지고 있습니다.
	 * @author HMCL_sy
	 */
	class GEHistorySave{
		public GEHistorySave(int place, GEShape shape) {
			this.place = place;
			shape_state = shape.dup();
		}
		public int place;/*현재 shape를 가리키는 포인터 같은 개념*/
		public GEShape shape_state;/*현재의 상태를 복사하여 가지고 있는 도형 정보*/
	}
	
}
