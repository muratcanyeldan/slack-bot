package com.muratcan.slackbot.strategy;

public interface SlackCommandStrategy {

	String getCommandName();

	void process(String message);
}
