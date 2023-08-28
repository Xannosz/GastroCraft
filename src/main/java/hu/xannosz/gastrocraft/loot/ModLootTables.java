package hu.xannosz.gastrocraft.loot;

import com.mojang.serialization.Codec;
import hu.xannosz.gastrocraft.GastroCraft;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootTables {
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_TABLES =
			DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, GastroCraft.MOD_ID);

	public static final RegistryObject<Codec<? extends IGlobalLootModifier>> PEAR =
			LOOT_TABLES.register("pear_modifier", () -> PearModifier.CODEC);
	public static final RegistryObject<Codec<? extends IGlobalLootModifier>> GRAPE =
			LOOT_TABLES.register("grape_modifier", () -> GrapeModifier.CODEC);

	public static void register(IEventBus eventBus) {
		LOOT_TABLES.register(eventBus);
	}
}
