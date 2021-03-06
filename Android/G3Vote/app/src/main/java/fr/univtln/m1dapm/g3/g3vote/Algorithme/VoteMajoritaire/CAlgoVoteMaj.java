package fr.univtln.m1dapm.g3.g3vote.Algorithme.VoteMajoritaire;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.univtln.m1dapm.g3.g3vote.Algorithme.AAlgorithme;
import fr.univtln.m1dapm.g3.g3vote.Entite.CCandidate;
import fr.univtln.m1dapm.g3.g3vote.Entite.CChoice;
import fr.univtln.m1dapm.g3.g3vote.Entite.CResult;
import fr.univtln.m1dapm.g3.g3vote.Entite.CVote;


public class CAlgoVoteMaj extends AAlgorithme{

    /**
     * Liste des choix faits par les participants
     */
    private List<CChoice> mChoices;

    /**
     * Liste des identifiants des candidats
     */
    private List<Integer> mIdCands;

    /**
     * Liste des notes attribués aux candidats
     */
    private List<List<Integer>> mCandVote;

    /**
     * Liste des resultats
     */
    List<CResult> mResult;

    /**
     * Nombre de vote
     */
    private int mNumbVote;

    /**
     * Constructeur de l'algo
     * @param pVote Vote pour lequel on fait le calcul
     */
    public CAlgoVoteMaj(CVote pVote) {
        super(pVote);
    }

    /**
     * Initialisation du vote
     * @param pChoices Liste des choix faits par les utilisateurs
     */
    public void initVote(List<CChoice> pChoices)
    {
        mResult = new ArrayList<>();
        mChoices = new ArrayList<>(pChoices);
        mIdCands = new ArrayList<>();

        List<CCandidate> lCands = mVote.getCandidates();

        mCandVote = new ArrayList<>();

        /// Remplis la liste des candidats
        for (int i = 0; i < lCands.size(); i++) {
            mIdCands.add(lCands.get(i).getIdCandidat());
            mCandVote.add(new ArrayList<Integer>());
        }


        mNumbVote = mChoices.size()/mIdCands.size();

        /// Remplissage de la liste des scores obtenus pour chaque candidats
        for (CChoice choice : mChoices)
            mCandVote.get(mIdCands.indexOf(choice.getIdCandidate())).add(choice.getScore());

    }

    /**
     * Calcul du resultat par la mediane des notes
     * @return Liste des vainqueurs
     */
    public List<CResult> calculateMedian() {

        if(mChoices.size() != mCandVote.size()) {
            List<Integer> lMedianeValue = new ArrayList<>();
            // Le tri
            for (List<Integer> VoteCand : mCandVote) {
                Collections.sort(VoteCand);
            }
            // recherche du vainqueur
            int lMediane = (mNumbVote + 1) / 2;
            for (List<Integer> VoteCand : mCandVote)
                lMedianeValue.add(VoteCand.get(lMediane));

            int lMax = Collections.max(lMedianeValue);

            for (int i = 0; i < lMedianeValue.size(); i++)
                //if (lMedianeValue.get(i) == lMax)
                mResult.add(new CResult(lMedianeValue.get(i), mVote.getIdVote(), mIdCands.get(i)));

            return mResult;
        }
        else {
            for (int i = 0; i < mCandVote.size(); ++i){
                mResult.add(new CResult(mChoices.get(i).getScore(), mVote.getIdVote(), mIdCands.get(i)));
            }
            return mResult;
        }
    }

    /**
     * Calcul du resultat par la moyenne de leurs notes
     * @return Liste des gagnants
     */
    public List<CResult> calculateAverage() {

        List<Double> lAverageValue = new ArrayList<>();
        // recherche du vainqueur
        double lValue;

        for (List<Integer> candVote : mCandVote) {
            lValue = 0.0;
            for (Integer value : candVote) {
                lValue += value;
            }

            lAverageValue.add(lValue/candVote.size());
        }
        double lMax = Collections.max(lAverageValue);
        // on cherche s'il y a d'autres vainqueurs qui ont la même moyenne

        for (int i = 0; i < lAverageValue.size(); i++) {
            if (lAverageValue.get(i) == lMax) {
                mResult.add(new CResult(lAverageValue.get(i).intValue(), mVote.getIdVote(), mIdCands.get(i)));
            }
        }

        return mResult;
    }

    /**
     * Calcul du resultat par la somme
     * @return Liste des gagnants
     */
    public List<CResult> calculateSum() {

        int lValue;
        List<Integer> lCandValue = new ArrayList<>();

        for (List<Integer> candVote : mCandVote) {
            lValue = 0;
            for (Integer value : candVote)
                lValue += value;

            lCandValue.add(lValue);
        }

        int lMax = Collections.max(lCandValue);


        for (int i = 0; i < lCandValue.size(); i++)
            //if (lCandValue.get(i) == lMax)
                mResult.add(new CResult(lCandValue.get(i), mVote.getIdVote(), mIdCands.get(i)));

        return mResult;
    }

}
