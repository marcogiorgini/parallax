/*
 * Copyright 2012 Alex Usachev, thothbot@gmail.com
 * 
 * This file based on the JavaScript source file of the THREE.JS project, 
 * licensed under MIT License.
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

package thothbot.parallax.core.shared.lights;

import thothbot.parallax.core.shared.core.Vector3f;
import thothbot.parallax.core.shared.objects.Object3D;

public class DirectionalLight extends Light
{
	private Object3D target;
	
	private float intensity;
	private float distance;

	private boolean castShadow = false;

	//

	public int shadowCameraNear = 50;
	public int shadowCameraFar = 5000;

	public int shadowCameraLeft = -500;
	public int shadowCameraRight = 500;
	public int shadowCameraTop = 500;
	public int shadowCameraBottom = -500;

	public boolean shadowCameraVisible = false;

	public float shadowBias = 0f;
	public float shadowDarkness = 0.5f;

	public int shadowMapWidth = 512;
	public int shadowMapHeight = 512;

	//

	public boolean shadowCascade = false;

	public Vector3f shadowCascadeOffset;
	public int shadowCascadeCount = 2;

	public float[] shadowCascadeBias = { 0f, 0f, 0f};
	public int[] shadowCascadeWidth = { 512, 512, 512 };
	public int[] shadowCascadeHeight = { 512, 512, 512 };

	public float[] shadowCascadeNearZ = { -1.000f, 0.990f, 0.998f };
	public float[] shadowCascadeFarZ = { 0.990f, 0.998f, 1.000f };

	public Object[] shadowCascadeArray;

	//

	public Object shadowMap;
	public Object shadowMapSize;
	public Object shadowCamera;
	public Object shadowMatrix;

	public DirectionalLight(int hex) 
	{
		this(hex, 1f);
	}

	public DirectionalLight(int hex, float intensity)
	{
		this(hex, intensity, 0f);
	}
	
	public DirectionalLight(int hex, float intensity, float distance) 
	{
		super(hex);

		this.intensity = intensity;
		this.distance = distance;

		this.position = new Vector3f(0f, 1f, 0f);
		this.target   = new Object3D();
		this.shadowCascadeOffset = new Vector3f(0f, 0f, -1000f);
	}
	
	public Object3D getTarget() {
		return this.target;
	}
	
	public void setTarget(Object3D target) {
		this.target = target;
	}
	
	public float getIntensity() {
		return this.intensity;
	}
	
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	
	public float getDistance() {
		return this.distance;
	}
	
	public void setDistance(float distance) {
		this.distance = distance;
	}

	public void setCastShadow(Boolean castShadow)
	{
		this.castShadow = castShadow;
	}

	public Boolean getCastShadow()
	{
		return castShadow;
	}

	public boolean isAllocateShadows()
	{
		return this.castShadow && !this.shadowCascade;
	}
}
