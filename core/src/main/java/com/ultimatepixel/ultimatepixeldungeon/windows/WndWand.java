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
import com.ultimatepixel.ultimatepixeldungeon.items.bags.ScrollHolder;
import com.ultimatepixel.ultimatepixeldungeon.items.scrolls.Scroll;
import com.ultimatepixel.ultimatepixeldungeon.items.spells.Spell;
import com.ultimatepixel.ultimatepixeldungeon.items.wands.Wand;
import com.ultimatepixel.ultimatepixeldungeon.messages.Messages;
import com.ultimatepixel.ultimatepixeldungeon.scenes.GameScene;
import com.ultimatepixel.ultimatepixeldungeon.ui.InventoryPane;
import com.ultimatepixel.ultimatepixeldungeon.ui.InventorySlot;
import com.ultimatepixel.ultimatepixeldungeon.ui.RedButton;
import com.ultimatepixel.ultimatepixeldungeon.ui.Window;
import com.ultimatepixel.ultimatepixeldungeon.utils.GLog;

import java.util.ArrayList;

public class WndWand extends WndInfoItem{

    private static final float BUTTON_HEIGHT	= 16;

    private static final float GAP	= 2;

    private InventorySlot inv1;
    private InventorySlot inv2;
    private InventorySlot inv3;

    private InventorySlot inv4;
    private InventorySlot inv5;
    private InventorySlot inv6;

    private final WndWand INSTANCE;

    private int activeSlot = 0;

    private final Wand itemDisplayed;

    public WndWand(final Window owner, final Item item) {
        super(item);

        INSTANCE = this;
        itemDisplayed = (Wand) item;

        float y = height;

        int slotWidth = 16;
        int slotHeight = 20;

        WndBag.ItemSelector itemSelector = new WndBag.ItemSelector(){

            @Override
            public String textPrompt() {
                return Messages.get(WndWand.class, "select_scroll");
            }

            @Override
            public Class<? extends Bag> preferredBag() {
                return ScrollHolder.class;
            }

            @Override
            public boolean itemSelectable(Item item) {
                return item instanceof Scroll;
            }

            @Override
            public void onSelect(Item i) {
                if(i instanceof Scroll){
                    if(!(((Wand) item).scrollSlots().get(activeSlot) instanceof Scroll.PlaceHolder)){
                        ((Wand) item).scrollSlots().get(activeSlot).collect();
                    }
                    Scroll scroll = (Scroll) i.detach(Dungeon.hero.belongings.backpack);
                    ((Wand) item).setScroll(scroll, activeSlot);
                    ((Wand) item).scrollSlots().set(activeSlot, scroll);
                    switch (activeSlot){
                        case 0:
                            inv1.getSprite().view(scroll);
                            INSTANCE.updateWindow(owner, true);
                            break;
                        case 1:
                            inv2.getSprite().view(scroll);
                            INSTANCE.updateWindow(owner, true);
                            break;
                        case 2:
                            inv3.getSprite().view(scroll);
                            INSTANCE.updateWindow(owner, true);
                            break;
                    }
                }
            }
        };

        WndBag.ItemSelector itemSelector2 = new WndBag.ItemSelector(){

            @Override
            public String textPrompt() {
                return Messages.get(WndRing.class, "select_spell");
            }

            @Override
            public boolean itemSelectable(Item item) {
                return item instanceof Spell;
            }

            @Override
            public void onSelect(Item i) {
                if(i instanceof Spell){
                    if(!(((Wand) item).spellSlots().get(activeSlot) instanceof Spell.PlaceHolder)){
                        ((Wand) item).spellSlots().get(activeSlot).collect();
                        GLog.p("?");
                    }
                    Spell st = (Spell) i.detach(Dungeon.hero.belongings.backpack);
                    ((Wand) item).setSpell(st, activeSlot);
                    ((Wand) item).spellSlots().set(activeSlot, st);
                    switch (activeSlot){
                        case 0:
                            inv4.getSprite().view(st);
                            INSTANCE.updateWindow(owner, true);
                            break;
                        case 1:
                            inv5.getSprite().view(st);
                            INSTANCE.updateWindow(owner, true);
                            break;
                        case 2:
                            inv6.getSprite().view(st);
                            INSTANCE.updateWindow(owner, true);
                            break;
                    }
                }
            }
        };

        if (Dungeon.hero.isAlive() && Dungeon.hero.belongings.contains(item) && item != null) {
            ArrayList<Scroll> scrollArrayList = ( (Wand) item).scrollSlots();
            y += GAP;
           inv1 = new InventorySlot(scrollArrayList.get(0)){
               boolean longClicked = false;

                @Override
                protected void onClick() {
                    super.onClick();
                    activeSlot = 0;
                    if(!longClicked){
                        GameScene.selectItem(itemSelector);
                    }
                    longClicked = false;
                }

               @Override
               protected boolean onLongClick() {
                   activeSlot = 0;
                   if(itemDisplayed.scrollSlots().get(activeSlot) instanceof Scroll.PlaceHolder){
                       return super.onLongClick();
                   }
                   longClicked = true;
                   itemDisplayed.scrollSlots().get(activeSlot).collect();
                   Scroll scroll = new Scroll.PlaceHolder();
                   itemDisplayed.setScroll(scroll, activeSlot);
                   inv1.getSprite().view(scroll);
                   INSTANCE.updateWindow(owner, true);
                   return super.onLongClick();
               }
           };
           inv2 = new InventorySlot(scrollArrayList.get(1)){

               boolean longClicked = false;

                @Override
                protected void onClick() {
                    super.onClick();
                    activeSlot = 1;
                    if(!longClicked){
                        GameScene.selectItem(itemSelector);
                    }
                    longClicked = false;
                }

               @Override
               protected boolean onLongClick() {
                    activeSlot = 1;
                    if(itemDisplayed.scrollSlots().get(activeSlot) instanceof Scroll.PlaceHolder){
                        return super.onLongClick();
                    }
                   longClicked = true;
                   itemDisplayed.scrollSlots().get(activeSlot).collect();
                   Scroll scroll = new Scroll.PlaceHolder();
                   itemDisplayed.setScroll(scroll, activeSlot);
                   inv1.getSprite().view(scroll);
                   INSTANCE.updateWindow(owner, true);
                   return super.onLongClick();
               }
           };
            inv3 = new InventorySlot(scrollArrayList.get(2)){
                boolean longClicked = false;

                @Override
                protected void onClick() {
                    super.onClick();
                    activeSlot = 2;
                    if(!longClicked){
                        GameScene.selectItem(itemSelector);
                    }
                    longClicked = false;
                }

                @Override
                protected boolean onLongClick() {
                    activeSlot = 2;
                    if(itemDisplayed.scrollSlots().get(activeSlot) instanceof Scroll.PlaceHolder){
                        return super.onLongClick();
                    }
                    longClicked = true;
                    itemDisplayed.scrollSlots().get(activeSlot).collect();
                    Scroll scroll = new Scroll.PlaceHolder();
                    itemDisplayed.setScroll(scroll, activeSlot);
                    inv1.getSprite().view(scroll);
                    INSTANCE.updateWindow(owner, true);
                    return super.onLongClick();
                }
            };
            inv4 = new InventorySlot(((Wand) item).spellSlots().get(0)){
                boolean longClicked = false;

                @Override
                protected void onClick() {
                    super.onClick();
                    activeSlot = 0;
                    if(!longClicked){
                        GameScene.selectItem(itemSelector2);
                    }
                    longClicked = false;
                }

                @Override
                protected boolean onLongClick() {
                    activeSlot = 0;
                    if(itemDisplayed.spellSlots().get(activeSlot) instanceof Spell.PlaceHolder){
                        return super.onLongClick();
                    }
                    longClicked = true;
                    itemDisplayed.spellSlots().get(activeSlot).collect();
                    Spell spell = new Spell.PlaceHolder();
                    itemDisplayed.setSpell(spell, activeSlot);
                    inv4.getSprite().view(spell);
                    INSTANCE.updateWindow(owner, true);
                    return super.onLongClick();
                }
            };
            inv5 = new InventorySlot(((Wand) item).spellSlots().get(1)){

                boolean longClicked = false;

                @Override
                protected void onClick() {
                    super.onClick();
                    activeSlot = 1;
                    if(!longClicked){
                        GameScene.selectItem(itemSelector2);
                    }
                    longClicked = false;
                }

                @Override
                protected boolean onLongClick() {
                    activeSlot = 1;
                    if(itemDisplayed.spellSlots().get(activeSlot) instanceof Spell.PlaceHolder){
                        return super.onLongClick();
                    }
                    longClicked = true;
                    itemDisplayed.spellSlots().get(activeSlot).collect();
                    Spell spell = new Spell.PlaceHolder();
                    itemDisplayed.setSpell(spell, activeSlot);
                    inv5.getSprite().view(spell);
                    INSTANCE.updateWindow(owner, true);
                    return super.onLongClick();
                }
            };
            inv6 = new InventorySlot(((Wand) item).spellSlots().get(2)){
                boolean longClicked = false;

                @Override
                protected void onClick() {
                    super.onClick();
                    activeSlot = 2;
                    if(!longClicked){
                        GameScene.selectItem(itemSelector2);
                    }
                    longClicked = false;
                }

                @Override
                protected boolean onLongClick() {
                    activeSlot = 2;
                    if(itemDisplayed.spellSlots().get(activeSlot) instanceof Spell.PlaceHolder){
                        return super.onLongClick();
                    }
                    longClicked = true;
                    itemDisplayed.spellSlots().get(activeSlot).collect();
                    Spell spell = new Spell.PlaceHolder();
                    itemDisplayed.setSpell(spell, activeSlot);
                    inv6.getSprite().view(spell);
                    INSTANCE.updateWindow(owner, true);
                    return super.onLongClick();
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

            inv4.setRect(width-(inv1.width()), y, slotWidth, slotHeight);
            add(inv4);
            inv5.setRect((width-(2*(inv1.width())))-GAP, y, slotWidth, slotHeight);
            add(inv5);
            inv6.setRect((width -(3*(inv1.width())))-(2*GAP), y, slotWidth, slotHeight);
            add(inv6);
        }
        updateWindow(owner, false);
        resize( width, (int)(y+GAP+slotHeight));
    }

    public void updateWindow(Window owner, boolean update){
        if(update){
            INSTANCE.remove();
            GameScene.show(new WndWand(owner, itemDisplayed));
        }
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
