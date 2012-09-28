/*
 * Copyright 2012 Alex Usachev, thothbot@gmail.com
 * 
 * This file is part of Parallax project.
 * 
 * Parallax is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 * 
 * Parallax is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with 
 * Parallax. If not, see http://www.gnu.org/licenses/.
 */

package thothbot.parallax.core.client.gl2.enums;

import thothbot.parallax.core.client.gl2.WebGLConstants;

public enum RenderbufferInternalFormat implements GLEnum
{
	RGBA4(WebGLConstants.RGBA4),
	RGB5_A1(WebGLConstants.RGB5_A1),
	RGB565(WebGLConstants.RGB565),
	DEPTH_COMPONENT16(WebGLConstants.DEPTH_COMPONENT16),
	STENCIL_INDEX8(WebGLConstants.STENCIL_INDEX8),
	DEPTH_STENCIL(WebGLConstants.DEPTH_STENCIL);

	private final int value;

	private RenderbufferInternalFormat(int value) {
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}
}