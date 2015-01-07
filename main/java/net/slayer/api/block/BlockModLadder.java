package net.slayer.api.block;

import net.industrial_magic.IndustrialBlocks;
import net.industrial_magic.IndustrialTabs;
import net.minecraft.block.BlockLadder;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockModLadder extends BlockLadder { 
    
	public BlockModLadder(String name) {
        setUnlocalizedName(name);
        setCreativeTab(IndustrialTabs.blocks);
        IndustrialBlocks.blockName.add(name);
        GameRegistry.registerBlock(this, name);
	}
}