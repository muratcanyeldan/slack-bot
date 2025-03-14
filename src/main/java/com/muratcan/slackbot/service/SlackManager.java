package com.muratcan.slackbot.service;

import com.muratcan.slackbot.strategy.SlackCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.muratcan.slackbot.constant.SlackConstant.NULL;


@Service
@RequiredArgsConstructor
public class SlackManager {

	private final SlackCommand slackCommand;
	private final SlackService slackService;

	public void process() {
		String textMessage = slackService.retrieveConversationsHistory();
		if (!NULL.equals(textMessage)) {
			try {
				slackCommand.process(textMessage);
			} catch (Exception e) {
				slackService.sendPostMessage(e.getMessage());
			}
		}
	}
}
