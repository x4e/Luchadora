package cookiedragon.luchadora.kotlin

import cookiedragon.luchadora.mixin.mixins.MixinMinecraft
import cookiedragon.luchadora.mixin.mixins.misc.MixinTimer
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.MobEffects
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.EnumDifficulty
import net.minecraft.network.play.client.CPacketEntityAction
import net.minecraft.network.play.client.CPacketPlayer
import net.minecraft.util.*
import org.omg.CORBA.Object
import sun.jvm.hotspot.ui.GraphicsUtilities.getStringWidth
import kotlin.math.roundToInt


/**
 * @author cookiedragon234 10/Jan/2020
 */

private val mc = Minecraft.getMinecraft()

fun Any?.toString(): String {
	if (this == null) return "null"
	return this.toString()
}

fun getExplosionDamage(blockPos: BlockPos): Map<Entity, Float> {
	return getExplosionDamage(Vec3d(blockPos.x.toDouble() + 0.5, blockPos.y.toDouble() + 0.5, blockPos.z.toDouble() + 0.5))
}

fun getExplosionDamage(explosionLoc: Vec3d): Map<Entity, Float> {
	val out = HashMap<Entity, Float>()
	
	val size = 6f
	val doubleSize = size * 2.0
	val minX = MathHelper.floor(explosionLoc.x - doubleSize - 1.0).toDouble()
	val maxX = MathHelper.floor(explosionLoc.x + doubleSize + 1.0).toDouble()
	val minY = MathHelper.floor(explosionLoc.y - doubleSize - 1.0).toDouble()
	val maxY = MathHelper.floor(explosionLoc.y + doubleSize + 1.0).toDouble()
	val minZ = MathHelper.floor(explosionLoc.z - doubleSize - 1.0).toDouble()
	val maxZ = MathHelper.floor(explosionLoc.z + doubleSize + 1.0).toDouble()
	
	val list = mc.world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ))
	for (entity in list) {
		val damageSource = EntityDamageSource("explosion.player", mc.player).setDifficultyScaled().setExplosion()
		if (
				entity is EntityPlayer
				&&
				!entity.isImmuneToExplosions
				&&
				!entity.isEntityInvulnerable(damageSource)
				&&
				entity.health > 0
			) {
			val distance = entity.getDistance(explosionLoc.x, explosionLoc.y, explosionLoc.z) / doubleSize
			
			if (distance <= 1.0) {
				var relativeX = entity.posX - explosionLoc.x
				var relativeY = entity.posY + entity.eyeHeight.toDouble() - explosionLoc.y
				var relativeZ = entity.posZ - explosionLoc.z
				val relativeDistance = MathHelper.sqrt(relativeX * relativeX + relativeY * relativeY + relativeZ * relativeZ).toDouble()
				
				if (relativeDistance != 0.0) {
					relativeX /= relativeDistance
					relativeY /= relativeDistance
					relativeZ /= relativeDistance
					val blockDensity = (1.0 - distance) * mc.world.getBlockDensity(explosionLoc, entity.entityBoundingBox).toDouble()
					
					var damage = ((blockDensity * blockDensity + blockDensity) / 2.0 * 7.0 * doubleSize + 1.0).toInt().toFloat()
					damage = when (mc.world.difficulty) {
						EnumDifficulty.PEACEFUL -> 	0.0f
						EnumDifficulty.EASY -> 		Math.min(damage / 2.0f + 1.0f, damage)
						EnumDifficulty.HARD -> 		damage * 3.0f / 2.0f
						else -> 					damage
					}
					if (damage > 0) {
						damage = CombatRules.getDamageAfterAbsorb(
								damage,
								entity.totalArmorValue.toFloat(),
								entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS)
										.attributeValue.toFloat())
						
						if (entity.isPotionActive(MobEffects.RESISTANCE)) {
							val resistance = 25 - ((entity.getActivePotionEffect(MobEffects.RESISTANCE)!!.amplifier + 1) * 5).toFloat()
							damage = (damage * resistance) / 25.0f
						}
						
						if (damage > 0.0f) {
							val enchantments = EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), damageSource)
							
							if (enchantments > 0) {
								damage = CombatRules.getDamageAfterMagicAbsorb(damage, enchantments.toFloat())
							}
						}
						
						damage = Math.max(damage - entity.absorptionAmount, 0.0f)
						if (damage > 0) {
							out[entity] = damage
						}
					}
				}
			}
		}
	}
	return out
}

fun placeBlockMainHand(pos: BlockPos) {
	placeBlock(EnumHand.MAIN_HAND, pos)
}

fun placeBlock(hand: EnumHand, pos: BlockPos) {
	val eyesPos = Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ)
	
	for (side in EnumFacing.values()) {
		val neighbor = pos.offset(side)
		val side2 = side.opposite
		
		// check if side is visible (facing away from player)
		/*if(eyesPos.squareDistanceTo(
				new Vec3d(pos).addVector(0.5, 0.5, 0.5)) >= eyesPos
				.squareDistanceTo(
					new Vec3d(neighbor).addVector(0.5, 0.5, 0.5)))
				continue;*/
		
		// check if neighbor can be right clicked
		if (!mc.world.getBlockState(neighbor).block.canCollideCheck(mc.world.getBlockState(neighbor), false))
			continue
		
		val hitVec = Vec3d(neighbor).add(0.5, 0.5, 0.5).add(Vec3d(side2.directionVec).scale(0.5))
		
		// check if hitVec is within range (4.25 blocks)
		if (eyesPos.squareDistanceTo(hitVec) > 18.0625)
			continue
		
		// place block
		val diffX = hitVec.x - eyesPos.x
		val diffY = hitVec.y - eyesPos.y
		val diffZ = hitVec.z - eyesPos.z
		
		val diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ)
		
		val yaw = Math.toDegrees(Math.atan2(diffZ, diffX)).toFloat() - 90f
		val pitch = (-Math.toDegrees(Math.atan2(diffY, diffXZ))).toFloat()
		
		val rotations = floatArrayOf(
				mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw),
				mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch))
		
		mc.player.connection.sendPacket(CPacketPlayer.Rotation(rotations[0], rotations[1], mc.player.onGround))
		mc.player.connection.sendPacket(CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING))
		mc.playerController.processRightClickBlock(mc.player, mc.world, neighbor, side2, hitVec, hand)
		mc.player.swingArm(hand)
		mc.player.connection.sendPacket(CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING))
		
		return
	}
}

fun Entity.setRotation(yaw: Float, pitch: Float) {
	this.setPositionAndRotation(this.posX, this.posY, this.posZ, yaw, pitch)
}

fun FontRenderer.drawStringClamped(text: String, x: Float, y: Float, width: Float, color: Int): Int {
	val original = this.FONT_HEIGHT
	
	val stringWidth = getStringWidth(text)
	
	return if (stringWidth > width) {
		val scale = width / stringWidth
		GlStateManager.scale(scale, scale, 1f)
		
		val out = drawString(text, x / scale, y / scale, color)
		
		GlStateManager.scale(1 / scale, 1 / scale, 1f)
		out
	} else {
		drawString(text, x, y, color)
	}
}

fun FontRenderer.drawStringRightClamped(text: String, x: Float, y: Float, width: Float, color: Int): Int {
	var _x = x
	var _y = y
	val original = this.FONT_HEIGHT
	
	val stringWidth = getStringWidth(text)
	
	if (stringWidth > width) {
		val scale = width / stringWidth
		GlStateManager.scale(scale, scale, 1f)
		
		_x /= scale
		_y /= scale
		
		val pos = _x - getStringWidth(text)
		val out = drawString(text, pos, _y, color)
		
		GlStateManager.scale(1 / scale, 1 / scale, 1f)
		return out
	} else {
		val pos = x - getStringWidth(text)
		return drawString(text, pos, _y, color)
	}
}

fun FontRenderer.drawStringRight(text: String, x: Float, y: Float, right: Float, color: Int): Int {
	val pos = right - getStringWidth(text)
	return drawString(text, pos, y, color)
}

fun FontRenderer.drawCenteredString(text: String, x: Float, y: Float, right: Float, color: Int): Int {
	val newX = x + (right - getStringWidth(text)) / 2f
	return drawString(text, newX, y, color, false)
}

fun FontRenderer.drawString(text: String, x: Float, y: Float, color: Int): Int {
	return this.drawString(text, x, y, color, false)
}
