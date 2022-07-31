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

package com.ultimatepixel.ultimatepixeldungeon.windows;

import com.ultimatepixel.ultimatepixeldungeon.Dungeon;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.Hero;
import com.ultimatepixel.ultimatepixeldungeon.actors.hero.abilities.ArmorAbility;
import com.ultimatepixel.ultimatepixeldungeon.items.KingsCrown;
import com.ultimatepixel.ultimatepixeldungeon.items.armor.Armor;
import com.ultimatepixel.ultimatepixeldungeon.messages.Messages;
import com.ultimatepixel.ultimatepixeldungeon.scenes.GameScene;
import com.ultimatepixel.ultimatepixeldungeon.scenes.PixelScene;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSprite;
import com.ultimatepixel.ultimatepixeldungeon.ui.HeroIcon;
import com.ultimatepixel.ultimatepixeldungeon.ui.IconButton;
import com.ultimatepixel.ultimatepixeldungeon.ui.Icons;
import com.ultimatepixel.ultimatepixeldungeon.ui.RedButton;
import com.ultimatepixel.ultimatepixeldungeon.ui.RenderedTextBlock;
import com.ultimatepixel.ultimatepixeldungeon.ui.Window;

public class WndChooseAbility extends Window {

	private static final int WIDTH		= 130;
	private static final float GAP		= 2;

	public WndChooseAbility(final KingsCrown crown, final Armor armor, final Hero hero){

		super();

		//crown can be null if hero is choosing from armor
		IconTitle titlebar = new IconTitle();
		titlebar.icon( new ItemSprite( crown == null ? armor.image() : crown.image(), null ) );
		titlebar.label( Messages.titleCase(crown == null ? armor.name() : crown.name()) );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );

		RenderedTextBlock body = PixelScene.renderTextBlock( 6 );
		if (crown != null) {
			body.text(Messages.get(this, "message"), WIDTH);
		} else {
			body.text(Messages.get(this, "message_no_crown"), WIDTH);
		}
		body.setPos( titlebar.left(), titlebar.bottom() + GAP );
		add( body );

		float pos = body.bottom() + 3*GAP;
		for (ArmorAbility ability : hero.heroClass.armorAbilities()) {

			RedButton abilityButton = new RedButton(ability.shortDesc(), 6){
				@Override
				protected void onClick() {
					GameScene.show(new WndOptions( new HeroIcon( ability ),
							Messages.titleCase(ability.name()),
							Messages.get(WndChooseAbility.this, "are_you_sure"),
							Messages.get(WndChooseAbility.this, "yes"),
							Messages.get(WndChooseAbility.this, "no")){

						@Override
						protected void onSelect(int index) {
							hide();
							if (index == 0 && WndChooseAbility.this.parent != null){
								WndChooseAbility.this.hide();
								if (crown != null) {
									crown.upgradeArmor(hero, armor, ability);
								} else {
									new KingsCrown().upgradeArmor(hero, null, ability);
								}
							}
						}
					});
				}
			};
			abilityButton.leftJustify = true;
			abilityButton.multiline = true;
			abilityButton.setSize(WIDTH-20, abilityButton.reqHeight()+2);
			abilityButton.setRect(0, pos, WIDTH-20, abilityButton.reqHeight()+2);
			add(abilityButton);

			IconButton abilityInfo = new IconButton(Icons.get(Icons.INFO)){
				@Override
				protected void onClick() {
					GameScene.show(new WndInfoArmorAbility(Dungeon.hero.heroClass, ability));
				}
			};
			abilityInfo.setRect(WIDTH-20, abilityButton.top() + (abilityButton.height()-20)/2, 20, 20);
			add(abilityInfo);

			pos = abilityButton.bottom() + GAP;
		}

		RedButton cancelButton = new RedButton(Messages.get(this, "cancel")){
			@Override
			protected void onClick() {
				hide();
			}
		};
		cancelButton.setRect(0, pos, WIDTH, 18);
		add(cancelButton);
		pos = cancelButton.bottom() + GAP;

		resize(WIDTH, (int)pos);

	}


}
