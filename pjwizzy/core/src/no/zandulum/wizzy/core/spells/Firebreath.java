package no.zandulum.wizzy.core.spells;

import no.zandulum.wizzy.core.gameobjects.Hand;
import no.zandulum.wizzy.core.gameobjects.Player;
import no.zandulum.wizzy.core.spells.projectiles.FirebreathProjectile;
import no.zandulum.wizzy.core.utils.Cooldown;

public class Firebreath extends AbstractSpell {

	public static final float SPREAD = 0.3f;

	public Firebreath(Player caster, Hand hand) {
		super(caster, hand, CastType.HOLD, new Cooldown(0.03f));
	}

	@Override
	public void onCast() {
		int noOfProjectiles = (int) (Math.random() * 7) + 1;
		for (int i = 0; i < noOfProjectiles; i++) {
			FirebreathProjectile fp = new FirebreathProjectile(this, castingHand.getCenterX(),
					castingHand.getCenterY());
			fp.setRotation((float) (caster.getRotation() + (Math.random() * SPREAD * 2 - SPREAD)));
			fp.velocity().x = caster.velocity().x / 2
					+ (float) (Math.cos(fp.getRotation()) * FirebreathProjectile.SPEED);
			fp.velocity().y = caster.velocity().y / 2
					+ (float) (Math.sin(fp.getRotation()) * FirebreathProjectile.SPEED);
			caster.getGameContext().spawn(fp);
		}
	}

	@Override
	public void onCharge(float delta) {
		// Not charge spell
	}

}
