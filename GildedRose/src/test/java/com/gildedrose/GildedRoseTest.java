package com.gildedrose;

import com.gildedrose.items.AgedBrie;
import com.gildedrose.items.Backstage;
import com.gildedrose.items.GuildedItem;
import com.gildedrose.items.Sulfuras;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void foo() {
        GuildedItem[] items = new GuildedItem[] { new GuildedItem("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].getName());
    }

    @Test
    void AgedBrie1() {
        GuildedItem[] items = new GuildedItem[] { new AgedBrie("Aged Brie", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(2, app.items[0].getQuality());
        assertEquals(-1, app.items[0].getSellIn());
    }

    @Test
    void AgedBrie2() {
        GuildedItem[] items = new GuildedItem[] { new AgedBrie("Aged Brie", 1, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(1, app.items[0].getQuality());
        assertEquals(0, app.items[0].getSellIn());
    }


    @Test
    void AgedBrie3() {
        GuildedItem[] items = new GuildedItem[] { new AgedBrie("Aged Brie", 0, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(50, app.items[0].getQuality());
        assertEquals(-1, app.items[0].getSellIn());
    }


    @Test
    void AgedBrie4() {
        GuildedItem[] items = new GuildedItem[] { new AgedBrie("Aged Brie", 50, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(50, app.items[0].getQuality());
        assertEquals(49, app.items[0].getSellIn());
    }

    // ===== SULFURAS =====

    @Test
    void Sulfuras1() {
        // Sulfuras ne change jamais de quality ni de sellIn
        GuildedItem[] items = new GuildedItem[] { new Sulfuras("Sulfuras, Hand of Ragnaros", 10, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(80, app.items[0].getQuality());
        assertEquals(10, app.items[0].getSellIn());
    }

    @Test
    void Sulfuras2() {
        // Sulfuras avec sellIn négatif reste inchangé
        GuildedItem[] items = new GuildedItem[] { new Sulfuras("Sulfuras, Hand of Ragnaros", -1, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(80, app.items[0].getQuality());
        assertEquals(-1, app.items[0].getSellIn());
    }

    @Test
    void Backstage1() {
        GuildedItem[] items = new GuildedItem[] { new Backstage("Backstage passes to a TAFKAL80ETC concert", 15, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(21, app.items[0].getQuality());
        assertEquals(14, app.items[0].getSellIn());
    }

    @Test
    void Backstage2() {
        GuildedItem[] items = new GuildedItem[] { new Backstage("Backstage passes to a TAFKAL80ETC concert", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(22, app.items[0].getQuality());
        assertEquals(9, app.items[0].getSellIn());
    }

    @Test
    void Backstage3() {
        // Entre 1 et 5 jours : quality +3
        GuildedItem[] items = new GuildedItem[] { new Backstage("Backstage passes to a TAFKAL80ETC concert", 5, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(23, app.items[0].getQuality());
        assertEquals(4, app.items[0].getSellIn());
    }

    @Test
    void Backstage4() {
        // Après le concert (sellIn < 0) : quality = 0
        GuildedItem[] items = new GuildedItem[] { new Backstage("Backstage passes to a TAFKAL80ETC concert", 0, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(0, app.items[0].getQuality());
        assertEquals(-1, app.items[0].getSellIn());
    }

    @Test
    void Backstage5() {
        // Quality ne dépasse pas 50
        GuildedItem[] items = new GuildedItem[] { new Backstage("Backstage passes to a TAFKAL80ETC concert", 5, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(50, app.items[0].getQuality());
        assertEquals(4, app.items[0].getSellIn());
    }


    @Test
    void NormalItem1() {
        GuildedItem[] items = new GuildedItem[] { new GuildedItem("foo", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(19, app.items[0].getQuality());
        assertEquals(9, app.items[0].getSellIn());
    }

    @Test
    void NormalItem2() {
        // Après sellIn : quality diminue de 2
        GuildedItem[] items = new GuildedItem[] { new GuildedItem("foo", 0, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(18, app.items[0].getQuality());
        assertEquals(-1, app.items[0].getSellIn());
    }

    @Test
    void NormalItem3() {
        // Quality ne descend pas sous 0
        GuildedItem[] items = new GuildedItem[] { new GuildedItem("foo", 10, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(0, app.items[0].getQuality());
        assertEquals(9, app.items[0].getSellIn());
    }

    @Test
    void NormalItem4() {
        // Quality à 1 après sellIn → tombe à 0, pas négatif
        GuildedItem[] items = new GuildedItem[] { new GuildedItem("foo", 0, 1) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(0, app.items[0].getQuality());
        assertEquals(-1, app.items[0].getSellIn());
    }



}
