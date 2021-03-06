package fr.univtln.m1dapm.g3.g3vote.Interface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.univtln.m1dapm.g3.g3vote.Communication.CCommunication;
import fr.univtln.m1dapm.g3.g3vote.Communication.CRequestTypesEnum;
import fr.univtln.m1dapm.g3.g3vote.Communication.CTaskParam;
import fr.univtln.m1dapm.g3.g3vote.Entite.CCandidate;
import fr.univtln.m1dapm.g3.g3vote.Entite.CType;
import fr.univtln.m1dapm.g3.g3vote.Entite.CVote;
import fr.univtln.m1dapm.g3.g3vote.R;

public class CTestActivity extends AppCompatActivity {

    private static ArrayList<CCandidate> listCandidat = new ArrayList<>();
    private CCandidatAffichageAdapter adapter;
    private String mVoteName;
    private String mDateDebut;
    private String mDateFin;
    private String mVoteType;
;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctest);


        ListView list = (ListView)findViewById(R.id.LVCandidat);
        List lListNomCandidat =new ArrayList();
        adapter = new CCandidatAffichageAdapter(this, android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
        Bundle extras = getIntent().getExtras();
        if (extras==null){
            return;
        }
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.test);
        listCandidat=(ArrayList<CCandidate>)extras.get("liste de Candidat");
        mVoteName = (String) extras.get("VOTE_NAME");
        mDateDebut = (String) extras.get("START_DATE");
        mDateFin = (String) extras.get("END_DATE");
        mVoteType=(String) extras.get("VOTE_TYPE");
        TextView lTVVoteType = (TextView) findViewById((R.id.voteType));
        TextView lTVVoteName = (TextView) findViewById(R.id.voteName);
        TextView lTVDateDebut = (TextView) findViewById(R.id.recapDateDebut);
        TextView lTVDateFin = (TextView) findViewById(R.id.recapDateFin);
        lTVVoteType.setText(mVoteType);
        lTVVoteName.setText(mVoteName);
        lTVDateDebut.setText(mDateDebut.toString());
        lTVDateFin.setText(mDateFin.toString());


        for (int i = 0; i <listCandidat.size() ; i++) {
            lListNomCandidat.add(listCandidat.get(i).getNomCandidat());
            adapter.add(listCandidat.get(i));
        }





    }


    public void validate (View view) throws ParseException {

        Timestamp lDateDeDebut = null;
        Timestamp lDateDeFin=null;

        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.FRANCE);
            java.util.Date parsedDate = dateFormat.parse(mDateDebut);
            lDateDeDebut = new java.sql.Timestamp(parsedDate.getTime());
            parsedDate=dateFormat.parse(mDateFin);
            lDateDeFin = new java.sql.Timestamp(parsedDate.getTime());
        }catch(Exception e){//this generic but you can control another types of exception

        }

        CVote lVote = new CVote(mVoteName, "", true, lDateDeDebut, lDateDeFin, 1, null, new CType(1,mVoteType,"test"), null, listCandidat, null,null);
        CTaskParam lParams = new CTaskParam(CRequestTypesEnum.add_new_vote, lVote);
        CCommunication lCom = new CCommunication();
        lCom.execute(lParams);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ctest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.websiteMenu){
            Intent lIntent = new Intent(this, CWebSiteActivity.class);
            startActivity(lIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
