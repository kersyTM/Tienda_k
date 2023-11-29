/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tienda.Service;

import java.io.IOException;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author kersy
 */
public interface ReporteService {
    
    //la firma del método encargado de generar los reportes 
    //resuver 3 parmetros;
    //1. EL NOMBRE DEK ARCHIVO jasper(String)
    //2. los parámetros que tiene el reporte... si aplica
    //3. el tipo...V´df, pdf, Xlu, Cvs(String)
    public ResponseEntity<Resource> generaReporte(
            
            String reporte,
            Map<String,Object>parametros,
            String tipo
    )throws IOException;
    
}
