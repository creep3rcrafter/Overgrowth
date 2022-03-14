package creep3rcrafter.overgrowth.block;

import creep3rcrafter.overgrowth.register.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.BooleanProperty;
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

public class XPStemBlock extends Block {
    public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");
    private static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 15.0D, 10.0D);

    public XPStemBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState blockState1, Direction direction, BlockState blockState2, IWorld world,  BlockPos blockPos1, BlockPos blockPos2) {
        if (!blockState1.canSurvive(world, blockPos1)) {
            world.destroyBlock(blockPos1, true);
        }
        return super.updateShape(blockState1, direction, blockState2, world, blockPos1, blockPos2);
    }
    @SuppressWarnings("deprecation")
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
        if (!blockState.canSurvive(serverWorld, blockPos)) {
            serverWorld.destroyBlock(blockPos, true);
        }
    }

    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState blockState, IWorldReader world, BlockPos blockPos) {
        BlockState blockstate = world.getBlockState(blockPos.below());
        if (isStemOrPlant(blockstate)){
            return true;
        }
        return false;
    }
    public static Boolean somethingAbove (BlockState blockState){
        return blockState.is(Blocks.AIR);
    }

    public static boolean isStemOrPlant(BlockState blockState) {
        if (blockState.is(ModBlocks.XP_STEM_BLOCK.get())){
            return true;
        }
        if (blockState.is(ModBlocks.XP_PLANT_BLOCK.get()) || blockState.getBlock() instanceof XPPlantBlock){
            if (((XPPlantBlock)blockState.getBlock()).getAge(blockState) == ((XPPlantBlock) blockState.getBlock()).getMaxAge()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(CONNECTED);
    }
}
