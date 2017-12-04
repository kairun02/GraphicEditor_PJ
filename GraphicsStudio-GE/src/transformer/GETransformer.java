package transformer;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

import constants.GEConstants;
import shapes.GEShape;
import utils.GEHistory;

public abstract class GETransformer {
	//History를 사용하지 않을 경우의 생성자
	public GETransformer(GEShape shape) {
		this.shape =shape;
		float dashes[] = {GEConstants.DEFAULT_DASH_OFFSET};
		dashedLineStroke = new BasicStroke(
				GEConstants.DEFAULT_DASHEDLINE_WIDTH, // 점선의 두꼐
				BasicStroke.CAP_ROUND, // 점선의 끝 모양
				BasicStroke.JOIN_ROUND, // 점선이 교차될 때 모양
				10, //miter limit
				dashes, // 점선의 끊기는 간격
				0); //점선 시작 지점
		actionhistory = null;
	}
	
	public GETransformer(GEShape shape, GEHistory history) {
		this(shape);
		init(history);
	}
	
	private void init(GEHistory history) {
		actionhistory = history;
	}
	
	public abstract void transformer(Graphics2D g2D, Point p);
	public abstract void init(Point p);
	
	
	protected GEShape shape;
	protected BasicStroke dashedLineStroke;
	protected GEHistory actionhistory;
}
