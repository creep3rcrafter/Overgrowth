package com.creep3rcrafter.overgrowth.common.util;

import com.creep3rcrafter.overgrowth.common.block.SoulSoilFarmlandBlock;
import com.creep3rcrafter.overgrowth.common.register.ModBlocks;
import com.google.common.base.Preconditions;
import net.minecraft.block.*;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ticket.ChunkTicketManager;
import net.minecraftforge.common.ticket.SimpleTicket;

import java.util.Map;
import java.util.WeakHashMap;

public class NetherCropHelper {


    private static final Map<IWorldReader, Map<ChunkPos, ChunkTicketManager<Vector3d>>> customLavaHandler = new WeakHashMap<>();
    public static final IntegerProperty ENERGY = IntegerProperty.create("energy", 0, 7);

    public static boolean isFertile(BlockState state, IBlockReader world, BlockPos pos) {
        if (state.is(ModBlocks.SOUL_SOIL_FARMLAND.get()))
            return state.getValue(SoulSoilFarmlandBlock.ENERGY) > 0;

        return false;
    }

    public static boolean hasBlockLavaTicket(IWorldReader world, BlockPos pos)
    {
        ChunkTicketManager<Vector3d> ticketManager = getTicketManager(new ChunkPos(pos.getX() >> 4, pos.getZ() >> 4), world);
        if (ticketManager != null)
        {
            Vector3d posAsVec3d = new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            for (SimpleTicket<Vector3d> ticket : ticketManager.getTickets()) {
                if (ticket.matches(posAsVec3d))
                    return true;
            }
        }
        return false;
    }
    private static ChunkTicketManager<Vector3d> getTicketManager(ChunkPos pos, IWorldReader world) {
        Preconditions.checkArgument(!world.isClientSide(), "Water region is only determined server-side");
        Map<ChunkPos, ChunkTicketManager<Vector3d>> ticketMap = customLavaHandler.get(world);
        if (ticketMap == null)
        {
            return null;
        }
        return ticketMap.get(pos);
    }
    public static boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable) {
        BlockState plant = plantable.getPlant(world, pos.relative(facing));

        if (plant.getBlock() == ModBlocks.XP_PLANT_BLOCK.get())
            return state.is(ModBlocks.SOUL_SOIL_FARMLAND.get());

        return false;
    }
}
