package creep3rcrafter.overgrowth.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class ExperienceOrbStemBlock extends Block {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
    public ExperienceOrbStemBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
        if (!blockState.canSurvive(serverWorld, blockPos)) {
            serverWorld.destroyBlock(blockPos, true);
        }
    }
    @SuppressWarnings("deprecation")
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

}
