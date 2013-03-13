package net.keplergaming.keplerbot.commands.exceptions;

@SuppressWarnings("serial")
public class CommandNotFoundException extends CommandException {

	public CommandNotFoundException(String command) {
		super(command + " not found");
		
		this.command = command;
	}

	private String command;

	public String getCommand() {
		return command;
	}
}
