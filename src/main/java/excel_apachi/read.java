package excel_apachi;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class read {
    public static void main(String[] args) throws IOException {
        /*
        FileInputStream file=new FileInputStream("C:\\Users\\2487618\\Downloads\\data.xlsx");
        XSSFWorkbook wb=new XSSFWorkbook(file);
        XSSFSheet sheet=wb.getSheet("Sheet1");
        int rowcount=sheet.getLastRowNum();
        int cellcount=sheet.getRow(0).getLastCellNum();
        System.out.println("rows="+rowcount);
        System.out.println("cells="+cellcount);
        for(int r=0;r<=rowcount;r++){
            XSSFRow cr=sheet.getRow(r);
            for(int c=0;c<cellcount;c++){
                XSSFCell cell=cr.getCell(c);
                System.out.print(cell.toString()+"\t");
            }
            System.out.println();
        }
        wb.close();
        file.close();

        */
        FileOutputStream file=new FileOutputStream("C:\\Users\\2487618\\Downloads\\data1.xlsx");
        XSSFWorkbook wb=new XSSFWorkbook();
        XSSFSheet sheet=wb.createSheet("data1");
        Scanner sc=new Scanner(System.in);
        System.out.println("enter rows:");
        int rowcount=sc.nextInt();
        System.out.println("enter cells:");
        int cellcount=sc.nextInt();
        System.out.println("rows="+rowcount);
        System.out.println("cells="+cellcount);
        for(int r=0;r<=rowcount;r++){
            XSSFRow cr=sheet.createRow(r);
            for(int c=0;c<cellcount;c++){
                XSSFCell cell=cr.createCell(c);
                cell.setCellValue(sc.next());
            }
        }
        wb.write(file);
        wb.close();
        file.close();
    }
}
