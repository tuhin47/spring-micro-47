package com.tuhin47.service;

import me.tuhin47.config.RedisUserService;
import me.tuhin47.config.RedisUser;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuhin47.dto.LocalUser;
import com.tuhin47.model.User;
import com.tuhin47.util.GeneralUtils;

/**
 *
 * @author Chinna
 *
 */
@Service
@Primary
public class LocalUserDetailService implements UserDetailsService {

	private final UserService userService;
	private final RedisUserService redisUserService;

	public LocalUserDetailService(UserService userService, RedisUserService redisUserService) {
		this.userService = userService;
		this.redisUserService = redisUserService;
	}

	@Override
	@Transactional
	public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
		User user = userService.findUserByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User " + email + " was not found in the database");
		}

		LocalUser localUser = createLocalUser(user);

		redisUserService.saveRedisUser(RedisUser.builder()
				.userId(user.getId())
				.name(user.getDisplayName())
				.id(user.getEmail())
				.authorities(localUser.getAuthorities())
				.password(user.getPassword())
				.build());

		return localUser;
	}

	/**
	 * @param user
	 * @return
	 */
	private LocalUser createLocalUser(User user) {
		return new LocalUser(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true,
				GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()), user.getEmail());
	}
}
