package com.equivida.spring.enriquecimiento;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.equivida.spring.enriquecimiento.model.Cliente;
import com.equivida.spring.enriquecimiento.model.Persona;
import com.equivida.spring.enriquecimiento.service.ClienteServiceImpl;
import com.equivida.spring.enriquecimiento.service.IClienteService;
import com.equivida.spring.enriquecimiento.service.IPersonaService;
import com.equivida.spring.enriquecimiento.service.PersonaServiceImpl;

public class App {

	private static final String FILE_NAME = "enriquecimiento.xlsx";
	private static String[] columns = {"Cedula", "Nombre", "Fecha de Nacimiento"};

	public static void main(String[] args) throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//		procesarIndividual(context, "1700915794");
//		obtenerPersona(context, "");
//		procesarExcel(context);
		leerTodos(context);
		((ConfigurableApplicationContext) context).close();
	}

	public static String getHTML(String urlToRead) throws Exception {
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlToRead);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString();
	}
	
	public static void leerTodos(ApplicationContext context) throws Exception {
		
		IPersonaService personaService = (IPersonaService) context.getBean(PersonaServiceImpl.class);
		Workbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		
		Sheet sheet = workbook.createSheet("Clientes");
		Row headerRow = sheet.createRow(0);
		
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
		}
		
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		int rowNum = 1;
		for (String s : personaService.listAll()) {
			Row row = sheet.createRow(rowNum++);
			String[] data = s.split("\t");
			row.createCell(0).setCellValue(data[0]);
			row.createCell(1).setCellValue(data[1]);
			
			Cell dateOfBirthCell = row.createCell(2);
	        dateOfBirthCell.setCellValue(format.parse(data[2]));
	        dateOfBirthCell.setCellStyle(dateCellStyle);
			
	        // row.createCell(2).setCellValue(data[2]);
//			System.out.println(s);
		}
		
		for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
		
		FileOutputStream fileOut = new FileOutputStream("resultado.xlsx");
        workbook.write(fileOut);
        fileOut.close();
	}
	
	public static void obtenerPersona(ApplicationContext context, String cedula) throws Exception {
		IPersonaService personaService = (IPersonaService) context.getBean(PersonaServiceImpl.class);
		personaService.find(201);
	}

	public static void procesarIndividual(ApplicationContext context, String cedula) throws Exception {
		IPersonaService personaService = (IPersonaService) context.getBean(PersonaServiceImpl.class);
		Persona p = new Persona();
		p.setCedula(cedula);
		p.setXml(getHTML("http://databook-premium.com.ec/equividawebservices.php?ced=" + p.getCedula()
				+ "&usr=EQUIVIDA"));
		personaService.create(p);
	}
	
	public static void procesarExcel(ApplicationContext context) {
		IPersonaService personaService = (IPersonaService) context.getBean(PersonaServiceImpl.class);
		FileInputStream excelFile;
		System.out.println("Start time " + Calendar.getInstance().getTime());
		try {
			excelFile = new FileInputStream(new File(FILE_NAME));

			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {

					Cell currentCell = cellIterator.next();
					// getCellTypeEnum shown as deprecated for version 3.15
					// getCellTypeEnum ill be renamed to getCellType starting from version 4.0
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						// System.out.print(currentCell.getStringCellValue() + "++");
						// Cliente c = new Cliente();
						// c.setCedula(currentCell.getStringCellValue());
						// service.create(c);
						// getHTML("http://databook-premium.com.ec/equividawebservices.php?ced=1715154215&usr=EQUIVIDA"));
						Persona p = new Persona();
						p.setCedula(currentCell.getStringCellValue());
						p.setXml(getHTML("http://databook-premium.com.ec/equividawebservices.php?ced=" + p.getCedula()
								+ "&usr=EQUIVIDA"));
						personaService.create(p);

					} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						System.out.print(currentCell.getNumericCellValue() + "--");
					}

				}
				// System.out.println();
				// break;
			}
			System.out.println("End time " + Calendar.getInstance().getTime());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
