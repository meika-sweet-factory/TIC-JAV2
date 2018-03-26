package controllers

import models.Response
import models.User
import services.addUser
import services.findAllUser
import services.findUserById
import services.userLogin
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.APPLICATION_JSON


@Path("user")
@Produces(APPLICATION_JSON)
class UserController {
  @GET
  @Path("{id}")
  fun getById(@PathParam("id") id: Int): Response {
    println("[trace] Open get by id user")

    val data = findUserById(id)
      ?: return Response(false, null, "This user doesn't exist in current database")

    return Response(true, findUserById(id), "")
  }

  @GET
  @Path("all")
  fun getAll(): Response {
    println("[trace] Open get all user")

    return Response(true, findAllUser(), "")
  }

  @GET
  @Path("login")
  fun login(
    @QueryParam("email") email: String,
    @QueryParam("password") password: String): Response {
    println("[trace] Login user")

    val data = userLogin(email, password)
      ?: return Response(false, null, "Login failed")

    return Response(true, data, "")
  }

  @POST
  @Consumes(APPLICATION_JSON)
  fun createUser(user: User): Response {
    println("[trace] Register user")

    val data = addUser(user)
    return Response(data, null, if (data) "" else "Register failed")
  }
}
