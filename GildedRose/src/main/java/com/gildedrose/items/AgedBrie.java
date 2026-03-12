package com.gildedrose.items;

public class AgedBrie extends GuildedItem {

    public AgedBrie(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void updateQuality() {
        ageSellIn();
        ageQuality();

        if(getSellIn() < 0) ageQuality();
    }

    /**
     * La qualité augmente au lieu de baisser (en raison de la maturation)
     */
    @Override
    public void ageQuality() {
        setQuality(getQuality() + 1);
    }
}
