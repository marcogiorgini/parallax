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

package org.parallax3d.parallax.graphics.extras.geometries.parametric;

import org.parallax3d.parallax.graphics.extras.geometries.ParametricGeometry;
import org.parallax3d.parallax.math.Vector3;

/**
 * <img src="http://thothbot.github.com/parallax/static/docs/mobius_3d.gif" />
 * <p>
 * Mobius 3D geometry
 * 
 * @author thothbot
 *
 */
public class Mobius3dParametricGeometry extends ParametricGeometry
{

	public Mobius3dParametricGeometry(int slices, int stacks) {
		super(new ParametricFunction() {
			
			@Override
			public Vector3 run(float u, float t)
			{
				// volumetric mobius strip
				u *= Math.PI;
				t *= 2.0 * Math.PI;

				u = u * 2.0f;
				float phi = u / 2.0f;
				float major = 2.25f, a = 0.125f, b = 0.65f;

				float x = (float)(a * Math.cos(t) * Math.cos(phi) - b * Math.sin(t) * Math.sin(phi));
				float z = (float)(a * Math.cos(t) * Math.sin(phi) + b * Math.sin(t) * Math.cos(phi));
				float y = (float)((major + x) * Math.sin(u));
				x = (major + x) * (float)Math.cos(u);
				return new Vector3(x, y, z);
			}
		}, slices, stacks);
		// TODO Auto-generated constructor stub
	}

}
