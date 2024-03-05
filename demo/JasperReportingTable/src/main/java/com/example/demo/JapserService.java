package com.example.demo;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@Service
public class JapserService {

    private final JasperRepo personRepository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public JapserService(JasperRepo personRepository, ResourceLoader resourceLoader) {
        this.personRepository = personRepository;
        this.resourceLoader = resourceLoader;
    }

    public byte[] generateReport() throws JRException, IOException {
        // Load JRXML template
        Resource reportResource = resourceLoader.getResource("classpath:person.jrxml");
        InputStream reportStream = reportResource.getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

        // Fetch data from the database
        List<Person> persons = personRepository.findAll();
        JsonDataSource dataSource = createJsonDataSource(persons);

        // Fill the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

        // Export the report to PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private JsonDataSource createJsonDataSource(List<Person> persons) throws IOException, JRException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(persons);

        // Create a JsonDataSource from the JSON data
        InputStream jsonDataStream = new ByteArrayInputStream(jsonData.getBytes());
        return new JsonDataSource(jsonDataStream);
    }
}
