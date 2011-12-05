package finki.ukim.mk.services;

import finki.ukim.mk.entities.User;

public interface UserService {
	User getUser();

	boolean authenticate(String email, String password);

	void logout();
}