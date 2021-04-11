package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projet.BaseDeDonnée.DatabaseClient;
import com.example.projet.BaseDeDonnée.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //
    private static final int REQUEST_CODE_ADD = 0;

    // DATA
    private DatabaseClient mDb;
    private UserAdapter adapter;

    private  MyApplication myapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDb = DatabaseClient.getInstance(getApplicationContext());
        myapp = ((MyApplication) this.getApplication());
        // Récupérer les vues
        ListView listUser = findViewById(R.id.listUser);
        // VIEW
        Button buttonAdd = findViewById(R.id.creerCmpt);

        // Lier l'adapter au listView
        adapter = new UserAdapter(this, new ArrayList<User>());
        listUser.setAdapter(adapter);

        // Ajouter un événement au bouton d'ajout
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        // EXEMPLE : Ajouter un événement click à la listView
        listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Récupération de la tâche cliquée à l'aide de l'adapter
                User user = adapter.getItem(position);
                myapp.setUserCourrant(user);
                // Message
                Toast.makeText(MainActivity.this, "Click : " + user.getPrenom(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, JouerActivity.class);
                startActivity(intent);
            }
        });

        // EXEMPLE : Ajouter un événement long click à la listView
        listUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // Récupération de la tâche cliquée à l'aide de l'adapter
                User user = adapter.getItem(position);
                myapp.setUserCourrant(user);

                Intent intent = new Intent(MainActivity.this, GestionCompteActivity.class);

                startActivity(intent);
                return false;
            }
        });

    }

    private void getUsers() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des users et de mettre à jour le listView de l'activité
        class GetUsers extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> usersList = mDb.getAppDatabase()
                        .UserDao()
                        .getAll();
                return usersList;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);

                // Mettre à jour l'adapter avec la liste de taches
                adapter.clear();
                adapter.addAll(users);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetUsers et execution de la demande asynchrone
        GetUsers gt = new GetUsers();
        gt.execute();
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Mise à jour des users
        getUsers();

    }


    public void accueilValider(View view){
        Intent intent = new Intent(this, JouerActivity.class);
        startActivity(intent);
        myapp.setUserCourrant(null);
    }


}