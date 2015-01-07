package net.slayer.api.item;

import java.util.List;

import net.industrial_magic.IndustrialItems;
import net.industrial_magic.IndustrialTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.slayer.api.SlayerAPI;

public class ItemModFood extends ItemFood {

	private int time = 32;
	
    public ItemModFood(String name, int food, float sat, boolean wolfFood) {
        super(food, sat, wolfFood);
        setUnlocalizedName(name);
        setCreativeTab(IndustrialTabs.items);
        IndustrialItems.itemNames.add(name);
        GameRegistry.registerItem(this, name);
    }
    
    public ItemModFood(String name, int food, float sat, int timeToEat, boolean wolfFood) {
       this(name, food, sat, wolfFood);
       time = timeToEat;
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
    	return time;
    }

    public ItemModFood(String name, int food, float sat, boolean wolfFood, int potionID, int potionDuration, int potionAmplifier, float potionEffectProbability) {
        this(name, food, sat, wolfFood);
        setPotionEffect(potionID, potionDuration, potionAmplifier, potionEffectProbability);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add("Fills " + (double) getHealAmount(stack) / 2 + " Hunger Bars");
        list.add(getSaturationModifier(stack) + " Saturation");
        if(time <= 32) list.add("Faster eating");
        list.add(SlayerAPI.Colour.DARK_AQUA + SlayerAPI.MOD_NAME);
    }
}