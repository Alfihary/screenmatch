package com.aluracursos.screenmatch.model;

public enum Categoria {

    ACCION("Action"),

    AVENTURA("Adventure"),

    DRAMA("Drama"),

    FANTASIA("Fantasy"),

    TERROR("Horror"),

    CRIMEN("Crime"),

    MISTERIO("Mystery"),

    SUSPENSO("Thriller"),

    ROMANCE("Romance"),

    COMEDIA("Comedy");

    private String categoriaOmdb;

    Categoria(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);

    }
}
