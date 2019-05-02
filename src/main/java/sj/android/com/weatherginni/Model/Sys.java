package sj.android.com.weatherginni.Model;

public class Sys {
    private String country;
    private double sunrise;
    private double sunset;
    private double message;

    public Sys(String country, double sunrise, double sunset, double message) {
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getSunrise() {
        return sunrise;
    }

    public void setSunrise(double sunrise) {
        this.sunrise = sunrise;
    }

    public double getSunset() {
        return sunset;
    }

    public void setSunset(double sunset) {
        this.sunset = sunset;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }
}
