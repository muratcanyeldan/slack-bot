package com.muratcan.slackbot.strategy.impl;

import com.muratcan.slackbot.service.SlackService;
import com.muratcan.slackbot.strategy.SlackCommandStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.muratcan.slackbot.constant.SlackConstant.CACHE_IS_EMPTY;
import static com.muratcan.slackbot.constant.SlackConstant.LIST_ALL;

@Component
@RequiredArgsConstructor
public class ListAllCommandStrategy implements SlackCommandStrategy {

	private final SlackService slackService;
	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public String getCommandName() {
		return LIST_ALL;
	}

	@Override
	public void process(String message) {
		Set<String> keys = redisTemplate.keys("*");

		if (keys != null && !keys.isEmpty()) {
			StringBuilder response = new StringBuilder("Available keys:\n");
			for (String key : keys) {
				response.append("- ").append(key).append("\n");
			}
			slackService.sendPostMessage(response.toString());
		} else {
			slackService.sendPostMessage(CACHE_IS_EMPTY);
		}
	}
} 