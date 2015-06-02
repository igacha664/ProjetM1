package fr.univtln.madapm.votemanager.rest;

import fr.univtln.madapm.votemanager.dao.CChoiceDAO;
import fr.univtln.madapm.votemanager.metier.vote.CChoice;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sebastien on 18/05/15.
 */
@Path("/choice")
public class CChoiceRest {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{pId}")
    public CChoice readChoice(@PathParam("pId") int pId){
        CChoiceDAO lChoiceDAO = new CChoiceDAO();
        return lChoiceDAO.findById(pId);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response addChoice(CChoice pChoice){
        CChoiceDAO lChoiceDAO = new CChoiceDAO();
        lChoiceDAO.createChoice(pChoice);
        return Response.status(200).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteChoice(@PathParam("pId") int pId){
        CChoiceDAO lChoiceDAO = new CChoiceDAO();
        lChoiceDAO.deleteChoice(pId);
        return Response.status(Response.Status.NO_CONTENT)// 204
                .entity("Choice has been removed").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public CChoice updateChoice(CChoice pChoice){
        CChoiceDAO lChoiceDAO = new CChoiceDAO();
        return lChoiceDAO.updateChoice(pChoice);
    }
}
