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

package thothbot.parallax.core.shared.materials;

import thothbot.parallax.core.client.shader.Shader;
import thothbot.parallax.core.client.shader.ShaderDepth;

public class MeshDepthMaterial extends Material
{
	public static class MeshDepthMaterialOptions extends Material.MaterialOptions 
	{
		public Material.SHADING shading = Material.SHADING.SMOOTH;
	}
	
	public MeshDepthMaterial(MeshDepthMaterialOptions options)
	{
		super(options);
		setShading(options.shading);
	}
		
	public Material.SHADING bufferGuessNormalType () 
	{
		// only MeshBasicMaterial and MeshDepthMaterial don't need normals
		return null;
	}
	
	@Override
	public Shader getShaderId()
	{
		return new ShaderDepth();
	}
}