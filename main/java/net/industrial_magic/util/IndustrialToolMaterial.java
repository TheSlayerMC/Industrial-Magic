package net.industrial_magic.util;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;

public enum IndustrialToolMaterial {

	;

	private ToolMaterial toolMaterial;
	private Item repairItem;
	private int harvestLevel = 0;

	private IndustrialToolMaterial(ToolMaterial toolMaterial, Item repair) {
		this.toolMaterial = toolMaterial;
		this.repairItem = repair;
		harvestLevel = 0;
	}
	
	private IndustrialToolMaterial(ToolMaterial toolMaterial, Item repair, int level) {
		this.toolMaterial = toolMaterial;
		this.repairItem = repair;
		this.harvestLevel = level;
	}

	private IndustrialToolMaterial(ToolMaterial toolMaterial) {
		this(toolMaterial, null);
	}

	public int getHarvestLevel(){
		return harvestLevel;
	}
	
	public Item getRepairItem(){
		return repairItem;
	}

	public ToolMaterial getToolMaterial() {
		return toolMaterial;
	}
}