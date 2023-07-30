package me.tuhin47.auth.security.oauth2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Chinna
 * @since 26/3/18
 */
@Getter
@RequiredArgsConstructor
public enum SocialProvider {

	FACEBOOK("facebook"), TWITTER("twitter"), LINKEDIN("linkedin"), GOOGLE("google"), GITHUB("github"), LOCAL("local");

	private final String providerType;

}
