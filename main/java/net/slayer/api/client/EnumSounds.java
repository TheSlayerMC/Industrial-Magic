package net.slayer.api.client;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.slayer.api.SlayerAPI;

public enum EnumSounds {

	;
	
	private String sound;

	private EnumSounds(String sound) {
		this.sound = sound;
	}
	
	public String getPrefixedName() {
		return SlayerAPI.PREFIX + sound;
	}
	
	public String getNonPrefixedName() {
		return sound;
	}
	
	public static void playSound(String sound, World w, EntityLivingBase e){
		w.playSoundAtEntity(e, sound, 1.0F, 1.0F);
	}
	
	public static void playSound(String sound, World w, EntityLivingBase e, float volume, float pitch){
		w.playSoundAtEntity(e, sound, volume, pitch);
	}
	
	public static void playSound(EnumSounds sound, World w, EntityLivingBase e){
		w.playSoundAtEntity(e, sound.getNonPrefixedName(), 1.0F, 1.0F);
	}
	
	public static void playSound(EnumSounds sound, World w, EntityLivingBase e, float volume, float pitch){
		w.playSoundAtEntity(e, sound.getNonPrefixedName(), volume, pitch);
	}
	
	public static void playSound(String sound, World w, TileEntity e){
		w.playSound((double)e.getPos().getX(), (double)e.getPos().getY(), (double)e.getPos().getZ(), sound, 1.0F, 1.0F, true);
	}
	
	public static void playSound(String sound, World w, TileEntity e, float volume, float pitch){
		w.playSound((double)e.getPos().getX(), (double)e.getPos().getY(), (double)e.getPos().getZ(), sound, volume, pitch, true);
	}
}