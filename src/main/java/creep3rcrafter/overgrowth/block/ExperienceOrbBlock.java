package creep3rcrafter.overgrowth.block;

import creep3rcrafter.overgrowth.entity.RisingBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class ExperienceOrbBlock extends RisingBlock {

    public ExperienceOrbBlock(Properties properties) {
        super(properties);
    }

    public static boolean isFree(BlockState blockState) {
        Material material = blockState.getMaterial();
        return blockState.is(Blocks.AIR.getBlock()) || blockState.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    public static boolean isStem(BlockState blockState) {
        Material material = blockState.getMaterial();
        return blockState.is(Blocks.DIRT.getBlock());//temp dirt but should be stem
    }

    @SuppressWarnings("deprecation")
    public void onPlace(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean p_220082_5_) {
        world.getBlockTicks().scheduleTick(blockPos, this, this.getDelayAfterPlace() + 20);
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
                explode(serverWorld, blockPos, 2f);
                dropXP(serverWorld, blockPos);
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

    public void explode(World world, BlockPos blockPos) {
        world.explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 4.0F, true, Explosion.Mode.BREAK);
    }

    public void explode(World world, BlockPos blockPos, float radius) {
        world.explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), radius, true, Explosion.Mode.BREAK);
    }

    public void dropXP(World world, BlockPos blockPos) {
        int i = 3 + world.random.nextInt(400) + world.random.nextInt(400);
        while (i > 0) {
            int j = ExperienceOrbEntity.getExperienceValue(i);
            i -= j;
            world.addFreshEntity(new ExperienceOrbEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), j));
        }
    }
}
