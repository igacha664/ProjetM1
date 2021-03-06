package fr.univtln.m1dapm.g3.g3vote.Interface;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.univtln.m1dapm.g3.g3vote.Communication.CCommunication;
import fr.univtln.m1dapm.g3.g3vote.Communication.CRequestTypesEnum;
import fr.univtln.m1dapm.g3.g3vote.Communication.CTaskParam;
import fr.univtln.m1dapm.g3.g3vote.Entite.CVote;
import fr.univtln.m1dapm.g3.g3vote.R;

/**
 * Created by chris on 15/05/15.
 */
public class CHubMyVotesFragment extends Fragment implements AdapterView.OnItemClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static List<CVote> sVotes=new ArrayList<>();
    private static CHubMyVotesFragment sFragment;
    private static CVoteAdapter sAdapter;
    private static ListView sList;
    private static Intent sIntent;

    public static CVoteAdapter getsAdapter(){return sAdapter;}
    public List<CVote> getmVotes() {
        return sVotes;
    }

    public void setmVotes(List<CVote> pVotes) {
        this.sVotes.clear();
        for (CVote lVote:pVotes) {
            this.sVotes.add(lVote);
        }
        sAdapter.notifyDataSetChanged();
    }

    public static Intent getsIntent() {
        return sIntent;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CHubMyVotesFragment newInstance(int sectionNumber) {
        sFragment = new CHubMyVotesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        sFragment.setArguments(args);
        return sFragment;

    }

    public static CHubMyVotesFragment getInstance(){
        return sFragment;
    }
    public CHubMyVotesFragment() {
    }

    //TODO: add 2 tabs one "Participant" and one "Admin"



    //TODO: make every element of the ListView clikable, then add interface to handle the votes
    //TODO: add a button in the action bar that will refresh the content of the ListViews

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chub_myvote, container, false);
        //Récupération du composant ListView
        sList = (ListView)rootView.findViewById(R.id.lListViewMyVote);
        sList.setOnItemClickListener(this);
        //Récupération de la liste des personnes
        //ArrayList<CVote> listVote = CVote.getAListOfVote();
        //sVotes = CVote.getAListOfVote();

        //Création et initialisation de l'Adapter pour les personnes
        sAdapter = new CVoteAdapter(rootView.getContext(), sVotes);

        //Initialisation de la liste avec les données
        sList.setAdapter(sAdapter);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Intent lIntent = new Intent(getActivity(), CRankingVote.class);

        CVote lVote = (CVote) sList.getItemAtPosition(position);
        // Si le vote est actif, on envoie sur la page de vote
        if(lVote.getStatusVote() && !lVote.isVoted()) {
            if (lVote.getTypes().getNom().equals("STV") || lVote.getTypes().getNom().equals("Kemeny-Young")) {
                sIntent = new Intent(getActivity(), CRankingVote.class);
            } else if (lVote.getTypes().getNom().equals("Uninominal à 1 tour")){
                sIntent = new Intent(getActivity(), CVoteUninominal.class);
            } else {
                sIntent = new Intent(getActivity(),CNoteVote.class);
            }


            sIntent.putExtra("VOTE", lVote);
            startActivity(sIntent);
        }

        else if(lVote.getStatusVote() && lVote.isVoted()){
            // On crée le dialogue
            AlertDialog.Builder lConfirmationDialog = new AlertDialog.Builder(getActivity());
            // On modifie le titre
            lConfirmationDialog.setTitle("Vote déjà effectué");
            // On modifie le message
            lConfirmationDialog.setMessage("Vous avez déjà voté !");
            // Bouton OK: on ferme le dialogue
            lConfirmationDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            // On affiche le message
            lConfirmationDialog.show();
        }
        // Sinon, on envoie sur la page des résultats
        else {
            Log.i("Vote : ", lVote.getTypes().getNom());
            if (lVote.getTypes().getNom().equals("STV") || lVote.getTypes().getNom().equals("Kemeny-Young")) {
                sIntent = new Intent(getActivity(), CResultRankingActivity.class);
                sIntent.putExtra("VOTE", lVote);
                sIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CTaskParam lParams = new CTaskParam(CRequestTypesEnum.get_choices, lVote.getIdVote(), "rank");
                CCommunication lCom = new CCommunication();
                lCom.execute(lParams);
                //startActivityIntent();
            } else if (lVote.getTypes().getNom().equals("Uninominal à 1 tour")||lVote.getTypes().getNom().equals("Borda")) {
                sIntent = new Intent(getActivity(), CResultUninominalActivity.class);
                sIntent.putExtra("VOTE", lVote);
                sIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CTaskParam lParams = new CTaskParam(CRequestTypesEnum.get_choices, lVote.getIdVote(), "uninominal");
                CCommunication lCom = new CCommunication();
                lCom.execute(lParams);
            } else if (lVote.getTypes().getNom().equals("MAJORITY")) {
                sIntent = new Intent(getActivity(), CResultJugementMajoritaire.class);
                sIntent.putExtra("VOTE", lVote);
                sIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CTaskParam lParams = new CTaskParam(CRequestTypesEnum.get_choices, lVote.getIdVote(), "note");
                CCommunication lCom = new CCommunication();
                lCom.execute(lParams);
            } else {
                sIntent = new Intent(getActivity(), CNoteVote.class);
                sIntent.putExtra("VOTE", lVote);
                sIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivityIntent();

            }



            //startActivity(lIntent);
        }
    }

    public static void startActivityIntent(){
        CHubActivity.getsContext().startActivity(sIntent);
    }

    //met a jour le fragment quand on reviens dessus
    @Override
    public void onStart(){
        super.onStart();
        sAdapter.notifyDataSetChanged();
    }

    @Override
    public void  onResume(){
        super.onResume();
        sAdapter.notifyDataSetChanged();
    }
}
