package ch.fhnw.roundtable.etopia.configuration;

import ch.fhnw.roundtable.etopia.Result;

public class State {

    private boolean windSuccess = false;
    private boolean solarSuccess = false;
    private boolean biogasSuccess = false;
    private boolean gridSuccess = false;
    private boolean geothermalSuccess = false;

    public boolean isWindSuccess() {
        return windSuccess;
    }

    public void updateWind(Result result) {
        windSuccess = switch (result) {
            case RUNNING -> windSuccess;
            case SUCCESS -> true;
            case FAIL_TIME, FAIL_HEALTH -> false;
        };
    }

    public boolean isSolarSuccess() {
        return solarSuccess;
    }

    public void updateSolar(Result result) {
        solarSuccess = switch (result) {
            case RUNNING -> windSuccess;
            case SUCCESS -> true;
            case FAIL_TIME, FAIL_HEALTH -> false;
        };
    }

    public boolean isBiogasSuccess() {
        return biogasSuccess;
    }

    public void updateBiogas(Result result) {
        biogasSuccess = switch (result) {
            case RUNNING -> windSuccess;
            case SUCCESS -> true;
            case FAIL_TIME, FAIL_HEALTH -> false;
        };
    }

    public boolean isGridSuccess() {
        return gridSuccess;
    }

    public void updateGrid(Result result) {
        gridSuccess = switch (result) {
            case RUNNING -> windSuccess;
            case SUCCESS -> true;
            case FAIL_TIME, FAIL_HEALTH -> false;
        };
    }

    public boolean isGeothermalSuccess() {
        return geothermalSuccess;
    }

    public void updateGeothermal(Result result) {
        geothermalSuccess = switch (result) {
            case RUNNING -> windSuccess;
            case SUCCESS -> true;
            case FAIL_TIME, FAIL_HEALTH -> false;
        };
    }
}
