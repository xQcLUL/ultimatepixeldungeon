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

package com.ultimatepixel.ultimatepixeldungeon.items.quest;

import com.ultimatepixel.ultimatepixeldungeon.Assets;
import com.ultimatepixel.ultimatepixeldungeon.Dungeon;
import com.ultimatepixel.ultimatepixeldungeon.actors.Actor;
import com.ultimatepixel.ultimatepixeldungeon.actors.Char;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.Hero;
import com.ultimatepixel.ultimatepixeldungeon.actors.mobs.Elemental;
import com.ultimatepixel.ultimatepixeldungeon.effects.CellEmitter;
import com.ultimatepixel.ultimatepixeldungeon.effects.particles.ElmoParticle;
import com.ultimatepixel.ultimatepixeldungeon.items.Heap;
import com.ultimatepixel.ultimatepixeldungeon.items.Item;
import com.ultimatepixel.ultimatepixeldungeon.scenes.GameScene;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;


public class CeremonialCandle extends Item {

	//generated with the wandmaker quest
	public static int ritualPos;

	{
		image = ItemSpriteSheet.CANDLE;

		defaultAction = AC_THROW;

		unique = true;
		stackable = true;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public void doDrop(Hero hero) {
		super.doDrop(hero);
		checkCandles();
	}

	@Override
	protected void onThrow(int cell) {
		super.onThrow(cell);
		checkCandles();
	}

	private static void checkCandles(){
		Heap heapTop = Dungeon.level.heaps.get(ritualPos - Dungeon.level.width());
		Heap heapRight = Dungeon.level.heaps.get(ritualPos + 1);
		Heap heapBottom = Dungeon.level.heaps.get(ritualPos + Dungeon.level.width());
		Heap heapLeft = Dungeon.level.heaps.get(ritualPos - 1);

		if (heapTop != null &&
				heapRight != null &&
				heapBottom != null &&
				heapLeft != null){

			if (heapTop.peek() instanceof CeremonialCandle &&
					heapRight.peek() instanceof CeremonialCandle &&
					heapBottom.peek() instanceof CeremonialCandle &&
					heapLeft.peek() instanceof CeremonialCandle){

				heapTop.pickUp();
				heapRight.pickUp();
				heapBottom.pickUp();
				heapLeft.pickUp();
				
				Elemental.NewbornFireElemental elemental = new Elemental.NewbornFireElemental();
				Char ch = Actor.findChar( ritualPos );
				if (ch != null) {
					ArrayList<Integer> candidates = new ArrayList<>();
					for (int n : PathFinder.NEIGHBOURS8) {
						int cell = ritualPos + n;
						if ((Dungeon.level.passable[cell] || Dungeon.level.avoid[cell]) && Actor.findChar( cell ) == null) {
							candidates.add( cell );
						}
					}
					if (candidates.size() > 0) {
						elemental.pos = Random.element( candidates );
					} else {
						elemental.pos = ritualPos;
					}
				} else {
					elemental.pos = ritualPos;
				}
				elemental.state = elemental.HUNTING;
				GameScene.add(elemental, 1);

				for (int i : PathFinder.NEIGHBOURS9){
					CellEmitter.get(ritualPos+i).burst(ElmoParticle.FACTORY, 10);
				}
				Sample.INSTANCE.play(Assets.Sounds.BURNING);
			}
		}

	}
}
