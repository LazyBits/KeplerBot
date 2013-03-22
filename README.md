# What is KeplerBot?
KeplerBot is an opensource Twitch bot that can run on a vps and even your own computer. It is written in Java, using PircBotX as the backbone. This provides for an application that can be used on most operating systems.

We are doing our best to make this application easy enough for anyone. This is why we provide a simplistic GUI with a few, non-confusing buttons. 

We don't offer a command line version of our bot at this moment but this could change if requested.

# Why KeplerBot?
We started this project because we saw that all other bots on the internet were confusing and hard to use. We wanted/needed a bot that was easy to use and fully customisable by the streamers in our community.

# The commands

## Public commands

### !help
* <code>!help</code> Displays all commands that the sender can do.
* <code>!help [Command]</code> Displays the syntax of that command.

### !about
* <code>!about</code> Provides a link to this page.

### !time
* <code>!time</code> Displays the time in the bot's timezone.
* <code>!time [timezone]</code> Displays the time in that timezone. Timezones can be found [here](http://keplergaming.github.com/KeplerBot/timezones.html).

## Mod-Only commands

### !mute
* <code>!mute errors</code> Mutes all errors relating to commands (e.g., Argument is not valid, Command not found).
* <code>!mute warnings</code> Mutes all warnings relating to the filter (e.g., Don't post links, Please don't shout).
* <code>!mute all</code> Mutes all output. Errors, Warnings, Messages. This includes news and other automatic messages.

### !unmute
* <code>!unmute errors</code> Unmutes all errors.
* <code>!unmute warnings</code> Unmutes all warnings.
* <code>!unmute all</code> Unmutes all messages.

### !set
* <code>!set [command] [message]</code> Creates a new command that displays a message when the command is triggered.

### !setmod 
* <code>!setmod [command] [message]</code> Creates a new command that displays a message when the command is triggered by a moderator or the streamer.

### !unset
* <code>!unset [command]</code> Removes the given command. You can only remove user-created commands.

### !counter
* <code>!counter set [command]</code> Creates a new counter.
   * <code>!command</code> Displays the count from the counter.
   * <code>!command + n</code> Adds n to the count.
   * <code>!command - n</code> Extracts n from the count.
   * <code>!command = n</code> Sets the count to n.
* <code>!counter unset [command]</code> Removes that counter.

### !filter
* <code>!filter</code> [caps|color|links|censor] [on|off]</code> Turn that filter on/off.

### !calculate
* <code>!calculate [formula]</code> Displays the result of the formula.

### MORE COMING SOON
# Credits
* Crazyputje
* Logomaster256
* Spacerulesz

# License
KeplerBot by [KeplerGaming](http://keplergaming.com/) is licensed under a [Attribution-NonCommercial-ShareAlike 3.0 Unported](http://creativecommons.org/licenses/by-nc-sa/3.0/).  Based on a work at https://code.google.com/p/pircbotx
