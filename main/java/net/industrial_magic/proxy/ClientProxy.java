package net.industrial_magic.proxy;

import net.industrial_magic.IndustrialBlocks;
import net.industrial_magic.IndustrialItems;
import net.industrial_magic.IndustrialMagic;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.slayer.api.SlayerAPI;
import net.slayer.api.client.GuiHandler;

public class ClientProxy extends ServerProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(IndustrialMagic.instance, new GuiHandler());
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		for(String s : IndustrialBlocks.blockName) {
			Item i = GameRegistry.findItem(SlayerAPI.MOD_ID, s);
			registerItem(i, s);
		}

		for(String s : IndustrialItems.itemNames) {
			Item i = GameRegistry.findItem(SlayerAPI.MOD_ID, s);
			registerItem(i, s);
		}
	}
	
	public static void registerModelBakery(Item i, String[] names) {
		ModelBakery.addVariantName(i, names);
	}

	public static void registerModelBakery(Block b, String[] names) {
		ModelBakery.addVariantName(SlayerAPI.toItem(b), names);
	}

	public static void registerItem(Item item, int metadata, String itemName) {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		mesher.register(item, metadata, new ModelResourceLocation(SlayerAPI.PREFIX + itemName, "inventory"));
	}

	public static void registerBlock(Block block, int metadata, String blockName) {
		registerItem(Item.getItemFromBlock(block), metadata, blockName);
	}

	public static void registerBlock(Block block, String blockName) {
		registerBlock(block, 0, blockName);
	}

	public static void registerItem(Item item, String itemName) {
		registerItem(item, 0, itemName);
	}
}