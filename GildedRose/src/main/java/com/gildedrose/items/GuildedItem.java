package com.gildedrose.items;

import com.gildedrose.Item;

public class GuildedItem extends Item {

    static final int MIN_CAP = 0;
    static final int MAX_CAP = 50;

    public GuildedItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    public void updateQuality() {
        ageSellIn();
        ageQuality();

        if(getSellIn() < 0) setQuality(getQuality() - 1);
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getSellIn() {
        return this.sellIn;
    }
    public void ageSellIn() {
        setSellIn(getSellIn() - 1);
    }
    public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public int getQuality() {
        return this.quality;
    }
    public void ageQuality() {
        setQuality(getQuality() - 1);
    }
    public void setQuality(int quality) {
        this.quality = Math.min(MAX_CAP, Math.max(MIN_CAP, quality));
    }
}