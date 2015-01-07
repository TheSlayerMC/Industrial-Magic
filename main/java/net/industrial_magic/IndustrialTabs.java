package net.industrial_magic;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IndustrialTabs extends CreativeTabs {
	
	public static final IndustrialTabs blocks = new IndustrialTabs("im.blocks");
	public static final IndustrialTabs items = new IndustrialTabs("im.items");

	public Item item;
	
	public IndustrialTabs(String name) {
		super(name);
	}
	
    public void setIcon(Item icon) {
        this.item = icon;
    }

    public void setIcon(Block icon) {
        this.item = Item.getItemFromBlock(icon);
    }
    
    public void setIcon(ItemStack icon) {
        this.item = icon.getItem();
    }
	
	@Override
	public Item getTabIconItem() {
		return item;
	}
	
	public static void init(){
		blocks.setIcon(Blocks.acacia_door);
		items.setIcon(Items.apple);
	}
}
