package com.tienda.Service.impl;

import com.tienda.Service.ReporteService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReporteServiceImpl implements ReporteService {

    //se usa para la cinexion la BD
    @Autowired
    private DataSource dataSource;


    @Override
    public ResponseEntity<Resource> generaReporte(String reporte, Map<String, Object> parametros, String tipo) throws IOException {
        try {

            String estilo
                    = tipo.equalsIgnoreCase("vPdf") ? "inline; " : "attachment;";

            //se establece el path del archivo de reporte
            String reportePath = "reportes";

            //la salida del reporte
            ByteArrayOutputStream salida = new ByteArrayOutputStream();

            ClassPathResource fuente = new ClassPathResource(
                    reportePath + File.separator + reporte + ".jasper");

            //se define un objeto para leer el reporte.jasper
            InputStream elReporte = fuente.getInputStream();

            //se genera en memoria el reporte
            var jasperReport = JasperFillManager.fillReport(elReporte, parametros, dataSource.getConnection());

            MediaType mediaType = null;

            String archivoSalida = "";

            //se crea un arreglo de bytes para transferir la infomacion
            byte[] data;

            switch (tipo) {
                case "vPdf", "Pdf" -> {
                    JasperExportManager.exportReportToPdfStream(jasperReport, salida);
                    mediaType = MediaType.APPLICATION_PDF;
                    archivoSalida = reporte + ".pdf";

                }
                case "Xls" -> {
                    JRXlsExporter paraExcel = new JRXlsExporter();
                    paraExcel.setExporterInput(
                            new SimpleExporterInput(jasperReport));

                    paraExcel.setExporterOutput(new SimpleOutputStreamExporterOutput(salida));
                    SimpleXlsxReportConfiguration configuracion = new SimpleXlsxReportConfiguration();

                    configuracion.setDetectCellType(Boolean.TRUE);//Lo que es numerico lo deje numerico, y lo de texto lo deje en etexto
                    configuracion.setCollapseRowSpan(Boolean.TRUE);

                    paraExcel.setConfiguration(configuracion);
                    paraExcel.exportReport();

                    mediaType = MediaType.APPLICATION_OCTET_STREAM; //Leer o escribir en un pdf
                    archivoSalida = reporte + ".xlsx";
                }
                case "Csv" -> {
                    JRCsvExporter paraCsv = new JRCsvExporter();
                    paraCsv.setExporterInput(
                            new SimpleExporterInput(jasperReport));

                    paraCsv.setExporterInput(new SimpleExporterInput(jasperReport));
                    paraCsv.setExporterOutput(new SimpleWriterExporterOutput(salida));
                    paraCsv.exportReport();

                    mediaType = MediaType.TEXT_PLAIN; //Leer o escribir en un pdf
                    archivoSalida = reporte + ".csv";
                }
            }

        data = salida.toByteArray();

        HttpHeaders header = new HttpHeaders();
        header.set("Content-Disposition", estilo + "filename\"" + archivoSalida + "\"");

        //IMPORTANTE GNERA EL HTML
        return ResponseEntity.
                ok().headers(header).contentLength(data.length)
                .contentType(mediaType).body(new InputStreamResource(new ByteArrayInputStream(data)));

    }
    catch (JRException | SQLException ex
        ) {
            Logger.getLogger(ReporteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }


return (ResponseEntity<Resource>)ResponseEntity.status(589);

    

}
}

 