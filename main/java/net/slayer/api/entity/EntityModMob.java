package net.slayer.api.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public abstract class EntityModMob extends EntityMob {

	public EntityModMob(World par1World) {
		super(par1World);
		addBasicAI();
		setSize(1.0F, 2.0F);
	}

	public double getHP(){return getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();}
	public double getMoveSpeed(){return getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();}
	public double getAttackDamage(){return getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();}
	public double getFollowRange(){return getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();}
	public double getKnockbackResistance(){return getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue();}

	protected void addAttackingAI() {
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.4D, false));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.4F, false));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	protected void addBasicAI(){
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIWander(this, 1.0F));
		this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(3, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(setFollowRange());
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(setMovementSpeed());
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(setKnockbackResistance());
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(setMaxHealth(new MobStats()));
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(setAttackDamage(new MobStats()));
	}

	public double setFollowRange(){return MobStats.follow;}
	public double setMovementSpeed(){return 0.200000011920929D;}
	public double setKnockbackResistance() {return MobStats.knockBackResistance;}

	public abstract double setAttackDamage(MobStats s);
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
	
	@Override
	public boolean getCanSpawnHere() {
		return worldObj.getDifficulty() != EnumDifficulty.PEACEFUL && this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox()) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
	}
}