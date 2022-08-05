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

package com.ultimatepixel.ultimatepixeldungeon.items.weapon.melee;

import com.ultimatepixel.ultimatepixeldungeon.Dungeon;
import com.ultimatepixel.ultimatepixeldungeon.UltimatePixelDungeon;
import com.ultimatepixel.ultimatepixeldungeon.actors.Char;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.Hero;
import com.ultimatepixel.ultimatepixeldungeon.effects.Speck;
import com.ultimatepixel.ultimatepixeldungeon.items.stones.Runestone;
import com.ultimatepixel.ultimatepixeldungeon.items.weapon.Weapon;
import com.ultimatepixel.ultimatepixeldungeon.messages.Messages;
import com.ultimatepixel.ultimatepixeldungeon.plants.Plant;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MeleeWeapon extends Weapon {
	
	public int tier;

	private Plant.Seed seed1 = new Plant.Seed.PlaceHolder();
	private Plant.Seed seed2 = new Plant.Seed.PlaceHolder();
	private Plant.Seed seed3 = new Plant.Seed.PlaceHolder();

	public void setSeed(Plant.Seed s, int slotActive) {
		switch (slotActive){
			case 0:
				seed1 = s;
				break;
			case 1:
				seed2 = s;
				break;
			case 2:
				seed3 = s;
				break;
		}
	}

	public ArrayList<Plant.Seed> seedSlots() {
		ArrayList<Plant.Seed> seedSlots = new ArrayList<>();
		seedSlots.add( seed1 );
		seedSlots.add( seed2 );
		seedSlots.add( seed3 );
		return seedSlots;
	}

	private Runestone stone1 = new Runestone.PlaceHolder();
	private Runestone stone2 = new Runestone.PlaceHolder();
	private Runestone stone3 = new Runestone.PlaceHolder();

	public void setRunestone(Runestone s, int slotActive) {
		switch (slotActive){
			case 0:
				stone1 = s;
				break;
			case 1:
				stone2 = s;
				break;
			case 2:
				stone3 = s;
				break;
		}
	}

	public ArrayList<Runestone> stoneSlots() {
		ArrayList<Runestone> stoneSlots = new ArrayList<>();
		stoneSlots.add( stone1 );
		stoneSlots.add( stone2 );
		stoneSlots.add( stone3 );
		return stoneSlots;
	}

	@Override
	public Emitter emitter() {
		Emitter emitter = new Emitter();
		if (!(stone1 instanceof Runestone.PlaceHolder)
				|| !(stone2 instanceof Runestone.PlaceHolder)
				|| !(stone3 instanceof Runestone.PlaceHolder)
				|| !(seed1 instanceof Plant.Seed.PlaceHolder)
				|| !(seed2 instanceof Plant.Seed.PlaceHolder)
				|| !(seed3 instanceof Plant.Seed.PlaceHolder)) {
			emitter.pos(ItemSpriteSheet.film.width(image)/2f + 2f, ItemSpriteSheet.film.height(image)/3f);
			emitter.fillTarget = false;
			emitter.pour(Speck.factory( Speck.RED_LIGHT ), 0.6f);
		}
		return emitter;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		for(Plant.Seed seed : seedSlots()){
			seed.proc(attacker, defender);
		}
		return super.proc(attacker, defender, damage);
	}

	@Override
	public int min(int lvl) {
		return tier +
				lvl
				+ stoneMin();
	}

	public int stoneMin(){
		int m = stone1.extraMinDmgWeapon
				+ stone2.extraMinDmgWeapon
				+ stone3.extraMinDmgWeapon;
		return Math.max(m, 0);
	}

	@Override
	public int max(int lvl) {
		return 5*(tier+1) +
				lvl*(tier+1)
				+ stoneMax();
	}

	public int stoneMax(){
		int m = stone1.extraMaxDmgWeapon
				+ stone2.extraMaxDmgWeapon
				+ stone3.extraMaxDmgWeapon;
		return Math.max(m, 0);
	}

	public int STRReq(int lvl){
		return STRReq(tier, lvl);
	}
	
	@Override
	public int damageRoll(Char owner) {
		int damage = augment.damageFactor(super.damageRoll( owner ));

		if (owner instanceof Hero) {
			int exStr = ((Hero)owner).STR() - STRReq();
			if (exStr > 0) {
				damage += Random.IntRange( 0, exStr );
			}
		}
		
		return damage;
	}
	
	@Override
	public String info() {

		String info = desc();

		if (levelKnown) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known", tier, augment.damageFactor(min()), augment.damageFactor(max()), STRReq());
			if (STRReq() > Dungeon.hero.STR()) {
				info += " " + Messages.get(Weapon.class, "too_heavy");
			} else if (Dungeon.hero.STR() > STRReq()){
				info += " " + Messages.get(Weapon.class, "excess_str", Dungeon.hero.STR() - STRReq());
			}
		} else {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_unknown", tier, min(0), max(0), STRReq(0));
			if (STRReq(0) > Dungeon.hero.STR()) {
				info += " " + Messages.get(MeleeWeapon.class, "probably_too_heavy");
			}
		}

		String statsInfo = statsInfo();
		if (!statsInfo.equals("")) info += "\n\n" + statsInfo;

		switch (augment) {
			case SPEED:
				info += " " + Messages.get(Weapon.class, "faster");
				break;
			case DAMAGE:
				info += " " + Messages.get(Weapon.class, "stronger");
				break;
			case NONE:
		}

		if (enchantment != null && (cursedKnown || !enchantment.curse())){
			info += "\n\n" + Messages.get(Weapon.class, "enchanted", enchantment.name());
			info += " " + enchantment.desc();
		}

		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed");
		} else if (!isIdentified() && cursedKnown){
			info += "\n\n" + Messages.get(Weapon.class, "not_cursed");
		}

		if(hasStones()){
			int max = stone1.extraMaxDmgWeapon+stone2.extraMaxDmgWeapon +stone3.extraMaxDmgWeapon;
			info += "\n\n"+Messages.get(this, "stone_max_dmg")+": _"+max+"_";
		}

		if(hasStones()){
			int min = stone1.extraMinDmgWeapon +stone2.extraMinDmgWeapon +stone3.extraMinDmgWeapon;
			info += "\n"+Messages.get(this, "stone_min_dmg")+": _"+min+"_";
		}
		
		return info;
	}

	public boolean hasStones(){
		return !(stone1 instanceof Runestone.PlaceHolder) ||
				!(stone2 instanceof Runestone.PlaceHolder) ||
				!(stone3 instanceof Runestone.PlaceHolder);
	}
	
	public String statsInfo(){
		return Messages.get(this, "stats_desc");
	}
	
	@Override
	public int value() {
		int price = 20 * tier;
		if (hasGoodEnchant()) {
			price *= 1.5;
		}
		if (cursedKnown && (cursed || hasCurseEnchant())) {
			price /= 2;
		}
		if (levelKnown && level() > 0) {
			price *= (level() + 1);
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}

	private static final String STONE1		    = "stone_1";
	private static final String STONE2		    = "stone_2";
	private static final String STONE3		    = "stone_3";
	private static final String SEED1		    = "seed_1";
	private static final String SEED2		    = "seed_2";
	private static final String SEED3		    = "seed_3";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(STONE1, stone1.getClass());
		bundle.put(STONE2, stone2.getClass());
		bundle.put(STONE3, stone3.getClass());
		bundle.put(SEED1, seed1.getClass());
		bundle.put(SEED2, seed2.getClass());
		bundle.put(SEED3, seed3.getClass());
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		try {
			stone1 = (Runestone) bundle.getClass(STONE1).newInstance();
			stone2 = (Runestone) bundle.getClass(STONE2).newInstance();
			stone3 = (Runestone) bundle.getClass(STONE3).newInstance();
			seed1 = (Plant.Seed) bundle.getClass(SEED1).newInstance();
			seed2 = (Plant.Seed) bundle.getClass(SEED2).newInstance();
			seed3 = (Plant.Seed) bundle.getClass(SEED3).newInstance();
		} catch (Exception e) {
			UltimatePixelDungeon.reportException(e);
		}
	}
}
