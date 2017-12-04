package utils;

import shapes.GEShape;

/**
 * 복사 및 붙여넣기를 할 임시 저장소입니다.
 * @author HMCL_sy
 */
public class GEClipBoard {
	public void copyShape(GEShape shape) {
		myshape = shape;
	}
	public GEShape pasteShape() {
		return myshape;
	}
	private GEShape myshape;
}
