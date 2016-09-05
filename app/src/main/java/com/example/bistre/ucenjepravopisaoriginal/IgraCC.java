package com.example.bistre.ucenjepravopisaoriginal;

/**
 * Created by Bistre on 4.9.2016..
 */


import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class IgraCC extends AppCompatActivity {

    DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igra_cc);

        dbHelper = new DataBaseHelper(this);

        try {
            dbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ImageButton buttonT = (ImageButton) findViewById(R.id.tocnoCC);
        final ImageButton buttonN = (ImageButton) findViewById(R.id.netocnoCC);
        final Button ponovno = (Button) findViewById(R.id.ponovnoCC);
        final Button izlaz = (Button) findViewById(R.id.izlazCC);
        final Button pocetak = (Button) findViewById(R.id.pocetakCC);

        ponovno.setVisibility(View.GONE);
        izlaz.setVisibility(View.GONE);
        pocetak.setVisibility(View.GONE);

        izlaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });


        final Random rand = new Random();
        final TextView tv1 = (TextView)findViewById(R.id.rijecCC);
        final TextView tvBodovi = (TextView) findViewById(R.id.bodoviCC);
        final int brojRijeci = (int) dbHelper.getNumberofDataCC();
        final TextView GameOver = (TextView) findViewById(R.id.GameOverCC);

        final MediaPlayer correct_buttonCC = MediaPlayer.create(this, R.raw.correct_sound);
        final MediaPlayer wrong_buttonCC = MediaPlayer.create(this, R.raw.wrong_sound);


        final ArrayList<String> TocneRijeci = new ArrayList<>();
        final ArrayList<String> NetocneRijeci = new ArrayList<>();
        List<Rijec> listaRijeci = dbHelper.sveRijeciCC();

        for (Rijec val : listaRijeci) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("RijecT", val.getTocno());
            TocneRijeci.add(val.getTocno());
            NetocneRijeci.add(val.getNetocno());
        }

        final boolean tocno = rand.nextBoolean();

        if(tocno) {
            String rijec = TocneRijeci.get(0);
            tv1.setText(rijec);
        }
        else {
            String rijec = NetocneRijeci.get(0);
            tv1.setText(rijec);
        }

        View.OnClickListener listener = new View.OnClickListener() {

            int i = 1;
            int brojac = 1;
            int bodovi = 0;
            @Override
            public void onClick(View view) {


                if (brojac >= brojRijeci) {
                    if (view.equals(buttonT)) {
                        if (tv1.getText().toString().equals(TocneRijeci.get(i - 1))) {
                            bodovi++;
                            correct_buttonCC.start();
                        }
                        else {
                            wrong_buttonCC.start();
                        }
                    }
                    else if (view.equals(buttonN)) {
                        if (tv1.getText().toString().equals(NetocneRijeci.get(i-1))) {
                            bodovi++;
                            tvBodovi.setText("" + bodovi);
                            correct_buttonCC.start();
                        }
                        else {
                            wrong_buttonCC.start();
                        }
                    }
                    tvBodovi.setVisibility(View.GONE);
                    tv1.setVisibility(View.GONE);
                    buttonT.setVisibility(View.GONE);
                    buttonN.setVisibility(View.GONE);
                    ponovno.setVisibility(View.VISIBLE);
                    izlaz.setVisibility(View.VISIBLE);
                    pocetak.setVisibility(View.VISIBLE);

                    GameOver.setText("Gotovo!" + "\nKonačni bodovi: " + bodovi + "/" + brojRijeci);
                }
                else  {

                    if (view.equals(buttonT)) {

                        if (tv1.getText().toString().equals(TocneRijeci.get(i-1))) {
                            bodovi++;
                            tvBodovi.setText("Bodovi: " + bodovi);

                            correct_buttonCC.start();

                        }
                        else {
                            wrong_buttonCC.start();
                        }

                        boolean tocno = rand.nextBoolean();

                        if(tocno) {
                            String rijec = TocneRijeci.get(i);
                            tv1.setText(rijec);
                            brojac++;
                            i++;
                        }
                        else {
                            String rijec = NetocneRijeci.get(i);
                            tv1.setText(rijec);
                            brojac++;
                            i++;
                        }
                    }
                    else if (view.equals(buttonN)) {

                        if (tv1.getText().toString().equals(NetocneRijeci.get(i-1))) {
                            bodovi++;
                            tvBodovi.setText("Bodovi: " + bodovi);

                            correct_buttonCC.start();
                        }
                        else {
                            wrong_buttonCC.start();
                        }
                        boolean tocno = rand.nextBoolean();

                        if(tocno) {
                            String rijec = TocneRijeci.get(i);
                            tv1.setText(rijec);
                            brojac++;
                            i++;
                        }
                        else {
                            String rijec = TocneRijeci.get(i);
                            tv1.setText(rijec);
                            brojac++;
                            i++;
                        }
                    }
                }
            }
        };

        buttonT.setOnClickListener(listener);
        buttonN.setOnClickListener(listener);
    }

    public void IdiNaPocetak (View view) {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }

    public void PonoviIgru (View view) {

        startActivity(new Intent(getApplicationContext(), IgraCC.class));

    }
}
