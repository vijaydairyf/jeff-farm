package com.github.ants280.jeff.farm.ws.resources;

import com.github.ants280.jeff.farm.ws.JeffFarmWsException;
import com.github.ants280.jeff.farm.ws.dao.LoginDao;
import com.github.ants280.jeff.farm.ws.dao.UserDao;
import com.github.ants280.jeff.farm.ws.model.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginResource
{
	private final LoginDao loginDao;
	private final UserDao userDao;

	@Inject
	public LoginResource(LoginDao loginDao, UserDao userDao)
	{
		this.loginDao = loginDao;
		this.userDao = userDao;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user)
	{
		try
		{
			loginDao.login(user);
		}
		catch (ServletException ex)
		{
			throw new JeffFarmWsException(ex.getMessage(), ex);
		}

		return Response.ok().build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("create")
	public Response createUser(User user)
	{
		int id = userDao.create(user);

		Logger.getLogger(this.getClass().getName())
			.log(Level.INFO, "Created User id={0}", id);

		return Response.ok().build();
	}
}
