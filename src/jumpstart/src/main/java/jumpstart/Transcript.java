package jumpstart;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;

public class Transcript {
    private static String SRC = "";
    private static String DEST = "";
    private static String PATH = "";
    private static String FONT = "";
    private static ArrayList<Student> studentList = new ArrayList<Student>();
    
    public Transcript(String SRC, String DEST, String PATH, String FONT) {
    	Transcript.SRC = SRC;
    	Transcript.DEST = DEST;
    	Transcript.PATH = PATH;
    	Transcript.FONT = FONT;
    }
    
//    public static void main(String[] args) throws Exception {
//    	Transcript a = new Transcript("results/Template1.xlsx", "results/transcripts");
//    	a.importExcel();
//    	for(Student student: studentList) {
//        	a.generateTranscript(student);
//    	}
//    }
    
    public ArrayList<Student> getStudentList(){
    	return studentList;
    }
    
    public boolean importExcel(){
    	studentList.removeAll(studentList);
    	try {
	    	Workbook workbook = WorkbookFactory.create(new File(SRC));    	
	    	DataFormatter dataFormatter = new DataFormatter();
	    	
	    	Iterator<Sheet> sheetIterator = workbook.sheetIterator();
	    	while(sheetIterator.hasNext()) {
	    		Sheet sheet = sheetIterator.next();
	    		System.out.println(sheet.getSheetName());
	    		boolean skip = false;
				Iterator<Row> rowIterator = sheet.rowIterator();
	    		if(sheet.getSheetName().equals("Info")) {
	    			while(rowIterator.hasNext()) {
	    				if(skip) {
	    					Row row = rowIterator.next();
	        				Iterator<Cell> cellIterator = row.cellIterator();
	        				String[] params = new String[8];
	        				int index = 0;
	        				while(cellIterator.hasNext()) {
	        					Cell cell = cellIterator.next();
	        					params[index] = dataFormatter.formatCellValue(cell).trim();
	        					index++;
	        				}
	        				studentList.add(new Student(params));
	    				} else {
	    					rowIterator.next();
	    					skip = true;
	    				}
	    			}
	    		} else if(sheet.getSheetName().equals("G10")) {
	    			String[] headerline = new String[23];
	    			while(rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();
						int iteratorIndex = 0;
						if(skip) {
							Grade[] params = new Grade[12];
							int index = 0;
							String _id = "";	
							while(cellIterator.hasNext()) {
								if(iteratorIndex == 0) {
									cellIterator.next();
								} else if(iteratorIndex == 1){
									_id = dataFormatter.formatCellValue(cellIterator.next()).trim();
								} else {
									String this_cell = dataFormatter.formatCellValue(cellIterator.next()).trim();
									if(!this_cell.equals("")) {
										params[index] = new Grade(headerline[iteratorIndex],this_cell);
										index++;
									}
								}
								iteratorIndex++;
							}
							System.out.println(index);
							if(index == 12) {
								for(Student i: studentList) {
									if(i.getID().equals(_id)) {
										i.setG10Grade(params);
										break;
									}
								}
							}
						} else {
							while(cellIterator.hasNext()) {
								headerline[iteratorIndex] = dataFormatter.formatCellValue(cellIterator.next());
								iteratorIndex++;
							}
							skip = true;
						}
	    			}
	    		} else if(sheet.getSheetName().equals("G11")) {
	    			String[] headerline = new String[36];
	    			String[] level = new String[36];
	    			while(rowIterator.hasNext()) {
	    				Row row = rowIterator.next();
	    				Iterator<Cell> cellIterator = row.cellIterator();
	    				int iteratorIndex = 0;
	    				if(skip) {
	    					Grade[] params = new Grade[9];
	    					int hl = 0;
	    					int sl = 3;
	    					int other = 6;
	    					String _id = "";
	    					while(cellIterator.hasNext()) {
	    						if(iteratorIndex == 0) {
	    							cellIterator.next();
	    						} else if(iteratorIndex == 1) {
	    							_id = dataFormatter.formatCellValue(cellIterator.next()).trim();
	    						} else {
									String this_cell = dataFormatter.formatCellValue(cellIterator.next()).trim();
									if(!this_cell.equals("")) {
										if(level[iteratorIndex].equals("HL")) {
											params[hl] = new Grade(headerline[iteratorIndex],this_cell);
											hl++;
										} else if(level[iteratorIndex].equals("SL")) {
											params[sl] = new Grade(headerline[iteratorIndex],this_cell);
											sl++;
										} else {
											params[other] = new Grade(headerline[iteratorIndex],this_cell);
											other++;
										}
									}
	    						}
	    						iteratorIndex++;
	    					}
	    					System.out.println(hl);
	    					System.out.println(sl);
	    					System.out.println(other);
	    					if(hl == 3 && sl == 6 && other == 9) {
								for(Student i: studentList) {
									if(i.getID().equals(_id)) {
										i.setG11Grade(params);
										break;
									}
								}
	    					}
	    				} else if(headerline[0] == null) {
	    					while(cellIterator.hasNext()) {
	    						headerline[iteratorIndex] = dataFormatter.formatCellValue(cellIterator.next());
	    						iteratorIndex++;
	    					}
	    				} else {
	    					while(cellIterator.hasNext()) {
	    						level[iteratorIndex] = dataFormatter.formatCellValue(cellIterator.next());
	    						iteratorIndex++;
	    					}
	    					skip = true;
	    				}
	    			}
	    		} else if(sheet.getSheetName().equals("G12")) {
	    			String[] headerline = new String[34];
	    			String[] level = new String[34];
	    			while(rowIterator.hasNext()) {
	    				Row row = rowIterator.next();
	    				Iterator<Cell> cellIterator = row.cellIterator();
	    				int iteratorIndex = 0;
	    				if(skip) {
	    					Grade[] params = new Grade[10];
	    					int hl = 0;
	    					int sl = 3;
	    					int other = 6;
	    					String _id = "";
	    					while(cellIterator.hasNext()) {
	    						if(iteratorIndex == 0) {
	    							cellIterator.next();
	    						} else if(iteratorIndex == 1) {
	    							_id = dataFormatter.formatCellValue(cellIterator.next());
	    						} else {
									String this_cell = dataFormatter.formatCellValue(cellIterator.next()).trim();
									if(!this_cell.equals("")) {
										if(level[iteratorIndex].equals("HL")) {
											params[hl] = new Grade(headerline[iteratorIndex],this_cell);
											hl++;
										} else if(level[iteratorIndex].equals("SL")) {
											params[sl] = new Grade(headerline[iteratorIndex],this_cell);
											sl++;
										} else {
											params[other] = new Grade(headerline[iteratorIndex],this_cell);
											other++;
										}
									}
	    						}
	    						iteratorIndex++;
	    					}
	    					System.out.println(hl);
	    					System.out.println(sl);
	    					System.out.println(other);
	    					if(hl == 3 && sl == 6 && other == 10) {
								for(Student i: studentList) {
									if(i.getID().equals(_id)) {
										i.setG12Grade(params);
										break;
									}
								}
	    					}
	    				} else if(headerline[0] == null) {
	    					while(cellIterator.hasNext()) {
	    						headerline[iteratorIndex] = dataFormatter.formatCellValue(cellIterator.next());
	    						iteratorIndex++;
	    					}
	    				} else {
	    					while(cellIterator.hasNext()) {
	    						level[iteratorIndex] = dataFormatter.formatCellValue(cellIterator.next());
	    						iteratorIndex++;
	    					}
	    					skip = true;
	    				}
	    			}
	    		}
	    	}
    	} catch(Exception e) {
    		System.out.println(e);
    		return false;
    	}
    	return true;
    }

    
    public boolean generateTranscript(Student student){
    	try {    		
	    	FontProgram fontProgram = FontProgramFactory.createFont(FONT);
	    	PdfFont font = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);
	    		
	        PdfDocument pdfDoc = new PdfDocument(new PdfReader(PATH), new PdfWriter(DEST + "/" + student.getID() + "-" + student.getName() + ".pdf"));
	        Document doc = new Document(pdfDoc);
	        
	        float height = (float) 14.2;
	        
	        //BIO INFO
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(student.getName()), (float) 173, (float) 638.5, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(student.getGender()), (float) 173, (float) 638.5 - height, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(student.getdoc()), (float) 173, (float) 638.5 - 2 * height, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(student.getCEEB_CODE()), (float) 173, (float) 638.5 - 3 * height, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(student.getUCAS_CODE()), (float) 173, (float) 638.5 - 4 * height, TextAlignment.LEFT);
	
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(student.getdob()), (float) 460, (float) 638.5, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(student.getNationality()), (float) 460, (float) 638.5 - height, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(student.getdog()), (float) 460, (float) 638.5 - 2 * height, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(student.getIB_CODE()), (float) 460, (float) 638.5 - 3 * height, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(student.getdoi()), (float) 460, (float) 638.5 - 4 * height, TextAlignment.LEFT);
	
	        
	        float offset = (float) 13.9;
	        
	        Grade[] g10 = student.getG10Grade();
	        
	        System.out.println(g10);
	        
	        //G10
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[0].getName()), (float) 43, (float) 525, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[1].getName()), (float) 43, (float) 525 - offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[2].getName()), (float) 43, (float) 525 - 2 * offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[3].getName()), (float) 43, (float) 525 - 3 * offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[4].getName()), (float) 43, (float) 525 - 4 * offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[5].getName()), (float) 43, (float) 525 - 5 * offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[6].getName()), (float) 43, (float) 525 - 6 * offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[7].getName()), (float) 43, (float) 525 - 7 * offset, TextAlignment.LEFT);
	
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[0].getGrade()), (float) 190, (float) 525, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[1].getGrade()), (float) 190, (float) 525 - offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[2].getGrade()), (float) 190, (float) 525 - 2 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[3].getGrade()), (float) 190, (float) 525 - 3 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[4].getGrade()), (float) 190, (float) 525 - 4 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[5].getGrade()), (float) 190, (float) 525 - 5 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[6].getGrade()), (float) 190, (float) 525 - 6 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[7].getGrade()), (float) 190, (float) 525 - 7 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[8].getGrade()), (float) 190, (float) 525 - 8 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[9].getGrade()), (float) 190, (float) 525 - 9 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[10].getGrade()), (float) 190, (float) 525 - 10 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g10[11].getGrade()), (float) 190, (float) 525 - 11 * offset, TextAlignment.CENTER);
	
	        Grade[] g11 = student.getG11Grade();
	        
	        //G11
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[0].getName()), (float) 48, (float) 315.56, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[1].getName()), (float) 48, (float) 315.56 - offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[2].getName()), (float) 48, (float) 315.56 - 2 * offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[3].getName()), (float) 48, (float) 315.56 - 3 * offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[4].getName()), (float) 48, (float) 315.56 - 4 * offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[5].getName()), (float) 48, (float) 315.56 - 5 * offset, TextAlignment.LEFT);
	
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[0].getGrade()), (float) 214, (float) 315.56, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[1].getGrade()), (float) 214, (float) 315.56 - offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[2].getGrade()), (float) 214, (float) 315.56 - 2 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[3].getGrade()), (float) 214, (float) 315.56 - 3 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[4].getGrade()), (float) 214, (float) 315.56 - 4 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[5].getGrade()), (float) 214, (float) 315.56 - 5 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[6].getGrade()), (float) 214, (float) 315.56 - 6 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[7].getGrade()), (float) 214, (float) 315.56 - 7 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g11[8].getGrade()), (float) 214, (float) 315.56 - 8 * offset, TextAlignment.CENTER);
	
	        Grade[] g12 = student.getG12Grade();
	        
	        //G12
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[0].getName()), (float) 284, (float) 525, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[1].getName()), (float) 284, (float) 525 - offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[2].getName()), (float) 284, (float) 525 - 2 * offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[3].getName()), (float) 284, (float) 525 - 3 * offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[4].getName()), (float) 284, (float) 525 - 4 * offset, TextAlignment.LEFT);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[5].getName()), (float) 284, (float) 525 - 5 * offset, TextAlignment.LEFT);
	
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[0].getGrade()), (float) 483, (float) 525, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[1].getGrade()), (float) 483, (float) 525 - offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[2].getGrade()), (float) 483, (float) 525 - 2 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[3].getGrade()), (float) 483, (float) 525 - 3 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[4].getGrade()), (float) 483, (float) 525 - 4 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[5].getGrade()), (float) 483, (float) 525 - 5 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[6].getGrade()), (float) 483, (float) 525 - 6 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[8].getGrade()), (float) 483, (float) 525 - 8 * offset, TextAlignment.CENTER);
	        doc.showTextAligned(new Paragraph().setFont(font).setFontSize(11).add(g12[9].getGrade()+"/42"), (float) 483, (float) 525 - 9 * offset, TextAlignment.CENTER);
	
	        doc.close();	    	
    	} catch(Exception e) {
    		System.out.println(e);
    		return false;
    	}
    	return true;
    }
}

