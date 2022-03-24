package com.creep3rcrafter.overgrowth.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.MovingPistonBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class NyliumFarmlandBlock extends FarmlandBlock {

	public NyliumFarmlandBlock(Properties Properties) {
		super(Properties);
	}
	
	public static void turnToNetherrack(BlockState blockState, World world, BlockPos blockPos) {
		world.setBlockAndUpdate(blockPos,
				pushEntitiesUp(blockState, Blocks.NETHERRACK.defaultBlockState(), world, blockPos));
	}

	@Override
	public void fallOn(World world, BlockPos blockPos, Entity entity, float fallDist) {
		if (!world.isClientSide && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(world, blockPos,
				Blocks.NETHERRACK.defaultBlockState(), fallDist, entity)) {
			turnToNetherrack(world.getBlockState(blockPos), world, blockPos);
		}
		//super.fallOn(world, blockPos, entity, fallDist); 
	}

	@Override
	public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
		if (!blockState.canSurvive(serverWorld, blockPos)) {
			turnToNetherrack(blockState, serverWorld, blockPos);
		}

	}

	@Override
	public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
		if (!isUnderCrops(serverWorld, blockPos)) {
			turnToNetherrack(blockState, serverWorld, blockPos);
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
		return !this.defaultBlockState().canSurvive(blockItemUseContext.getLevel(), blockItemUseContext.getClickedPos())
				? Blocks.NETHERRACK.defaultBlockState()
				: super.getStateForPlacement(blockItemUseContext);
	}
	@Override
	public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
		BlockState blockstate = p_196260_2_.getBlockState(p_196260_3_.above());
		return !blockstate.getMaterial().isSolid() || blockstate.getBlock() instanceof FenceGateBlock
				|| blockstate.getBlock() instanceof MovingPistonBlock;
	}
	
	private boolean isUnderCrops(IBlockReader blockReader, BlockPos blockPos) {
		BlockState plant = blockReader.getBlockState(blockPos.above());
		BlockState state = blockReader.getBlockState(blockPos);
		return plant.getBlock() instanceof net.minecraftforge.common.IPlantable && state.canSustainPlant(blockReader,
				blockPos, Direction.UP, (net.minecraftforge.common.IPlantable) plant.getBlock());
	}

}
