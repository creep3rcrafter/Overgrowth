package com.creep3rcrafter.overgrowth.common.block;

import com.creep3rcrafter.creep3rcore.block.RisingBlock;
import com.creep3rcrafter.creep3rcore.entity.RisingBlockEntity;
import com.creep3rcrafter.creep3rcore.util.Utils;
import com.creep3rcrafter.overgrowth.common.register.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class XPBaulbBlock extends RisingBlock {
    private static final VoxelShape SHAPE = Block.box(2.0D, 2.0D, 2.0D, 14.0D, 14.0D, 14.0D);

    public XPBaulbBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    public void onPlace(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean p_220082_5_) {
        world.getBlockTicks().scheduleTick(blockPos, this, this.getDelayAfterPlace() + 10);
    }

    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, IWorld world, BlockPos blockPos, BlockPos blockPos2) {
        world.getBlockTicks().scheduleTick(blockPos, this, this.getDelayAfterPlace());
        return super.updateShape(blockState, direction, blockState2, world, blockPos, blockPos2);
    }

    @SuppressWarnings("deprecation")
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
        if (!isStem(serverWorld.getBlockState(blockPos.below())) && blockPos.getY() >= 0) {//no Stem below
            if (!isFree(serverWorld.getBlockState(blockPos.above()))) {
                //explode and drop xp
                Utils.explode(serverWorld, blockPos, 2f);
                Utils.dropXP(serverWorld, blockPos);
            } else {
                //float
                RisingBlockEntity risingBlockEntity =
                        new RisingBlockEntity(serverWorld, blockPos.getX() + 0.5D,
                                (double) blockPos.getY(), blockPos.getZ() + 0.5D, serverWorld.getBlockState(blockPos));
                this.falling(risingBlockEntity);
                serverWorld.addFreshEntity(risingBlockEntity);
            }
        } else {
            //has stem
        }
    }

    public static boolean isFree(BlockState blockState) {
        Material material = blockState.getMaterial();
        return blockState.is(Blocks.AIR.getBlock()) || blockState.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    public static boolean isStem(BlockState blockState) {
        Material material = blockState.getMaterial();
        return blockState.is(ModBlocks.XP_STEM_BLOCK.get());
    }
}
