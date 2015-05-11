package fr.univtln.m1dapm.g3.g3vote.Algorithme.VoteMajoritaire;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lyamsi on 09/05/15.
 */
public class TestVM {
    public static int NB_VOTANT = 21;
    public static int NB_CANDIDATE = 5;
    public static void main(String[] args) {
        // entrée
        List<CChoixVM> candidates = new ArrayList<CChoixVM>();
        List<CRegleVM> votants = new ArrayList<CRegleVM>();
        // init Entrée
        for( int i = 0 ; i < NB_CANDIDATE ; i++) {
            candidates.add(new CChoixVM("Cand0" + i));
        }
        for(int i = 0; i < NB_VOTANT ; i++){
            CRegleVM v = new CRegleVM();// on crée un votant
            for ( CChoixVM c : candidates) {//on rempli sa liste (candidate <==> note)
                Random rand = new Random();
                int note = rand.nextInt(5);// une note aléatoire pour le test
                v.addCN(c,note);
            }
            votants.add(v); // en fin on l'ajoute à la liste
        }

        CAlgoVoteMaj cAlgoVoteMaj = new CAlgoVoteMaj(candidates,votants);
        System.out.println(cAlgoVoteMaj.calculer());
    }
}