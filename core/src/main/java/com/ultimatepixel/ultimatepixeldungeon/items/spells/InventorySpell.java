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

package com.ultimatepixel.ultimatepixeldungeon.items.spells;

import com.ultimatepixel.ultimatepixeldungeon.Assets;
import com.ultimatepixel.ultimatepixeldungeon.actors.buffs.Invisibility;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.Hero;
import com.ultimatepixel.ultimatepixeldungeon.items.Item;
import com.ultimatepixel.ultimatepixeldungeon.items.bags.Bag;
import com.ultimatepixel.ultimatepixeldungeon.messages.Messages;
import com.ultimatepixel.ultimatepixeldungeon.scenes.GameScene;
import com.ultimatepixel.ultimatepixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;

public abstract class InventorySpell extends Spell {
	
	@Override
	protected void onCast(Hero hero) {
		curItem = detach( hero.belongings.backpack );
		GameScene.selectItem( itemSelector );
	}

	private String inventoryTitle(){
		return Messages.get(this, "inv_title");
	}

	protected Class<?extends Bag> preferredBag = null;

	protected boolean usableOnItem( Item item ){
		return true;
	}
	
	protected abstract void onItemSelected( Item item );
	
	protected WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

		@Override
		public String textPrompt() {
			return inventoryTitle();
		}

		@Override
		public Class<? extends Bag> preferredBag() {
			return preferredBag;
		}

		@Override
		public boolean itemSelectable(Item item) {
			return usableOnItem(item);
		}

		@Override
		public void onSelect( Item item ) {
			
			//FIXME this safety check shouldn't be necessary
			//it would be better to eliminate the curItem static variable.
			if (!(curItem instanceof InventorySpell)){
				return;
			}
			
			if (item != null) {
				
				((InventorySpell)curItem).onItemSelected( item );
				curUser.spend( 1f );
				curUser.busy();
				(curUser.sprite).operate( curUser.pos );
				
				Sample.INSTANCE.play( Assets.Sounds.READ );
				Invisibility.dispel();
				
			} else {
				curItem.collect( curUser.belongings.backpack );
			}
		}
	};
}
