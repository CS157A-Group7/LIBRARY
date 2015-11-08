/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/** Class to read library branch dataset and create insertion queries.
 *
 * @author Aishwarya
 */
public class branchReader
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        //read the file
        FileReader fileReader = new FileReader("branch.txt");

        BufferedReader bufferedReader =    new BufferedReader(fileReader);

           String line = "";
           String title = "";
           String address = "";
            while((line = bufferedReader.readLine()) != null) 
            {
                if(line.contains("title"))
                {
                    title = line.substring(line.indexOf("<title>")+7,line.indexOf("</title>"));
                    //System.out.println(title);
                }
                else
                {
                    if(line.contains("td"))
                    {
                        line = (line.substring(line.indexOf("<td>")+4));
                        if(line.contains("/td"))
                            line = line.substring(0, line.indexOf("</td>"));
                        if(line.contains("<br>"))
                            line = line.substring(0,line.indexOf("<br>"))+" " + line.substring(line.indexOf("<br>")+4) ;
                        address = line;
                        //System.out.println(address);
                    }
                }
                if(!title.equals("") && !address.equals(""))
                {
                    System.out.println("insert into LibraryBranch(branchName,address) values(\"" + title + "\",\"" + address + "\");");
                title = "";
                address = "";
                }
            }   

        //if contains title - get the right part
        //if not, it is an address line. break accordingly.
    }
    
}
