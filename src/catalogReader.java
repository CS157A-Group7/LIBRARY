/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

/**File to read library catalog dataset and create insertion queries.
 *
 * @author Aishwarya
 */
public class catalogReader
{


    static Random random = new Random(System.currentTimeMillis());
    static int total=0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        //read the file
        FileReader fileReader = new FileReader("t.csv");

        BufferedReader bufferedReader =    new BufferedReader(fileReader);

           String line = "";
           //String title = "";
           //String address = "";
           int i = 0;
           int j = 0;
            while((line = bufferedReader.readLine()) != null) 
            {

                  i++;
                  
                  if(i%5 == 0 && !line.contains("'") && !line.contains("1.")&& !line.contains("2.")&& !line.contains("3.")&& !line.contains("4.")&& !line.contains("5.")&& !line.contains("6.")&& !line.contains("7.")&& !line.contains("8.")&& !line.contains("9."))
                  {
                      j++;
                //we have one whole line containing all the required info.
                   //obtain from call #
                   int comma = 0;
                   comma = line.indexOf(',',0);
                    String type1 = line.substring(0,comma);
                    String type = "";
                    
                    if(type1.length() == 0)
                        type = "";
                    else
                    {
                    if(type1.contains("VIDEO"))
                        type=type +"VIDEO ";
                    if(type1.contains("DVD"))
                        type=type +"DVD ";
                    if(type1.contains("CD-ROM"))
                        type=type +"CD ";
                    if(type1.contains("WEB"))
                        type=type +"WEBSITE ";
                    if(type1.charAt(0) == 'J' || type1.charAt(0) == 'J')
                        type=type +"JOURNAL ";
                    if(type1.charAt(0) == 'q')
                       type=type +"SCIENCE ";
                    if(type1.contains("FIC"))
                    {
                        type=type +"FICTION ";
                    }
                    if(type1.contains("AUDIO"))
                        type=type +"AUDIO ";
                    if(type1.contains("eBook") ||type1.contains("eBOOK"))
                        type=type +"EBOOK ";
                    if(type1.contains("NEIG"))
                        type=type +"PICTURE ";
                    if(type1.contains("Mu"))
                        type=type + "MUSIC ";
                    if(type1.contains("SPA"))
                        type=type + "SPANISH ";
                    if(type1.contains("FRE"))
                        type= type + "FRENCH ";
                    if(type1.contains("HEB"))
                        type=type + "HEBREW ";
                    if(type1.contains("RUS"))
                        type=type + "RUSSIAN ";
                    if(type1.contains("GER"))
                        type= type + "GERMAN ";
                    if(type1.contains("CHI") )
                       type=type + "CHINESE ";
                    if(type1.contains("HIN") )
                       type= type + "HINDI ";
                    if( type.length()==0)
                        type = "BOOK";
                    }

                    

                    int oldComma = comma;
                    String author = "null";
                    
                    if(line.length()>=oldComma+2 && line.charAt(oldComma) == ',' && line.charAt(oldComma+1) == '"')
                   {
                       int start = oldComma+2;
                      int end = line.indexOf("\"",start+1);
                      
                         author = line.substring(start,end );
                         oldComma = end+1;
                   } 
                   else if(line.length()>=oldComma+2 && line.charAt(oldComma) == ',')
                   {
                       comma = line.indexOf(",",oldComma+1);
                    author = line.substring(oldComma+1,comma);
                    oldComma = comma;
                   }   
                   else 
                    {
                        author ="null";
                        oldComma++;
                    }
                     if(author.length()>120)
                       author = author.substring(0,120);

                    
                   String title = "null";

                   if(line.length()>=oldComma+2 && line.charAt(oldComma) == ',' && line.charAt(oldComma+1) == '"')
                   {
                       int start = oldComma+2;
                      int end = line.indexOf("\"",start+1);
                      
                         title = line.substring(start,end );
                         oldComma = end+1;
                   } 
                   else if(line.length()>=oldComma+2 && line.charAt(oldComma) == ',')
                   {
                       comma = line.indexOf(",",oldComma+1);
                    title = line.substring(oldComma+1,comma);
                    oldComma = comma;
                   }   
                   else 
                    {
                        title ="null";
                        oldComma++;
                    }
                   if( title.length()>0 && title.charAt(title.length()-1)=='.')
                       title = title.substring(0,title.length()-1);
                   if(title.length()>150)
                       title = title.substring(0,150);
                    
                   
                   String edition = "null";
                   if(line.length()>=oldComma+2 && line.charAt(oldComma) == ',')
                   {
                      
                       comma = line.indexOf(",",oldComma+1);
                    edition = line.substring(oldComma+1,comma);
                    oldComma = comma;
                    if(edition.length()>0 && edition.charAt(0)=='"')
                    {
                    edition = edition.substring(1);
                    }
                   }   
                   else 
                    {
                        edition ="null";
                        oldComma++;
                    }

                   
                   String skip = "";
                    if(line.length()>=oldComma+2 && line.charAt(oldComma) == ',' && line.charAt(oldComma+1) == '"')
                   {
                       int start = oldComma+2;
                      int end = line.indexOf("\"",start+1);
                      
                         skip = line.substring(start,end );
                         oldComma = end+1;
                   } 
                   else if(line.length()>=oldComma+2 && line.charAt(oldComma) == ',')
                   {
                       comma = line.indexOf(",",oldComma+1);
                    skip = line.substring(oldComma+1,comma);
                    oldComma = comma;
                    
                   }   
                   else 
                    {
                        skip ="";
                        oldComma++;
                    }

                   
                   String isbn = "null";
                   if(line.length()>=oldComma+2 && line.charAt(oldComma) == ',')
                   {
                    isbn = line.substring(oldComma+1);
                    if(isbn.contains(":"))
                    {
                    comma = line.indexOf(":",oldComma+1);
                    isbn = line.substring(oldComma+1,comma);
                    }
                    if(isbn.length()>0 && isbn.charAt(0)=='"')
                    {
                    isbn = isbn.substring(1);
                    }
                    if(isbn.contains("$"))
                    {
                    isbn = isbn.substring(0,isbn.indexOf('$'));
                    }
                    if(isbn.contains(" "))
                    {
                    isbn = isbn.substring(0,isbn.indexOf(' '));
                    }
                    if(isbn.contains("\""))
                    {
                    isbn = isbn.substring(0,isbn.indexOf('"'));
                    }
                    if(isbn.length()>0 && (isbn.charAt(isbn.length()-1)=='.' || isbn.charAt(isbn.length()-1)=='X' || isbn.charAt(isbn.length()-1)=='-' || isbn.charAt(isbn.length()-1)=='*' || !Character.isDigit(isbn.charAt(isbn.length()-1))))
                    {
                        
                       isbn = isbn.substring(0,isbn.length()-1);
                           
                    }
                    if(isbn.length()>0 && (isbn.contains("download") || isbn.contains("sound")) || isbn.contains("none") || isbn.contains("Ne") )
                    {
                       isbn = "null";
                    }
                   }   
                   else 
                    {
                        isbn ="null";
                        oldComma++;
                    }
                   
                   if(isbn.length()==0 || "".equals(isbn) || isbn==null || isbn.contains("NONE") || (isbn.length()>2 && !Character.isDigit(isbn.charAt(1))))
                       isbn = "null";
                   if(title.length()==0)
                       title = "null";
                   if(author.length()==0)
                       author = "null";
                   if(type.length()==0)
                       type = "null";
                   if(edition.length()==0)
                       edition = "null";

                   //Copies and library branch id are going to be randomly allocated.
                   createQuery(title,author,type,edition,isbn);
                   
              
                  }
                  
            }   
            System.out.println(total);
        //if contains title - get the right part
        //if not, it is an address line. break accordingly.
    }

    private static void createQuery(String title, String author, String type,
            String edition, String isbn)
    {
                   int numberOfBranchOccurences = random.nextInt(5 ) + 1; //a random number between 1-5.
                   int libraryBranchID[] = new int[numberOfBranchOccurences];//allocate a random number between 1-65.
                   int copies[] = new int[numberOfBranchOccurences];//allocate a random number between 1-3.
                  
                   for(int j=0;j<numberOfBranchOccurences;j++)
                   {
                       int tempRand = random.nextInt(65 ) + 1;
                       while(contains(libraryBranchID,tempRand))
                       {
                          tempRand = random.nextInt(65 ) + 1;
                       }
                       libraryBranchID[j] = tempRand;
                       copies[j] = random.nextInt(3 ) + 1;
                   }
                   
                   
                    String query = "";
                    int i = 0;
                    while(i<numberOfBranchOccurences)
                    {
                       
                    query = "insert into item(title,author,edition,libraryBranchID,ItemType,standardNumber,copies) values("+
                            clean(title) + ","+
                            clean(author) + "," + 
                            clean(edition) +"," + 
                            libraryBranchID[i] + "," +
                            clean(type) + "," + 
                            isbn + "," + 
                            copies[i] + ");"; 
                            System.out.println(query);
                    
                    total ++;
                    i++;
                    
                    
                    }
                    
                        
    }

    private static boolean contains(int[] libraryBranchID, int tempRand)
    {
       for(int i=0;i<libraryBranchID.length;i++)
       {
           if(tempRand == libraryBranchID[i])
               return true;
       }
       return false;
       
           
       }

    private static String clean(String word)
    {
        if(word.equals("null"))
            return "null";
        else 
            return "\"" + word + "\"";
    }

    
}


