package com.creep3rcrafter.overgrowth.util;

import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class Utils {
    public static void explode(World world, BlockPos blockPos) {
        explode(world, blockPos, 4f);
    }

    public static void explode(World world, BlockPos blockPos, float radius) {
        explode(world, blockPos, radius, false);
    }

    public static void explode(World world, BlockPos blockPos, float radius, boolean fire) {
        world.explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), radius, fire, Explosion.Mode.BREAK);
    }

    public static void dropXP(World world, BlockPos blockPos) {
        int i = 3 + world.random.nextInt(5) + world.random.nextInt(5);
        while (i > 0) {
            int j = ExperienceOrbEntity.getExperienceValue(i);
            i -= j;
            world.addFreshEntity(new ExperienceOrbEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), j));
        }
    }
}
