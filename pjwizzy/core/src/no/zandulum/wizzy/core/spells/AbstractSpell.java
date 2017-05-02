package no.zandulum.wizzy.core.spells;

import no.zandulum.wizzy.core.gameobjects.Hand;
import no.zandulum.wizzy.core.gameobjects.Player;
import no.zandulum.wizzy.core.utils.Cooldown;

public abstract class AbstractSpell {

	public enum CastType {
		SINGLE, HOLD, CHARGE
	}

	protected Player caster;
	protected Hand castingHand;
	protected CastType castType;

	private Cooldown cooldown;
	private boolean casting;

	public AbstractSpell(Player caster, Hand castingHand, CastType castType, Cooldown cooldown) {
		this.castingHand = castingHand;
		this.setCaster(caster);
		this.setCastType(castType);
		this.cooldown = cooldown;
	}

	public abstract void onCast();

	public CastType getCastType() {
		return castType;
	}

	public void setCastType(CastType castType) {
		this.castType = castType;
	}

	public void update(float delta) {
		if (!cooldown.isReady()) {
			cooldown.update(delta);
		}

		if (isCasting()) {
			switch (castType) {
			case CHARGE:
				break;
			case HOLD:
				if (cooldown.isReady()) {
					cooldown.start();
					onCast();
				}
				break;
			case SINGLE:
				if (cooldown.isReady()) {
					cooldown.start();
					onCast();
				}
				break;
			default:
				break;

			}
		}
	}

	public void cast(boolean casting) {
		this.casting = casting;
	}

	public boolean isCasting() {
		return casting;
	}

	public Player getCaster() {
		return caster;
	}

	public void setCaster(Player caster) {
		this.caster = caster;
	}

}
