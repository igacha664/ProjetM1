package fr.univtln.m1dapm.g3.g3vote.Interface;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import fr.univtln.m1dapm.g3.g3vote.Entite.CVote;
import fr.univtln.m1dapm.g3.g3vote.R;

/**
 * Created by chris on 18/05/15.
 */
public class CVoteAdapter extends BaseAdapter {

    //A list of vote
    private List<CVote> mListVote;
    //le contexte dans lequel est présent notre adapter
    private Context mContext;
    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    //constructeur
    public CVoteAdapter(Context context, List<CVote> listVote){
        this.mContext = context;
        this.mListVote = listVote;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListVote.size();
    }

    @Override
    public Object getItem(int position) {
        return mListVote.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem;
        //re-use of layout
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML "vote_layout.xml"
            layoutItem = (RelativeLayout) mInflater.inflate(R.layout.vote_layout, parent, false);
        } else {
            layoutItem = (RelativeLayout) convertView;
        }

        if (position%2==0){
            layoutItem.setBackgroundColor(-7829368);//gris clair
        }

        //(2) : Récupération des TextView de notre layout
        TextView ltv_voteName = (TextView)layoutItem.findViewById(R.id.lTV_voteName);
        TextView ltv_voteStatus = (TextView)layoutItem.findViewById(R.id.lTV_voteStatus);
        TextView ltv_beginDate = (TextView)layoutItem.findViewById(R.id.lTV_beginDate);
        TextView ltv_endDate = (TextView)layoutItem.findViewById(R.id.lTV_endDate);
        ImageView liv_icon = (ImageView)layoutItem.findViewById(R.id.icon);

        if(mListVote.get(position).isVoted()){
            liv_icon.setImageResource(R.mipmap.ic_yes_vote);
        }
        else{
            liv_icon.setImageResource(R.mipmap.ic_no_vote);
        }
        //(3) : Renseignement des valeurs
        ltv_voteName.setText(mListVote.get(position).getVoteName());
        if (mListVote.get(position).getStatusVote()){
            ltv_voteStatus.setText("En Cours");
            ltv_voteStatus.setTextColor(Color.parseColor("green"));
        }
        else{
            ltv_voteStatus.setText("Fini");
            ltv_voteStatus.setTextColor(Color.parseColor("red"));
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.FRANCE);
        ltv_beginDate.setText(df.format(mListVote.get(position).getDateDebut()));
        ltv_endDate.setText(df.format(mListVote.get(position).getDateFin()));


        //On retourne l'item créé.
        return layoutItem;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 500;
    }
}
