package ymoreau.boitier;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class HelpActivity extends Activity
{

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        String version;
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            version = "error";
        }
        String html =
                "<br/><br/>" +
                        "<p>L'application <b>Boitier code de la route</b> simule le boitier utilisé lors des examens officiels.</p>" +

                        "<br/><p><big>Les options de série</big></p>" +

                        "<p>&bull; <b>Mode pédagogique</b> : " +
                        "ce mode permet de noter la réponse comme correcte ou non après chaque question.<br/>" +
                        "Dans tous les cas, les réponses peuvent être saisies à la fin de la série.</p>" +

                        "<p>&bull; Il est possible de régler le nombre de questions de la série.<br/>" +
                        "<b>Attention</b> tout changement annule une éventuelle série en cours.</p>" +

                        "<br/><p><big>À propos</big></p>" +
                        "<p>Application développée par Yoann Moreau (moreau.yo@gmail.com).</p>" +
                        "<p>Version de l'application : " + version + "</p>" +

                        "<br/><br/>";

        TextView textView = findViewById(R.id.helpRichText);
        textView.setText(Html.fromHtml(html));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
