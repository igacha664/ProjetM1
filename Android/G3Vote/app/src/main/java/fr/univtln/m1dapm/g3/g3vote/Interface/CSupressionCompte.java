package fr.univtln.m1dapm.g3.g3vote.Interface;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import fr.univtln.m1dapm.g3.g3vote.R;

public class CSupressionCompte extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csupression_compte);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_csupression_compte, menu);
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

        return super.onOptionsItemSelected(item);
    }



    public void suprimer (View view) {
        //changer destination pour suprimer le compte
        final EditText lET_Mail = (EditText)findViewById(R.id.mailInputsupression);
        final EditText lET_Password = (EditText)findViewById(R.id.passInputsupression);
        final String lMail = lET_Mail.getText().toString();
        final String lPassword = lET_Password.getText().toString();
        if (!(lET_Mail.getText().toString().isEmpty())) {

            final Intent lIntent = new Intent(this, CSupressionCompte.class);
            lIntent.putExtra("sup_mail",lET_Mail.getText().toString());
            lIntent.putExtra("sup_mdr",lET_Password.getText().toString());
            // On cr�e le dialogue
            AlertDialog.Builder lConfirmationDialog = new AlertDialog.Builder(CSupressionCompte.this);
            // On modifie le titre
            lConfirmationDialog.setTitle("suprimer compte");
            // On modifie le message
            lConfirmationDialog.setMessage("Voulez-vous suprimez votre compte ?");
            // Bouton Oui
            lConfirmationDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //supression du compte

                    startActivity(lIntent);

                }
            });
        }
    }
}
