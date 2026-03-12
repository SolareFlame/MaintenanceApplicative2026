package com.gildedrose.items;

import com.gildedrose.Item;

public class GuildedItem extends Item {

    private boolean conjured;

    static final int MIN_CAP = 0;
    static final int MAX_CAP = 50;

    public GuildedItem(String name, int sellIn, int quality, boolean conjured) {
        super(name, sellIn, quality);
        this.conjured = conjured;
    }

    public GuildedItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
        this.conjured = false;
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

    /**
     * En cas d'items qui ont une évolution positive, on applique un *2
     * (donc du brie doublera sa qualité par exemple)
     *
     * @param amount Qualité à rajouter/retirer
     */
    public void ageQuality(int amount) {
        int conjuredForce = isConjured() ? 2 : 1;
        setQuality(getQuality() + (amount * conjuredForce));
    }

    public void ageQuality() {
        ageQuality(-1); // périssable par défaut
    }

    public void setQuality(int quality) {
        this.quality = Math.min(MAX_CAP, Math.max(MIN_CAP, quality));
    }

    public boolean isConjured() {
        return conjured;
    }

    public void setConjured(boolean conjured) {
        this.conjured = conjured;
    }
}