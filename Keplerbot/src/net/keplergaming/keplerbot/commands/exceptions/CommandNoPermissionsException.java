package net.keplergaming.keplerbot.commands.exceptions;

import net.keplergaming.keplerbot.commands.ICommand;

import org.pircbotx.User;

@SuppressWarnings("serial")
public class CommandNoPermissionsException extends CommandException {

	public CommandNoPermissionsException(User sender, ICommand command) {
		super(sender.getNick() + " can not use command " + command.getCommandName());
		
		this.sender = sender;
		this.command = command;
	}

	private User sender;
	private ICommand command;

	public User getSender() {
		return sender;
	}

	public ICommand getCommand() {
		return command;
	}
}
