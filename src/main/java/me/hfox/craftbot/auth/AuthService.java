package me.hfox.craftbot.auth;

import me.hfox.craftbot.exception.auth.InvalidSessionException;

public interface AuthService<T extends Session> {

	T login(String username, String password) throws Exception;

	void authenticate(T session, String serverId) throws Exception;

	T validateSession(Session session) throws InvalidSessionException;

}
