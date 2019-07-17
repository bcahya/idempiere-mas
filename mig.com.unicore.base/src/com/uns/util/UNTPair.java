/**
 * 
 */
package com.uns.util;

/**
 * @author menjangan
 *
 */
public class UNTPair<X, Y> {

	private X m_x;
	private Y m_y;
	public UNTPair(X x, Y y) {
		m_x = x;
		m_y = y;
	}
	
	@Override
	public String toString () {
		return "UNT-Pair{" + m_x + "," + m_y + "}";
	}

	public X getX () {
		return m_x;
	}
	
	public Y getY () {
		return m_y;
	}
}
