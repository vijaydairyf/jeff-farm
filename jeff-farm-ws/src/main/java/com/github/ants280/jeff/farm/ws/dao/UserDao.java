package com.github.ants280.jeff.farm.ws.dao;

import com.github.ants280.jeff.farm.ws.model.User;
import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.ws.rs.core.Context;
import org.springframework.jdbc.core.RowMapper;

public class UserDao extends StoredProcedureDao implements CrudDao<User>
{
	@Context private HttpServletRequest httpServletRequest;
	
	@Inject
	public UserDao(DataSource dataSource)
	{
		super(dataSource);
	}
	
	public void login(User user) throws ServletException
	{
		Principal userPrincipal1 = httpServletRequest.getUserPrincipal();
		httpServletRequest.login(user.getUserName(), user.getPassword());
		Principal userPrincipal2 = httpServletRequest.getUserPrincipal();
		HttpSession session = httpServletRequest.getSession();
		System.out.println(session.getId());
	}
	
	public void logout()
	{
		httpServletRequest.getSession().invalidate();
	}
	
	public String getContextUserName()
	{
		httpServletRequest.getSession().getId();
		String username = httpServletRequest.getUserPrincipal().getName();
		return username;
	}
	
	public User getUser()
	{
		String userName = getContextUserName();
		
		return this.executeRead(
				"read_user",
				Collections.singletonList(
						new Parameter<>(User.USER_NAME_COLUMN, userName, Types.VARCHAR)),
				new ResultSetExtractor());
	}

	@Override
	public int create(User user)
	{
		String userName = getContextUserName();
		
		return this.executeCreate(
				"create_user",
				Collections.singletonList(
						new Parameter<>(User.USER_NAME_COLUMN, userName, Types.VARCHAR)),
				User.ID_COLUMN);
	}
	
	@Override
	public User read(int id)
	{
		User user = this.getUser();
		
		if (user.getId() != id)
		{
			throw new IllegalArgumentException(String.format(
					"Cannot read user other than your own (id= %d).  "
							+ "Requested id=%d.",
					user.getId(),
					id));
		}
		
		return user;
	}

	@Override
	public List<User> readList(int parentId)
	{
		throw new IllegalArgumentException("Not allowed");
	}

	@Override
	public void update(User user)
	{
		String userName = getContextUserName();
		
		if (userName == null
				|| user == null
				|| userName.equals(user.getUserName()))
		{
			throw new IllegalArgumentException(String.format(
					"Cannot update user '%s' when logged in as user '%s'.",
					user == null ? "" : user.getUserName(),
					userName));
		}
		
		this.executeUpdate("update_user", Arrays.asList(
			new Parameter<>(User.USER_NAME_COLUMN, user.getUserName(), Types.VARCHAR),
			new Parameter<>(User.FIRST_NAME_COLUMN, user.getFirstName(), Types.VARCHAR),
			new Parameter<>(User.LAST_NAME_COLUMN, user.getLastName(), Types.VARCHAR)));
	}

	@Override
	public void delete(int id)
	{
		throw new UnsupportedOperationException("Not supported yet."); // TODO: Implement delete(UserId)
	}

	@Override
	public boolean canDelete(int id)
	{
		return true;
	}
	
	public static class ResultSetExtractor implements RowMapper<User>
	{
		@Override
		public User mapRow(ResultSet rs, int i) throws SQLException
		{
			return new User(
					rs.getInt(User.ID_COLUMN),
					rs.getString(User.USER_NAME_COLUMN),
					null, // password
					rs.getString(User.FIRST_NAME_COLUMN),
					rs.getString(User.LAST_NAME_COLUMN),
					rs.getTimestamp(User.CREATED_DATE_COLUMN),
					rs.getTimestamp(User.MODIFIED_DATE_COLUMN));
		}
	}
}
