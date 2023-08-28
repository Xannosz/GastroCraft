package hu.xannosz.gastrocraft.screen;

import hu.xannosz.gastrocraft.GastroCraft;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENUS =
			DeferredRegister.create(ForgeRegistries.MENU_TYPES, GastroCraft.MOD_ID);

	public static final RegistryObject<MenuType<MillMenu>> MILL_MENU =
			registerMenuType(MillMenu::new, "mill_menu");
	public static final RegistryObject<MenuType<DryerMenu>> DRYER_MENU =
			registerMenuType(DryerMenu::new, "dryer_menu");
	public static final RegistryObject<MenuType<PressMenu>> PRESS_MENU =
			registerMenuType(PressMenu::new, "press_menu");

	public static final RegistryObject<MenuType<WineBarrelMenu>> WINE_BARREL_MENU =
			registerMenuType(WineBarrelMenu::new, "wine_barrel_menu");
	public static final RegistryObject<MenuType<DistillerMenu>> DISTILLER_MENU =
			registerMenuType(DistillerMenu::new, "distiller_menu");

	private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
																								  String name) {
		return MENUS.register(name, () -> IForgeMenuType.create(factory));
	}

	public static void register(IEventBus eventBus) {
		MENUS.register(eventBus);
	}
}
