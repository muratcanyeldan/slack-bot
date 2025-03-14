package com.muratcan.slackbot.strategy.impl;

import com.muratcan.slackbot.service.SlackService;
import com.muratcan.slackbot.strategy.SlackCommandStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.muratcan.slackbot.constant.SlackConstant.SET;
import static com.muratcan.slackbot.constant.SlackConstant.SUCCESSFULLY_ADDED;

@Component
@RequiredArgsConstructor
public class SetCommandStrategy implements SlackCommandStrategy {

	private final SlackService slackService;
	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public String getCommandName() {
		return SET;
	}

	@Override
	public void process(String message) {
		String command = message.replace(SET, "").trim();
		String[] parts = command.split("\\s+", 3);

		if (parts.length < 2) {
			slackService.sendPostMessage("Invalid SET command. Format: SET key value [ttl]");
			return;
		}

		String key = parts[0];
		String value = parts[1];

		try {
			if (parts.length == 3) {
				long ttl = Long.parseLong(parts[2]);
				redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
				slackService.sendPostMessage(SUCCESSFULLY_ADDED + " (expires in " + ttl + " seconds)");
			} else {
				redisTemplate.opsForValue().set(key, value);
				slackService.sendPostMessage(SUCCESSFULLY_ADDED);
			}
		} catch (NumberFormatException e) {
			slackService.sendPostMessage("Invalid TTL value. Must be a number in seconds.");
		}
	}
} 