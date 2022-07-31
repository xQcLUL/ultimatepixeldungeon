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

package com.ultimatepixel.ultimatepixeldungeon.effects;

import com.ultimatepixel.ultimatepixeldungeon.Assets;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.RectF;

public class MoneyFloat extends Component {

	private static final RectF MONEYFLOAT = new RectF( 0, 0, 0.25f, 1 );
	
	private Image moneyicon;
	private int floatcount = 0;
	private boolean floatup = true;
	
	@Override
	protected void createChildren() {
		
		moneyicon = new Image( Assets.Effects.FIREBALL );
		moneyicon.frame(MONEYFLOAT);
		moneyicon.origin.set( moneyicon.width / 2 );
		add(moneyicon);
	}

	@Override
	public synchronized void update() {
		super.update();
		if(floatup){
			moneyicon.y++;
			floatcount++;
			if(floatcount >= 10)
				floatup = false;
		} else {
			moneyicon.y--;
			floatcount--;
			if(floatcount <= 0)
				floatup = true;
		}
	}

	@Override
	protected void layout() {
		
		moneyicon.x = x - moneyicon.width / 2;
		moneyicon.y = y - moneyicon.height / 2;
	}
}
