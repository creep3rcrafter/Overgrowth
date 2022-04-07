package com.creep3rcrafter.overgrowth.common.block;

import com.creep3rcrafter.creep3rcore.common.block.RisingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class ActiveSoulSandBlock extends RisingBlock {

    protected static final VoxelShape SHAPE = box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    private final int dustColor;
    public ActiveSoulSandBlock(int dustColor, Properties properties) {
        super(properties);
        this.dustColor = dustColor;
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getBlockSupportShape(BlockState pState, IBlockReader pReader, BlockPos pPos) {
        return VoxelShapes.block();
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getVisualShape(BlockState pState, IBlockReader pReader, BlockPos pPos, ISelectionContext pContext) {
        return VoxelShapes.block();
    }
    public void tick(BlockState state, ServerWorld level, BlockPos pos, Random rand) {
        super.tick(state, level, pos, rand);
        BubbleColumnBlock.growColumn(level, pos.above(), false);
    }
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing == Direction.UP && pFacingState.is(Blocks.WATER)) {
            pLevel.getBlockTicks().scheduleTick(pCurrentPos, this, 20);
        }
        //super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public void onPlace(BlockState state, World level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        level.getBlockTicks().scheduleTick(pos, this, 20);
    }

    @SuppressWarnings("deprecation")
    public boolean isPathfindable(BlockState pState, IBlockReader pLevel, BlockPos pPos, PathType pType) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState state, IBlockReader level, BlockPos pos) {
        return this.dustColor;
    }
}
