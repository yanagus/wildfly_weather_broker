package ru.bellintegrator.view;

import java.io.Serializable;
import java.util.Objects;

/**
 * Город
 */
public class City implements Serializable {

    private static final long serialVersionUID = 2041275512219239990L;

    /**
     * Название города
     */
    private String name;

    /**
     * Регион
     */
    private String region;

    public City() {
    }

    public City(String name, String region) {
        this.name = name;
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(name, city.name) &&
                Objects.equals(region, city.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, region);
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
