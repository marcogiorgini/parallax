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

package org.parallax3d.parallax.graphics.extras.curves.parametric;

import org.parallax3d.parallax.graphics.extras.core.Curve;
import org.parallax3d.parallax.math.Vector3;

public class CurveDecoratedTorusKnot4a extends Curve
{

	protected float scale;
	
	public CurveDecoratedTorusKnot4a()
	{
		this(40);
	}
	
	public CurveDecoratedTorusKnot4a(float scale)
	{
		this.scale = scale;
	}
	
	@Override
	public Vector3 getPoint(float t)
	{
		t *= Math.PI * 2.0;
		
		float x = (float)(Math.cos(2.0 * t) * (1 + 0.6 * (Math.cos(5.0 * t) + 0.75 * Math.cos(10.0 * t))));
		float y = (float)(Math.sin(2.0 * t) * (1 + 0.6 * (Math.cos(5.0 * t) + 0.75 * Math.cos(10.0 * t))));
		float z = (float)(0.35 * Math.sin(5.0 * t));

		return new Vector3(x, y, z).multiply(this.scale);
	}
}
