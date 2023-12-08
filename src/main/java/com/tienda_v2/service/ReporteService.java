
package com.tienda_v2.service;


import jakarta.mail.Quota.Resource;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface ReporteService {
  //Se definde la firma del metodo que genera los reportes, con los siguiente
    //parametros
    //1. reporte: es el nombre del archivo de reporte (.jasper)
    //2. parametros: un Map que contiene los paramentros del reporte.. si los ocupa
    //3. tipo: es el tipo de reporte: vpdf, pdf, xls, csv
    public ResponseEntity<Resource> generaReporte(
    String reporte,
    Map<String, Object> parametros,
    String tipo) throws IOException;
}
