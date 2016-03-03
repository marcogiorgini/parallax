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

package org.parallax3d.parallax.loaders;

import org.parallax3d.parallax.graphics.extras.core.FontData;

public abstract class FontLoader extends Loader {

    FontLoadHandler handler;

    public FontLoader(String url, final FontLoadHandler handler) {
        super(url);

        this.handler = handler;
    }

    @Override
    protected void onReady() {
        if(handler != null)
            handler.onFontLoaded(FontLoader.this, getFontData());
    }

    public abstract FontData getFontData();
}