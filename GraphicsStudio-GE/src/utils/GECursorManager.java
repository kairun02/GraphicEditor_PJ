package utils;

import java.awt.Cursor;
import java.util.ArrayList;

/**
 * resize할 때 커서 모양을 나타내기 위함입니다.
 * 
 */
public class GECursorManager extends ArrayList<Cursor> {
	public GECursorManager() {
		add(new Cursor(Cursor.NW_RESIZE_CURSOR));
		add(new Cursor(Cursor.N_RESIZE_CURSOR));
		add(new Cursor(Cursor.NE_RESIZE_CURSOR));
		add(new Cursor(Cursor.W_RESIZE_CURSOR));
		add(new Cursor(Cursor.E_RESIZE_CURSOR));
		add(new Cursor(Cursor.SW_RESIZE_CURSOR));
		add(new Cursor(Cursor.S_RESIZE_CURSOR));
		add(new Cursor(Cursor.SE_RESIZE_CURSOR));
		add(new Cursor(Cursor.HAND_CURSOR));
		add(new Cursor(Cursor.MOVE_CURSOR));
	}
}
