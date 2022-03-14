package creep3rcrafter.overgrowth.entity;

import creep3rcrafter.overgrowth.block.RisingBlock;
import creep3rcrafter.overgrowth.register.ModEntities;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class RisingBlockEntity extends FallingBlockEntity implements IEntityAdditionalSpawnData {

    private double speed = 0.04D;

    public RisingBlockEntity(EntityType<? extends FallingBlockEntity> p_i50218_1_, World p_i50218_2_) {
        super(p_i50218_1_, p_i50218_2_);
    }

    public RisingBlockEntity(World pLevel, double pX, double pY, double pZ, BlockState pState) {
        this(ModEntities.RISING_BLOCK.get(), pLevel);
        this.blockState = pState;
        this.blocksBuilding = true;
        this.setPos(pX, pY + (double) ((1.0F - this.getBbHeight()) / 2.0F), pZ);
        this.setDeltaMovement(Vector3d.ZERO);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.setStartPos(this.blockPosition());
    }

    public RisingBlockEntity(World pLevel, double pX, double pY, double pZ, BlockState pState, double speed) {
        this(ModEntities.RISING_BLOCK.get(), pLevel);
        this.blockState = pState;
        this.blocksBuilding = true;
        this.setPos(pX, pY + (double) ((1.0F - this.getBbHeight()) / 2.0F), pZ);
        this.setDeltaMovement(Vector3d.ZERO);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.speed = speed;
        this.setStartPos(this.blockPosition());
    }

    @Override
    public void move(MoverType type, Vector3d pos) {
        super.move(type, pos);
    }

    public void tick() {
        if (this.blockState.isAir()) {
            this.remove();
        } else {
            Block block = this.blockState.getBlock();
            if (this.time++ == 0) {
                BlockPos blockpos = this.blockPosition();
                if (this.level.getBlockState(blockpos).is(block)) {
                    this.level.removeBlock(blockpos, false);
                } else if (!this.level.isClientSide) {
                    this.remove();
                    return;
                }
            }

            if (!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, speed, 0.0D));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!this.level.isClientSide) {
                BlockPos blockPos1 = this.blockPosition();
                boolean flag = this.blockState.getBlock() instanceof ConcretePowderBlock;
                boolean flag1 = flag && this.level.getFluidState(blockPos1).is(FluidTags.WATER);
                double d0 = this.getDeltaMovement().lengthSqr();
                if (flag && d0 > 1.0D) {
                    BlockRayTraceResult blockraytraceresult = this.level.clip(new RayTraceContext(new Vector3d(this.xo, this.yo, this.zo), this.position(), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.SOURCE_ONLY, this));
                    if (blockraytraceresult.getType() != RayTraceResult.Type.MISS && this.level.getFluidState(blockraytraceresult.getBlockPos()).is(FluidTags.WATER)) {
                        blockPos1 = blockraytraceresult.getBlockPos();
                        flag1 = true;
                    }
                }

                if (!this.verticalCollision && !flag1) {
                    if (!this.level.isClientSide && (this.time > 100 && (blockPos1.getY() < 1 || blockPos1.getY() > 256) || this.time > 600)) {
                        if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            this.spawnAtLocation(block);
                        }

                        this.remove();
                    }
                } else {
                    BlockState blockstate = this.level.getBlockState(blockPos1);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
                    if (!blockstate.is(Blocks.MOVING_PISTON)) {
                        this.remove();
                        if (!this.cancelDrop) {
                            boolean flag2 = blockstate.canBeReplaced(new DirectionalPlaceContext(this.level, blockPos1, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                            boolean flag3 = RisingBlock.isFree(this.level.getBlockState(blockPos1.above())) && (!flag || !flag1);
                            boolean flag4 = this.blockState.canSurvive(this.level, blockPos1) && !flag3;
                            if (flag2 && flag4) {
                                if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level.getFluidState(blockPos1).getType() == Fluids.WATER) {
                                    this.blockState = this.blockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
                                }

                                if (this.level.setBlock(blockPos1, this.blockState, 3)) {
                                    if (block instanceof RisingBlock) {
                                        ((RisingBlock)block).onLand(this.level, blockPos1, this.blockState, blockstate, this);
                                    }

                                    if (this.blockData != null && this.blockState.hasTileEntity()) {
                                        TileEntity tileentity = this.level.getBlockEntity(blockPos1);
                                        if (tileentity != null) {
                                            CompoundNBT compoundnbt = tileentity.save(new CompoundNBT());

                                            for(String s : this.blockData.getAllKeys()) {
                                                INBT inbt = this.blockData.get(s);
                                                if (!"x".equals(s) && !"y".equals(s) && !"z".equals(s)) {
                                                    compoundnbt.put(s, inbt.copy());
                                                }
                                            }

                                            tileentity.load(this.blockState, compoundnbt);
                                            tileentity.setChanged();
                                        }
                                    }
                                } else if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.spawnAtLocation(block);
                                }
                            } else if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                this.spawnAtLocation(block);
                            }
                        } else if (block instanceof RisingBlock) {
                            ((RisingBlock)block).onBroken(this.level, blockPos1, this);
                        }
                    }
                }
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        }
    }


    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeInt(Block.getId(this.getBlockState()));
        buffer.writeInt(this.time);
        buffer.writeInt(this.time);
        buffer.writeBoolean(this.dropItem);
    }

    @Override
    public void readSpawnData(PacketBuffer buffer) {
        this.blockState = Block.stateById(buffer.readInt());
        this.time = buffer.readInt();
        this.time = buffer.readInt();
        this.dropItem = buffer.readBoolean();
        if (this.blockState.isAir()) {
            this.blockState = Blocks.SAND.defaultBlockState();
        }
    }
}
