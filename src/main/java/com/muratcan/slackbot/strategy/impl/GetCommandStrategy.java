package com.muratcan.slackbot.strategy.impl;

import com.muratcan.slackbot.service.SlackService;
import com.muratcan.slackbot.strategy.SlackCommandStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.muratcan.slackbot.constant.SlackConstant.GET;

@Component
@RequiredArgsConstructor
public class GetCommandStrategy implements SlackCommandStrategy {

	private final SlackService slackService;
	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public String getCommandName() {
		return GET;
	}

	@Override
	public void process(String message) {
		String key = message.replace(GET, "").trim();
		String value = redisTemplate.opsForValue().get(key);

		if (value != null) {
			slackService.sendPostMessage("Value for key '" + key + "': " + value);
		} else {
			slackService.sendPostMessage("No value found for key: " + key);
		}
	}
} 