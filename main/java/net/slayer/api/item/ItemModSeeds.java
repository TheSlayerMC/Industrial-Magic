package net.slayer.api.item;

import java.util.List;

import net.industrial_magic.IndustrialItems;
import net.industrial_magic.IndustrialTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.slayer.api.SlayerAPI;

public class ItemModSeeds extends ItemSeeds {
	
	public ItemModSeeds(String name, Block block) {
		super(block, Blocks.farmland);
		setUnlocalizedName(name);
		setCreativeTab(IndustrialTabs.items);
		IndustrialItems.itemNames.add(name);
		GameRegistry.registerItem(this, name);
	}
	
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(SlayerAPI.Colour.DARK_AQUA + SlayerAPI.MOD_NAME);
	}
}