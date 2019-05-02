package sj.android.com.weatherginni.Model;

import java.util.List;

public class OpenWeatherMap {
    private Coord coord;
    private Sys sys;
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private Clouds cloud;
    private int dt;
    private int id;
    private String name;
    private int cod;
    private String base;
    private Rain rain;

    public OpenWeatherMap() {
    }

    public OpenWeatherMap(Coord coord, Sys sys, List<Weather> weather, Main main, Wind wind, Clouds cloud, int dt, int id, String name, int cod, String base, Rain rain) {
        this.coord = coord;
        this.sys = sys;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.cloud = cloud;
        this.dt = dt;
        this.id = id;
        this.name = name;
        this.cod = cod;
        this.base = base;
        this.rain = rain;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getCloud() {
        return cloud;
    }

    public void setCloud(Clouds cloud) {
        this.cloud = cloud;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }
}
