package net.slayer.api.block;

import net.industrial_magic.IndustrialTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.slayer.api.EnumMaterialTypes;

public class BlockModSlab extends BlockMod {

	public boolean isFull;

	public BlockModSlab(boolean full, String name) {
		super(name, 1.0F);
		isFull = full;
		setCreativeTab(IndustrialTabs.blocks);
		setStepSound(EnumMaterialTypes.WOOD.getSound());
		setHardness(1.0F);
		this.useNeighborBrightness = true;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		if(isFull) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
	}

	@Override
	public void setBlockBoundsForItemRender() {
		if(isFull) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
	}
}