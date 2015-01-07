package net.slayer.api;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {

	public static Configuration cfg;
	public static int baseMobID, baseProjectileID, baseEntityListID;

	public static void init(FMLPreInitializationEvent event) {
		cfg = new Configuration(new File(event.getModConfigurationDirectory() + "/" + SlayerAPI.MOD_NAME + ".cfg"));
		cfg.load();
		dimensionInit();
		miscInit();
		cfg.save();
	}


	private static void dimensionInit() {
		
	}


	private static void miscInit() {
		baseMobID = cfg.get("Entity", "The starting ID for the mobs (only gets greater the more mobs this mod has registered)", 400).getInt();
		baseProjectileID = cfg.get("Entity", "The starting ID for the projectiles (only gets greater the more projectiles this mod has registered)", 550).getInt();
		baseEntityListID = cfg.get("Entity", "The starting 'Entity List ID'", 4000).getInt();
	}
}