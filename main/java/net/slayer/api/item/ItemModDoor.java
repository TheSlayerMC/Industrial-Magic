package net.slayer.api.item;

import net.industrial_magic.IndustrialTabs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.slayer.api.block.BlockModDoor;

public class ItemModDoor extends ItemMod {

	private Block door;

	public ItemModDoor(BlockModDoor block, String name) {
		super(name);
		this.door = block;
		setCreativeTab(IndustrialTabs.items);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(side != EnumFacing.UP) return false;
		else {
			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();
			if(!block.isReplaceable(worldIn, pos)) pos = pos.offset(side);
			if(!playerIn.canPlayerEdit(pos, side, stack)) return false;
			else if(!this.door.canPlaceBlockAt(worldIn, pos)) return false;
			else {
				ItemDoor.placeDoor(worldIn, pos, EnumFacing.fromAngle((double)playerIn.rotationYaw), this.door);
				stack.stackSize--;
				return true;
			}
		}
	}
}