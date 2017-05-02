package no.zandulum.wizzy.core.spells;

import no.zandulum.wizzy.core.gameobjects.Hand;
import no.zandulum.wizzy.core.gameobjects.Player;
import no.zandulum.wizzy.core.spells.projectiles.FireballProjectile;
import no.zandulum.wizzy.core.spells.projectiles.FirebombProjectile;
import no.zandulum.wizzy.core.utils.Cooldown;

public class Firebomb extends AbstractSpell {
	
	public static final float CHARGE_TARGET = 1.0f;
	
	public float charge;

	public Firebomb(Player caster, Hand hand) {
		super(caster, hand, CastType.SINGLE, new Cooldown(0.5f));
	}

	@Override
	public void onCast() {
		caster.getGameContext().spawn(new FirebombProjectile(this, charge, castingHand.getCenterX(), castingHand.getCenterY()));
	}

	@Override
	public void onCharge(float delta) {
		// Not charge spell
	}

}
