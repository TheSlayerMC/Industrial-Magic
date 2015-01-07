package net.slayer.api;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.industrial_magic.IndustrialMagic;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import net.slayer.api.client.ChatHandler;
import net.slayer.api.client.GL11Helper;

import org.lwjgl.opengl.GL11;

public class SlayerAPI {

	public static int mobID = Config.baseMobID, projectileID = Config.baseProjectileID, entityListID = Config.baseEntityListID;
	public static Logger logger = Logger.getLogger(SlayerAPI.MOD_ID);

	public static final String MOD_NAME = "Industrial Magic", MOD_ID = "im", PREFIX = MOD_ID + ":", MOD_VERSION = "1.0"; 
	public static final boolean DEVMODE = true, BETA = false;

	public static void addRecipe(ItemStack i, Object... o) {
		GameRegistry.addRecipe(i, o);
	}

	public static void scaleFont(FontRenderer f, String s, int x, int y, int color, double scale){
		GL11.glScaled(scale, scale, scale);
		f.drawString(s, 0, 0, color);
		GL11.glTranslatef(x, y, 0);
		double fixScale = 1 / scale;
		GL11.glScaled(fixScale, fixScale, fixScale);
	}

	public static void addBucket(Fluid fluid, ItemStack modBucket) {
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(FluidRegistry.getFluidStack(fluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), modBucket, new ItemStack(Items.bucket)));
	}
	
	public static void addMapGen(Class c, String s){
		try {
			MapGenStructureIO.registerStructureComponent(c, s);
			MapGenStructureIO.registerStructure(c, s);
		} catch(Exception e){
			logger.log(Level.WARNING, "[" + MOD_NAME + "] Failed To Spawn The Map Piece With The ID: " + s);
		}
	}

	public static void addVillageCreationHandler(IVillageCreationHandler v){
		VillagerRegistry.instance().registerVillageCreationHandler(v);
	}

	public static void registerCommand(ICommand o){
		if (MinecraftServer.getServer().getCommandManager() instanceof ServerCommandManager) {
			((CommandHandler)MinecraftServer.getServer().getCommandManager()).registerCommand(o);
		}
	}

	public static void addEventBus(Object o) {
		MinecraftForge.EVENT_BUS.register(o);
	}

	public static void addForgeEventBus(Object o) {
		FMLCommonHandler.instance().bus().register(o);
	}

	public static void registerMob(Class entityClass, String entityName) {
		EntityRegistry.registerModEntity(entityClass, entityName, mobID++, IndustrialMagic.instance, 128, 5, true);
        EntityList.addMapping(entityClass, entityName, entityListID++, 0x123123, 0x321321);
	}
	
	public static void registerNPC(Class entityClass, String entityName) {
		EntityRegistry.registerModEntity(entityClass, entityName, mobID++, IndustrialMagic.instance, 128, 5, true);
        EntityList.addMapping(entityClass, entityName, entityListID++, 0x00FF8C, 0x00F6FF);
	}
	
	public static void registerEntity(Class entityClass, String entityName, int ID) {
		EntityRegistry.registerModEntity(entityClass, entityName, ID, IndustrialMagic.instance, 120, 5, true);
	}

	public static void registerBossMob(Class entityClass, String entityName) {
        EntityRegistry.registerModEntity(entityClass, entityName, mobID++, IndustrialMagic.instance, 128, 5, true);
        EntityList.addMapping(entityClass, entityName, entityListID++, 0x000000, 0x9B0000);
	}

	public static void registerProjectile(Class entityClass, String entityName) {
		EntityRegistry.registerModEntity(entityClass, entityName + " Projectile", projectileID, IndustrialMagic.instance, 250, 5, true);
		projectileID++;
	}

    public static ArmorMaterial addArmorMaterial(String name, int durability, int[] oldArmor, int enchantability) {
        int duraNew = (int) Math.round(durability / 13.75);
        return EnumHelper.addArmorMaterial(name, name, duraNew, oldArmor, enchantability);
    }

	public static void addChatMessageWithColour(EntityPlayer p, EnumChatFormatting colour, String str) {
		ChatComponentText chat = new ChatComponentText(SlayerAPI.Colour.AQUA + "[" + SlayerAPI.Colour.BLUE + MOD_NAME + SlayerAPI.Colour.AQUA + "] " + str);
		chat.getChatStyle().setColor(colour);
		p.addChatMessage(chat);
	}

	public static void addChatMessage(EntityPlayer p, String str) {
		ChatComponentText ret = new ChatComponentText(str);
		p.addChatMessage(ret);
	}
	
	public static void addFormattedChatMessage(EntityPlayer p, String str) {
		ChatComponentText ret = new ChatComponentText(I18n.format(str, new Object[0]));
		p.addChatMessage(ret);
	}

	private static final String	SECTION_SIGN	= "\u00a7";

	public static final class Colour {
		public static final String	BLACK		= SECTION_SIGN + "0";
		public static final String	DARK_BLUE	= SECTION_SIGN + "1";
		public static final String	DARK_GREEN	= SECTION_SIGN + "2";
		public static final String	DARK_AQUA	= SECTION_SIGN + "3";
		public static final String	DARK_RED	= SECTION_SIGN + "4";
		public static final String	PURPLE		= SECTION_SIGN + "5";
		public static final String	GOLD		= SECTION_SIGN + "6";
		public static final String	GRAY		= SECTION_SIGN + "7";
		public static final String	DARK_GRAY	= SECTION_SIGN + "8";
		public static final String	BLUE		= SECTION_SIGN + "9";
		public static final String	GREEN		= SECTION_SIGN + "A";
		public static final String	AQUA		= SECTION_SIGN + "B";
		public static final String	RED			= SECTION_SIGN + "C";
		public static final String	LIGHT_PURPLE= SECTION_SIGN + "D";
		public static final String	YELLOW		= SECTION_SIGN + "E";
		public static final String	WHITE		= SECTION_SIGN + "F";
	}

	public static final class Format {
		public static final String	OBFUSCATED	= SECTION_SIGN + "k";
		public static final String	BOLD		= SECTION_SIGN + "l";
		public static final String	STRIKE		= SECTION_SIGN + "m";
		public static final String	UNDERLINE	= SECTION_SIGN + "n";
		public static final String	ITALIC		= SECTION_SIGN + "o";
		public static final String	RESET		= SECTION_SIGN + "r";
	}

	public static void registerItemRenderer(Item i, IItemRenderer ir) {
		MinecraftForgeClient.registerItemRenderer(i, ir);
	}

	public static void registerItemRenderer(Block b, IItemRenderer ir) {
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(b), ir);
	}

	public static void sendMessageToAll(String message, boolean showMod) {
		if(showMod) FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(SlayerAPI.Colour.DARK_AQUA + "[" + SlayerAPI.Colour.DARK_GREEN + MOD_NAME + SlayerAPI.Colour.DARK_AQUA + "] " + SlayerAPI.Colour.GREEN + message));
		else FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(SlayerAPI.Colour.GREEN + message));
	}

	public static void sendContinuedMessageToAll(String message) {
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(SlayerAPI.Colour.GREEN + message));
	}
	
    public static ToolMaterial addMeleeMaterial(int maxUses, float damage, int enchantability) {
        return EnumHelper.addEnum(ToolMaterial.class, "", 0, maxUses, 0, damage - 4, enchantability);
    }
    
    public static ToolMaterial addMeleeMaterial(float damage, int enchantability) {
        return EnumHelper.addEnum(ToolMaterial.class, "", 0, -1, 0, damage - 4, enchantability);
    }

    public static ToolMaterial addAxeMaterial(int harvestLevel, int maxUses, float efficiency, float damage, int enchantability) {
        return EnumHelper.addEnum(ToolMaterial.class, "", harvestLevel, maxUses, efficiency, damage - 3, enchantability);
    }

    public static ToolMaterial addAxeMaterial(int harvestLevel, float efficiency, float damage, int enchantability) {
        return EnumHelper.addEnum(ToolMaterial.class, "", harvestLevel, -1, efficiency, damage - 4, enchantability);
    }

	public static void removeCraftingRecipe(Item removed) {
		ItemStack recipeResult = null;
		ArrayList recipes = (ArrayList)CraftingManager.getInstance().getRecipeList();

		for (int i = 0; i < recipes.size(); i++) {
			IRecipe tmpRecipe = (IRecipe) recipes.get(i);

			if (tmpRecipe instanceof ShapedRecipes) {
				ShapedRecipes recipe = (ShapedRecipes) tmpRecipe;
				recipeResult = recipe.getRecipeOutput();
			}

			if (ItemStack.areItemStacksEqual(new ItemStack(removed), recipeResult)) {
				System.out.println("[" + MOD_NAME + "] Removed Recipe: " + recipes.get(i) + " -> " + recipeResult);
				recipes.remove(i);
			}
		}
	}

	public static void removeCraftingRecipe(Block removed) {
		ItemStack recipeResult = null;
		ArrayList recipes = (ArrayList)CraftingManager.getInstance().getRecipeList();

		for (int i = 0; i < recipes.size(); i++) {
			IRecipe tmpRecipe = (IRecipe) recipes.get(i);

			if (tmpRecipe instanceof ShapedRecipes) {
				ShapedRecipes recipe = (ShapedRecipes) tmpRecipe;
				recipeResult = recipe.getRecipeOutput();
			}

			if (ItemStack.areItemStacksEqual(new ItemStack(removed), recipeResult)) {
				System.out.println("[" + MOD_NAME + "] Removed Recipe: " + recipes.get(i) + " -> " + recipeResult);
				recipes.remove(i);
			}
		}
	}
	
	public static void removeCraftingRecipe(ItemStack removed) {
		ItemStack recipeResult = null;
		ArrayList recipes = (ArrayList)CraftingManager.getInstance().getRecipeList();

		for (int i = 0; i < recipes.size(); i++) {
			IRecipe tmpRecipe = (IRecipe) recipes.get(i);

			if (tmpRecipe instanceof ShapedRecipes) {
				ShapedRecipes recipe = (ShapedRecipes) tmpRecipe;
				recipeResult = recipe.getRecipeOutput();
			}

			if (ItemStack.areItemStacksEqual(removed, recipeResult)) {
				System.out.println("[" + MOD_NAME + "] Removed Recipe: " + recipes.get(i) + " -> " + recipeResult);
				recipes.remove(i);
			}
		}
	}

	public static Item toItem(Block block){
		return Item.getItemFromBlock(block);
	}
	
	public static boolean giveItemStackToPlayer(EntityPlayer player, Integer count, ItemStack itemstack) {
		if (player.worldObj.isRemote) {
			boolean boolAddedToInventory = true;
			for (int i = 0; i < count; i++) {
				boolAddedToInventory = player.inventory.addItemStackToInventory(itemstack);
				if (!boolAddedToInventory && itemstack.getItemDamage() == 0) {
					player.dropItem(itemstack.getItem(), 1);
					String itemName = itemstack.getUnlocalizedName();
					ChatHandler.sendFormattedChat(player, EnumChatFormatting.RED, "IndustrialMagic.fullinv", StatCollector.translateToLocal(itemName + ".name"));
				}
			}
			return boolAddedToInventory;
		} else {
			return giveItemStackToPlayer((EntityPlayerMP)player, count, itemstack);
		}
	}
	
	public static void giveItemStackToPlayer(EntityPlayer player, ItemStack itemstack) {
		giveItemStackToPlayer(player, itemstack);
	}
	
	public static boolean giveItemStackToPlayer(EntityPlayerMP player, Integer count, ItemStack itemstack) {
		boolean boolAddedToInventory = true;
		for (int i = 0; i < count; i++) {
			itemstack.stackSize = 1;
			boolAddedToInventory = player.inventory.addItemStackToInventory(itemstack);
			if (!boolAddedToInventory && itemstack.getItemDamage() == 0) {
				player.dropItem(itemstack.getItem(), 1);
				String itemName = itemstack.getUnlocalizedName();
				ChatHandler.sendFormattedChat(player, EnumChatFormatting.RED, "industrial.fullinv", StatCollector.translateToLocal(itemName + ".name"));
			} else {
				player.sendContainerToPlayer(player.inventoryContainer);
			}
		}
		return boolAddedToInventory;
	}
	
	public static void renderItem(ItemStack stack, double x, double y, double z, float scale) {
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		if(stack != null) {
			GL11.glTranslated(x, y, z);
			GL11Helper.scale(scale);
			renderItem.renderItemModel(stack);
		}
	}
}