package com.muratcan.slackbot.strategy;

import com.muratcan.slackbot.service.SlackService;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.muratcan.slackbot.constant.SlackConstant.*;

@Service
public class SlackCommand {

	private final Map<String, SlackCommandStrategy> slackCommandStrategyMap = new HashMap<>();
	private final SlackService slackService;

	public SlackCommand(ListableBeanFactory beanFactory, SlackService slackService) {
		this.slackService = slackService;
		beanFactory.getBeansOfType(SlackCommandStrategy.class)
				.values()
				.forEach(service -> slackCommandStrategyMap.put(service.getCommandName().trim(), service));
	}

	public void process(String commandName) {
		try {
			if (commandName.startsWith(GET) || commandName.startsWith(SET) ||
					commandName.startsWith(DELETE)) {
				processCommand(commandName);
			} else {
				processSimpleCommand(commandName);
			}
		} catch (Exception e) {
			slackService.sendPostMessage("Error processing command: " + e.getMessage());
		}
	}

	private void processCommand(String commandName) {
		String command = commandName.split("\\s+")[0];
		switch (command) {
			case GET -> slackCommandStrategyMap.get(GET).process(commandName);
			case SET -> slackCommandStrategyMap.get(SET).process(commandName);
			case DELETE -> slackCommandStrategyMap.get(DELETE).process(commandName);
			default -> slackService.sendPostMessage("Unknown command. Type HELP for available commands.");
		}
	}

	private void processSimpleCommand(String commandName) {
		switch (commandName) {
			case HELP -> slackService.sendPostMessage(HELP_MESSAGE);
			case PING -> slackService.sendPostMessage(PING_MESSAGE);
			case LIST_ALL -> slackCommandStrategyMap.get(LIST_ALL).process(commandName);
			default -> slackService.sendPostMessage("Unknown command. Type HELP for available commands.");
		}
	}
}
