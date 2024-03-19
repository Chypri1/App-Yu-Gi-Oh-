package com.example.appyugioh.controlleur;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;

import com.example.appyugioh.R;
import com.example.appyugioh.modele.comportementFront.ComportementFiltre;
import com.example.appyugioh.modele.comportementFront.ComportementMenu;
import com.example.appyugioh.modele.comportementFront.ComportementRechercheCarte;
import com.example.appyugioh.modele.rest.AccesExterneRest;
import com.example.appyugioh.modele.comportementFront.OnSwipeTouchListener;
import com.example.appyugioh.vue.Filtre;
import com.example.appyugioh.vue.RechercheCarte;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ControlleurRechercheCarte {

    private RechercheCarte activite;
    private Filtre filtre;

    private ComportementMenu comportementMenu;
    private ComportementRechercheCarte comportementRechercheCarte;

    private AccesExterneRest accesExterneRest;
    private ComportementFiltre comportementFiltre;
    private View layoutFiltrage;
    private AlertDialog dialog;

    public ControlleurRechercheCarte (RechercheCarte activity)
    {
        this.activite = activity;
        this.filtre=new Filtre();
        initialiseActivite();
        initialiseFiltre();
        initialiseComportement();
        observateur();
    }

    public RechercheCarte getActivite() {
        return activite;
    }

    public void setActivite(RechercheCarte activite) {
        this.activite = activite;
    }

    public ComportementMenu getComportementMenu() {
        return comportementMenu;
    }

    public void setComportementMenu(ComportementMenu comportementMenu) {
        this.comportementMenu = comportementMenu;
    }

    public AccesExterneRest getAccesExterneRest() {
        return accesExterneRest;
    }

    public void setAccesExterneRest(AccesExterneRest accesExterneRest) {
        this.accesExterneRest = accesExterneRest;
    }

    public Filtre getFiltre(){return filtre;}

    public void setFiltre(Filtre filtre) {this.filtre = filtre;}

    public void initialiseActivite()
    {
        activite.setDrawerLayout( activite.findViewById(R.id.drawerLayout));
        activite.setBoutonRechercheCarte(  activite.findViewById(R.id.boutonRechercheCarte));
        activite.setRechercheCarte( activite.findViewById(R.id.rechercheCarte));
        activite.setLayoutResultatRecherche(activite.findViewById(R.id.layoutResultatRecherche));
        activite.setBoutonFiltre(activite.findViewById(R.id.boutonFiltre));
        activite.setBtn_prev(activite.findViewById(R.id.previousButton));
        activite.setBtn_next(activite.findViewById(R.id.nextButton));
        activite.setScrollView((NestedScrollView) activite.findViewById(R.id.scrollViewRecherche));

        activite.setNavigationView(activite.findViewById(R.id.nav_view));
        Menu menu = activite.getNavigationView().getMenu();
        MenuItem menuItem1 = menu.findItem(R.id.menu_bouton_recherche_carte);
        MenuItem menuItem2 = menu.findItem(R.id.menu_bouton_accueil);
        MenuItem menuItem3 = menu.findItem(R.id.menu_bouton_recherche_deck);
        MenuItem menuItem4 = menu.findItem(R.id.menu_bouton_mes_cartes);
        MenuItem menuItem5 = menu.findItem(R.id.menu_bouton_mes_decks);

        this.accesExterneRest = new AccesExterneRest(activite.getBtn_prev(),activite.getBtn_next(),activite.findViewById(R.id.scrollViewRecherche));

    }


    public void initialiseComportement()
    {
        this.comportementMenu = new ComportementMenu();
        this.comportementRechercheCarte = new ComportementRechercheCarte();
        this.comportementFiltre=new ComportementFiltre();
    }

    public void initialiseFiltre() {
        // Initialisation des CheckBox
        List<CheckBox> checkBoxCardType = new ArrayList<>();
        List<CheckBox> checkBoxLevel = new ArrayList<>();
        List<CheckBox> checkBoxMonsterType = new ArrayList<>();
        List<CheckBox> checkBoxSpellType = new ArrayList<>();
        List<CheckBox> checkBoxTrapType = new ArrayList<>();
        List<CheckBox> checkBoxLinkClass = new ArrayList<>();
        List<CheckBox> checkBoxLinkMark = new ArrayList<>();
        List<CheckBox> checkBoxAttribut = new ArrayList<>();
        List<CheckBox> checkBoxRace = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(activite);
        layoutFiltrage = inflater.inflate(R.layout.layout_filtrage, null);

        // Initialisation des boutons et layouts
        filtre.setBtnReset(layoutFiltrage.findViewById(R.id.btn_reset));
        filtre.setBtnOk(layoutFiltrage.findViewById(R.id.btn_ok));
        filtre.setBtnRetour(layoutFiltrage.findViewById(R.id.btn_retour));
        filtre.setLayoutCardType(layoutFiltrage.findViewById(R.id.layout_type_carte));
        filtre.setLayoutLevel(layoutFiltrage.findViewById(R.id.layout_niveau_rang));
        filtre.setLayoutMonsterType(layoutFiltrage.findViewById(R.id.layout_type_monstre));
        filtre.setLayoutSpellType(layoutFiltrage.findViewById(R.id.layout_type_magie));
        filtre.setLayoutTrapType(layoutFiltrage.findViewById(R.id.layout_type_piege));
        filtre.setLayoutLinkClass(layoutFiltrage.findViewById(R.id.layout_class_link));
        filtre.setLayoutLinkMark(layoutFiltrage.findViewById(R.id.layout_mark_link));
        filtre.setLayoutAttribut(layoutFiltrage.findViewById(R.id.layout_attribut));
        filtre.setLayoutRace(layoutFiltrage.findViewById(R.id.layout_race));

        // Initialisation des CheckBox de chaque layout
        initialiseCheckBoxList(filtre.getLayoutCardType(), checkBoxCardType);
        filtre.setCheckBoxCardType(checkBoxCardType);

        initialiseCheckBoxList(filtre.getLayoutLevel(), checkBoxLevel);
        filtre.setCheckBoxLevel(checkBoxLevel);

        initialiseCheckBoxList(filtre.getLayoutMonsterType(), checkBoxMonsterType);
        filtre.setCheckBoxMonsterType(checkBoxMonsterType);

        initialiseCheckBoxList(filtre.getLayoutSpellType(), checkBoxSpellType);
        filtre.setCheckBoxSpellType(checkBoxSpellType);

        initialiseCheckBoxList(filtre.getLayoutTrapType(), checkBoxTrapType);
        filtre.setCheckBoxTrapType(checkBoxTrapType);

        initialiseCheckBoxList(filtre.getLayoutLinkClass(), checkBoxLinkClass);
        filtre.setCheckBoxLinkClass(checkBoxLinkClass);

        initialiseCheckBoxList(filtre.getLayoutLinkMark(), checkBoxLinkMark);
        filtre.setCheckBoxLinkMark(checkBoxLinkMark);

        initialiseCheckBoxList(filtre.getLayoutAttribut(), checkBoxAttribut);
        filtre.setCheckBoxAttribut(checkBoxAttribut);

        initialiseCheckBoxList(filtre.getLayoutRace(), checkBoxRace);
        filtre.setCheckBoxRace(checkBoxRace);

        //Masquer les layouts
        filtre.getLayoutMonsterType().setVisibility(View.GONE);
        filtre.getLayoutSpellType().setVisibility(View.GONE);
        filtre.getLayoutTrapType().setVisibility(View.GONE);
        filtre.getLayoutLevel().setVisibility(View.GONE);
        filtre.getLayoutLinkClass().setVisibility(View.GONE);
        filtre.getLayoutLinkMark().setVisibility(View.GONE);
        filtre.getLayoutAttribut().setVisibility(View.GONE);
        filtre.getLayoutRace().setVisibility(View.GONE);
        filtre.getCheckBoxCardType("token").setVisibility(View.GONE);
        filtre.getCheckBoxCardType("skill").setVisibility(View.GONE);
        layoutFiltrage.findViewById(R.id.layout_type_monstre).setVisibility(View.GONE);
    }

    private void initialiseCheckBoxList(View view, List<CheckBox> checkBoxList) {
        if (view instanceof CheckBox) {
            checkBoxList.add((CheckBox) view);
        } else if (view instanceof ViewGroup viewGroup) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                initialiseCheckBoxList(viewGroup.getChildAt(i), checkBoxList);
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    public void observateur()
    {

        /*Recherche des cartes en fonction du texte dans le EditText ainsi que l'affichage des cartes
        * lors du clique sur le bouton RechercheCarte ou le bouton Ok du clavier virtuel*/
        activite.getBoutonRechercheCarte().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comportementRechercheCarte.masquerClavier(activite);
                comportementFiltre.reset(filtre);
                activite.getLayoutResultatRecherche().removeAllViews();
                accesExterneRest.appRest(activite.getRechercheCarte().getText().toString(), activite.getLayoutResultatRecherche(),activite);
            }
        });
        activite.getRechercheCarte().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    // Masquer le clavier virtuel
                    comportementRechercheCarte.masquerClavier(activite);

                    // Effectuer une action similaire à celle du bouton Recherche Carte
                    activite.getLayoutResultatRecherche().removeAllViews();
                    accesExterneRest.appRest(activite.getRechercheCarte().getText().toString(), activite.getLayoutResultatRecherche(), activite);
                    return true;
                }
                return false;
            }
        });
        /*Chargement de chaque section dans le menu déroulant*/
        activite.getNavigationView().setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return comportementMenu.initItemMenu(item, activite);
            }
        });
        /*Affichage du menu déroulant lors d'un clique sur le bouton de menuDeroulant*/
        ImageButton boutonMenuDeroulant=activite.findViewById(R.id.menuDeroulant);
        boutonMenuDeroulant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activite.getDrawerLayout().openDrawer(GravityCompat.START);
            }
        });
        // Configuration du geste de balayage pour ouvrir le menu déroulant avec le layout et le scrollview
        activite.getDrawerLayout().setOnTouchListener(new OnSwipeTouchListener(activite) {
            @Override
            public void onSwipeRight() {
                if (!activite.getDrawerLayout().isDrawerOpen(GravityCompat.START)) {
                    activite.getDrawerLayout().openDrawer(GravityCompat.START);
                }
            }
        });
        activite.getScrollView().setOnTouchListener(new OnSwipeTouchListener(activite) {
            @Override
            public void onSwipeRight() {
                if (!activite.getDrawerLayout().isDrawerOpen(GravityCompat.START)) {
                    activite.getDrawerLayout().openDrawer(GravityCompat.START);
                }
            }
        });

        activite.getRechercheCarte().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // Vérifiez si l'EditText a le focus
                if (hasFocus) {
                    // Si oui, effacez le texte par défaut
                    activite.getRechercheCarte().getText().clear();
                } else {
                    // Si non, réinitialisez le texte par défaut si le champ est vide
                    if (activite.getRechercheCarte().getText().toString().isEmpty()) {
                        activite.getRechercheCarte().setText("nom Carte");
                    }
                }
            }
        });
        activite.getBoutonFiltre().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifier si layoutFiltrage a déjà un parent
                ViewGroup parentView = (ViewGroup) layoutFiltrage.getParent();
                if (parentView != null) {
                    // Retirer layoutFiltrage de son parent actuel
                    parentView.removeView(layoutFiltrage);
                }
                filtre.getBtnReset().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Décocher toutes les cases de chaque layout
                        comportementFiltre.reset(filtre);
                    }
                });

                // Définir les actions des boutons
                filtre.getBtnOk().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(accesExterneRest.getListeFiltreCarteYuGiOh() != null)
                            if(!accesExterneRest.getListeFiltreCarteYuGiOh().isEmpty())
                                comportementFiltre.filtre(filtre,accesExterneRest);
                        activite.getDialog().dismiss();
                    }
                });

                filtre.getBtnRetour().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Retire le dialog lorsque le bouton "Retour" est cliqué
                        activite.getDialog().dismiss();
                    }
                });
                CheckBox monstre=layoutFiltrage.findViewById(R.id.monster);
                CheckBox magie=layoutFiltrage.findViewById(R.id.spell);
                CheckBox piege=layoutFiltrage.findViewById(R.id.trap);
                monstre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            // Checkbox cochée, afficher les layouts
                            filtre.getLayoutMonsterType().setVisibility(View.VISIBLE);
                            filtre.getLayoutLevel().setVisibility(View.VISIBLE);
                            filtre.getLayoutAttribut().setVisibility(View.VISIBLE);
                        } else {
                            // Checkbox non cochée, masquer les layouts
                            filtre.getLayoutMonsterType().setVisibility(View.GONE);
                            filtre.getLayoutLevel().setVisibility(View.GONE);
                            filtre.getLayoutAttribut().setVisibility(View.GONE);
                            for (CheckBox checkBox : filtre.getCheckBoxLevel()) {
                                checkBox.setChecked(false);
                            }
                            for (CheckBox checkBox : filtre.getCheckBoxMonsterType()) {
                                checkBox.setChecked(false);
                            }
                            for (CheckBox checkBox : filtre.getCheckBoxAttribut()) {
                                checkBox.setChecked(false);
                            }
                            for (CheckBox checkBox : filtre.getCheckBoxRace()) {
                                checkBox.setChecked(false);
                            }
                        }
                    }
                });

                magie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            // Checkbox cochée, afficher les layouts
                            filtre.getLayoutSpellType().setVisibility(View.VISIBLE);
                        } else {
                            // Checkbox non cochée, masquer les layouts
                            filtre.getLayoutSpellType().setVisibility(View.GONE);
                            for (CheckBox checkBox : filtre.getCheckBoxSpellType()) {
                                checkBox.setChecked(false);
                            }

                        }
                    }
                });
                piege.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (piege.isChecked()) {
                            // Checkbox cochée, afficher les layouts
                            filtre.getLayoutTrapType().setVisibility(View.VISIBLE);
                        } else {
                            // Checkbox non cochée, masquer les layouts
                            filtre.getLayoutTrapType().setVisibility(View.GONE);
                            for (CheckBox checkBox : filtre.getCheckBoxTrapType()) {
                                checkBox.setChecked(false);
                            }
                        }
                    }
                });


                // Affichage de la boite de dialogue
                AlertDialog.Builder builder = new AlertDialog.Builder(activite);
                builder.setView(layoutFiltrage);
                dialog = builder.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(true);
                activite.setDialog(dialog);
            }
        });

    }
}
