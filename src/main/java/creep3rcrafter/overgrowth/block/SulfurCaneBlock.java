package creep3rcrafter.overgrowth.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.TickPriority;
import net.minecraft.world.server.ServerWorld;

public class SulfurCaneBlock extends Block implements net.minecraftforge.common.IPlantable {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

	public SulfurCaneBlock(Properties p_i48312_1_) {
		super(p_i48312_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
	}

	public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
		debug("tick");
		if (!blockState.canSurvive(serverWorld, blockPos)) {
			serverWorld.destroyBlock(blockPos, true);
		}
	}

	public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
		if (serverWorld.isEmptyBlock(blockPos.above())) {
			int i;
			for (i = 1; serverWorld.getBlockState(blockPos.below(i)).is(this); ++i) {

			}

			if (i < 3) {
				int j = blockState.getValue(AGE);
				if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(serverWorld, blockPos, blockState, true)) {
					if (j == 15) {
						serverWorld.setBlockAndUpdate(blockPos.above(), this.defaultBlockState());
						serverWorld.setBlock(blockPos, blockState.setValue(AGE, Integer.valueOf(0)), 4);
					} else {
						serverWorld.setBlock(blockPos, blockState.setValue(AGE, Integer.valueOf(j + 1)), 4);
					}
				}
			}
		}
	}

	public BlockState updateShape(BlockState blockState1, Direction direction, BlockState blockState2, IWorld world,
			BlockPos blockPos1, BlockPos blockPos2) {
		if (!blockState1.canSurvive(world, blockPos1)) {
			//debug("b");
			 //world.getBlockTicks().scheduleTick(blockPos1, this.asBlock(), world);
			// blockState1.tick((ServerWorld) world, blockPos1, RANDOM);
			world.destroyBlock(blockPos1, true);
			 //world.getBlockTicks().scheduleTick(blockPos1, this, 1, TickPriority.VERY_HIGH);
		}

		return super.updateShape(blockState1, direction, blockState2, world, blockPos1, blockPos2);
	}

	public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
		BlockState blockstate = p_196260_2_.getBlockState(p_196260_3_.below());
		BlockState soil = p_196260_2_.getBlockState(p_196260_3_.below());
		if (canSustainPlant(soil, p_196260_2_, p_196260_3_.below(), Direction.UP, this)) {
			return true;
		}
		if (blockstate.getBlock() == this) {
			return true;
		} else {
			if (blockstate.is(Blocks.SOUL_SOIL) || blockstate.is(Blocks.SOUL_SAND)) {
				BlockPos blockpos = p_196260_3_.below();
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					FluidState fluidstate = p_196260_2_.getFluidState(blockpos.relative(direction));
					if (fluidstate.is(FluidTags.LAVA)) {
						return true;
					}
				}
			}
			return false;
		}
		/*
		 * if (blockstate.getBlock() == this) {
		 * Minecraft.getInstance().player.sendMessage(new StringTextComponent("2"),
		 * Minecraft.getInstance().player.getUUID()); return true; } else {
		 * Minecraft.getInstance().player.sendMessage(new StringTextComponent("3"),
		 * Minecraft.getInstance().player.getUUID()); if
		 * (blockstate.is(Blocks.SOUL_SAND) || blockstate.is(Blocks.SOUL_SOIL)) {
		 * BlockPos blockpos = p_196260_3_.below();
		 * Minecraft.getInstance().player.sendMessage(new StringTextComponent("3-1"),
		 * Minecraft.getInstance().player.getUUID()); for (Direction direction :
		 * Direction.Plane.HORIZONTAL) { FluidState fluidstate =
		 * p_196260_2_.getFluidState(blockpos.relative(direction)); if
		 * (fluidstate.is(FluidTags.LAVA)) {
		 * Minecraft.getInstance().player.sendMessage(new StringTextComponent("3-2"),
		 * Minecraft.getInstance().player.getUUID()); return true; } } }
		 * Minecraft.getInstance().player.sendMessage(new StringTextComponent("4"),
		 * Minecraft.getInstance().player.getUUID()); return false; }
		 */
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(AGE);
	}

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) {
		return defaultBlockState();
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing,
			net.minecraftforge.common.IPlantable plantable) {
		BlockState plant = plantable.getPlant(world, pos.relative(facing));
		boolean isSoil = state.is(Blocks.SOUL_SOIL) || state.is(Blocks.SOUL_SAND);
		boolean hasLava = false;
		for (Direction face : Direction.Plane.HORIZONTAL) {
			net.minecraft.fluid.FluidState fluidState = world.getFluidState(pos.relative(face));
			hasLava |= fluidState.is(FluidTags.LAVA);
			if (hasLava)
			break;
		}
		return isSoil && hasLava;
	}
	
	public static void debug(String string) {// i know this is a stupid way to debug but its temporary
		Minecraft.getInstance().player.sendMessage(new StringTextComponent(string), Minecraft.getInstance().player.getUUID());
	}
}
