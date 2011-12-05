package finki.ukim.mk.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import finki.ukim.mk.entities.User;
import finki.ukim.mk.services.UserService;

public class UserServiceImpl implements UserService {
	@Inject
	private Session session;

	@Inject
	private ApplicationStateManager asm;

	public User getUser() {
		return asm.getIfExists(User.class);
	}

	public boolean authenticate(String email, String password) {
		System.out.println((Long) session.createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.add(Restrictions.eq("passwordHash", password))
				.setProjection(Projections.rowCount()).uniqueResult());

		User user = (User) session.createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.add(Restrictions.eq("passwordHash", password)).uniqueResult();
		login(user);
		return true;
	}

	private void login(User user) {
		asm.set(User.class, user);
	}

	public void logout() {
		asm.set(User.class, null);
	}
}
