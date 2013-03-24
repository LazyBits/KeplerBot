package net.keplergaming.keplerbot.config;

public enum ConfigConstants {

	USERNAME("twitch_name", " "),
	PASSWORD("twitch_password", " "),
	BOT_NAME("bot_name", "KeplerBot"),
	JOIN_MESSAGE("join_message", "%s has joined your stream"),
	LEAVE_MESSAGE("leave_message", "%s has left your stream"),
	MUTE_ALL("mute_all", false),
	MUTE_WARNINGS("mute_warnings", false),
	MUTE_ERRORS("mute_errors", false),
	PROXY("proxy", false),
	PROXYSERVER("proxy_server", "0.0.0.0:1080");

	private ConfigConstants(String key, Object defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}

	public String getKey() {
		return key;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	private String key;
	private Object defaultValue;
}
