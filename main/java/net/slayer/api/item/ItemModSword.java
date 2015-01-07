package net.slayer.api.item;

import java.util.List;

import net.industrial_magic.IndustrialItems;
import net.industrial_magic.IndustrialTabs;
import net.industrial_magic.util.IndustrialToolMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slayer.api.SlayerAPI;

public class ItemModSword extends ItemSword {

	protected IndustrialToolMaterial mat;
	
    public ItemModSword(String name, IndustrialToolMaterial toolMaterial) {
        super(toolMaterial.getToolMaterial());
        setUnlocalizedName(name);
        mat = toolMaterial;
        setCreativeTab(IndustrialTabs.items);
        IndustrialItems.itemNames.add(name);
        GameRegistry.registerItem(this, name);
    }
    
	@Override
	public boolean isItemTool(ItemStack i) {
		return true;
	}
    
	@Override
	public boolean getIsRepairable(ItemStack i, ItemStack i1) {
		boolean canRepair = mat.getRepairItem() != null;
		if(canRepair) return mat.getRepairItem() == i1.getItem() ? true : super.getIsRepairable(i, i1);
		return super.getIsRepairable(i, i1);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List infoList, boolean par4) {
        if (item.getMaxDamage() != -1) infoList.add(item.getMaxDamage() - item.getItemDamage() + " Uses Remaining");
        else infoList.add(SlayerAPI.Colour.GREEN + "Infinite Uses");
        infoList.add(SlayerAPI.Colour.DARK_AQUA + SlayerAPI.MOD_NAME);
    }
}