package no.zandulum.wizzy.core.spells;

import no.zandulum.wizzy.core.gameobjects.players.Hand;
import no.zandulum.wizzy.core.gameobjects.players.Player;
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

	public abstract void onCharge(float delta);
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
				if (cooldown.isReady()) {
					onCharge(delta);
				}
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
					cast(false);
				}
				break;
			default:
				break;

			}
		}else if(!isCasting()){
			switch (castType) {
			case CHARGE:
				resetCharge();
				break;
			case HOLD:
				break;
			case SINGLE:
				break;
			default:
				break;
			
			}
		}
	}

	protected abstract void resetCharge();

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

	public Cooldown getCooldown() {
		return cooldown;
	}

	public Hand getCastingHand() {
		return castingHand;
	}

}
