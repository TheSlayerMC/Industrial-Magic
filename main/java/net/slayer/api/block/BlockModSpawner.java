package net.slayer.api.block;

import java.util.Random;

import net.industrial_magic.IndustrialTabs;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockModSpawner extends BlockContainer {

    protected String mobName;

    public BlockModSpawner(String name, String mobName) {
        super(Material.rock);
        this.mobName = mobName;
        setUnlocalizedName(name);
        setCreativeTab(IndustrialTabs.blocks);
        GameRegistry.registerBlock(this, name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par1) {
        TileEntityMobSpawner spawner = new TileEntityMobSpawner();
        spawner.getSpawnerBaseLogic().setEntityName(mobName);
        return spawner;
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 0;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState par5, float par6, int par7) {
        super.dropBlockAsItemWithChance(world, pos, par5, par6, par7);
        int var8 = 15 + world.rand.nextInt(15) + world.rand.nextInt(15);
        this.dropXpOnBlockBreak(world, pos, var8);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}