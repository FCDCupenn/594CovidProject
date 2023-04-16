package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.Property;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyReader {



        public CSVReader csvReader;
        private Map<String, Integer> headerID;
        private ArrayList<Property> propertyDataList = new ArrayList<>();

        public PropertyReader(String filename) throws IOException {
            try( var reader = new CharacterReader(filename)){
                csvReader = new CSVReader(reader);
                headerID =csvReader.generateHeaderID();
                String[] row;
                while((row =csvReader.readRow())!=null){
                    Double marketValue = parseStringtoInt(row[headerID.get("market_value")]);
                    Double totalLivableArea = parseStringtoInt(row[headerID.get("total_livable_area")]);
                    String zipCode = row[headerID.get("zip_code")];


                    //skip invalid digit
                    if(zipCode.length() < 5){
                        continue;
                    }else {
                        Pattern p = Pattern.compile("\\d{5}");
                        Matcher m = p.matcher(zipCode);
                        if(m.find()){
                        zipCode = m.group();}
                        else{
                            continue;
                    }


                    Property eachPropertyRecord = new Property(marketValue, totalLivableArea,zipCode);
                        propertyDataList.add(eachPropertyRecord);
                }

            }
            } catch (CSVFormatException e) {
                throw new RuntimeException(e);
            }
            System.out.println("peoperty data List size:"+ propertyDataList.size());


        }



    private Double parseStringtoInt(String text){
        if(text.isEmpty()){
            return Double.NEGATIVE_INFINITY;
        }else{
            return Double.parseDouble(text);
        }
    }

    public List<Property> getPropertiesDataList(){
        return propertyDataList;
    }


}
