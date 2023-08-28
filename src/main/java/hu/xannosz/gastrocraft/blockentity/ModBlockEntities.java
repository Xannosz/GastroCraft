package hu.xannosz.gastrocraft.blockentity;

import hu.xannosz.gastrocraft.GastroCraft;
import hu.xannosz.gastrocraft.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
			DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GastroCraft.MOD_ID);

	public static final RegistryObject<BlockEntityType<MillBlockEntity>> MILL_ENTITY =
			BLOCK_ENTITIES.register("mill_entity", () ->
					BlockEntityType.Builder.of(
							MillBlockEntity::new,
							ModBlocks.MILL.get()).build(null));
	public static final RegistryObject<BlockEntityType<DryerBlockEntity>> DRYER_ENTITY =
			BLOCK_ENTITIES.register("dryer_entity", () ->
					BlockEntityType.Builder.of(
							DryerBlockEntity::new,
							ModBlocks.DRYER.get()).build(null));

	public static final RegistryObject<BlockEntityType<PressBlockEntity>> PRESS_ENTITY =
			BLOCK_ENTITIES.register("press_entity", () ->
					BlockEntityType.Builder.of(
							PressBlockEntity::new,
							ModBlocks.PRESS.get()).build(null));

	public static final RegistryObject<BlockEntityType<WineBarrelBlockEntity>> WINE_BARREL_ENTITY =
			BLOCK_ENTITIES.register("wine_barrel_entity", () ->
					BlockEntityType.Builder.of(
							WineBarrelBlockEntity::new,
							ModBlocks.WINE_BARREL.get()).build(null));

	public static final RegistryObject<BlockEntityType<DistillerBlockEntity>> DISTILLER_ENTITY =
			BLOCK_ENTITIES.register("distiller_entity", () ->
					BlockEntityType.Builder.of(
							DistillerBlockEntity::new,
							ModBlocks.DISTILLER.get()).build(null));

	public static void register(IEventBus eventBus) {
		BLOCK_ENTITIES.register(eventBus);
	}
}
