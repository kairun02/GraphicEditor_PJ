package utils;

import java.util.ArrayList;

import constants.GEConstants.ACTION_LIST;
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
	 * @param act : create, delete 등의 행동
	 * @param place : 현재 panel list의 위치
	 * @param shape : 당시(직전) 상태가 기록되어있는 shape
	 */
	public void Push(ACTION_LIST act, GEShape place, GEShape shape) {
		/*undo상태에서 push할 경우*/
		while(historyList.size() > currentAction) {
			--maxAction;
			historyList.remove(maxAction);
		}
		
		historyList.add(new GEHistorySave(act, place, shape));
		currentAction = maxAction = historyList.size();
		System.out.println("Max : "+maxAction+", Current : "+currentAction);
		panel.onHistory();
	}
	
	/**
	 * 바로 직전상태로 undo합니다.
	 */
	public void Undo(){
		if(currentAction <=0) {
			System.out.println("undo할 데이터가 없습니다.");
		}else {
			GEShape shape;
			GEHistorySave data = historyList.get(--currentAction);
			switch(data.act) {
			case Create :
			case Delete :
				panel.changeShape(data.place, data.shape_state);
				break;
			case Color :
				shape = data.place.dup();
				data.place.setFillColor(data.shape_state.getFillColor());
				data.place.setLineColor(data.shape_state.getLineColor());
				historyList.set(historyList.indexOf(data), new GEHistorySave(data.act, data.place, shape));
				panel.repaint();
				break;
			case Move :
				shape = data.place.dup();
				data.place.setPlace(data.shape_state.getPlace());
				historyList.set(historyList.indexOf(data), new GEHistorySave(data.act, data.place, shape));
				panel.repaint();
				break;
			default:
				break;
			}
			
		}
		System.out.println("Max : "+maxAction+", Current : "+currentAction);
	}
	
	/**
	 * undo했을 경우 다시 복구합니다.
	 */
	public void Redo() {
		if(currentAction >= maxAction) {
			System.out.println("redo할 데이터가 없습니다.");
		}
		else {
			GEHistorySave data = historyList.get(currentAction);
			currentAction++;
			switch (data.act) {
			case Create :
			case Delete :
				panel.changeShape(data.shape_state, data.place);
				break;
			case Color :
				GEShape shape = data.place.dup();
				data.place.setFillColor(data.shape_state.getFillColor());
				data.place.setLineColor(data.shape_state.getLineColor());
				historyList.set(historyList.indexOf(data), new GEHistorySave(data.act, data.place, shape));
				panel.repaint();
				break;
			case Move :
				shape = data.place.dup();
				data.place.setPlace(data.shape_state.getPlace());
				historyList.set(historyList.indexOf(data), new GEHistorySave(data.act, data.place, shape));
				panel.repaint();
				break;
			case Resize :
				
				break;
			default:
				panel.changeShape(data.place, data.shape_state);
				break;
			}
		}
		System.out.println("Max : "+maxAction+", Current : "+currentAction);
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
		public GEHistorySave(ACTION_LIST act, GEShape place, GEShape shape) {
			if(act == ACTION_LIST.Delete) {
				shape_state = shape;
			}else {
				shape_state = shape.dup();
			}
			this.act = act;
			this.place = place;
			
		}
		public ACTION_LIST act;
		public GEShape place;/*현재 shape를 가리키는 포인터 같은 개념*/
		public GEShape shape_state;/*현재의 상태를 복사하여 가지고 있는 도형 정보*/
	}
	
}
