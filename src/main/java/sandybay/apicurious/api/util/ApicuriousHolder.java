package sandybay.apicurious.api.util;

import net.minecraft.resources.ResourceKey;

public record ApicuriousHolder<T>(ResourceKey<T> key, T value) {

}
