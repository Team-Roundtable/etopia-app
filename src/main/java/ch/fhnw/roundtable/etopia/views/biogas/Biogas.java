package ch.fhnw.roundtable.etopia.views.biogas;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Assets;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.View;
import ch.fhnw.roundtable.etopia.views.ViewType;
import ch.fhnw.roundtable.etopia.views.biogas.game.BiogasGame;
import ch.fhnw.roundtable.etopia.views.biogas.ui.BiogasAsset;
import ch.fhnw.roundtable.etopia.views.biogas.ui.BiogasUI;
import ch.fhnw.roundtable.etopia.views.energy.EnergyConfiguration;
import ch.fhnw.roundtable.etopia.views.energy.ui.EnergyAsset;
import ch.fhnw.roundtable.etopia.views.energy.ui.EnergyUI;
import ch.fhnw.roundtable.etopia.views.health.HealthConfiguration;
import ch.fhnw.roundtable.etopia.views.health.ui.HealthAsset;
import ch.fhnw.roundtable.etopia.views.health.ui.HealthUI;

import java.util.Random;

public class Biogas implements View {

    private final BiogasGame biogasGame;
    private final BiogasUI biogasUI;
    private final HealthUI healthUI;
    private final EnergyUI energyUI;

    public Biogas() {
        var biogasConfiguration = new BiogasConfiguration();
        var healthConfiguration = new HealthConfiguration();
        var energyConfiguration = new EnergyConfiguration();

        biogasGame = new BiogasGame(new Random(), biogasConfiguration);
        biogasUI = new BiogasUI(biogasConfiguration, new Assets<>(BiogasAsset.class));
        healthUI = new HealthUI(healthConfiguration, new Assets<>(HealthAsset.class));
        energyUI = new EnergyUI(energyConfiguration, new Assets<>(EnergyAsset.class));
    }

    @Override
    public void update(float delta, Input input) {
        biogasGame.update(delta, input);
    }

    @Override
    public void render(Renderer renderer) {
        biogasUI.render(biogasGame, renderer);
        healthUI.render(biogasGame.getHealthGame(), renderer);
        energyUI.render(biogasGame.getEnergyGame(), renderer);
    }

    @Override
    public void dispose() {
        biogasUI.dispose();
        healthUI.dispose();
        energyUI.dispose();
    }

    @Override
    public ViewType next() {
        return biogasGame.next();
    }
}
