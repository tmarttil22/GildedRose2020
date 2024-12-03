package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	@Test
	public void exampleTest() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();
		
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert quality has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
	}
	
	@Test
	public void testQualityDeteriorationForAllItems() {
		GildedRose inn = new GildedRose();
        
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.setItem(new Item("Aged Brie", 2, 0));
		inn.setItem(new Item("Elixir of the Mongoose", 5, 7));
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		inn.setItem(new Item("Conjured Mana Cake", 3, 6));
        
        inn.oneDay();
        
        List<Item> items = inn.getItems();
        
        int quality = items.get(0).getQuality();
        assertEquals("Failed quality for Dexterity Vest", 19, quality);
        
        // Aged Brie increases in quality instead
        quality = items.get(1).getQuality();
        assertEquals("Failed quality for Aged Brie", 1, quality);
        
        quality = items.get(2).getQuality();
        assertEquals("Failed quality for Elixir of the Mongoose", 6, quality);
        
        // Sulfuras stays at the same quality
        quality = items.get(3).getQuality();
        assertEquals("Failed quality for Sulfuras, Hand of Ragnaros", 80, quality);
        
        // Backstage passes rise in quality
        quality = items.get(4).getQuality();
        assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 21, quality);
        
        quality = items.get(5).getQuality();
        assertEquals("Failed quality for Conjured Mana Cake", 5, quality);
		
	}
	
	@Test
	public void testQualityDeteriorationBelow0SellDate() {
		GildedRose inn = new GildedRose();
        
		inn.setItem(new Item("+5 Dexterity Vest", 0, 20));
		inn.setItem(new Item("Aged Brie", 0, 0));
		inn.setItem(new Item("Elixir of the Mongoose", 0, 7));
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20));
		inn.setItem(new Item("Conjured Mana Cake", 0, 6));
        
        inn.oneDay();
        
        List<Item> items = inn.getItems();
        
        int quality = items.get(0).getQuality();
        assertEquals("Failed quality for Dexterity Vest", 18, quality);
        
        quality = items.get(1).getQuality();
        assertEquals("Failed quality for Aged Brie", 2, quality);
        
        quality = items.get(2).getQuality();
        assertEquals("Failed quality for Elixir of the Mongoose", 5, quality);
        
        // Sulfuras stays at the same quality
        quality = items.get(3).getQuality();
        assertEquals("Failed quality for Sulfuras, Hand of Ragnaros", 80, quality);
        
        // Backstage passes price goes to 0 on day 0
        quality = items.get(4).getQuality();
        assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 0, quality);
        
        quality = items.get(5).getQuality();
        assertEquals("Failed quality for Conjured Mana Cake", 4, quality);
	}
	
	@Test
	public void testQualityIncreaseForBackstagePasses() {
		GildedRose inn = new GildedRose();
		
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20));
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 10));
		
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		
		int quality = items.get(0).getQuality();
		assertEquals("Wrong value for 2-increase Backstage pass value increase", 22, quality);
		
		quality = items.get(1).getQuality();
		assertEquals("Wrong value for 3-increase Backstage pass value increase", 13, quality);
	}
	
	@Test
	public void testSulfurasRequirements() {
		GildedRose inn = new GildedRose();
		
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 5, 80));
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 2, 80));
		
		inn.oneDay();
		inn.oneDay();
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		
		int quality = items.get(0).getQuality();
		assertEquals("Quality is not the same", 80, quality);
		
		quality = items.get(1).getQuality();
		assertEquals("Quality is not the same", 80, quality);
	}
	
	@Test
	public void testMaxQualityOf50() {
		GildedRose inn = new GildedRose();

		inn.setItem(new Item("Aged Brie", 20, 49));
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 20, 49));
        
        inn.oneDay();
        inn.oneDay();
        
		List<Item> items = inn.getItems();
		
		int quality = items.get(0).getQuality();
		assertEquals("Quality is not correct", 50, quality);
		
		quality = items.get(1).getQuality();
		assertEquals("Quality is not correct", 50, quality);

	}
	
	@Test
	public void testSellInValueCorrectlyChanging() {
		GildedRose inn = new GildedRose();
        
		inn.setItem(new Item("+5 Dexterity Vest", 0, 20));
		inn.setItem(new Item("Aged Brie", 10, 0));
		inn.setItem(new Item("Elixir of the Mongoose", 20, 7));
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 10, 80));
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20));
		inn.setItem(new Item("Conjured Mana Cake", 3, 6));
        
        inn.oneDay();
        
        List<Item> items = inn.getItems();
        
        int sellIn = items.get(0).getSellIn();
        assertEquals("+5 Dexterity Vest SellIn is not correct", -1, sellIn);
        
        sellIn = items.get(1).getSellIn();
        assertEquals("Aged Brie SellIn is not correct", 9, sellIn);
        
        sellIn = items.get(2).getSellIn();
        assertEquals("Elixir of the Mongoose SellIn is not correct", 19, sellIn);
        
        sellIn = items.get(3).getSellIn();
        assertEquals("Sulfuras, Hand of Ragnaros SellIn is not correct", 10, sellIn);
        
        sellIn = items.get(4).getSellIn();
        assertEquals("Backstage passes to a TAFKAL80ETC concert SellIn is not correct", 4, sellIn);
        
        sellIn = items.get(5).getSellIn();
        assertEquals("Conjured Mana Cake SellIn is not correct", 2, sellIn);
	}
	
	@Test
	public void testItemsWithIllegitimateValues() {
		GildedRose inn = new GildedRose();
        
		inn.setItem(new Item("+5 Dexterity Vest", 10, -1));
		inn.setItem(new Item("Aged Brie", 2, 0));
		inn.setItem(new Item("Elixir of the Mongoose", -5, -5));
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		
		inn.setItem(new Item("Aged Brie", -2, 60));
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", -5, 80));
		
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 70));
		inn.setItem(new Item("Conjured Mana Cake", 3, 70));
        
        inn.oneDay();
	}
	
	@Test
	public void mutationTestAllItemsWithNegativeValues() {
		GildedRose inn = new GildedRose();
        
		inn.setItem(new Item("+5 Dexterity Vest", -10, -10));
		inn.setItem(new Item("Aged Brie", -20, -20));
		inn.setItem(new Item("Elixir of the Mongoose", -5, -5));
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", -5, -5));		
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", -15, -70));
		inn.setItem(new Item("Conjured Mana Cake", -3, 70));
        
        inn.oneDay();
        
        List<Item> items = inn.getItems();
        
        int sellIn = items.get(0).getSellIn();
        assertEquals("+5 Dexterity Vest SellIn is not correct", -11, sellIn);
        
        sellIn = items.get(1).getSellIn();
        assertEquals("Aged Brie SellIn is not correct", -21, sellIn);
        
        sellIn = items.get(2).getSellIn();
        assertEquals("Elixir of the Mongoose SellIn is not correct", -6, sellIn);
        
        sellIn = items.get(3).getSellIn();
        assertEquals("Sulfuras, Hand of Ragnaros SellIn is not correct", -5, sellIn);
        
        sellIn = items.get(4).getSellIn();
        assertEquals("Backstage passes to a TAFKAL80ETC concert SellIn is not correct", -16, sellIn);
        
        sellIn = items.get(5).getSellIn();
        assertEquals("Conjured Mana Cake SellIn is not correct", -4, sellIn);
        
        
        int quality = items.get(0).getQuality();
        assertEquals("Failed quality for Dexterity Vest", -10, quality);
        
        quality = items.get(1).getQuality();
        assertEquals("Failed quality for Aged Brie", -18, quality);
        
        quality = items.get(2).getQuality();
        assertEquals("Failed quality for Elixir of the Mongoose", -5, quality);
        
        quality = items.get(3).getQuality();
        assertEquals("Failed quality for Sulfuras, Hand of Ragnaros", -5, quality);
        
        quality = items.get(4).getQuality();
        assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 0, quality);
        
        quality = items.get(5).getQuality();
        assertEquals("Failed quality for Conjured Mana Cake", 68, quality);
	}
	
	@Test
	public void mutationTestForBackStagePass() {
		GildedRose inn = new GildedRose();
		
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 11, 55));
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 45));
		
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 11, 45));
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 55));
		
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		
		int sellIn = items.get(0).getSellIn();
		assertEquals("Backstage passes to a TAFKAL80ETC concert SellIn is not correct", 10, sellIn);
		
		sellIn = items.get(1).getSellIn();
		assertEquals("Backstage passes to a TAFKAL80ETC concert SellIn is not correct", 9, sellIn);
		
		int quality = items.get(0).getQuality();
		assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 55, quality);
		
		quality = items.get(1).getQuality();
		assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 47, quality);
		
		
		
		sellIn = items.get(2).getSellIn();
		assertEquals("Backstage passes to a TAFKAL80ETC concert SellIn is not correct", 10, sellIn);
		
		sellIn = items.get(3).getSellIn();
		assertEquals("Backstage passes to a TAFKAL80ETC concert SellIn is not correct", 9, sellIn);
		
		quality = items.get(2).getQuality();
		assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 46, quality);
		
		quality = items.get(3).getQuality();
		assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 55, quality);
	}
	
	@Test
	public void mutationTestForConjuredManaCake() {
		GildedRose inn = new GildedRose();
		
		inn.setItem(new Item("Conjured Mana Cake", 11, 55));
		inn.setItem(new Item("Conjured Mana Cake", 10, 45));
		
		inn.setItem(new Item("Conjured Mana Cake", 11, 45));
		inn.setItem(new Item("Conjured Mana Cake", 10, 55));
		
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		
		int sellIn = items.get(0).getSellIn();
		assertEquals("Backstage passes to a TAFKAL80ETC concert SellIn is not correct", 10, sellIn);
		
		sellIn = items.get(1).getSellIn();
		assertEquals("Backstage passes to a TAFKAL80ETC concert SellIn is not correct", 9, sellIn);
		
		int quality = items.get(0).getQuality();
		assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 54, quality);
		
		quality = items.get(1).getQuality();
		assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 44, quality);
		
		
		
		sellIn = items.get(2).getSellIn();
		assertEquals("Backstage passes to a TAFKAL80ETC concert SellIn is not correct", 10, sellIn);
		
		sellIn = items.get(3).getSellIn();
		assertEquals("Backstage passes to a TAFKAL80ETC concert SellIn is not correct", 9, sellIn);
		
		quality = items.get(2).getQuality();
		assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 44, quality);
		
		quality = items.get(3).getQuality();
		assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 54, quality);
	}
	
	@Test
	public void testMain() {
		//Should not throw
		GildedRose.main(null);
	}
}
