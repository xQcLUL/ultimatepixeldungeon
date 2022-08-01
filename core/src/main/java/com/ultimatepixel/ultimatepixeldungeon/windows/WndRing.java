/*
 *
 *  * Pixel Dungeon
 *  * Copyright (C) 2012-2015 Oleg Dolya
 *  *
 *  * Shattered Pixel Dungeon
 *  * Copyright (C) 2014-2022 Evan Debenham
 *  *
 *  * Ultimate Pixel Dungeon
 *  * Copyright (C) 2022
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

package com.ultimatepixel.ultimatepixeldungeon.windows;

import com.ultimatepixel.ultimatepixeldungeon.Dungeon;
import com.ultimatepixel.ultimatepixeldungeon.items.Item;
import com.ultimatepixel.ultimatepixeldungeon.items.bags.Bag;
import com.ultimatepixel.ultimatepixeldungeon.items.bags.VelvetPouch;
import com.ultimatepixel.ultimatepixeldungeon.items.rings.Ring;
import com.ultimatepixel.ultimatepixeldungeon.items.stones.Runestone;
import com.ultimatepixel.ultimatepixeldungeon.messages.Messages;
import com.ultimatepixel.ultimatepixeldungeon.scenes.GameScene;
import com.ultimatepixel.ultimatepixeldungeon.ui.InventoryPane;
import com.ultimatepixel.ultimatepixeldungeon.ui.InventorySlot;
import com.ultimatepixel.ultimatepixeldungeon.ui.RedButton;
import com.ultimatepixel.ultimatepixeldungeon.ui.Window;

import java.util.ArrayList;

public class WndRing extends WndInfoItem{

    private static final float BUTTON_HEIGHT	= 16;

    private static final float GAP	= 2;

    private InventorySlot inv1;
    private InventorySlot inv2;
    private InventorySlot inv3;

    private int activeSlot = 0;

    public WndRing(final Window owner, final Item item) {
        super(item);

        float y = height;

        int slotWidth = 16;
        int slotHeight = 20;

        WndBag.ItemSelector itemSelector = new WndBag.ItemSelector(){

            @Override
            public String textPrompt() {
                return Messages.get(WndRing.class, "select_stone");
            }

            @Override
            public Class<? extends Bag> preferredBag() {
                return VelvetPouch.class;
            }

            @Override
            public boolean itemSelectable(Item item) {
                return item instanceof Runestone;
            }

            @Override
            public void onSelect(Item i) {
                if(i instanceof Runestone){
                    if(!(((Ring) item).stoneSlots(Dungeon.hero).get(activeSlot) instanceof Runestone.PlaceHolder)){
                        ((Ring) item).stoneSlots(Dungeon.hero).get(activeSlot).doDrop(Dungeon.hero);
                    }
                    Runestone st = (Runestone) i.detach(Dungeon.hero.belongings.backpack);
                    ((Ring) item).setRunestone(st, activeSlot);
                    ((Ring) item).stoneSlots(Dungeon.hero).set(activeSlot, st);
                    switch (activeSlot){
                        case 0:
                            inv1.getSprite().view(st);
                            break;
                        case 1:
                            inv2.getSprite().view(st);
                            break;
                        case 2:
                            inv3.getSprite().view(st);
                            break;
                    }
                }
            }
        };

        if (Dungeon.hero.isAlive() && Dungeon.hero.belongings.contains(item) && item instanceof Ring) {
            y += GAP;
           inv1 = new InventorySlot(((Ring) item).stoneSlots(Dungeon.hero).get(0)){
                @Override
                protected void onClick() {
                    super.onClick();
                    activeSlot = 0;
                    GameScene.selectItem(itemSelector);
                }
           };
           inv2 = new InventorySlot(((Ring) item).stoneSlots(Dungeon.hero).get(1)){
                @Override
                protected void onClick() {
                    super.onClick();
                    activeSlot = 1;
                    GameScene.selectItem(itemSelector);
                }
            };
            inv3 = new InventorySlot(((Ring) item).stoneSlots(Dungeon.hero).get(2)){
                @Override
                protected void onClick() {
                    super.onClick();
                    activeSlot = 2;
                    GameScene.selectItem(itemSelector);
                }
            };
            ArrayList<RedButton> buttons = new ArrayList<>();
            for (final String action : item.actions( Dungeon.hero )) {

                RedButton btn = new RedButton( item.actionName(action, Dungeon.hero), 8 ) {
                    @Override
                    protected void onClick() {
                        hide();
                        if (owner != null && owner.parent != null) owner.hide();
                        if (Dungeon.hero.isAlive() && Dungeon.hero.belongings.contains(item)){
                            item.execute( Dungeon.hero, action );
                        }
                        Item.updateQuickslot();
                        if (action == item.defaultAction && item.usesTargeting && owner == null){
                            InventoryPane.useTargeting();
                        }
                    }
                };
                btn.setSize( btn.reqWidth(), BUTTON_HEIGHT );
                buttons.add(btn);
                add( btn );

                if (action.equals(item.defaultAction)) {
                    btn.textColor( TITLE_COLOR );
                }

            }
            y = layoutButtons(buttons, width, y);
            y += GAP;
            inv1.setRect(0, y, slotWidth, slotHeight);
            add(inv1);
            inv2.setRect(inv1.width()+GAP, y, slotWidth, slotHeight);
            add(inv2);
            inv3.setRect(inv2.width() + GAP + inv1.width() + GAP, y, slotWidth, slotHeight);
            add(inv3);
        }

        resize( width, (int)(y+GAP+slotHeight));
    }

    private static float layoutButtons(ArrayList<RedButton> buttons, float width, float y){
        ArrayList<RedButton> curRow = new ArrayList<>();
        float widthLeftThisRow = width;

        while( !buttons.isEmpty() ){
            RedButton btn = buttons.get(0);

            widthLeftThisRow -= btn.width();
            if (curRow.isEmpty()) {
                curRow.add(btn);
                buttons.remove(btn);
            } else {
                widthLeftThisRow -= 1;
                if (widthLeftThisRow >= 0) {
                    curRow.add(btn);
                    buttons.remove(btn);
                }
            }

            //layout current row. Currently forces a max of 3 buttons but can work with more
            if (buttons.isEmpty() || widthLeftThisRow <= 0 || curRow.size() >= 3){

                //re-use this variable for laying out the buttons
                widthLeftThisRow = width - (curRow.size()-1);
                for (RedButton b : curRow){
                    widthLeftThisRow -= b.width();
                }

                //while we still have space in this row, find the shortest button(s) and extend them
                while (widthLeftThisRow > 0){

                    ArrayList<RedButton> shortest = new ArrayList<>();
                    RedButton secondShortest = null;

                    for (RedButton b : curRow) {
                        if (shortest.isEmpty()) {
                            shortest.add(b);
                        } else {
                            if (b.width() < shortest.get(0).width()) {
                                secondShortest = shortest.get(0);
                                shortest.clear();
                                shortest.add(b);
                            } else if (b.width() == shortest.get(0).width()) {
                                shortest.add(b);
                            } else if (secondShortest == null || secondShortest.width() > b.width()){
                                secondShortest = b;
                            }
                        }
                    }

                    float widthToGrow;

                    if (secondShortest == null){
                        widthToGrow = widthLeftThisRow / shortest.size();
                        widthLeftThisRow = 0;
                    } else {
                        widthToGrow = secondShortest.width() - shortest.get(0).width();
                        if ((widthToGrow * shortest.size()) >= widthLeftThisRow){
                            widthToGrow = widthLeftThisRow / shortest.size();
                            widthLeftThisRow = 0;
                        } else {
                            widthLeftThisRow -= widthToGrow * shortest.size();
                        }
                    }

                    for (RedButton toGrow : shortest){
                        toGrow.setRect(0, 0, toGrow.width()+widthToGrow, toGrow.height());
                    }
                }

                //finally set positions
                float x = 0;
                for (RedButton b : curRow){
                    b.setRect(x, y, b.width(), b.height());
                    x += b.width() + 1;
                }

                //move to next line and reset variables
                y += BUTTON_HEIGHT+1;
                widthLeftThisRow = width;
                curRow.clear();

            }

        }

        return y - 1;
    }

}
