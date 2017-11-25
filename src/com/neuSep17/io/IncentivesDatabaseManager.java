package com.neuSep17.io;

import com.neuSep17.dao.Incentive;
import com.sun.media.sound.InvalidDataException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IncentivesDatabaseManager {
    private String dbFilePath;
    private ObjectMapper mapper = new ObjectMapper();

    public IncentivesDatabaseManager(String dbfilePath) throws IOException {
        this.dbFilePath = dbfilePath;
        CreateDbFileIfNotExists();
    }

    public IncentivesDatabaseManager() throws Exception {
        this.dbFilePath = System.getProperty("user.home") + "\\incentives_db.json";

        CreateDbFileIfNotExists();
    }

    private void CreateDbFileIfNotExists() throws IOException {
        Path p = Paths.get(dbFilePath);
        if (!Files.exists(p)) {
            File f = new File(dbFilePath);
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
    }

    public void add(Incentive incentive) throws Exception {
        if (!IsNullOrEmpty(incentive.getId())) {
            throw new InvalidDataException("Incentive Id is Auto Generate. Unable to add the record to the database.");
        }
        String newId = "Incentive_" + System.currentTimeMillis();
        incentive.setId(newId);
        List<Incentive> allIncentives = getAllIncentives();
        allIncentives.add(incentive);
        WriteIncentives(allIncentives);
    }

    public void update(Incentive incentive)
    {
        //TODO: implement update incentives
    }

    public void delete(Incentive incentive)
    {
        //TODO: implement delete incentives
    }

    private void WriteIncentives(List<Incentive> allIncentives) {
        try {
            mapper.defaultPrettyPrintingWriter().writeValue(new File(dbFilePath), allIncentives);
            //mapper.defaultPrettyPrintingWriter().writeValue(new File("C:\\Users\\nisha\\Desktop\\Mama\\incentives.json"),incentive2);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean IsNullOrEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public List<Incentive> getAllIncentives() throws Exception {
        List<Incentive> allIncentives = new ArrayList<>();
        try {
            allIncentives = mapper.readValue(new File(dbFilePath), List.class);
        } catch (EOFException e) {
        }
        return allIncentives;
    }
}


/*
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JavaToJSONExample
{
   public static void main(String[] args)
   {
      @SuppressWarnings("deprecation")
      Employee employee = new Employee(1, "Lokesh", "Gupta", new Date(1981,8,18));
      ObjectMapper mapper = new ObjectMapper();
      try
      {
         mapper.writeValue(new File("c://temp/employee.json"), employee);
      } catch (JsonGenerationException e)
      {
         e.printStackTrace();
      } catch (JsonMappingException e)
      {
         e.printStackTrace();
      } catch (IOException e)
      {
         e.printStackTrace();
      }
   }
}
 */
