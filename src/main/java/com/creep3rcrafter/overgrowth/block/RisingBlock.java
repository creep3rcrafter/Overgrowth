package com.creep3rcrafter.overgrowth.block;

import com.creep3rcrafter.overgrowth.entity.RisingBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class RisingBlock extends Block {
    public RisingBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    public void onPlace(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean p_220082_5_) {
        world.getBlockTicks().scheduleTick(blockPos, this, this.getDelayAfterPlace());
    }

    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, IWorld world, BlockPos blockPos, BlockPos blockPos2) {
        world.getBlockTicks().scheduleTick(blockPos, this, this.getDelayAfterPlace());
        return super.updateShape(blockState, direction, blockState2, world, blockPos, blockPos2);
    }

    @SuppressWarnings("deprecation")
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
        if (serverWorld.isEmptyBlock(blockPos.above()) || isFree(serverWorld.getBlockState(blockPos.above())) && blockPos.getY() >= 0) {
            RisingBlockEntity risingBlockEntity = new RisingBlockEntity(serverWorld, blockPos.getX() + 0.5D, (double) blockPos.getY(), blockPos.getZ() + 0.5D, serverWorld.getBlockState(blockPos));
            this.falling(risingBlockEntity);
            serverWorld.addFreshEntity(risingBlockEntity);
        }
    }

    public static boolean isFree(BlockState blockState) {
        Material material = blockState.getMaterial();
        return blockState.is(Blocks.AIR.getBlock()) || blockState.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    public int getDelayAfterPlace() {
        return 2;
    }

    public void onLand(World world, BlockPos blockPos, BlockState blockState, BlockState blockState2, RisingBlockEntity risingBlockEntity) {

    }

    public void onBroken(World world, BlockPos blockPos, RisingBlockEntity risingBlockEntity) {
    }

    public void falling(RisingBlockEntity risingBlockEntity) {

    }

}
