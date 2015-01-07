package net.slayer.api.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public abstract class EntityModTameable extends EntityTameable {
    
	public EntityModTameable(World w) {
		super(w);
		addBasicAI();
	}
	
	public double getHP(){return getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();}
	public double getMoveSpeed(){return getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();}
	public double getAttackDamage(){return getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();}
	public double getFollowRange(){return getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();}
	public double getKnockbackResistance(){return getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue();}
	
	public abstract String setLivingSound();
	public abstract String setHurtSound();
	public abstract String setDeathSound();

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
	
	protected void addBasicAI(){
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0F, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0F, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIMate(this, 1.0F));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.setTamed(false);
	}
	
	protected void addAttackingAI(){
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0F, false));
		this.targetTasks.addTask(6, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));

	}
	
	@Override
    public boolean getCanSpawnHere() {
        return true;
    }
}