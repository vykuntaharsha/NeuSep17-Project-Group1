/**
 * @author aarabhi pugazhendhi
 *
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class VehicleDataGen {
    
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String SOURCE_DATA_DIRECTORY = "C:\\Users\\aarabhi\\Downloads\\NeuSep17-Project-Group1-master\\NeuSep17-Project-Group1-master\\data";
    private static final String DESTINATION_DATA_DIRECTORY = "C:\\Users\\aarabhi\\Desktop\\data";

    String[] entertainment = {"iDrive4.2 infotainment system","mBrace2 and a COMAND infotainment system","Bang & Olufsen premium audio system, HD Radio, real-time traffic reports, and Bluetooth streaming audio","14-speaker Bose premium audio system, Audi Connect service, Bluetooth streaming audio, and a rear-seat entertainment system","1,700-watt Meridian Signature Reference surround-sound system","16-speaker Burmeister surround-sound system"};
    String[] interiorColor = {"Charcoal Black","Ardent Red","Tangerine Orange","Black Raven","Pearl White","Bottle Green"};
    String[] exteriorColor = {"Iridium Silver","Orkney Grey","Cognac Metallic","Ardent Red","Racing blue","Techno Pink"};
    String[] fuelType = {"Premium unleaded petrol","Super unleaded petrol","Diesel","LPG Autogas","Biogas","LPG"};
    String[] engine = {"3.5L V6 SOHC 24V","2.0L 4Cyl Turbo","3.0L V6 Supercharged","2.0L I4 TURBO","3.0L TT V6 double overhead c","4.6L V8 380hp"};
    String[] transmission = {"Automatic","Allison 3000 6-Speed","8-Speed Automatic w/Sequenti","Manual"};
    String[] battery = {"Deep Car Battery","SLI Battery","VRLA Battery","AGM Battery","AGM BAttery"};
    String[] optionalFeatures = {"Adaptive Cruise Control","Ambient lighting","Anti-theft system (alarm with remote)","Blind Spot Monitor","Climate Control","Clock","Driver Vanity Mirror"};
    
    Random random = new Random();
     String vin;
     String entertain;
     String icolor;
     String ecolor;
    String fuel;
    String engi;
    String trans;
    String batt;
    String option;
    
    private  String randomAlphaNumeric(int count) 
    {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) 
        {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
    
    private ArrayList<String> getData()
    {
        ArrayList<String> features = new ArrayList<String>();
        
        vin = randomAlphaNumeric(12);
        entertain = entertainment[random.nextInt(entertainment.length)];
        icolor = interiorColor[random.nextInt(interiorColor.length)];
        ecolor = exteriorColor[random.nextInt(exteriorColor.length)];
        fuel = fuelType[random.nextInt(fuelType.length)];
        engi = engine[random.nextInt(engine.length)];
        trans = transmission[random.nextInt(transmission.length)];
        batt = battery[random.nextInt(battery.length)];
        option = optionalFeatures[random.nextInt(optionalFeatures.length)];
        
        features.add(vin);
        features.add(entertain);
        features.add(icolor);
        features.add(ecolor);
        features.add(fuel);
        features.add(engi);
        features.add(trans);
        features.add(batt);
        features.add(option);

        return features;
    }
    private void insertData(BufferedReader buffread, BufferedWriter buffwrite) throws IOException
    {
        String line;
        while((line = buffread.readLine())!=null)
        {
            ArrayList<String> featureList = new ArrayList<String>();
            featureList = getData();
            if(line.startsWith("id"))
            {
                  line = line.concat("~").concat("vin").concat("~").concat("entertainment").concat("~").concat("interiorcolor")
                          .concat("~").concat("exteriorcolor").concat("~").concat("fuel").concat("~").concat("engine").concat("~").concat("transmission").concat("~").concat("battery").concat("~").concat("optionalfeatures");
                  buffwrite.write(line);
            }
            else
            {
                for(int i = 0;i<featureList.size();i++)
                {
                    line = line.concat("~").concat(featureList.get(i));
                }
                buffwrite.write("\r\n");
                buffwrite.write(line);   
               
            }
        }
    }
    private  void readAndWriteFiles(File[] file) throws IOException,FileNotFoundException {
        String name = null;
            for(int i = 0 ; i<file.length;i++)
            {
                name = file[i].getName();
                if(!(name.equalsIgnoreCase("dealers")||name.equalsIgnoreCase("incentives")))
                {
                    FileReader reader = null;
                    reader = new FileReader(file[i]);
                    BufferedReader buffread = new BufferedReader(reader); 
                    
                    FileWriter writer = new FileWriter(DESTINATION_DATA_DIRECTORY+file[i].getName());
                    BufferedWriter buffwrite = new BufferedWriter(writer);
     
                    insertData(buffread,buffwrite);
                    buffwrite.close();
                    writer.close();
                    buffread.close();
                    reader.close();  
                }                
            } 
     } 
    public static void main(String args[]) 
    {        
        File dir = new File(SOURCE_DATA_DIRECTORY);
        File[] file = dir.listFiles();
        VehicleDataGen vd = new VehicleDataGen();
        try {
            vd.readAndWriteFiles(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

          
    

    

}
