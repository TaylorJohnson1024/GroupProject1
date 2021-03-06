package groupproject1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Class to Export All Readings in to JSON file
 * @author Zinet
 * @Date Sun Jan, 28 2018
 */
public class ExportAllReadings 
{
    //readings export filename 
    private String exportFileName;
    
    /**
    * Constructor
    * @param exportFileName filename to export all readings to
    */
    public ExportAllReadings(String exportFileName)
    {
        this.exportFileName = exportFileName;
    }
    
    /**
    * Method to read patient record and generates all readings export data
     * @param patientReadings
     * @throws java.io.FileNotFoundException
    */
    public void parseJSONAndExportAllReadings(JSONArray patientReadings) throws FileNotFoundException
    {
        // creating JSON Object
        JSONObject stdObject = new JSONObject();
        // creating JSON Array 
        JSONArray jsonArray = new JSONArray();
        //define LinkedHashMap variable 
        Map lhm = new LinkedHashMap();
        
        // iterating patient readings
        Iterator itr2 = patientReadings.iterator();
        Iterator<Map.Entry> itr1;
        while (itr2.hasNext()) 
        {
            itr1 = ((Map) itr2.next()).entrySet().iterator();
            lhm = new LinkedHashMap(4);  // create a new LinkedHashMap
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                //add only selected fields to the array 
                if(pair.getKey().equals("reading_id") 
                    || pair.getKey().equals("reading_type")
                    || pair.getKey().equals("reading_value")
                    || pair.getKey().equals("reading_date")
                )
                {
                    //add the object key and value to the map variable
                    lhm.put(pair.getKey(), pair.getValue());
                    
                }
                
            }
          //add the map variable to the array
            jsonArray.add(lhm); // This was moved out of the inner while loop to avoid redundancy
        }
        // putting readings to JSONObject
        stdObject.put("readings", jsonArray);
        PrintWriter pw = new PrintWriter(this.exportFileName);
        //write the export out put to the file 
        pw.write(stdObject.toJSONString());
        //close the writer
        pw.flush();
        
    }          
}