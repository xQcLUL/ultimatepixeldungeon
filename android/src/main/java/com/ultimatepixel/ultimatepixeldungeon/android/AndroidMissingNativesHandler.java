/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
 *
 * Ultimate Pixel Dungeon
 * Copyright (C) 2022
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.ultimatepixel.ultimatepixeldungeon.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.ultimatepixel.ultimatepixeldungeon.utils.Xml;

public class AndroidMissingNativesHandler extends Activity {

	public static String errorMsg = "";

	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView text = new TextView(this);
		text.setText("Ultimate Pixel Dungeon cannot start because some of its code is missing!\n\n" +
				"This usually happens when the Google Play version of the game is installed from somewhere outside of Google Play.\n\n" +
				errorMsg);
		text.setTextSize(16);
		text.setTextColor(Xml.getColor("pure_white"));
		text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/pixel_font.ttf"));
		text.setGravity(Gravity.CENTER_VERTICAL);
		text.setPadding(10, 10, 10, 10);
		setContentView(text);

	}
}
