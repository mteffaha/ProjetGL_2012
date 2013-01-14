package bebetes;

/*
 *                  Bebete.java
 *
 */

/**
 *
 * @author collet  (d'apr�s L. O'Brien, C. Reynolds & B. Eckel)
 * @version 3.0
 */

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import Panel.BebteControl;
import Panel.PanelCustom;

import comportement.*;
import fr.unice.plugin.Plugin;

import simu.Actionnable;
import util.DistancesEtDirections;

import visu.Dessinable;
import visu.PerceptionAble;
import visu.Positionnable;

/**
 * @author collet
 */
public class Bebete extends Observable implements Dessinable,
        Actionnable, PerceptionAble, Plugin ,Observer{

    // il y a 1 chance sur CHANSE_HASARD pour que la bebete soit hasard sinon elle sera Emergente
    private static int CHANCE_HASARD = 20;
    private static final int MINIMUM_ENERDY_FOR_PREDATOR = 20;

    private BebeteAvecComportement currentState;


    public Bebete(BebeteAvecComportement bebete) {
        this.currentState = bebete;
    }

	/* D�finition plus pr�cise de l'action de la bebete */

    public boolean isDead() {
        return currentState.isDead();
    }

    public void setDead(boolean dead) {

        currentState.setDead(dead);
        if (dead == true) {
            BebteControl.getInstance().getPanel().setOnglet(0);
            setChanged();
            notifyObservers();
        }
    }

    public void calculeDeplacementAFaire() {
        boolean notify = currentState.calculeDeplacementAFaire();
        if(notify){
            //on recupere le bon onglet d'affichage
            BebteControl.getInstance().getPanel().setOnglet(1);
            setChanged();
            notifyObservers();
        }
    }

    public void effectueDeplacement() {
        currentState.effectueDeplacement();
    }

    // modifie l'energie en fonction du type de la bebete et de son
    // environnement
    public void changeEnergie() {
        currentState.changeEnergie();
    }

    public int getEnergie() {
        return currentState.getEnergie();
    }

    // Impl�mentation de Actionnable */

    public void agit() {
        currentState.agit();
        changeCurrentState();

    }

    public void setPendingState(Class<? extends BebeteAvecComportement> pendingState){
        this.currentState.setPendingState(pendingState);
    }

    private void changeCurrentState(){
        // si on a un changement d'état voulue
        if(currentState.getPendingState() != null){

            //public BebeteHasard(ChampDeBebetes c, int x, int y, float dC, float vC, Color col) {
            try {
                int energy = currentState.getEnergie();
                currentState = (BebeteAvecComportement) currentState.getPendingState()
                        .getConstructors()[0].newInstance(currentState.getChamp(),currentState.getX(),currentState.getY()
                        ,currentState.getDirectionCourante(),currentState.getVitesseCourante(),currentState.getCouleur());
                currentState.setEnergie(energy);
                currentState.setPendingState(null);

            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

	/* Impl�mentation de Dessinable */

    public Color getCouleur() {
        return currentState.getCouleur();
    }

    public void setCouleur(Color couleur) {
        currentState.setCouleur(couleur);
    }

    public void seDessine(Graphics g) {
        currentState.seDessine(g);
    }

    public void calculerDeplacemementSensible() {
        currentState.calculerDeplacemementSensible();
    }

	/* Impl�mentation de Positionnable */

    public int getX() {
          return currentState.getX();
    }

    public void setX(int x) {
         currentState.setX(x);
    }

    public int getY() {
          return currentState.getY();
    }

    public void setY(int y) {
         currentState.setY(y);
    }

    public ChampDeBebetes getChamp() {
        return currentState.getChamp();
    }

	/* Impl�mentation de Dirigeable */

    public float getVitesseCourante() {
        return currentState.getVitesseCourante();
    }

    public void setVitesseCourante(float vitesseCourante) {
              currentState.setVitesseCourante(vitesseCourante);
    }

    public float getDirectionCourante() {
            return currentState.getDirectionCourante();
    }

    public void setDirectionCourante(float directionCourante) {
         currentState.setDirectionCourante(directionCourante);
    }

	/* Impl�mentation de PerceptionAble */

    public int getLongueurDeVue() {
        return currentState.getLongueurDeVue();
    }

    public float getChampDeVue() {
        return currentState.getChampDeVue();
    }

    public List<Positionnable> getChosesVues() { // utilisation de l'utilitaire
         return currentState.getChosesVues();
    }

	/*
     * changer la longueur et le champ de vue est "static", alors que les
	 * consulter se fait par des fonctions membres
	 */

    public static void setLongueurDeVue(int lDV) {
         BebeteAvecComportement.setLongueurDeVue(lDV);
    }

    public static void setChampDeVue(float cv) {
        BebeteAvecComportement.setChampDeVue(cv);
    }


    // partie propre � la transformation en Plugin

    public String getName() {
         return currentState.getName();
    }

    public Deplacement getDeplacement() {
         return currentState.getDeplacement();
    }

    public void setDeplacement(Deplacement move) {
        currentState.setDeplacement(move);
    }

    public float getDistancePlusProche() {
        return currentState.getDistancePlusProche();
    }

    public void setDistancePlusProche(float distancePlusProche) {
        setDistancePlusProche(distancePlusProche);
    }

    public BebeteAvecComportement getCurrentState(){
        return currentState;
    }


    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }
}
