package net.slayer.api.block;

import java.util.Random;

import net.industrial_magic.IndustrialTabs;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.slayer.api.EnumMaterialTypes;

public class BlockModGrass extends BlockMod implements IGrowable {

    protected BlockMod dirt; 
    protected String tex;

    public BlockModGrass(BlockMod dirt, String name, float hardness) {
        super(EnumMaterialTypes.GRASS, name, hardness);
        this.dirt = dirt;
        setCreativeTab(IndustrialTabs.blocks);
        setTickRandomly(true);
    }
    
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
    	if(!world.isRemote) {
    		int x = pos.getX(), y = pos.getY(), z = pos.getZ();
            if(world.getLightFromNeighbors(pos.up()) < 4 && world.getBlockLightOpacity(pos.up()) > 2) world.setBlockState(pos, dirt.getDefaultState(), 2);
            else if(world.getLightFromNeighbors(pos.up()) >= 9) {
                for (int l = 0; l < 4; ++l) {
                    int i1 = x + random.nextInt(3) - 1;
                    int j1 = y + random.nextInt(5) - 3;
                    int k1 = z + random.nextInt(3) - 1;
                    BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
                    if(world.getBlockState(new BlockPos(i1, j1, k1)) == dirt.getDefaultState() && world.getBlockState(new BlockPos(i1, j1 + 1, k1)) == Blocks.air.getDefaultState() && world.getLightFromNeighbors(blockpos1.up()) >= 4 && world.getLightFromNeighbors(new BlockPos(i1, j1 + 1, k1)) <= 2)
                        world.setBlockState(new BlockPos(i1, j1, k1), getDefaultState());
                }
            }
        }
    }
    
    @Override
    public Item getItemDropped(IBlockState pos, Random par2, int par3) {
    	return Item.getItemFromBlock(dirt);
    }
    
    @Override
    public Item getItem(World w, BlockPos pos) {
    	return Item.getItemFromBlock(this);
    }
    
    @Override
    public boolean canSustainPlant(IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
    	return true;
    }

	@Override
	public boolean canUseBonemeal(World worldIn, Random p_180670_2_, BlockPos p_180670_3_, IBlockState p_180670_4_) {
		return true;
	}

	@Override
	public void grow(World worldIn, Random p_176474_2_, BlockPos p_176474_3_, IBlockState p_176474_4_) { }

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}
}