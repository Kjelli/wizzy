package no.zandulum.wizzy.core.spells;

import no.zandulum.wizzy.core.gameobjects.players.Hand;
import no.zandulum.wizzy.core.gameobjects.players.Player;
import no.zandulum.wizzy.core.spells.projectiles.FireballProjectile;
import no.zandulum.wizzy.core.utils.Cooldown;

public class Fireball extends AbstractSpell {

	public Fireball(Player caster, Hand hand) {
		super(caster, hand, CastType.SINGLE, new Cooldown(0.5f));
	}

	@Override
	public void onCast() {
		caster.getGameContext().spawn(new FireballProjectile(this, castingHand.getCenterX(), castingHand.getCenterY()));
	}

	@Override
	public void onCharge(float delta) {
		// Not charge spell
	}

	@Override
	protected void resetCharge() {
		// Not charge spell
	}

}
