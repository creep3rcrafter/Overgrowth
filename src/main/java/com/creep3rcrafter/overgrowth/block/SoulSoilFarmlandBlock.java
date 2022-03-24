package com.creep3rcrafter.overgrowth.block;

import com.creep3rcrafter.overgrowth.util.NetherCropHelper;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class SoulSoilFarmlandBlock extends Block {
    public static final IntegerProperty ENERGY = IntegerProperty.create("energy", 0, 7);//heat or other
    public static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    public SoulSoilFarmlandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ENERGY, Integer.valueOf(0)));
    }

    public BlockState updateShape(BlockState blockState, Direction facing, BlockState facingBlockState, IWorld level, BlockPos currentBlockPos, BlockPos facingBlockPos) {
        if (facing == Direction.UP && !blockState.canSurvive(level, currentBlockPos)) {
            level.getBlockTicks().scheduleTick(currentBlockPos, this, 1);
        }

        return super.updateShape(blockState, facing, facingBlockState, level, currentBlockPos, facingBlockPos);
    }

    public boolean canSurvive(BlockState blockState, IWorldReader level, BlockPos blockPos) {
        BlockState blockstate = level.getBlockState(blockPos.above());
        return !blockstate.getMaterial().isSolid() || blockstate.getBlock() instanceof FenceGateBlock || blockstate.getBlock() instanceof MovingPistonBlock;
    }

    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return !this.defaultBlockState().canSurvive(blockItemUseContext.getLevel(),
                blockItemUseContext.getClickedPos()) ? Blocks.SOUL_SOIL.defaultBlockState() : super.getStateForPlacement(blockItemUseContext);
    }

    public boolean useShapeForLightOcclusion(BlockState blockState) {
        return true;
    }

    public VoxelShape getShape(BlockState blockState, IBlockReader level, BlockPos blockPos, ISelectionContext selectionContext) {
        return SHAPE;
    }

    public void tick(BlockState blockState, ServerWorld level, BlockPos blockPos, Random random) {
        if (!blockState.canSurvive(level, blockPos)) {
            turnToSoulSoil(blockState, level, blockPos);
        }
    }

    public void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
        int i = pState.getValue(ENERGY);
        if (!isNearLava(pLevel, pPos)) {
            if (i > 0) {
                pLevel.setBlock(pPos, pState.setValue(ENERGY, Integer.valueOf(i - 1)), 2);
            } else if (!isUnderCrops(pLevel, pPos)) {
                turnToSoulSoil(pState, pLevel, pPos);
            }
        } else if (i < 7) {
            pLevel.setBlock(pPos, pState.setValue(ENERGY, Integer.valueOf(7)), 2);
        }

    }

    public void fallOn(World level, BlockPos blockPos, Entity entity, float dist) {
        if (!level.isClientSide && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(level, blockPos, Blocks.SOUL_SOIL.defaultBlockState(), dist, entity)) {
            turnToSoulSoil(level.getBlockState(blockPos), level, blockPos);
        }

        super.fallOn(level, blockPos, entity, dist);
    }

    public static void turnToSoulSoil(BlockState blockState, World level, BlockPos blockPos) {
        level.setBlockAndUpdate(blockPos, pushEntitiesUp(blockState, Blocks.SOUL_SOIL.defaultBlockState(), level, blockPos));
    }

    public boolean isUnderCrops(IBlockReader level, BlockPos blockPos) {
        BlockState plant = level.getBlockState(blockPos.above());
        BlockState state = level.getBlockState(blockPos);
        return plant.getBlock() instanceof net.minecraftforge.common.IPlantable &&
                NetherCropHelper.canSustainPlant(state, level, blockPos, Direction.UP, (net.minecraftforge.common.IPlantable) plant.getBlock());
    }

    public static boolean isNearLava(IWorldReader level, BlockPos blockPos) {
        for (BlockPos blockpos : BlockPos.betweenClosed(blockPos.offset(-4, 0, -4), blockPos.offset(4, 1, 4))) {
            if (level.getFluidState(blockpos).is(FluidTags.LAVA)) {
                return true;
            }
        }
        return NetherCropHelper.hasBlockLavaTicket(level, blockPos);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(ENERGY);
    }

    public boolean isPathfindable(BlockState blockState, IBlockReader level, BlockPos blockPos, PathType pathType) {
        return false;
    }
}
