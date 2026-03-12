package com.gildedrose.items;

public class Sulfuras extends GuildedItem {
    public Sulfuras(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void updateQuality() {
        return;
    }
}
