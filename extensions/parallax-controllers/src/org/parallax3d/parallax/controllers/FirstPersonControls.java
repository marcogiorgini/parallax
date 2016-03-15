/*
 * Copyright 2012 Alex Usachev, thothbot@gmail.com
 * This file is part of Parallax project.
 * Parallax is free software: you can redistribute it and/or modify it
 * under the terms of the Creative Commons Attribution 3.0 Unported License.
 * Parallax is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Creative Commons Attribution
 * 3.0 Unported License. for more details.
 * You should have received a copy of the the Creative Commons Attribution
 * 3.0 Unported License along with Parallax.
 * If not, see http://creativecommons.org/licenses/by/3.0/.
 */

package org.parallax3d.parallax.controllers;

import org.parallax3d.parallax.Input;
import org.parallax3d.parallax.RenderingContext;
import org.parallax3d.parallax.graphics.core.Object3D;
import org.parallax3d.parallax.input.KeyCodes;
import org.parallax3d.parallax.input.KeyDownHandler;
import org.parallax3d.parallax.input.KeyUpHandler;
import org.parallax3d.parallax.input.TouchDownHandler;
import org.parallax3d.parallax.input.TouchMoveHandler;
import org.parallax3d.parallax.input.TouchUpHandler;
import org.parallax3d.parallax.math.Mathematics;
import org.parallax3d.parallax.math.Vector3;

/**
 * The control implements control from a first person.
 * <p>
 * Mouse move - rotate the {@link #getObject()}.
 * <p>
 * Mouse down - translate the {@link #getObject()} forward.
 * <p>
 * Mouse up - translate the {@link #getObject()} backward.
 * <p>
 * Keyboard: <br>
 * [W or *up*] - translate the {@link #getObject()} forward.<br>
 * [S or *down*] - translate the {@link #getObject()} backward.<br>
 * [A or *left*] - translate the {@link #getObject()} to the left.<br>
 * [D or *right*] - translate the {@link #getObject()} to the right.<br>
 * [R] - translate the {@link #getObject()} up.<br>
 * [F] - translate the {@link #getObject()} down.<br>
 * [Q] - freez the {@link #getObject()}.
 * <p>
 * Based on the three.js code.
 * 
 * @author thothbot
 */
public class FirstPersonControls extends Controls
		implements TouchMoveHandler, TouchDownHandler, TouchUpHandler, KeyDownHandler, KeyUpHandler
{
	private final Vector3	target;
	private double			movementSpeed		= 1.0;
	private double			lookSpeed			= 0.005;

	private final boolean	lookVertical		= true;
	private final boolean	autoForward			= false;

	private final boolean	activeLook			= true;

	private final boolean	heightSpeed			= false;
	private final double	heightCoef			= 1.0;
	private final double	heightMin			= 0.0;
	// TODO: Check
	private final double	heightMax			= 0.0;

	private final boolean	constrainVertical	= false;
	private final double	verticalMin			= 0.0;
	private final double	verticalMax			= Math.PI;

	private double			autoSpeedFactor		= 0.0;

	private int				mouseX				= 0;
	private int				mouseY				= 0;

	private double			lat					= 0.0;
	private double			lon					= 0.0;
	private double			phi					= 0.0;
	private double			theta				= 0.0;

	private boolean			moveForward			= false;
	private boolean			moveBackward		= false;
	private boolean			moveLeft			= false;
	private boolean			moveRight			= false;
	private boolean			moveUp				= false;
	private boolean			moveDown			= false;
	private boolean			freeze				= false;

	private final int		viewHalfX;
	private final int		viewHalfY;

	public FirstPersonControls ( final Object3D object, final RenderingContext context )
	{
		super( object, context );

		this.viewHalfX = context.getWidth() / 2;
		this.viewHalfY = context.getHeight() / 2;

		this.target = new Vector3();

		context.getInput().addInputHandler( this );
	}

	/**
	 * Sets the movement speed. Default 1.0
	 * 
	 * @param movementSpeed the movement speed.
	 */
	public void setMovementSpeed ( final double movementSpeed )
	{
		this.movementSpeed = movementSpeed;
	}

	/**
	 * Sets look speed. Default 0.005.
	 * 
	 * @param lookSpeed the look speed.
	 */
	public void setLookSpeed ( final double lookSpeed )
	{
		this.lookSpeed = lookSpeed;
	}

	/**
	 * The method must be called in the
	 * {@link org.parallax3d.parallax.Animation}} onUpdate method.
	 * 
	 * @param delta the time in milliseconds needed to render one frame.
	 */
	public void update ( final double delta )
	{
		double actualMoveSpeed = 0;
		double actualLookSpeed;

		if ( this.freeze )
		{
			return;
		}
		else
		{
			if ( this.heightSpeed )
			{
				final double y = Mathematics.clamp( this.getObject().getPosition().getY(), this.heightMin,
						this.heightMax );
				final double heightDelta = y - this.heightMin;

				this.autoSpeedFactor = delta * ( heightDelta * this.heightCoef );
			}
			else
			{
				this.autoSpeedFactor = 0.0;
			}

			actualMoveSpeed = delta * this.movementSpeed;

			if ( this.moveForward || ( this.autoForward && !this.moveBackward ) )
			{
				this.getObject().translateZ( - ( actualMoveSpeed + this.autoSpeedFactor ) );
			}

			if ( this.moveBackward )
			{
				this.getObject().translateZ( actualMoveSpeed );
			}

			if ( this.moveLeft )
			{
				this.getObject().translateX( -actualMoveSpeed );
			}

			if ( this.moveRight )
			{
				this.getObject().translateX( actualMoveSpeed );
			}

			if ( this.moveUp )
			{
				this.getObject().translateY( actualMoveSpeed );
			}

			if ( this.moveDown )
			{
				this.getObject().translateY( -actualMoveSpeed );
			}

			actualLookSpeed = delta * this.lookSpeed;

			if ( !this.activeLook )
			{
				actualLookSpeed = 0.0;
			}

			this.lon += this.mouseX * actualLookSpeed;
			if ( this.lookVertical )
			{
				this.lat -= this.mouseY * actualLookSpeed; // *
															 // this.invertVertical?-1:1;
			}

			this.lat = Math.max( -85.0, Math.min( 85.0, this.lat ) );
			this.phi = ( ( 90.0 - this.lat ) * Math.PI ) / 180.0;
			this.theta = ( this.lon * Math.PI ) / 180.0;

			final Vector3 targetPosition = this.target;
			final Vector3 position = this.getObject().getPosition();

			targetPosition.setX( position.getX() + ( 100.0 * Math.sin( this.phi ) * Math.cos( this.theta ) ) );
			targetPosition.setY( position.getY() + ( 100.0 * Math.cos( this.phi ) ) );
			targetPosition.setZ( position.getZ() + ( 100.0 * Math.sin( this.phi ) * Math.sin( this.theta ) ) );

		}

		double verticalLookRatio = 1.0;

		if ( this.constrainVertical )
		{
			verticalLookRatio = Math.PI / ( this.verticalMax - this.verticalMin );
		}

		this.lon += this.mouseX * actualLookSpeed;
		if ( this.lookVertical )
		{
			this.lat -= this.mouseY * actualLookSpeed * verticalLookRatio;
		}

		this.lat = Math.max( -85.0, Math.min( 85.0, this.lat ) );
		this.phi = ( ( 90.0 - this.lat ) * Math.PI ) / 180.0;

		this.theta = ( this.lon * Math.PI ) / 180.0;

		if ( this.constrainVertical )
		{
			this.phi = Mathematics.mapLinear( this.phi, 0.0, Math.PI, this.verticalMin, this.verticalMax );
		}

		final Vector3 targetPosition = this.target;
		final Vector3 position = this.getObject().getPosition();

		targetPosition.setX( position.getX() + ( 100.0 * Math.sin( this.phi ) * Math.cos( this.theta ) ) );
		targetPosition.setY( position.getY() + ( 100.0 * Math.cos( this.phi ) ) );
		targetPosition.setZ( position.getZ() + ( 100.0 * Math.sin( this.phi ) * Math.sin( this.theta ) ) );

		this.getObject().lookAt( targetPosition );
	}

	@Override
	public void onTouchMove ( final int screenX, final int screenY, final int pointer )
	{
		this.mouseX = screenX - this.viewHalfX;
		this.mouseY = screenY - this.viewHalfY;
	}

	@Override
	public void onTouchDown ( final int screenX, final int screenY, final int pointer, final int button )
	{
		if ( this.activeLook )
		{
			switch ( button )
			{
				case Input.Buttons.LEFT:
					this.moveForward = true;
					break;
				case Input.Buttons.RIGHT:
					this.moveBackward = true;
					break;
			}
		}
	}

	@Override
	public void onTouchUp ( final int screenX, final int screenY, final int pointer, final int button )
	{
		if ( this.activeLook )
		{
			switch ( button )
			{
				case Input.Buttons.LEFT:
					this.moveForward = false;
					break;
				case Input.Buttons.RIGHT:
					this.moveBackward = false;
					break;
			}
		}
	}

	@Override
	public void onKeyDown ( final int keycode )
	{
		switch ( keycode )
		{
			case KeyCodes.KEY_UP:
			case KeyCodes.KEY_W:
				this.moveForward = true;
				break;

			case KeyCodes.KEY_LEFT:
			case KeyCodes.KEY_A:
				this.moveLeft = true;
				break;

			case KeyCodes.KEY_DOWN:
			case KeyCodes.KEY_S:
				this.moveBackward = true;
				break;

			case KeyCodes.KEY_RIGHT:
			case KeyCodes.KEY_D:
				this.moveRight = true;
				break;

			case KeyCodes.KEY_R:
				this.moveUp = true;
				break;
			case KeyCodes.KEY_F:
				this.moveDown = true;
				break;

			case KeyCodes.KEY_Q:
				this.freeze = !this.freeze;
				break;
		}
	}

	@Override
	public void onKeyUp ( final int keycode )
	{
		switch ( keycode )
		{
			case KeyCodes.KEY_UP:
			case KeyCodes.KEY_W:
				this.moveForward = false;
				break;

			case KeyCodes.KEY_LEFT:
			case KeyCodes.KEY_A:
				this.moveLeft = false;
				break;

			case KeyCodes.KEY_DOWN:
			case KeyCodes.KEY_S:
				this.moveBackward = false;
				break;

			case KeyCodes.KEY_RIGHT:
			case KeyCodes.KEY_D:
				this.moveRight = false;
				break;

			case KeyCodes.KEY_R:
				this.moveUp = false;
				break;
			case KeyCodes.KEY_F:
				this.moveDown = false;
				break;
		}
	}
}
