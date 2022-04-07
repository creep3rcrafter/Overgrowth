package com.creep3rcrafter.overgrowth.common.register;

import com.creep3rcrafter.overgrowth.Overgrowth;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, Overgrowth.MOD_ID);

    public static final RegistryObject<Potion> LUMINOUS_POTION = POTIONS.register("luminous",
            () -> new Potion("luminous", new EffectInstance(ModEffects.LUMINOUS.get(),4800, 8)));
    public static final RegistryObject<Potion> LONG_LUMINOUS_POTION = POTIONS.register("long_luminous",
            () -> new Potion("luminous", new EffectInstance(ModEffects.LUMINOUS.get(),9600, 8)));
    public static final RegistryObject<Potion> STRONG_LUMINOUS_POTION = POTIONS.register("strong_luminous",
            () -> new Potion("luminous", new EffectInstance(ModEffects.LUMINOUS.get(),1200, 15)));

    public static final RegistryObject<Potion> EXPLOSIVE_POTION = POTIONS.register("explosive",
            () -> new Potion("explosive", new EffectInstance(ModEffects.EXPLOSIVE.get(),1, 3)));
    public static final RegistryObject<Potion> STRONG_EXPLOSIVE_POTION = POTIONS.register("strong_explosive",
            () -> new Potion("explosive", new EffectInstance(ModEffects.EXPLOSIVE.get(),1, 5)));

    public static final RegistryObject<Potion> THUNDEROUS_POTION = POTIONS.register("thunderous",
            () -> new Potion("thunderous", new EffectInstance(ModEffects.THUNDEROUS.get(),40)));
    public static final RegistryObject<Potion> LONG_THUNDEROUS_POTION = POTIONS.register("long_thunderous",
            () -> new Potion("thunderous", new EffectInstance(ModEffects.THUNDEROUS.get(),120)));
    public static final RegistryObject<Potion> STRONG_THUNDEROUS_POTION = POTIONS.register("strong_thunderous",
            () -> new Potion("thunderous", new EffectInstance(ModEffects.THUNDEROUS.get(),40, 5)));

    public static final RegistryObject<Potion> OXIDATION_POTION = POTIONS.register("oxidation",
            () -> new Potion("oxidation", new EffectInstance(ModEffects.OXIDATION.get(),800, 1)));
    public static final RegistryObject<Potion> LONG_OXIDATION_POTION = POTIONS.register("long_oxidation",
            () -> new Potion("oxidation", new EffectInstance(ModEffects.OXIDATION.get(),1600, 1)));
    public static final RegistryObject<Potion> STRONG_OXIDATION_POTION = POTIONS.register("strong_oxidation",
            () -> new Potion("oxidation", new EffectInstance(ModEffects.OXIDATION.get(),100, 5)));

    public static final RegistryObject<Potion> NULLIFIER_POTION = POTIONS.register("nullifier",
            () -> new Potion("nullifier", new EffectInstance(ModEffects.NULLIFIER.get(),3600, 1)));
    public static final RegistryObject<Potion> LONG_NULLIFIER_POTION = POTIONS.register("long_nullifier",
            () -> new Potion("nullifier", new EffectInstance(ModEffects.NULLIFIER.get(),9600, 1)));
    public static final RegistryObject<Potion> STRONG_NULLIFIER_POTION = POTIONS.register("strong_nullifier",
            () -> new Potion("nullifier", new EffectInstance(ModEffects.NULLIFIER.get(),2400, 2)));

    public static final RegistryObject<Potion> REVIVAL_POTION = POTIONS.register("revival",
            () -> new Potion("revival", new EffectInstance(ModEffects.REVIVAL.get(),3600, 1)));
    public static final RegistryObject<Potion> LONG_REVIVAL_POTION = POTIONS.register("long_revival",
            () -> new Potion("revival", new EffectInstance(ModEffects.REVIVAL.get(),9600, 1)));

    public static final RegistryObject<Potion> INVERSON_POTION = POTIONS.register("inversion",
            () -> new Potion("inversion", new EffectInstance(ModEffects.INVERSION.get(),1, 1)));



    ///give Dev potion{Potion:"overgrowth:luminous"} 1
    ///give Dev potion{Potion:"overgrowth:long_luminous"} 1
    ///give Dev potion{Potion:"overgrowth:strong_luminous"} 1
}
