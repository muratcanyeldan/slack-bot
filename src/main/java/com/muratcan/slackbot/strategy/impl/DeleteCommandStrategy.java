package com.muratcan.slackbot.strategy.impl;

import com.muratcan.slackbot.service.SlackService;
import com.muratcan.slackbot.strategy.SlackCommandStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.muratcan.slackbot.constant.SlackConstant.DELETE;
import static com.muratcan.slackbot.constant.SlackConstant.SUCCESSFULLY_DELETED;

@Component
@RequiredArgsConstructor
public class DeleteCommandStrategy implements SlackCommandStrategy {

	private final SlackService slackService;
	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public String getCommandName() {
		return DELETE;
	}

	@Override
	public void process(String message) {
		String key = message.replace(DELETE, "").trim();
		Boolean deleted = redisTemplate.delete(key);

		if (Boolean.TRUE.equals(deleted)) {
			slackService.sendPostMessage(SUCCESSFULLY_DELETED);
		} else {
			slackService.sendPostMessage("Key not found: " + key);
		}
	}
} 