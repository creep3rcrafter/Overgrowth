package com.creep3rcrafter.overgrowth.common.entity;

import com.creep3rcrafter.overgrowth.common.register.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;
import java.util.function.Predicate;

public class GloomEntity extends TameableEntity implements IFlyingAnimal {
    public static final Predicate<LivingEntity> PREY_SELECTOR = (livingEntity) -> {
        EntityType<?> entitytype = livingEntity.getType();

        return entitytype == EntityType.PIG || entitytype == EntityType.PIGLIN || entitytype == EntityType.PIGLIN_BRUTE || entitytype == EntityType.ZOMBIFIED_PIGLIN;
    };

    public GloomEntity(EntityType<? extends GloomEntity> entityType, World world) {
        super(entityType, world);
        this.setTame(false);
        this.moveControl = new FlyingMovementController(this, 20, true);
        this.setPathfindingMalus(PathNodeType.FENCE, -1.0F);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 10.0F);
        this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, 10.0F);
        this.setPathfindingMalus(PathNodeType.LAVA, 10.0F);
        this.setPathfindingMalus(PathNodeType.COCOA, -1.0F);
        this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
        this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttribute() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.FLYING_SPEED, 0.6D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    public float getWalkTargetValue(BlockPos pPos, IWorldReader pLevel) {
        return pLevel.getBlockState(pPos).isAir() ? 10.0F : 0.0F;
    }

    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.5D, (Ingredient.of(Items.GLOWSTONE_DUST)), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.75D, (Ingredient.of(Items.GLOWSTONE)), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 2.0D, (Ingredient.of(Items.SHROOMLIGHT)), false));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(7, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, AbstractPiglinEntity.class, false));
        this.targetSelector.addGoal(8, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, PREY_SELECTOR));

    }

    protected PathNavigator createNavigation(World level) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, level) {
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }

            public void tick() {
                super.tick();
            }
        };
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(false);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    public void defineSynchedData() {
        super.defineSynchedData();
    }

    public void die(DamageSource cause) {
        super.die(cause);
    }

    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            return super.hurt(source, amount);
        }
    }

    public boolean doHurtTarget(Entity entity) {
        boolean flag = entity.hurt(DamageSource.mobAttack(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, entity);
        }
        return flag;
    }

    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }
    }

    public void tick() {
        super.tick();
    }


    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || item == Items.GLOWSTONE_DUST || item == Items.SHROOMLIGHT || item == Items.GLOWSTONE && !this.isTame();
            return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
        } else {
            if (this.isTame()) {
                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!player.abilities.instabuild) {
                        itemstack.shrink(1);
                    }

                    this.heal((float) item.getFoodProperties().getNutrition());
                    return ActionResultType.SUCCESS;
                }

                if (!(item instanceof DyeItem)) {
                    ActionResultType actionresulttype = super.mobInteract(player, hand);
                    if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(player)) {
                        this.setOrderedToSit(!this.isOrderedToSit());
                        this.jumping = false;
                        this.navigation.stop();
                        this.setTarget((LivingEntity) null);
                        return ActionResultType.SUCCESS;
                    }

                    return actionresulttype;
                }
            } else if (item == Items.GLOWSTONE_DUST || item == Items.SHROOMLIGHT || item == Items.GLOWSTONE) {
                if (!player.abilities.instabuild) {
                    itemstack.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget((LivingEntity) null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte) 6);
                }

                return ActionResultType.SUCCESS;
            }

            return super.mobInteract(player, hand);
        }
    }

    public boolean isFood(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.GLOWSTONE_DUST || item == Items.SHROOMLIGHT || item == Items.GLOWSTONE;
    }

    private void pathfindRandomlyTowards(BlockPos pPos) {
        Vector3d vector3d = Vector3d.atBottomCenterOf(pPos);
        int i = 0;
        BlockPos blockpos = this.blockPosition();
        int j = (int) vector3d.y - blockpos.getY();
        if (j > 2) {
            i = 4;
        } else if (j < -2) {
            i = -4;
        }

        int k = 6;
        int l = 8;
        int i1 = blockpos.distManhattan(pPos);
        if (i1 < 15) {
            k = i1 / 2;
            l = i1 / 2;
        }

        Vector3d vector3d1 = RandomPositionGenerator.getAirPosTowards(this, k, l, i, vector3d, (double) ((float) Math.PI / 10F));
        if (vector3d1 != null) {
            this.navigation.setMaxVisitedNodesMultiplier(0.5F);
            this.navigation.moveTo(vector3d1.x, vector3d1.y, vector3d1.z, 1.0D);
        }
    }
    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity mate) {
        GloomEntity gloomEntity = ModEntities.GLOOM.get().create(serverWorld);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            gloomEntity.setOwnerUUID(uuid);
            gloomEntity.setTame(true);
        }
        return gloomEntity;
    }

    @Override
    public Entity getEntity() {
        return super.getEntity();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return super.serializeNBT();
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return super.getPickedResult(target);
    }

    @Override
    public EntityClassification getClassification(boolean forSpawnCount) {
        return super.getClassification(forSpawnCount);
    }

    @Override
    public boolean isMultipartEntity() {
        return super.isMultipartEntity();
    }

    public void aiStep() {
        if (!this.onGround && this.getDeltaMovement().y < 0.0D) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }
        super.aiStep();
    }

    public float getBrightness() {
        return 1.0F;
    }

    public boolean isSensitiveToWater() {
        return false;
    }

    public void stop() {
        GloomEntity.this.navigation.stop();
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    private boolean closerThan(BlockPos pPos, int pDistance) {
        return pPos.closerThan(this.blockPosition(), (double) pDistance);
    }

    private boolean isTooFarAway(BlockPos pPos) {
        return !this.closerThan(pPos, 32);
    }

    public boolean causeFallDamage(float pFallDistance, float pDamageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    protected float getStandingEyeHeight(Pose pPose, EntitySize pSize) {
        return this.isBaby() ? pSize.height * 0.5F : pSize.height * 0.5F;
    }

    protected boolean makeFlySound() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, (double) (0.5F * this.getEyeHeight()), (double) (this.getBbWidth() * 0.2F));
    }


    class WanderGoal extends Goal {
        WanderGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            return GloomEntity.this.navigation.isDone() && GloomEntity.this.random.nextInt(10) == 0;
        }

        public boolean canContinueToUse() {
            return GloomEntity.this.navigation.isInProgress();
        }

        public void start() {
            Vector3d vector3d = this.findPos();
            if (vector3d != null) {
                GloomEntity.this.navigation.moveTo(GloomEntity.this.navigation.createPath(new BlockPos(vector3d), 1), 1.0D);
            }

        }

        @Nullable
        private Vector3d findPos() {
            Vector3d vector3d;
            vector3d = GloomEntity.this.getViewVector(0.0F);

            int i = 8;
            Vector3d vector3d2 = RandomPositionGenerator.getAboveLandPos(GloomEntity.this, 8, 7, vector3d, ((float) Math.PI / 2F), 2, 1);
            return vector3d2 != null ? vector3d2 : RandomPositionGenerator.getAirPos(GloomEntity.this, 8, 4, -2, vector3d, (double) ((float) Math.PI / 2F));
        }
    }
}
