package cookiedragon.luchadora.command.commands

import com.mojang.authlib.GameProfile
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.string
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.builder.RequiredArgumentBuilder.argument
import cookiedragon.luchadora.command.AbstractCommand
import cookiedragon.luchadora.mixin.mixins.entity.MixinTileEntitySkull
import cookiedragon.luchadora.mixin.mixins.netty.MixinNetHandlerPlayClient
import cookiedragon.luchadora.util.ChatUtils
import net.minecraft.client.entity.EntityOtherPlayerMP
import net.minecraft.client.network.NetworkPlayerInfo
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.thread

/**
 * @author cookiedragon234 18/Feb/2020
 */
class FakePlayerCommand: AbstractCommand() {
	override fun <T> register(dispatcher: CommandDispatcher<T>) {
		registerNodes(
			dispatcher,
			literal<T>("fakeplayer")
				.then(
					literal<T>("remove")
						.then(
							argument<T, String>("player", string())
								.executes{c -> removePlayer(getString(c, "player"))}
						)
				)
				.then(
					argument<T, String>("player", string())
						.executes{c -> createPlayer(getString(c, "player"))}
				).executes{ createPlayer("player") }
				.build()
		)
	}
	
	private fun removePlayer(name: String): Int {
		println("Removing $name")
		
		for (entity in mc.world.loadedEntityList) {
			if (entity is FakePlayer) {
				if (entity.name == name) {
					mc.world.removeEntity(entity)
					println("Removed")
					return Command.SINGLE_SUCCESS
				}
			}
		}
		return 0
	}
	
	private fun createPlayer(name: String): Int {
		if (mc.connection == null) return 0
		
		thread(true, isDaemon = true) {
			
			val compound = NBTTagCompound().also { mc.player.writeToNBT(it) }
			val cache = MixinTileEntitySkull.profileCache
				/*with(TileEntitySkull::class.java.getDeclaredField("profileCache")) {
				isAccessible = true
				get(null) as PlayerProfileCache
			}*/
			
			val profile = cache.getGameProfileForUsername(name) ?: GameProfile(null, name)
			
			if (profile.id != null) {
				val networkplayerinfo = NetworkPlayerInfo(profile)
				/*val map = with(NetHandlerPlayClient::class.java.getDeclaredField("playerInfoMap")) {
					isAccessible = true
					get(mc.connection!!) as HashMap<UUID, NetworkPlayerInfo>
				}*/
				val map = (mc.connection as MixinNetHandlerPlayClient).playerInfoHashMap
						as HashMap<UUID, NetworkPlayerInfo>
				map[profile.id] = networkplayerinfo
			}
			
			//TileEntitySkull.updateGameProfile(profile)
			
			val entity = FakePlayer(mc.world, profile)
			entity.copyLocationAndAnglesFrom(mc.player)
			compound.also {
				it.removeTag("UUID")
				it.removeTag("CustomName")
				it.removeTag("CustomNameVisible")
				entity.readFromNBT(it)
			}
			
			for (i in -25 downTo -400) {
				if (mc.world.getEntityByID(i) == null) {
					mc.addScheduledTask {
						mc.world.addEntityToWorld(i, entity)
						println("Added $i $entity")
					}
					return@thread
				}
			}
			ChatUtils.printMessage("Couldnt find available entity ID")
		}
		return Command.SINGLE_SUCCESS
	}
}

class FakePlayer(worldIn: World, gameProfileIn: GameProfile): EntityOtherPlayerMP(worldIn, gameProfileIn)
