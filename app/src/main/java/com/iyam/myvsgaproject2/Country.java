package com.iyam.myvsgaproject2;
/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

import java.util.ArrayList;
import java.util.List;

public class Country {
    private Integer id;
    private String name;

    Country(String name, Integer id) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

     public static List<Country> getData(){
        List<Country> countries = new ArrayList<>();
        countries.add(new Country("Afghanistan", 1));
        countries.add(new Country("Albania", 2));
        countries.add(new Country("Algeria", 3));
        countries.add(new Country("Andorra", 4));
        countries.add(new Country("Angola", 5));
        countries.add(new Country("Antigua and Barbuda", 6));
        countries.add(new Country("Argentina", 7));
        countries.add(new Country("Armenia", 8));
        countries.add(new Country("Australia", 9));
        countries.add(new Country("Austria", 10));
        countries.add(new Country("Azerbaijan", 11));
        countries.add(new Country("Bahamas", 12));
        countries.add(new Country("Bahrain", 13));
        countries.add(new Country("Bangladesh", 14));
        countries.add(new Country("Barbados", 15));
        countries.add(new Country("Belarus", 16));
        countries.add(new Country("Belgium", 17));
        countries.add(new Country("Belize", 18));
        countries.add(new Country("Benin", 19));
        countries.add(new Country("Bhutan", 20));
        countries.add(new Country("Bolivia", 21));
        countries.add(new Country("Bosnia and Herzegovina", 22));
        countries.add(new Country("Botswana", 23));
        countries.add(new Country("Brazil", 24));
        countries.add(new Country("Brunei", 25));
        countries.add(new Country("Bulgaria", 26));
        countries.add(new Country("Burkina Faso", 27));
        countries.add(new Country("Burundi", 28));
        countries.add(new Country("Cabo Verde", 29));
        countries.add(new Country("Cambodia", 30));
        return countries;
    }
}
