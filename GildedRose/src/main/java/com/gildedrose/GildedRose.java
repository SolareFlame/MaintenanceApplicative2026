package com.gildedrose;

import com.gildedrose.items.GuildedItem;

class GildedRose {
    GuildedItem[] items;

    public GildedRose(GuildedItem[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (GuildedItem item : items) {
            item.updateQuality();
        }
    }

}
