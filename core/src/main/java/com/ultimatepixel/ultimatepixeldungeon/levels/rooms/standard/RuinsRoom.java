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

package com.ultimatepixel.ultimatepixeldungeon.levels.rooms.standard;

import com.ultimatepixel.ultimatepixeldungeon.levels.Level;
import com.ultimatepixel.ultimatepixeldungeon.levels.Terrain;
import com.ultimatepixel.ultimatepixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;

public class RuinsRoom extends PatchRoom {
	
	@Override
	public float[] sizeCatProbs() {
		return new float[]{4, 2, 1};
	}

	@Override
	public boolean canMerge(Level l, Point p, int mergeTerrain) {
		return true;
	}

	@Override
	public void paint(Level level) {
		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1 , Terrain.EMPTY );
		for (Door door : connected.values()) {
			door.set( Door.Type.REGULAR );
		}

		//fill scales from ~20% at 4x4, to ~50% at 18x18
		// normal   ~20% to ~30%
		// large    ~30% to ~40%
		// giant    ~40% to ~50%
		float fill = 0.20f + (width()*height())/1024f;
		
		setupPatch(level, fill, 0, true);
		cleanDiagonalEdges();
		
		for (int i = top + 1; i < bottom; i++) {
			for (int j = left + 1; j < right; j++) {
				if (patch[xyToPatchCoords(j, i)]) {
					int cell = i * level.width() + j;
					level.map[cell] = Terrain.WALL;
				}
			}
		}
	}
}
