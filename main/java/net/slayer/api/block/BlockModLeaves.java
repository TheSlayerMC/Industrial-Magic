package net.slayer.api.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slayer.api.EnumMaterialTypes;

public class BlockModLeaves extends BlockMod implements IShearable {
	
    protected int[] adjacentTreeBlocks;

    public BlockModLeaves(String name, float hardness) {
        super(EnumMaterialTypes.LEAVES, name, hardness);
        this.setHardness(0.3F);
        this.setLightOpacity(1);
        this.setTickRandomly(true);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState s) {
        byte b0 = 1;
        int i1 = b0 + 1;
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        if (world.isAreaLoaded(new BlockPos(x - i1, y - i1, z - i1), new BlockPos(x + i1, y + i1, z + i1))) {
            for (int j1 = -b0; j1 <= b0; ++j1) {
                for (int k1 = -b0; k1 <= b0; ++k1) {
                    for (int l1 = -b0; l1 <= b0; ++l1) {
                        Block block = world.getBlockState(new BlockPos(x + j1, y + k1, z + l1)).getBlock();
                        if (block.isLeaves(world, new BlockPos(x + j1, y + k1, z + l1)))
                            block.beginLeavesDecay(world, new BlockPos(x + j1, y + k1, z + l1));
                    }
                }
            }
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState s, Random rand) {
    	int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        if (!world.isRemote) {
            int l = world.getBlockState(pos).getBlock().getMetaFromState(s);

            if ((l & 8) != 0 && (l & 4) == 0) {
                byte b0 = 4;
                int i1 = b0 + 1;
                byte b1 = 32;
                int j1 = b1 * b1;
                int k1 = b1 / 2;

                if (this.adjacentTreeBlocks == null)
                    this.adjacentTreeBlocks = new int[b1 * b1 * b1];

                int l1 = 0;

                if (world.isAreaLoaded(new BlockPos(x - i1, y - i1, z - i1), new BlockPos(x + i1, y + i1, z + i1))) {
                    int i2 = 0;
                    int j2 = 0;

                    for (l1 = -b0; l1 <= b0; ++l1) {
                        for (i2 = -b0; i2 <= b0; ++i2) {
                            for (j2 = -b0; j2 <= b0; ++j2) {
                                Block block = world.getBlockState(new BlockPos(x + l1, y + i2, z + j2)).getBlock();

                                if (!block.canSustainLeaves(world, new BlockPos(x + l1, y + i2, z + j2))) {
                                    if (block.isLeaves(world, new BlockPos(x + l1, y + i2, z + j2))) this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2;
                                    else this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1;
                                } else this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0;
                            }
                        }
                    }

                    for (l1 = 1; l1 <= 4; ++l1) {
                        for (i2 = -b0; i2 <= b0; ++i2) {
                            for (j2 = -b0; j2 <= b0; ++j2) {
                                for (int k2 = -b0; k2 <= b0; ++k2) {
                                    if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1] == l1 - 1) {
                                        if (this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2)
                                            this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;

                                        if (this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2)
                                            this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;

                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] == -2)
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] = l1;

                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] == -2)
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] = l1;

                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] == -2)
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] = l1;

                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] == -2)
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] = l1;
                                    }
                                }
                            }
                        }
                    }
                }

                l1 = this.adjacentTreeBlocks[k1 * j1 + k1 * b1 + k1];

                if (l1 >= 0) world.setBlockState(new BlockPos(x, y, z), this.getStateFromMeta(l & -9), 4);
                else this.removeLeaves(world, x, y, z, s);
            }
        }
    }

    protected void removeLeaves(World world, int x, int y, int z, IBlockState s) {
        this.dropBlockAsItem(world, new BlockPos(x, y, z), s, 0);
        world.setBlockToAir(new BlockPos(x, y, z));
    }
    
    /*@Override
    public void beginLeavesDecay(World world, BlockPos pos) {
    	world.destroyBlock(pos, false);
    }*/

    @Override
    public boolean isLeaves(IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
    	return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, BlockPos pos, EnumFacing s) {
        Block block = w.getBlockState(pos).getBlock();
        return !Minecraft.getMinecraft().gameSettings.fancyGraphics && block == this ? false : super.shouldSideBeRendered(w, pos, s);
    }

    @Override
    public Item getItemDropped(IBlockState par1, Random rand, int par3) {
        return null;
    }
    
    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this, 1, 0));
        return ret;
    }
}