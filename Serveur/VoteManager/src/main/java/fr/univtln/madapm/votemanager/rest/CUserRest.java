package fr.univtln.madapm.votemanager.rest;

import fr.univtln.madapm.votemanager.dao.CUserDAO;
import fr.univtln.madapm.votemanager.metier.user.CUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by civars169 on 06/05/15.
 * copyright Christian
 */


@Path("/user")
public class CUserRest {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{pId}")
    public CUser read(@PathParam("pId") int pId) {
        CUserDAO lUserDAO=new CUserDAO();
        return lUserDAO.findByID(pId);
    }

    @GET
    @Path("/contact/{pIdU}")
    public Response getContacts(@PathParam("pIdU") int pIdU){
        System.out.println("getContact");
        CUserDAO lUserDAO=new CUserDAO();
        CUser lUser=lUserDAO.findByID(pIdU);

        List<CUser> lContactsUser=lUser.obtainContacts();
        lUserDAO.startTransac();
        for(CUser lContact:lContactsUser) {
            lContact.setPassword("");
        }
        List<CUser> lContacts=new ArrayList<>();
        lContacts.addAll(lContactsUser);
        lUserDAO.rollBackTransac();
        lUserDAO.update(lUser);
        return Response.status(200).entity(lContacts).build();
    }

    @PUT
    @Path("/contact/{pIdU}/{emailC}")
    public Response addContact(@PathParam("pIdU") int pIdU,@PathParam("emailC") String pEmailC){
        System.out.println("addContact");
        CUserDAO lUserDAO=new CUserDAO();
        CUser lUser=lUserDAO.findByID(pIdU);
        Map<String,String> lParams = new HashMap<>();
        lParams.put("emailUser",pEmailC);
        List<CUser> lUsers=new ArrayList<>();
        lUsers=lUserDAO.findWithNamedQuery("CUser.findAll",lParams);
        if(lUsers.isEmpty()) {
            //TODO treatment for a non-existing invited user
            return Response.status(404).build();
        }

        lUser.addContact(lUserDAO.findByID(lUsers.get(0).getUserId()));
        lUserDAO.update(lUser);
        return Response.status(200).build();
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(CUser pNewUser){
        System.out.println("addUser");
        Map<String,String> lParams = new HashMap<>();
        lParams.put("emailUser",pNewUser.getEmail());
        CUserDAO lUserDAO=new CUserDAO();
        List<CUser> lUsers=new ArrayList<>();
        lUsers=lUserDAO.findWithNamedQuery("CUser.findAll",lParams);
        if(lUsers.isEmpty()) {
            lUserDAO.create(pNewUser);
            return Response.status(201).entity(pNewUser.getUserId()).build();
        }
        else if(lUsers.get(0).getPassword()==("attente")){
            CUser lExistingUser=lUsers.get(0);
            lExistingUser.setFirstName(pNewUser.getFirstName());
            lExistingUser.setName(pNewUser.getName());
            lExistingUser.setPassword(pNewUser.getPassword());
            lUserDAO.update(lExistingUser);
            return Response.status(201).entity(lExistingUser.getUserId()).build();
        }
        return Response.status(409).entity(0).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/connect")
    public Response logUser(CUser pUser){
        System.out.println("logUser");
        Map<String,String> lParams = new HashMap<>();
        lParams.put("emailUser",pUser.getEmail());
        CUserDAO lUserDAO=new CUserDAO();
        List<CUser> lUsers=new ArrayList<>();
        lUsers=lUserDAO.findWithNamedQuery("CUser.findAll",lParams);
        if(!lUsers.isEmpty()) {
            CUser lFindedUser = lUsers.get(0);
            if ((pUser.getEmail().equals(lFindedUser.getEmail())) && (pUser.getPassword().equals(lFindedUser.getPassword())))
                return Response.status(200).entity(lFindedUser).build();

        }
        return Response.status(401).header("WWW-Authenticate", "xBasic realm=\"fake\"").build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{emailC}/{password}")
    public Response removeUser(@PathParam("emailC") String pEmail,@PathParam("password") String pPassword){
        System.out.println("removeUser");
        Map<String,String> lParams = new HashMap<>();
        lParams.put("emailUser",pEmail);
        CUserDAO lUserDAO=new CUserDAO();
        List<CUser> lUsers=lUserDAO.findWithNamedQuery("CUser.findAll",lParams);
        if(!lUsers.isEmpty()){
            CUser lUser=lUsers.get(0);
            if(lUser.getPassword().equals(pPassword)) {
                lUserDAO.deleteUser(lUser.getUserId());

                return Response.status(Response.Status.OK).entity("User has been removed").build();
            }
        }

        return Response.status(401).header("WWW-Authenticate", "xBasic realm=\"fake\"").build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/contact/{idU}/{idC}")
    public Response removeContact(@PathParam("idU") int pIdU,@PathParam("idC") int pIdC){
        System.out.println("removeContact");

        CUserDAO lUserDAO=new CUserDAO();
        CUser lUser =lUserDAO.findByID(pIdU);
        lUser.obtainContacts().remove(lUserDAO.findByID(pIdC));
        lUserDAO.update(lUser);
        return Response.status(Response.Status.OK).entity("Contact has been removed").build();
    }

}
