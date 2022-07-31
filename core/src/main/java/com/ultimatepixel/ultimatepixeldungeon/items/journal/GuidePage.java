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

package com.ultimatepixel.ultimatepixeldungeon.items.journal;

import com.ultimatepixel.ultimatepixeldungeon.journal.Document;
import com.ultimatepixel.ultimatepixeldungeon.messages.Messages;
import com.ultimatepixel.ultimatepixeldungeon.sprites.ItemSpriteSheet;

public class GuidePage extends DocumentPage {
	
	{
		image = ItemSpriteSheet.GUIDE_PAGE;
	}
	
	@Override
	public Document document() {
		return Document.ADVENTURERS_GUIDE;
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", document().pageTitle(page()));
	}
}
