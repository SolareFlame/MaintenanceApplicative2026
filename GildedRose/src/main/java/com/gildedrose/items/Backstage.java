package com.gildedrose.items;

public class Backstage extends GuildedItem {
    public Backstage(String name, int sellIn, int quality, boolean conjured) {
        super(name, sellIn, quality, conjured);
    }

    public Backstage(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void updateQuality() {
        ageSellIn();

        if (getSellIn() < 0) {
            setQuality(0); // passé

        } else if (getSellIn() < 5) {
            setQuality(getQuality() + 3); // qualité + 3 à 5 jours ou moins

        } else if (getSellIn() < 10) {
            setQuality(getQuality() + 2); // qualité + 2 à 10 jours ou moins

        } else {
            setQuality(getQuality() + 1); // qualité + 1 à chaque jour perdu
        }
    }
}
