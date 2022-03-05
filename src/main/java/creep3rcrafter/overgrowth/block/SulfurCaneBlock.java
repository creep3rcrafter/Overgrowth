package creep3rcrafter.overgrowth.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;

public class SulfurCaneBlock extends Block implements net.minecraftforge.common.IPlantable {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

	public SulfurCaneBlock(Properties p_i48312_1_) {
		super(p_i48312_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
	}

	public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
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
			world.destroyBlock(blockPos1, true);
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

	public void entityInside(BlockState blockState, World world, BlockPos blockPos, Entity entity) {
		if (entity instanceof LivingEntity && entity.getType() != EntityType.BLAZE) {
			if (!world.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
				entity.hurt(DamageSource.HOT_FLOOR, 1.0F);
			}

		}
	}
}
