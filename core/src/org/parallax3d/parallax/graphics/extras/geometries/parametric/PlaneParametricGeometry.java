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

public class PlaneParametricGeometry extends ParametricGeometry
{

	public PlaneParametricGeometry(final int width, final int height, int slices, int stacks)
	{
		super(new ParametricFunction() {
			
			@Override
			public Vector3 run(float u, float v)
			{
				float x = u * (float)width;
				float y = 0.0f;
				float z = v * (float)height;

				return new Vector3(x, y, z);
			}
		}, slices, stacks);
	}

}
