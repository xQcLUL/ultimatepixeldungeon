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

package com.ultimatepixel.ultimatepixeldungeon.items.stones;

import com.ultimatepixel.ultimatepixeldungeon.Dungeon;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Invisibility;
import com.ultimatepixel.ultimatepixeldungeon.items.Item;
import com.ultimatepixel.ultimatepixeldungeon.messages.Messages;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public abstract class Runestone extends Item {

	public float evasionSkill = 1f;
	public int drSkill = 0;
	public int dmgSkill = 0;
	public float speedSkill = 1f;
	public boolean canSupriseAttack = false;
	public float accSkill = 1f;

	public boolean icon_evasion = false;
	public boolean icon_dr = false;
	public boolean icon_dmg = false;
	public boolean icon_speed = false;
	public boolean icon_surprise = false;
	public boolean icon_acc = false;

	public int extraMaxDmgWeapon = 0;
	public int extraMinDmgWeapon = 0;
	
	{
		stackable = true;
		defaultAction = AC_THROW;
	}

	//runestones press the cell they're thrown to by default, but a couple stones override this
	protected boolean pressesCell = true;

	public int color = 0;

	@Override
	protected void onThrow(int cell) {
		if (Dungeon.level.pit[cell] || !defaultAction.equals(AC_THROW)){
			super.onThrow( cell );
		} else {
			if (pressesCell) Dungeon.level.pressCell( cell );
			activate(cell);
			Invisibility.dispel();
		}
	}
	
	protected abstract void activate(int cell);
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public int value() {
		return 15 * quantity;
	}

	@Override
	public int energyVal() {
		return 3 * quantity;
	}

	@Override
	public String desc() {
		String s = "";
		s += "\n\n";
		String weaponDmg = Messages.get(Runestone.class, "extra_max_damage")+": _"+this.extraMaxDmgWeapon+"_ \n"+
				Messages.get(Runestone.class, "extra_min_damage")+": _"+this.extraMinDmgWeapon+"_ ";
		s += weaponDmg;
		s += "\n\n";
		String ringEffect = (this.evasionSkill != 1f ? Messages.get(Runestone.class, "evasion")+": _"+Math.round((this.evasionSkill-1f)*100)+"%_ &" : "")+
				(this.drSkill != 0 ? Messages.get(Runestone.class, "dr")+": _"+this.drSkill+"_ &" : "")+
				(this.dmgSkill != 0 ? Messages.get(Runestone.class, "dmg")+": _"+this.dmgSkill+"_ &" : "")+
				(this.speedSkill != 1f ? Messages.get(Runestone.class, "speed")+": _"+Math.round((this.speedSkill-1f)*100)+"%_ &" : "")+
				(this.canSupriseAttack ? Messages.get(Runestone.class, "surprise")+": _"+this.canSupriseAttack+"_ &" : "")+
				(this.accSkill != 1f ? Messages.get(Runestone.class, "acc")+": _"+Math.round((this.accSkill-1f)*100)+"%_ &" : "");
		ringEffect = ringEffect.substring(0, ringEffect.length()-1);
		ringEffect = ringEffect.replaceAll("&", "\n");
		s += ringEffect;
		return super.desc() + s;
	}

	public static class PlaceHolder extends Runestone {
		
		{
			image = ItemSpriteSheet.STONE_HOLDER;
		}
		
		@Override
		protected void activate(int cell) {
			//does nothing
		}
		
		@Override
		public boolean isSimilar(Item item) {
			return item instanceof Runestone;
		}
		
		@Override
		public String info() {
			return "";
		}
	}
}
