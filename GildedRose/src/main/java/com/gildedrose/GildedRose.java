package com.gildedrose;

class GildedRose {
    Item[] items;

    final int MIN_CAP = 0;
    final int MAX_CAP = 50;

    final String AGED_BRIE = "Aged Brie";
    final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    final String BACKSTAGE =  "Backstage passes to a TAFKAL80ETC concert";

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateBaseQuality(Item item) {
        item.sellIn--; // Baisse du jour
        item.quality--; // Baisse de la qualité

        if(item.sellIn < 0) item.quality--;

        capQuality(item);
    }

    public void updateSulfuras(Item leg) {
        // pas de mouvement sellIn
        // pas de mouvement quality
    }

    public void updateBackStage(Item bs) {
        bs.sellIn--;

        if (bs.sellIn < 0) {
            bs.quality = 0; // passé

        } else if (bs.sellIn < 5) {
            bs.quality += 3; // qualité + 3 à 5 jours ou moins

        } else if (bs.sellIn < 10) {
            bs.quality += 2; // qualité + 2 à 10 jours ou moins

        } else {
            bs.quality += 1; // qualité + 1 à chaque jour perdu
        }

        capQuality(bs);
    }
    public void updateAgedBrie(Item brie) {
        brie.sellIn--;
        brie.quality++;

        if(brie.sellIn < 0) brie.quality++;

        capQuality(brie);
    }

    public void updateQuality() {
        for (Item item : items) {
            switch (item.name) {
                case SULFURAS:
                    updateSulfuras(item);
                    break;

                case BACKSTAGE:
                    updateBackStage(item);
                    break;

                case AGED_BRIE:
                    updateAgedBrie(item);
                    break;

                default:
                    updateBaseQuality(item);
            }
        }
    }


    private void capQuality(Item item) {
        if (item.quality < MIN_CAP) item.quality = MIN_CAP;
        if (item.quality > MAX_CAP) item.quality = MAX_CAP;
    }

}
