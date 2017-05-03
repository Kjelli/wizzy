package no.zandulum.wizzy.core.spells;

import no.zandulum.wizzy.core.gameobjects.players.Hand;
import no.zandulum.wizzy.core.gameobjects.players.Player;
import no.zandulum.wizzy.core.spells.projectiles.FirebombProjectile;
import no.zandulum.wizzy.core.utils.Cooldown;

public class Firebomb extends AbstractSpell {

	public static final float CHARGE_TARGET = 1.0f;

	public float charge;

	public FirebombProjectile projectile;

	public Firebomb(Player caster, Hand hand) {
		super(caster, hand, CastType.CHARGE, new Cooldown(0.5f));
	}

	@Override
	public void onCast() {

	}

	@Override
	public void onCharge(float delta) {

		if (projectile == null) {
			projectile = new FirebombProjectile(this, charge, castingHand.getCenterX() - FirebombProjectile.WIDTH / 2,
					castingHand.getCenterY() - FirebombProjectile.HEIGHT / 2);
			caster.getGameContext().spawn(projectile);
		}

		// Not charge spell
		charge += delta;
		projectile.setCharge(charge);
		System.out.println(charge);
		if (charge > CHARGE_TARGET) {
			fire();
		}

	}

	@Override
	protected void resetCharge() {
		charge = 0;
		if (projectile != null) {
			fire();
		}
	}

	protected void fire() {
		onCast();
		projectile.fire();
		getCooldown().start();
		projectile = null;
		charge = 0;
	}

}
