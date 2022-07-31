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

package com.ultimatepixel.ultimatepixeldungeon.levels.rooms.secret;

import com.ultimatepixel.ultimatepixeldungeon.items.Generator;
import com.ultimatepixel.ultimatepixeldungeon.items.bombs.Bomb;
import com.ultimatepixel.ultimatepixeldungeon.levels.Level;
import com.ultimatepixel.ultimatepixeldungeon.levels.Terrain;
import com.ultimatepixel.ultimatepixeldungeon.levels.painters.Painter;

public class SecretArtilleryRoom extends SecretRoom {
	
	@Override
	public void paint(Level level) {
		Painter.fill(level, this, Terrain.WALL);
		Painter.fill(level, this, 1, Terrain.EMPTY_SP);
		
		Painter.set(level, center(), Terrain.STATUE_SP);
		
		for (int i = 0; i < 3; i++){
			int itemPos;
			do{
				itemPos = level.pointToCell(random());
			} while ( level.map[itemPos] != Terrain.EMPTY_SP
					|| level.heaps.get(itemPos) != null);
			
			if( i == 0 ){
				level.drop(new Bomb.DoubleBomb(), itemPos);
			} else {
				level.drop(Generator.randomMissile(), itemPos);
			}
		}
		
		entrance().set(Door.Type.HIDDEN);
	}
}
