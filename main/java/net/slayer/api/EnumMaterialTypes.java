package net.slayer.api;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;

public enum EnumMaterialTypes {

    STONE(Material.rock, Block.soundTypeStone),
    LEAVES(Material.leaves, Block.soundTypeGrass),
    DIRT(Material.ground, Block.soundTypeGravel),
    WOOD(Material.wood, Block.soundTypeWood),
    GRASS(Material.grass, Block.soundTypeGrass),
    GLASS(Material.glass, Block.soundTypeGlass),
    PORTAL(Material.portal, Block.soundTypeStone),
    VINES(Material.vine, Block.soundTypeGrass),
    PLANT(Material.plants, Block.soundTypeGrass),
    SNOW(Material.snow, Block.soundTypeSnow),
    FIRE(Material.fire, Block.soundTypeWood),
    WOOL(Material.cloth, Block.soundTypeCloth),
    METAL_SOUND(Material.rock, Block.soundTypeMetal);

    private Material m;
    private SoundType s;
    
	private EnumMaterialTypes(Material m, SoundType s){
		this.m = m;
		this.s = s;
	}
	
	public Material getMaterial(){
		return m;
	}
	
	public SoundType getSound(){
		return s;
	}
}