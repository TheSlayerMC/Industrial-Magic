package net.slayer.api.block;

import net.industrial_magic.IndustrialBlocks;
import net.industrial_magic.IndustrialTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockModFence extends BlockFence {

	public BlockModFence(Block block, String name, boolean light) {
		super(block.getMaterial());
		setUnlocalizedName(name);
		setCreativeTab(IndustrialTabs.blocks);
		if(light) setLightLevel(0.5F);
		setHardness(block.getBlockHardness(null, null));
		IndustrialBlocks.blockName.add(name);
		GameRegistry.registerBlock(this, name);
	}
	
	public BlockModFence(Block b, String n) {
		this(b, n, false);
	}
	
	@Override
	public boolean canConnectTo(IBlockAccess blockAccess, BlockPos pos) {
		Block block = blockAccess.getBlockState(pos).getBlock();
		 return block == Blocks.barrier ? false : ((!(block instanceof BlockFence) || block.getMaterial() != this.blockMaterial) && !(block instanceof BlockFenceGate) ? (block.getMaterial().isOpaque() && block.isFullCube() ? block.getMaterial() != Material.gourd : false) : true);
	}
}