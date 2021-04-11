package com.example.projet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.projet.BaseDeDonnée.User;

import java.util.List;




public class UserAdapter extends ArrayAdapter<User> { //creation de la classe adapter qui etends la liste d utilisateurs de la bdd

    public UserAdapter(Context mCtx, List<User> userList) {
        super(mCtx, R.layout.template_user, userList);//on le lie a la vue du template d'une ligne et a la liste d'users
    }

    /**
     * Remplit une ligne de la listView avec les informations de la multiplication associée
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final User user = getItem(position);//position de lutilisateur

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_user, parent, false);//on inflate le temmplate de la ligne pour avoir
        //l'affichage de tout les comptes utilisateur

        // Récupération des objets graphiques dans le template
        TextView textViewPrenom = (TextView) rowView.findViewById(R.id.textViewPrenom);
        TextView textViewNom = (TextView) rowView.findViewById(R.id.textViewNom);

        //
        textViewPrenom.setText(user.getPrenom());//on modifie les vues avec les infos de l'utilisateur
        textViewNom.setText(user.getNom());

        //
        return rowView;
    }

}
