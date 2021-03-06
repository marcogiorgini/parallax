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

package org.parallax3d.parallax.graphics.renderers.plugins.postprocessing;

import org.parallax3d.parallax.graphics.materials.ShaderMaterial;
import org.parallax3d.parallax.graphics.renderers.RenderTargetTexture;
import org.parallax3d.parallax.graphics.renderers.plugins.postprocessing.shaders.CopyShader;
import org.parallax3d.parallax.system.gl.enums.PixelFormat;
import org.parallax3d.parallax.system.gl.enums.TextureMagFilter;
import org.parallax3d.parallax.system.gl.enums.TextureMinFilter;

public class SavePass extends Pass
{
	private RenderTargetTexture renderTarget;
	private String textureID = "tDiffuse";
	private ShaderMaterial material;
	
	private boolean clear = false;

	public SavePass(int width, int height)
	{
		this(new RenderTargetTexture( width, height ));
		
		renderTarget.setMinFilter(TextureMinFilter.LINEAR);
		renderTarget.setMagFilter(TextureMagFilter.LINEAR);
		renderTarget.setFormat(PixelFormat.RGB);
		renderTarget.setStencilBuffer(true);
	}

	public SavePass( RenderTargetTexture renderTarget ) 
	{
		this.renderTarget = renderTarget;	
		
		this.textureID = "tDiffuse";
		
		this.material = new ShaderMaterial(new CopyShader());
		
		this.setEnabled(true);
		this.setNeedsSwap(false);
	}
	@Override
	public void render(Postprocessing postprocessing, double delta, boolean maskActive)
	{
		if ( this.material.getShader().getUniforms().containsKey(this.textureID))
			this.material.getShader().getUniforms().get("this.textureID").setValue( postprocessing.getReadBuffer() );

		postprocessing.getQuad().setMaterial(this.material);

		postprocessing.getRenderer().render( 
				postprocessing.getScene(), postprocessing.getCamera(), this.renderTarget, this.clear );

	}

}
