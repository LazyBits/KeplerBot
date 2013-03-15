package net.keplergaming.keplerbot.filter;

public abstract class Filter implements IFilter{

	@Override
	public boolean isDisabled() {
		return disabled;
	}

	private boolean disabled = false;

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
