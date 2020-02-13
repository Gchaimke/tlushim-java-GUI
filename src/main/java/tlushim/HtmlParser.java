/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlushim;

/**
 *
 * @author gchaim
 */
import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * Write a description of class htmlParser here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class HtmlParser
{
    // instance variables - replace the example below with your own
    private String user;
    private String pass;
    private String site;

    /**
     * Constructor for objects of class htmlParser
     */
    public HtmlParser(String user, String pass, String site)
    {
        // initialise instance variables
        this.user =user;
        this.pass = pass;
        this.site = site;
        
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public String getData()
    {
        HashMap<String, String> formData = new HashMap<>();
        String loginFormUrl = "https://www.tlushim.co.il/login.php";
        formData.put("id_num", user);
        formData.put("password", pass);
        Map.Entry<String,String> entry;
        Document homePage= null;
        try {
            Connection.Response response  = Jsoup.connect(loginFormUrl)
                    .method(Connection.Method.POST)
                    .referrer("https://www.tlushim.co.il/index.php")
                    .ignoreHttpErrors(true)
                    .data(formData)
                    .followRedirects(true)
                    .execute();
            System.out.println("HTTP Status Code: " + response.statusCode());
            System.out.println(response.cookies());
            Map<String, String> cookies = response.cookies();

            if(!cookies.isEmpty()) {
                entry = cookies.entrySet().iterator().next();
                String key = entry.getKey();
                String value = entry.getValue();
                homePage = Jsoup
                        .connect(site)
                        .method(Connection.Method.GET)
                        .cookie(key, value)
                        .get();
            }
            return getTableRows(homePage);
        }catch (IOException ex){
            System.out.println("Error: "+ex);
            return "error";
        }
    }
    
    private String getTableRows(Document page) {
        StringBuilder cleanCells = new StringBuilder();
        try {
            Element table = page.select("table[class=atnd]").get(0);
            Elements rows = table.select("tr");
            rows = rows.not("tr[class=atnd_remark_hide]");
            cleanCells.append(table.getElementsByTag("caption").text()).append("\n");
            for (int i = 0; i < rows.size(); i++) {
                StringBuilder tmpString = new StringBuilder();
                for (Element cell : rows.get(i).getAllElements()) {
                    //old cells cleaner : if (!cell.text().isEmpty() && !cell.text().equals(":") &&!cell.text().equals("\r") && !cell.text().equals("\r\n") && !cell.text().equals("\n") && !cell.text().equals(" ") && !cell.text().contains("רגיל") && !cell.text().contains("0.50") &&  !cell.text().contains("8.40") &&  !row.text().contains("שישי") &&  !row.text().contains("שבת") &&  !cell.text().contains("תיאור"))
                    tmpString.append(",").append(cell.text());
                }
                cleanCells.append("\n").append(tmpString);
            }
            return cleanCells.toString(); // old cleaner .replaceAll("(?m)^[ \t]*\r?\n", "");
        }catch (Exception e){
            cleanCells.append("error");
        }
        return cleanCells.toString();
    }
    
}
