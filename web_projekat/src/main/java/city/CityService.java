package city;

import java.util.List;

public class CityService {

    public static List<City> getCities(){
        return CityRepo.getCities();
    }
}
