package hu.xannosz.gastrocraft;

import hu.xannosz.gastrocraft.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GastroCraft.MOD_ID);

	public static final RegistryObject<CreativeModeTab> GASTRO_CRAFT_TAB = CREATIVE_MODE_TABS.register("gastrocrafttab",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.WHEAT))
					.title(Component.translatable("creativetab.gastrocrafttab"))
					.displayItems((pParameters, pOutput) -> {
						ModItems.ITEMS.getEntries().forEach(
								i -> {
									pOutput.accept(i.get());
								}
						);
					})
					.build());


	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}
}
