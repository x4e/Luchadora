package cookiedragon.luchadora.module.impl.combat

import cookiedragon.eventsystem.Subscriber
import cookiedragon.luchadora.event.entity.UpdateWalkingPlayerEvent
import cookiedragon.luchadora.kotlin.getExplosionDamage
import cookiedragon.luchadora.kotlin.placeBlockMainHand
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.luchadora.util.Globals.mc
import cookiedragon.luchadora.util.Key
import cookiedragon.luchadora.value.values.*
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.network.play.client.CPacketHeldItemChange
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d

/**
 * @author cookiedragon234 10/Jan/2020
 */
class CrystalAuraModule : AbstractModule("Crystal Aura", "Automatically place and break crystals", Category.COMBAT) {
	private val rangeSetting = NumberValue("Range", 4f, 0, 6)
	private val minDamage = NumberValue("Min Damage", 3f, 0, 30)
	private val perTick = NumberValue("Per Tick", 1, 1, 5)
			.addCallback { oldVal, newVal -> currentTargets = arrayOfNulls(newVal.toInt())}
	
	private var currentTargets: Array<PlacePosition?> = emptyArray()
	
	override fun onEnabled() {
		currentTargets = arrayOfNulls(perTick.value.toInt())
	}
	
	override fun onDisabled() {
		currentTargets = emptyArray()
	}
	
	@Subscriber
	private fun onUpdateWalkingPlayer(event: UpdateWalkingPlayerEvent) {
		if (mc.player.inventory.getCurrentItem().item != Items.END_CRYSTAL) {
			for(i in 0..9) {
				if (mc.player.inventory.mainInventory[i].item == Items.END_CRYSTAL) {
					mc.player.inventory.currentItem = i
					mc.connection!!.sendPacket(CPacketHeldItemChange(i))
				}
			}
			if (mc.player.inventory.getCurrentItem().item != Items.END_CRYSTAL) {
				return
			}
		}
		
		val range = rangeSetting.value.toInt() + 1
		for(x in -range..range) {
			for(y in -range..range) {
				for(z in -range..range) {
					val pos = mc.player.position.add(x,y,z)
					if (mc.world.checkNoEntityCollision(AxisAlignedBB(pos))) {
						if (pos.canPlaceCrystal()) {
							for ((entity, damage) in getExplosionDamage(pos)) {
								//if (damage >= minDamage.value.toFloat()) {
								addPos(PlacePosition(damage, entity, pos))
							}
						}
					}
				}
			}
		}
		
		for (target in currentTargets) {
			if (target != null)
				placeBlockMainHand(target.blockPos)
		}
	}
	
	private fun addPos(placePosition: PlacePosition) {
		if (placePosition.blockPos.isInRange()) {
			for((index, target) in currentTargets.withIndex()) {
				if (target == null || target.damage < placePosition.damage) {
					currentTargets[index] = placePosition
				}
			}
		}
	}
	
	private fun BlockPos.isInRange(): Boolean {
		return mc.player.getDistance(this.x.toDouble(), this.y.toDouble(), this.z.toDouble()) <= rangeSetting.value.toDouble()
	}
	
	private fun BlockPos.canPlaceCrystal(): Boolean {
		val block = mc.world.getBlockState(this).block
		if (block == Blocks.OBSIDIAN || block == Blocks.BEDROCK) {
			val bottomBlock = mc.world.getBlockState(this.up())
			val topBlock = mc.world.getBlockState(this.up(2))
			if (bottomBlock.block == Blocks.AIR && topBlock.block == Blocks.AIR) {
				return true
			}
		}
		return false
	}
	
	private data class PlacePosition(val damage: Float, val entity: Entity, val blockPos: BlockPos)
}
