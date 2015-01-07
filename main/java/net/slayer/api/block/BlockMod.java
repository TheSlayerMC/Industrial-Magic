package net.slayer.api.block;

import java.util.Random;

import net.industrial_magic.IndustrialTabs;
import net.industrial_magic.IndustrialBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.slayer.api.EnumMaterialTypes;
import net.slayer.api.EnumToolType;
import net.slayer.api.SlayerAPI;

public class BlockMod extends Block{

	protected EnumMaterialTypes blockType;
	protected Item drop = null;
	protected Random rand;
	public int boostBrightnessLow;
	public int boostBrightnessHigh;
	public boolean enhanceBrightness;
	public String name;

	public BlockMod(String name, float hardness) {
		this(EnumMaterialTypes.STONE, name, hardness, IndustrialTabs.blocks);
	}
	
	public BlockMod(String name) {
		this(EnumMaterialTypes.STONE, name, 2.0F, IndustrialTabs.blocks);
	}
	
	public BlockMod(EnumMaterialTypes type, String name, float hardness) {
		this(type, name, hardness, IndustrialTabs.blocks);
	}

	public BlockMod(String name, boolean breakable, CreativeTabs tab) {
		this(EnumMaterialTypes.STONE, name, tab);
	}
	
	public BlockMod(String name, boolean breakable) {
		this(name, breakable, IndustrialTabs.blocks);
	}

	public BlockMod(EnumMaterialTypes blockType, String name, CreativeTabs tab) {
		super(blockType.getMaterial());
		this.blockType = blockType;
		setHardness(2.0F);
		rand = new Random();
		setStepSound(blockType.getSound());
		setCreativeTab(tab);
		setUnlocalizedName(name);
		this.name = name; 
		IndustrialBlocks.blockName.add(name);
		GameRegistry.registerBlock(this, name);
	}

	public BlockMod(EnumMaterialTypes blockType, String name, float hardness, CreativeTabs tab) {
		super(blockType.getMaterial());
		this.blockType = blockType;
		rand = new Random();
		setStepSound(blockType.getSound());
		setCreativeTab(tab);
		setUnlocalizedName(name);
		setHardness(hardness);
		this.name = name;
		IndustrialBlocks.blockName.add(name);
		GameRegistry.registerBlock(this, name);
	}

	public Block addName(String name) {
		IndustrialBlocks.blockName.add(name);
		return this;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(drop == null) return SlayerAPI.toItem(this);
		return drop;
	}

	public BlockMod setHarvestLevel(EnumToolType type) {
		setHarvestLevel(type.getType(), type.getLevel());
		return this;
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.SOLID;
    }
	
	@Override
	public int quantityDropped(Random rand) {
		return 1;
	}
	
	@Override
	public boolean isNormalCube() {
		return true;
	}
}