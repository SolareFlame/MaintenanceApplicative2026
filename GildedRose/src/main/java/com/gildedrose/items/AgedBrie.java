package com.gildedrose.items;

public class AgedBrie extends GuildedItem {

    public AgedBrie(String name, int sellIn, int quality, boolean conjured) {
        super(name, sellIn, quality, conjured);
    }

    public AgedBrie(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }


    @Override
    public void updateQuality() {
        ageSellIn();
        ageQuality(1);

        if(getSellIn() < 0) ageQuality(1);
    }
}
