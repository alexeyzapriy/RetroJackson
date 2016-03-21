package com.example.azapriy.retrojackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    private String _name;
    private LocationCoord _coord;

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public LocationCoord getCoord() {
        return _coord;
    }

    public void setCoord(LocationCoord _coord) {
        this._coord = _coord;
    }
}
