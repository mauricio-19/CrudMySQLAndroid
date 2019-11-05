package com.example.crudmysqlandroid;

public class Config {

    public static final String urlGuardar = "http://localhost/demostracioncrud/guardar.php";                               //Guardar datos DB.
    public static final String urlEliminar = "http://localhost/demostracioncrud/eliminar.php";                             //Eliminar datos DB.
    public static final String urlActualizar = "http://localhost/demostracioncrud/actualizar.php";                          //Actualizar datos DB.

    public static final String urlConsultaApiMySQLi = "http://localhost/demostracioncrud/Api.php";                          //Ver todos los registros DB.
    public static final String urlConsultaApiPDO = "http://localhost/demostracioncrud/buscarAll.php";                       //Ver todos los registros DB.

    public static final String urlConsultaCodigo = "http://localhost/demostracioncrud/buscarArticulosCodigo.php";            //Busquedas por código.
    public static final String urlConsultaDescripcion = "http://localhost/demostracioncrud/buscarArticulosDescripcion.php";  //Busquedas por descripción.
    public static final String urlConsultaAllArticulos = "http://localhost/demostracioncrud/buscarArticulos.php";            //Devuelve todos los registros de la tabla MySQL.


}
