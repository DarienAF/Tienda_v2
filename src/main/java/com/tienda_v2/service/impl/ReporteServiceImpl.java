package com.tienda_v2.service.impl;

import com.tienda_v2.service.ReporteService;
import jakarta.activation.DataSource;
import jakarta.mail.Quota.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private DataSource dataSource;

    @Override
    public ResponseEntity<Resource> generaReporte(
            String report,
            Map<String, Object> parametros,
            String tipo) throws IOException {
        //Se asigna el tipo de la pagina a generar
        String estilo = tipo.equalsIgnoreCase("vPdf")
                ? "inline;" : "attachment;";

        //Se establece la ruta de los reportes
        String reportePath = "reportes";

        //Se define la salida temporal del reporte generado
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        String reporte = null;

        //Se establece la fuente para leer el report .jasper
        ClassPathResource fuente = new ClassPathResource(
                reportePath
                + File.separator
                + reporte
                + ".jasper");

        //Se define el objeto que leer el archivo de reporte .jasper
        InputStream elReporte = fuente.getInputStream();

        //Se genera el reporte en memoria
        var reporteJasper = JasperFillManager
                .fillReport(
                        elReporte,
                        parametros,
                        dataSource.getConnection());

        //A partir de aca inicia la respuesta al usuario
        MediaType mediaType = null;

        //Se debe decidir cual tipo de reporte se genera
        switch (tipo) {
            case "Pdf", "vPdf" -> { //Se descargara un Excel
                JasperExportManager
                        .exportReportToPdfStream(
                                reporteJasper,
                                salida);
                mediaType = MediaType.APPLICATION_PDF;
                String archivoSalida = reporte + ".pdf";
            }
            case "Xls" -> { //Se descargara un Pdf
                JRXlsxExporter paraExcel = new JRXlsxExporter();

                paraExcel.setExporterInput(
                        new SimpleExporterInput(
                                reporteJasper));

                paraExcel.setExporterOutput(
                        new SimpleOutputStreamExporterOutput(
                                salida));

                SimpleXlsxReportConfiguration configuracion
                        = new SimpleXlsxReportConfiguration();

                configuracion.setDetectCellType(true);
                configuracion.setCollapseRowSpan(true);

                paraExcel.setConfiguration(configuration);
                paraExcel.exportReport();

                mediaType = MediaType.APPLICATION_OCTET_STREAM;
                String archivoSalida = reporte + ".xlsx";

            }
            case "Csv" -> { //Se descargara un Excel
                JRCsvExporter paraCvs = new JRCsvExporter();

                paraCvs.setExporterInput(
                        new SimpleExporterInput(
                                reporteJasper));

                paraCvs.setExporterOutput(
                        new SimpleWriterExporterOutput(
                                salida));

                paraCvs.exportReport();

                mediaType = MediaType.TEXT_PLAIN;
                String archivoSalida = reporte + ".cvs";
            }
        }

        HttpHeaders headers = new HttpHeaders();
        String archivoSalida = null;
        headers.set("Content-Disposition",
                estilo + "filename=\"" + archivoSalida + "\"");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLenght(data.length)
                .contentType(mediaType)
                .body(
                        new InputStreamResource(
                                new ByteArrayInputStream(data)));

    }
    catch (JRException | SQLException ex

    
        ) {
        ex.printStackTrace();
    }

return null;
}
