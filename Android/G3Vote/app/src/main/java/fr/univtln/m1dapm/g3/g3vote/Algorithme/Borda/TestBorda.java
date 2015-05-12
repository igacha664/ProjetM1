package fr.univtln.m1dapm.g3.g3vote.Algorithme.Borda;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyamsi on 12/05/15.
 */
public class TestBorda {
    public static void main(String[] args) {
        // 4 Candidats :
        CCandidatBorda A = new CCandidatBorda("A");
        CCandidatBorda B = new CCandidatBorda("B");
        CCandidatBorda C = new CCandidatBorda("C");
        CCandidatBorda D = new CCandidatBorda("D");
        // 4 clessemnt pour ce test
        List<CCandidatBorda> classement1 = new ArrayList<CCandidatBorda>();
        List<CCandidatBorda> classement2 = new ArrayList<CCandidatBorda>();
        List<CCandidatBorda> classement3 = new ArrayList<CCandidatBorda>();
        List<CCandidatBorda> classement4 = new ArrayList<CCandidatBorda>();
        // 1) ABCD 2) BCDA 3) CDBA 4) DCBA
        classement1.add(A);classement1.add(B);classement1.add(C);classement1.add(D);
        classement2.add(B);classement2.add(C);classement2.add(D);classement2.add(A);
        classement3.add(C);classement3.add(D);classement3.add(B);classement3.add(A);
        classement4.add(D);classement4.add(C);classement4.add(B);classement4.add(A);
        CAlgoBorda cAlgoBorda = new CAlgoBorda();
        // 42 ont choisis un classement ABCD
        for (int i = 0; i < 42; i++) {
            cAlgoBorda.put(classement1);
        }
        // 26 ont choisis un classement BCDA
        for (int i = 0; i < 26; i++) {
            cAlgoBorda.put(classement2);
        }
        // 15 ont choisis un classement CDBA
        for (int i = 0; i < 15; i++) {
            cAlgoBorda.put(classement3);
        }
        // 17 ont choisis un classement DCBA
        for (int i = 0; i < 17; i++) {
            cAlgoBorda.put(classement4);
        }


        // Calcule du gagnant :
        System.out.println(cAlgoBorda.borda());

    }
}
