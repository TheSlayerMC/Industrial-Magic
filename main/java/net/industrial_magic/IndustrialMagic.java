package net.industrial_magic;

import net.industrial_magic.proxy.ServerProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.slayer.api.SlayerAPI;

@Mod(modid = SlayerAPI.MOD_ID, name = SlayerAPI.MOD_NAME, version = SlayerAPI.MOD_VERSION)
public class IndustrialMagic {

	@Instance(SlayerAPI.MOD_ID)
	public static IndustrialMagic instance;
	
	@SidedProxy(clientSide = "net.industrial_magic.proxy.ClientProxy", serverSide = "net.industrial_magic.proxy.ServerProxy")
	public static ServerProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}