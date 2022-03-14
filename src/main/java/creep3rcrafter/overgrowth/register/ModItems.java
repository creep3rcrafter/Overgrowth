package creep3rcrafter.overgrowth.register;

import creep3rcrafter.overgrowth.Overgrowth;
import creep3rcrafter.overgrowth.item.ModFoods;
import net.minecraft.item.*;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    // Ideas Fire(Ignis), luminous - growth, Blistering, Scorching(ardore), Searing, Pyro,
    // Sparking\

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Overgrowth.MOD_ID);


    public static final RegistryObject<Item> NETHER_CARROT = ITEMS.register("nether_carrot",
            () -> new BlockNamedItem(ModBlocks.NETHER_CARROTS.get(), (new Item.Properties()).food(ModFoods.NETHER_CARROT).tab(ItemGroup.TAB_FOOD)));

    public static final RegistryObject<Item> NETHER_BEETROOT_SEEDS = ITEMS.register("nether_beetroot_seeds",
            () -> new BlockNamedItem(ModBlocks.NETHER_BEETROOTS.get(), (new Item.Properties()).tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> NETHER_POTATO = ITEMS.register("nether_potato",
            () -> new BlockNamedItem(ModBlocks.NETHER_POTATOS.get(), (new Item.Properties()).food(ModFoods.NETHER_POTATO).tab(ItemGroup.TAB_FOOD)));

    public static final RegistryObject<Item> SULFUR_CANE_ITEM = ITEMS.register("sulfur_cane",
            () -> new BlockNamedItem(ModBlocks.SULFUR_CANE_BLOCK.get(), (new Item.Properties()).tab(ItemGroup.TAB_DECORATIONS)));

    public static final RegistryObject<Item> NETHER_BEETROOT = ITEMS.register("nether_beetroot",
            () -> new Item(new Item.Properties().food(ModFoods.NETHER_BEETROOT).tab(ItemGroup.TAB_FOOD)));

    public static final RegistryObject<Item> NETHER_POISONOUS_POTATO = ITEMS.register("nether_poisonous_potato",
            () -> new Item(new Item.Properties().food(ModFoods.NETHER_POISONOUS_POTATO).tab(ItemGroup.TAB_FOOD)));

    public static final RegistryObject<Item> BAKED_NETHER_POTATO = ITEMS.register("baked_nether_potato",
            () -> new Item(new Item.Properties().food(ModFoods.BAKED_NETHER_POTATO).tab(ItemGroup.TAB_FOOD)));

    public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> SOUL_PAPER = ITEMS.register("soul_paper",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static final RegistryObject<Item> DARK_PAPER = ITEMS.register("dark_paper",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    public static final RegistryObject<Item> SOUL_BOOK = ITEMS.register("soul_book",
            () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static final RegistryObject<Item> SOUL_BOTTLE = ITEMS.register("soul_bottle",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).tab(ItemGroup.TAB_MATERIALS).stacksTo(1)){
                @Override
                public boolean isFoil(ItemStack item){
                    return true;
                }
            });


    public static final RegistryObject<Item> SOUL_FLOWER = ITEMS.register("soul_seeds",
            () -> new BlockNamedItem(ModBlocks.XP_PLANT_BLOCK.get(), (new Item.Properties()).tab(ItemGroup.TAB_DECORATIONS)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
