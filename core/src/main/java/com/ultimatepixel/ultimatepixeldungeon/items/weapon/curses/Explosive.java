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

package com.ultimatepixel.ultimatepixeldungeon.items.weapon.curses;

import com.ultimatepixel.ultimatepixeldungeon.Dungeon;
import com.ultimatepixel.ultimatepixeldungeon.actors.Char;
import com.ultimatepixel.ultimatepixeldungeon.effects.particles.BlastParticle;
import com.ultimatepixel.ultimatepixeldungeon.effects.particles.SmokeParticle;
import com.ultimatepixel.ultimatepixeldungeon.items.Item;
import com.ultimatepixel.ultimatepixeldungeon.items.bombs.Bomb;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.Weapon;
import com.ultimatepixel.ultimatepixeldungeon.mechanics.Ballistica;
import com.ultimatepixel.ultimatepixeldungeon.messages.Messages;
import com.ultimatepixel.ultimatepixeldungeon.sprites.CharSprite;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSprite;
import com.ultimatepixel.ultimatepixeldungeon.utils.GLog;
import com.ultimatepixel.ultimatepixeldungeon.utils.Xml;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Explosive extends Weapon.Enchantment {

	private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( Xml.getColor("pure_black") );
	private static ItemSprite.Glowing WARM = new ItemSprite.Glowing( Xml.getColor("pure_black"), 0.5f );
	private static ItemSprite.Glowing HOT = new ItemSprite.Glowing( Xml.getColor("pure_black"), 0.25f );
	private int durability = 100;

	@Override
	public int proc( Weapon weapon, Char attacker, Char defender, int damage ) {

		//average value of 5, or 20 hits to an explosion
		int durToReduce = Math.round(Random.IntRange(0, 10) * procChanceMultiplier(attacker));
		int currentDurability = durability;
		durability -= durToReduce;

		if (currentDurability > 50 && durability <= 50){
			attacker.sprite.showStatus(CharSprite.WARNING, Messages.get(this, "warm"));
			GLog.w(Messages.get(this, "desc_warm"));
			attacker.sprite.emitter().burst(SmokeParticle.FACTORY, 4);
			Item.updateQuickslot();
		} else if (currentDurability > 10 && durability <= 10){
			attacker.sprite.showStatus(CharSprite.NEGATIVE, Messages.get(this, "hot"));
			GLog.n(Messages.get(this, "desc_hot"));
			attacker.sprite.emitter().burst(BlastParticle.FACTORY, 5);
			Item.updateQuickslot();
		} else if (durability <= 0) {
			//explosion position is either the attacker's position (when attacker and defender are adjacent)
			//or the closest cell to the defender on a straight path to them otherwise
			int explosionPos = -1;
			if (Dungeon.level.adjacent(attacker.pos, defender.pos)){
				explosionPos = attacker.pos;
			} else {
				Ballistica path = new Ballistica(attacker.pos, defender.pos, Ballistica.PROJECTILE);
				if (path.dist == 0){
					explosionPos = attacker.pos;
				} else {
					explosionPos = path.path.get(path.dist-1);
				}
			}

			new Bomb().explode(explosionPos);

			durability = 100;
			Item.updateQuickslot();
		}

		return damage;
	}

	@Override
	public boolean curse() {
		return true;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		if (durability > 50){
			return BLACK;
		} else if (durability > 10){
			return WARM;
		} else {
			return HOT;
		}
	}

	@Override
	public String desc() {
		String desc = super.desc();
		if (durability > 50){
			desc += " " + Messages.get(this, "desc_cool");
		} else if (durability > 10){
			desc += " " + Messages.get(this, "desc_warm");
		} else {
			desc += " _" + Messages.get(this, "desc_hot") + "_";
		}
		return desc;
	}

	private static final String DURABILITY = "durability";

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		durability = bundle.getInt(DURABILITY);
		//pre-1.3 saves
		if (durability <= 0){
			durability = 100;
		}
	}

	@Override
	public void storeInBundle( Bundle bundle ) {
		bundle.put(DURABILITY, durability);
	}

}
