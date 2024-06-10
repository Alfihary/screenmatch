package com.aluracurso.desafio.principal;

import com.aluracurso.desafio.model.Datos;
import com.aluracurso.desafio.model.DatosLibros;
import com.aluracurso.desafio.service.ConsumoAPI;
import com.aluracurso.desafio.service.ConvierteDatos;

import java.sql.SQLOutput;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";

    private ConsumoAPI comsumoAPI = new ConsumoAPI();

    private ConvierteDatos conversor = new ConvierteDatos();

    private Scanner teclado = new Scanner(System.in);

    public void muestraElMuenu(){
        var json =comsumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        //top 10 libros mas destacados
        System.out.println("Top 10 libros más descargados: ");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        //Busqueda de libros por nombre:
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        var tituloLibro = teclado.nextLine();
        json = comsumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()) {
            System.out.println("Libro encontrado: ");
            System.out.println(libroBuscado.get());
        }else {
            System.out.println("Libro no encontrado");
        }

        //Trabajando con estadisticas

        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d -> d.numeroDeDescargas()>0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));

        System.out.println("Cantidad media de descargas: " + est.getAverage());
        System.out.println("Cantidad maxima de descargas: " +  est.getMax());
        System.out.println("Cantidad minima de descargas: " + est.getMin());
        System.out.println("Numero de registros evaluados para calcular las estadisticas: " + est.getCount());
    }

}
