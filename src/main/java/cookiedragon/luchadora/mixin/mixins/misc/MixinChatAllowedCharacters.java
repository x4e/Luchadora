package cookiedragon.luchadora.mixin.mixins.misc;

import com.google.common.collect.ImmutableSet;
import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.client.AllowedCharactersEvent;
import net.minecraft.util.ChatAllowedCharacters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

/**
 * @author cookiedragon234 24/Dec/2019
 */
@Mixin(ChatAllowedCharacters.class)
public class MixinChatAllowedCharacters
{
	private static final Set<Character> serverUnallowedChars = ImmutableSet.copyOf(
		new Character[] { '/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'}
	);
	
	@Inject(method = "filterAllowedCharacters", at = @At("HEAD"), cancellable = true)
	private static void isAllowedCharWrapper(String input, CallbackInfoReturnable<String> cir)
	{
		AllowedCharactersEvent event = new AllowedCharactersEvent(AllowedCharactersEvent.State.DISALLOW);
		EventDispatcher.dispatch(event);
		
		if (event.state == AllowedCharactersEvent.State.ALLOW)
		{
			cir.setReturnValue(input);
			cir.cancel();
		}
		else if (event.state == AllowedCharactersEvent.State.ALLOW_SERVER)
		{
			StringBuilder stringbuilder = new StringBuilder();
			
			for (char c0 : input.toCharArray())
			{
				if (!serverUnallowedChars.contains(c0))
				{
					stringbuilder.append(c0);
				}
			}
			
			cir.setReturnValue(stringbuilder.toString());
			cir.cancel();
		}
	}
}
