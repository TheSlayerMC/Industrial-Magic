package net.slayer.api.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public abstract class EntityModWaterMob extends EntityWaterMob {

	public EntityModWaterMob(World w) {
		super(w);
		setSize(0.5F, 0.5F);
	}
	
	public double getHP(){return getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();}
	public double getMoveSpeed(){return getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();}
	public double getAttackDamage(){return getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();}
	public double getFollowRange(){return getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();}
	public double getKnockbackResistance(){return getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue();}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(setFollowRange());
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(setMovementSpeed());
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(setKnockbackResistance());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(setMaxHealth(new MobStats()));
	}

	public double setFollowRange(){return MobStats.follow;}
	public double setMovementSpeed(){return MobStats.normalSpeed;}
	public double setKnockbackResistance() {return MobStats.knockBackResistance;}

	public abstract double setMaxHealth(MobStats s);
	public abstract String setLivingSound();
	public abstract String setHurtSound();
	public abstract String setDeathSound();
	public abstract Item getItemDropped();

	@Override
	protected Item getDropItem() {
		return getItemDropped();
	}
	
	@Override
	protected void dropFewItems(boolean b, int j) {
		for(int i = 0; i < 1 + rand.nextInt(1); i++)
			this.dropItem(getItemDropped(), 1);
	}

	@Override
	protected String getLivingSound() {
		super.getLivingSound();
		return setLivingSound();
	}

	@Override
	protected String getHurtSound() {
		super.getHurtSound();
		return setHurtSound();
	}

	@Override
	protected String getDeathSound() {
		super.getDeathSound();
		return setDeathSound();
	}
}