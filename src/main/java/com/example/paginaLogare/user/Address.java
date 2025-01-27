package com.example.paginaLogare.user;

public record Address(
        String street,
        String suite,
        String city,
        String zipcode,
        Geo geo
) {
//    @Override
//    public String toString() {
//        return "Location {\n" +
//                "  street=" + street + ",\n" +
//                "  suite='" + suite + "',\n" +
//                "  city='" + city + "',\n" +
//                "  zipcode='" + zipcode + "',\n" +
//                "  geo='" + geo + "',\n" +
//                '}';
//    }
}
