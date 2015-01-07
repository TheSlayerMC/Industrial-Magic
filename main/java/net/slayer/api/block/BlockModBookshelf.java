package net.slayer.api.block;

import net.industrial_magic.IndustrialTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBookshelf;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockModBookshelf extends BlockBookshelf {
	
    public BlockModBookshelf(String name) {
        setUnlocalizedName(name);
        setCreativeTab(IndustrialTabs.blocks);
        setStepSound(Block.soundTypeWood);
        GameRegistry.registerBlock(this, name);
    }
}