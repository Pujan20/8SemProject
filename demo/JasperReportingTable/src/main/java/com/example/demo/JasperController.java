package com.example.demo;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/reports")
public class JasperController {
	 @Autowired
	    private JapserService reportService;

	    @GetMapping("/generate")
	    public ResponseEntity<byte[]> generateReport() throws JRException, IOException {
	        byte[] reportBytes = reportService.generateReport();

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.setContentDispositionFormData("attachment", "Tablereport.pdf");

	        return ResponseEntity.ok().headers(headers).body(reportBytes);
	    }
}
