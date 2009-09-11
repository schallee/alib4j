package net.darkmist.alib.servlet;

import javax.servlet.ServletResponse;

public interface OutputMethodServletResponse extends ServletResponse
{
	/** Check for {@link #getWriter()} having been called.
	 * @return true if {@link #getWriter()} was called, false otherwise.
	 */
	public boolean isUsingWriter();

	/** Check for {@link #getOutputStream()} having been called.
	 * @return true if {@link #getOutputStream()} was called, false otherwise.
	 */
	public boolean isUsingOutputStream();
}
