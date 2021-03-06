package fr.univtln.madapm.votemanager.dao;


import fr.univtln.madapm.votemanager.crud.ICRUDService;
import fr.univtln.madapm.votemanager.metier.vote.CCandidate;

/**
 * Created by sebastien on 13/05/15.
 */
public interface ICandidateDAO extends ICRUDService<CCandidate>{
    public CCandidate findById(int pId);
    public void deleteCandidate(int pId);
    public CCandidate updateCandidate(CCandidate pCandidate);
    public CCandidate createCandidate(CCandidate pCandidate);
}
