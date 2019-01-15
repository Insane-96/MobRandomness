package net.insane96mcp.mpr.json.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.insane96mcp.mpr.exceptions.InvalidJsonException;
import net.insane96mcp.mpr.lib.Logger;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

public class Slot {

	@SerializedName("override_vanilla")
	public boolean overrideVanilla;
	@SerializedName("replace_only")
	public boolean replaceOnly;
	public Chance chance;
	public ArrayList<Item> items = new ArrayList<Item>();
	
	@Override
	public String toString() {
		return String.format("Slot{overrideVanilla: %s, replaceOnly: %s, chance: %s, items: %s}", overrideVanilla, replaceOnly, chance, items);
	}
	
	private List<Item> GetItemsWithWeightDifficulty(World world){
		ArrayList<Item> items = new ArrayList<Item>();
		for (Item item : this.items) {
			items.add(item.GetWeightWithDifficulty(item, world));
		}
		System.out.println(items);
		return items;
	}
	
	public Item GetRandomItem(World world) {
		return WeightedRandom.getRandomItem(world.rand, GetItemsWithWeightDifficulty(world));
	}

	public void Validate(final File file) throws InvalidJsonException{
		if (chance != null)
			chance.Validate(file);
		else
			throw new InvalidJsonException("Missing Chance for " + this, file);
		
		if (replaceOnly && !overrideVanilla)
		{
			Logger.Debug("overrideVanilla has been set to true since replaceOnly is true for " + this);
			overrideVanilla = true;
		}
		
		if (items.isEmpty())
			throw new InvalidJsonException("There's no item set in the slot " + this, file);
		else {
			for (Item item : items) {
				item.Validate(file);
			}
		}
	}
}
