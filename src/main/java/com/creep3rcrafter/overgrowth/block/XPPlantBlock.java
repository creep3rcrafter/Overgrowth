package com.creep3rcrafter.overgrowth.block;

import com.creep3rcrafter.overgrowth.register.ModBlocks;
import com.creep3rcrafter.overgrowth.util.NetherCropHelper;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class XPPlantBlock extends CropsBlock {
    public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    public static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D)};

    public Item item;

    public XPPlantBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), Integer.valueOf(0)));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader level, BlockPos blockPos, ISelectionContext context) {
        return SHAPE_BY_AGE[blockState.getValue(this.getAgeProperty())];
    }

    @Override
    public boolean mayPlaceOn(BlockState blockState, IBlockReader level, BlockPos blockPos) {
        return blockState.is(ModBlocks.SOUL_SOIL_FARMLAND.get());
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    public int getAge(BlockState pState) {
        return pState.getValue(this.getAgeProperty());
    }

    @Override
    public BlockState getStateForAge(int pAge) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), Integer.valueOf(pAge));
    }

    @Override
    public boolean isMaxAge(BlockState pState) {
        return pState.getValue(this.getAgeProperty()) >= this.getMaxAge();
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        //BlockState above = level.getBlockState(blockPos.above(3));
        return true;
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld level, BlockPos blockPos, Random random) {
        if (!level.isAreaLoaded(blockPos, 1))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        int i = this.getAge(blockState);
        if (i < this.getMaxAge()) {
            float f = getGrowthSpeed(this, level, blockPos);
            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, blockPos, blockState, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                level.setBlock(blockPos, this.getStateForAge(i + 1), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, blockPos, blockState);
            }
        }
        if (i >= getMaxAge()) {
            growPlant(level, blockPos, blockState);
        }
    }

    public static boolean isStem(World level, BlockPos blockPos) {
        BlockState blockState = level.getBlockState(blockPos);
        return blockState.is(ModBlocks.XP_STEM_BLOCK.get());
    }

    public static boolean isFree(World level, BlockPos blockPos) {
        BlockState blockState = level.getBlockState(blockPos);
        Material material = blockState.getMaterial();
        return blockState.is(Blocks.AIR.getBlock()) || blockState.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    public static boolean hasFlower(World level, BlockPos blockPos, int maxHeight) {
        boolean isTrue = false;
        for (int i = 1; i < maxHeight; i++) {
            BlockState blockState = level.getBlockState(blockPos.above(i));
            if (blockState.is(ModBlocks.XP_BULB_BLOCK.get())) {
                isTrue = true;
            }
        }
        return isTrue;
    }

    public static int stemHeight(World level, BlockPos blockPos) {
        int i = 1;
        while (isStem(level, blockPos.above(i))) {
            i++;
        }
        return i;
    }

    public void growPlant (ServerWorld level, BlockPos blockPos, BlockState blockState) {
        Random random = new Random();
        if (!hasFlower(level, blockPos, 8)) {
            if (stemHeight(level, blockPos) < 7) {
                if (stemHeight(level, blockPos) > 2) {
                    int chanceFlower = random.nextInt(10);
                    if (chanceFlower >= 8) {
                        if (isFree(level, blockPos.above(stemHeight(level, blockPos)))) {
                            level.setBlock(blockPos.above(stemHeight(level, blockPos)), ModBlocks.XP_BULB_BLOCK.get().defaultBlockState(), 2);
                        }
                    } else {
                        if (isFree(level, blockPos.above(stemHeight(level, blockPos)))) {
                            level.setBlock(blockPos.above(stemHeight(level, blockPos)), ModBlocks.XP_STEM_BLOCK.get().defaultBlockState(), 2);
                        }
                    }
                }else{
                    if (isFree(level, blockPos.above(stemHeight(level, blockPos)))) {
                        level.setBlock(blockPos.above(stemHeight(level, blockPos)), ModBlocks.XP_STEM_BLOCK.get().defaultBlockState(), 2);
                    }
                }
            }
            if (stemHeight(level, blockPos) == 7) {//setflower no matter what on top
                if (isFree(level, blockPos.above(stemHeight(level, blockPos)))) {
                    level.setBlock(blockPos.above(7), ModBlocks.XP_BULB_BLOCK.get().defaultBlockState(), 2);
                }
            }
        }else {//has flower
            //grow flower
        }

    }



    @Override
    public void growCrops(World level, BlockPos blockPos, BlockState blockState) {
        Random random = new Random();
        int i = this.getAge(blockState) + this.getBonemealAgeIncrease(level);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }
        level.setBlock(blockPos, this.getStateForAge(i), 2);
        if (i >= getMaxAge()) {
            //growPlant(level, blockPos, blockState);
        }
    }

    @Override
    public int getBonemealAgeIncrease(World level) {
        return MathHelper.nextInt(level.random, 2, 5);
    }

    public static float getGrowthSpeed(Block block, IBlockReader level, BlockPos pPos) {
        float f = 1.0F;
        BlockPos blockpos = pPos.below();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = level.getBlockState(blockpos.offset(i, 0, j));
                if (NetherCropHelper.canSustainPlant(blockstate, level, blockpos.offset(i, 0, j), net.minecraft.util.Direction.UP, (net.minecraftforge.common.IPlantable) block)) {
                    f1 = 1.0F;
                    if (NetherCropHelper.isFertile(blockstate, level, pPos.offset(i, 0, j))) {
                        f1 = 3.0F;
                    }
                }
                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }
                f += f1;
            }
        }
        BlockPos blockpos1 = pPos.north();
        BlockPos blockpos2 = pPos.south();
        BlockPos blockpos3 = pPos.west();
        BlockPos blockpos4 = pPos.east();
        boolean flag = block == level.getBlockState(blockpos3).getBlock() || block == level.getBlockState(blockpos4).getBlock();
        boolean flag1 = block == level.getBlockState(blockpos1).getBlock() || block == level.getBlockState(blockpos2).getBlock();
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = block == level.getBlockState(blockpos3.north()).getBlock() ||
                    block == level.getBlockState(blockpos4.north()).getBlock() ||
                    block == level.getBlockState(blockpos4.south()).getBlock() ||
                    block == level.getBlockState(blockpos3.south()).getBlock();
            if (flag2) {
                f /= 2.0F;
            }
        }
        return f;
    }


    @Override
    public IItemProvider getBaseSeedId() {
        return item;
    }

    @Override
    public Item asItem() {
        if (this.item == null) {
            this.item = Item.byBlock(this);
        }
        return this.item.delegate.get();
    }

    @Override
    public ItemStack getCloneItemStack(IBlockReader level, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(this.getBaseSeedId());
    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader level, BlockPos blockPos, BlockState blockState, boolean pIsClient) {
        return !this.isMaxAge(blockState);
    }

    @Override
    public boolean isBonemealSuccess(World level, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld level, Random random, BlockPos blockPos, BlockState blockState) {
        this.growCrops(level, blockPos, blockState);
    }

    @Override
    public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(AGE);
        blockBlockStateBuilder.add(CONNECTED);
    }
}
