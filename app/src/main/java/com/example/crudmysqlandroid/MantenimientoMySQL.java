package com.example.crudmysqlandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MantenimientoMySQL {

    boolean estadoGuardar = false;
    boolean estadoEliminar = false;

    private ProgressDialog pd;
    AlertDialog.Builder dialogo1;
    AlertDialog.Builder dialogo;
    ProgressDialog progressDialog;



    public void guardar(final Context context, final String codigo, final String descripcion, final String precio){
        String url = "http://mjgl.com.sv/mysql_crud/guardar.php";
        //String url = "localhost/democrudsis21a/guardar.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //En este método se recibe la respuesta en json desde el web service o API.

                        try{
                            JSONObject requestJSON = new JSONObject(response.toString());
                            String estado = requestJSON.getString("estado");
                            String mensaje = requestJSON.getString("mensaje");

                            if(estado.equals("1")){
                                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                                //Toast.makeText(context, "Registro almacenado en MySQL.", Toast.LENGTH_SHORT).show();
                            }else if(mensaje.equals("2")){
                                Toast.makeText(context, "Error. No se pudo guardar.\n" +
                                        "Intentelo mas tarde.", Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            //Toast.makeText(context, "Se encontrarón problemas...", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //En este método se notifica al usuario acerca de un posible error al tratar de
                //realizar una acción cualquier en la base de datos remota.
                Toast.makeText(context, "No se puedo guardar. \n" +
                        "Verifique su acceso a internet.", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                //En este método se colocan o se setean los valores a recibir por el fichero *.php
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("codigo", codigo);
                map.put("descripcion", descripcion);
                map.put("precio", precio);
                return map;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(request);

    }




    public boolean guardar1(final Context context, final String codigo, final String descripcion, final String precio) {
        //String url = "http://mjgl.com.sv/mysqlcrud/guardar.php";
        String url  = Config.urlGuardar;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //En este método se recibe la respuesta en json desde el web service o API.

                        try {
                            JSONObject requestJSON = new JSONObject(response.toString());
                            String estado = requestJSON.getString("estado");
                            String mensaje = requestJSON.getString("mensaje");

                            if (estado.equals("1")) {
                                //Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                                estadoGuardar = true;
                            } else if (mensaje.equals("2")) {
                                Toast.makeText(context, "Error. No se pudo guardar.\n" +
                                        "Intentelo mas tarde.", Toast.LENGTH_SHORT).show();
                                estadoGuardar = false;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(context, "Se encontrarón problemas...", Toast.LENGTH_SHORT).show();
                            estadoGuardar = false;
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //En este método se notifica al usuario acerca de un posible error al tratar de
                //realizar una acción cualquier en la base de datos remota.
                Toast.makeText(context, "No se puedo guardar. \n" +
                        "Verifique su acceso a internet.", Toast.LENGTH_SHORT).show();
                estadoGuardar = false;
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                //En este método se colocan o se setean los valores a recibir por el fichero *.php
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("codigo", codigo);
                map.put("descripcion", descripcion);
                map.put("precio", precio);
                return map;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(request);

        return estadoGuardar;
    }



    public void eliminar(final Context context, final String codigo){

        progressDialog = new ProgressDialog(context);

        dialogo = new AlertDialog.Builder(context);
        dialogo.setIcon(R.drawable.ic_delete);
        dialogo.setTitle("¡¡¡Advertencia!!!");
        dialogo.setMessage("¿Realmente desea borrar el registro?\n" +
                "Código: "+codigo);
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aplicar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Espere por favor, Estamos trabajando en el servidor");
                progressDialog.show();

                //String url = "http://mjgl.com.sv/mysqlcrud/eliminar.php";
                String url  = Config.urlEliminar;

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                            JSONObject respuestaJSON = new JSONObject(response.toString());         //Creo un JSONObject a partir del StringBuilder pasado a cadena
                            String resultJSON = respuestaJSON.getString("estado");            // estado es el nombre del campo en el JSON
                            String result_msj = respuestaJSON.getString("mensaje");           // estado es el nombre del campo en el JSON
                            if (resultJSON.equals("1")) {

                                Toast toast = Toast.makeText(context, result_msj, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            } else if (resultJSON.equals("2")) {
                                Toast toast = Toast.makeText(context, "--> Nothing." +
                                        "\n" + result_msj, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        Toast.makeText(context, "Algo salio mal. Intente mas tarde\n" +
                                "Verifique su acceso a Internet.", Toast.LENGTH_LONG).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("Content-Type", "application/json; charset=utf-8");
                        map.put("Accept", "application/json");
                        map.put("codigo", codigo);
                        return map;
                    }
                };

                MySingleton.getInstance(context).addToRequestQueue(request);
            }
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                Toast toast = Toast.makeText(context, "Operación Cancelada.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        //AlertDialog dialogo = builder.create();
        dialogo.show();

    }



    public void consultarCodigo(final Context context, String codigo){


    }


    public void consultarDescripcion(final Context context, String codigo){


    }


    public void consultarAllArticulos(final Context context){


    }


    public void modificar(final Context context, String codigo, String descripcion, String precio){

    }

}
