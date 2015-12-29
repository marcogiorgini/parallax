/*
 * Copyright 2012 Alex Usachev, thothbot@gmail.com
 * 
 * This file is part of Parallax project.
 * 
 * Parallax is free software: you can redistribute it and/or modify it 
 * under the terms of the Creative Commons Attribution 3.0 Unported License.
 * 
 * Parallax is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Creative Commons Attribution 
 * 3.0 Unported License. for more details.
 * 
 * You should have received a copy of the the Creative Commons Attribution 
 * 3.0 Unported License along with Parallax. 
 * If not, see http://creativecommons.org/licenses/by/3.0/.
 */

package org.parallax3d.parallax.graphics.renderers;

import org.parallax3d.parallax.App;
import org.parallax3d.parallax.system.ThreeJsObject;
import org.parallax3d.parallax.system.gl.GL20;

@ThreeJsObject("THREE.WebGLExtensions")
public final class WebGLExtensions {
	
	public static enum Id {
		OES_texture_float,
		OES_texture_float_linear,
		OES_standard_derivatives,
		EXT_texture_filter_anisotropic,
		WEBGL_compressed_texture_s3tc,
		WEBGL_compressed_texture_pvrtc,
		OES_element_index_uint,
		EXT_blend_minmax,
		EXT_frag_depth
	};

	public static boolean get(Id id) {
		
		String[] extensions = App.gl.glGetString(GL20.GL_EXTENSIONS).split(" ");

		boolean retval = false;
		for(int i = 0, len = extensions.length; i < len; i++)
		{
			if(extensions[i] == id.name())
			{
				retval = true;
				break;
			}
		}

		if ( retval == false ) {

			App.app.error("WebGLRenderer", id.toString() + " extension not supported.");

		}
		
		return retval;

	}
	
}