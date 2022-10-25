package brrf;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class PDFGenerator {

    private PDFGenerator() {

    }

    public static void generatePDF(String start, String end, String departureTime,
                                   String arrivalTime, List<List<Map<String, Object>>> data) {

        //-----------------Creating pdf document------------------------------------------------------------------------
        String pdfName = "Timetable.pdf";
        Document document = new Document(new Rectangle(595, 842), 5, 5, 7, 7);
        String path = "C:\\studia\\git\\po2021\\pdf\\" + pdfName;


        try {
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font textStyle = new Font(baseFont, 11);

            //---------------Adding image-------------------------------------------------------------------------------
            String imagePath = "C:\\studia\\git\\po2021\\Nowy\\BRRF\\src\\main\\resources\\trainToPDF2.png" ;
            Image img = Image.getInstance(imagePath);
            img.setAbsolutePosition(200, 700);
            document.add(img);


            //---------------Adding paragraphs--------------------------------------------------------------------------
            Paragraph emptyLines = new Paragraph("\n\n\n\n\n\n\n");
            document.add(emptyLines);

            Paragraph title = new Paragraph("Your Timetable", textStyle);
            title.setAlignment(title.ALIGN_CENTER);
            textStyle.setSize(25);
            textStyle.setStyle("bold");
            textStyle.setStyle("underline");
            textStyle.setColor(0, 0, 255);
            document.add(title);

            Paragraph emptyLines2 = new Paragraph("\n\n");
            document.add(emptyLines2);

            Paragraph fromTo = new Paragraph(" ".repeat(20) + " From: " + start +
                                                   ", " + departureTime, textStyle);
            fromTo.setAlignment(fromTo.ALIGN_LEFT);
            textStyle.setColor(0, 0, 0);
            textStyle.setSize(13);
            textStyle.setStyle(0);
            textStyle.setStyle("bold");
            document.add(fromTo);

            Paragraph startAndDestination = new Paragraph( " ".repeat(20) + " To: " + end +
                                                                 ", " + arrivalTime, textStyle);
            startAndDestination.setAlignment(startAndDestination.ALIGN_LEFT);
            textStyle.setColor(0, 0, 0);
            textStyle.setSize(13);
            textStyle.setStyle("bold");
            document.add(startAndDestination);

            document.add(emptyLines2);


            //---------------Adding table-------------------------------------------------------------------------------
            PdfPTable table = new PdfPTable(8);
            table.setWidths(new int[]{100, 110, 100, 100, 90, 100, 100, 100});

            textStyle.setSize(11);
            textStyle.setStyle("bold");

            PdfPCell column1 = new PdfPCell(new Phrase("Departure", textStyle));
            column1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(column1);

            PdfPCell column2 = new PdfPCell(new Phrase("Name", textStyle));
            column2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(column2);

            PdfPCell column3 = new PdfPCell(new Phrase("Carrier", textStyle));
            column3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(column3);

            PdfPCell column4 = new PdfPCell(new Phrase("Platform", textStyle));
            column4.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(column4);

            PdfPCell column5 = new PdfPCell(new Phrase("Track", textStyle));
            column5.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(column5);

            PdfPCell column6 = new PdfPCell(new Phrase("Distance", textStyle));
            column6.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(column6);

            PdfPCell column7 = new PdfPCell(new Phrase("Arrival", textStyle));
            column7.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(column7);

            PdfPCell column8 = new PdfPCell(new Phrase("Time", textStyle));
            column8.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(column8);

            document.add(table);


            //---------------Adding table with data---------------------------------------------------------------------
            PdfPTable tableWithData = new PdfPTable(8);
            tableWithData.setWidths(new int[]{100, 110, 100, 100, 90, 100, 100, 100});
            textStyle.setSize(11);
            textStyle.setStyle(0);


            for (int i =0; i < data.get(0).size(); i++) {
                PdfPCell cell = new PdfPCell(new Phrase((data.get(0).get(i).get("dept_time")).toString(), textStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableWithData.addCell(cell);

                cell = new PdfPCell(new Phrase((data.get(0).get(i).get("name")).toString(), textStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableWithData.addCell(cell);

                cell = new PdfPCell(new Phrase(data.get(0).get(i).get("carrier").toString(), textStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableWithData.addCell(cell);

                cell = new PdfPCell(new Phrase((data.get(0).get(i).get("platform")).toString(), textStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableWithData.addCell(cell);

                cell = new PdfPCell(new Phrase((data.get(0).get(i).get("track")).toString(), textStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableWithData.addCell(cell);

                double distance = Math.abs(Double.parseDouble(data.get(1).get(i).get("kilometers").toString())
                        - Double.parseDouble(data.get(0).get(i).get("kilometers").toString()));
                cell = new PdfPCell(new Phrase(String.format("%.2f", distance), textStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableWithData.addCell(cell);

                cell = new PdfPCell(new Phrase((data.get(1).get(i).get("arr_time")).toString(), textStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableWithData.addCell(cell);

                String atime = (data.get(1).get(i).get("arr_time")).toString();
                String etime = (data.get(0).get(i).get("dept_time")).toString();
                Time t = new Time(atime);
                String traveTime = t.subtractTime(etime);

                cell = new PdfPCell(new Phrase(traveTime, textStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableWithData.addCell(cell);
            }

            document.add(tableWithData);

            document.close();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
