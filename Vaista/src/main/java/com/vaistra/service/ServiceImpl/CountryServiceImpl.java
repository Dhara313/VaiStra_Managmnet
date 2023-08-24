package com.vaistra.service.ServiceImpl;


import com.vaistra.dao.CountryDao;
import com.vaistra.dto.CountryDto;
import com.vaistra.entity.Country;
import com.vaistra.exception.DuplicateEntryException;
import com.vaistra.exception.ResourceNotFoundException;
import com.vaistra.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;


@Service
public class CountryServiceImpl implements CountryService
{

    @Autowired
    CountryDao countryDao;
    private Integer id;


    // .......Utility method.....

    public static CountryDto countryToDto(Country country) {
        return new CountryDto(country.getNo(), country.getCOUNTRY(), country.getSTATUS());
    }

    public static Country dtoToCountry(CountryDto dto) {
        return new Country(dto.getNo(), dto.getCOUNTRY(), dto.getSTATUS(),dto.isDeleted());
    }

    public static List<CountryDto> countriesToDtos(List<Country> countries) {
        List<CountryDto> dtos = new ArrayList<>();
        for (Country c : countries) {
            dtos.add(new CountryDto(c.getNo(), c.getCOUNTRY(), c.getSTATUS()));
        }
        return dtos;
    }

    public static List<Country> dtosToCountries(List<CountryDto> dtos) {
        List<Country> countries = new ArrayList<>();
        for (CountryDto dto : dtos) {
            countries.add(new Country(dto.getNo(),dto.getCOUNTRY(),dto.getSTATUS(),dto.isDeleted()));
        }
        return countries;
    }











    // add country

    @Override
    public CountryDto addCountry(CountryDto c) {

        // HANDLE DUPLICATE NAME ENTRY EXCEPTION
        Country country = countryDao.findByCOUNTRY(c.getCOUNTRY());
        if(country != null)
            throw new DuplicateEntryException("Country with name '"+c.getCOUNTRY()+"' already exist!");

        c.setCOUNTRY(c.getCOUNTRY().toUpperCase());
        return countryToDto(countryDao.save(dtoToCountry(c)));
    }






// pagination and sorting

    @Override
    public List<CountryDto> getAllCountries(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Country> pageCountry = countryDao.findAll(pageable);
        return countriesToDtos(pageCountry.getContent());
    }

    @Override
    public CountryDto getCountryById(@PathVariable("No") int id) {
        return countryToDto(countryDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country with id '" + id + "' Not Found!")));
    }



    @Override
    public String deleteCountryById(@PathVariable("No") int id) {
        countryDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country with Id '" + id + "' not found!"));

        countryDao.deleteById(id);
        return "Country with Id '" + id + "' deleted";
    }

    @Override
    public String softDeleteCountryById(@PathVariable int id) {

        Country country = countryDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country with Id '" + id + "' not found!"));
        country.setSTATUS(String.valueOf(true));
        countryDao.save(country);
        return "Country with Id '" + id + "' Soft Deleted";
    }

    @Override
    public String restoreCountryById(@PathVariable int id) {
        Country country = countryDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country with Id '" + id + "' not found!"));
        country.setSTATUS(String.valueOf(false));
        countryDao.save(country);
        return "Country with id '" + id + "' restored!";
    }



    public CountryDto updateCountry(CountryDto c,@PathVariable int id) {
        // HANDLE IF COUNTRY EXIST BY ID
        Country country = countryDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country with Id '" + id + "' not found!"));

        // HANDLE DUPLICATE ENTRY EXCEPTION
        Country existedCountry = countryDao.findByCOUNTRY(c.getCOUNTRY());
        if(existedCountry != null)
            throw new DuplicateEntryException("Country with name '"+c.getCOUNTRY()+"' already exist!");

        country.setCOUNTRY(c.getCOUNTRY().toUpperCase());
        country.setSTATUS(c.getSTATUS());

        return countryToDto(countryDao.save(country));

    }















//        countryDao.save(country);   // update & save
//            //  questionDao.delete(question); // for delete
//            return new ResponseEntity<String>("Success", HttpStatus.OK);

    // }





//    // get all contry detail
//    public ResponseEntity<List<CountryDto>> getAllCountry() {
//
//            return new ResponseEntity<List<CountryDto>>((MultiValueMap<String, String>) countryDao.findAll(), HttpStatus.OK);
//
//    }





//    // delete  country
//    public ResponseEntity<String> deleteCountry(CountryDto country)
//    {
//            // countryDao.save(country);   // update & save
//            countryDao.delete(countryToDto(country)); // for delete
//            return new ResponseEntity<>("Success", HttpStatus.OK);
//
//
//    }
//
//
//
//
//    // get detail by id
//
//
//    @Transactional(readOnly = true)
//    public CountryDto getCountry(int id ) {
//        Optional<Country> countryResponse = Optional.ofNullable(countryDao.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Country with id '" + id + "' Not Found!")));
//        Country country = countryResponse.get();
//        return countryToDto(country);
//    }
//
//
//
//
//
//    public CountryDto updateCountry(CountryDto c, int id) {
//        // HANDLE IF COUNTRY EXIST BY ID
//        Country country = countryDao.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Country with Id '" + id + "' not found!"));
//
//        // HANDLE DUPLICATE ENTRY EXCEPTION
//        Country existedCountry = countryDao.findByCountryName(c.getCOUNTRY());
//        if(existedCountry != null)
//            throw new DuplicateEntryException("Country with name '"+c.getCOUNTRY()+"' already exist!");
//
//        country.setCOUNTRY(c.getCOUNTRY().toUpperCase());
//        country.setSTATUS(c.getSTATUS());
//
//        return countryToDto(countryDao.save(country));
//
//    }
//
//
//    // delete by id
//    public void deleteCountryById(int id)
//    {
//
//        countryDao.deleteById(id);
//    }




    // update data
//    @Transactional
//    public Country update(CountryDto country) {
//
//
//        Country updateResponse = countryDao.save(country);
//        System.out.println("Data updated Successfully");
//        return updateResponse;
//    }






//    // soft delete
//    public void remove(Integer No)
//    {
//        countryDao.deleteById(No);
//    }


}

//import com.vaistra.dao.CountryDao;
//import com.vaistra.dto.CountryDto;
//import com.vaistra.entity.Country;
//import com.vaistra.exception.DuplicateEntryException;
//import com.vaistra.exception.ResourceNotFoundException;
//import com.vaistra.service.CountryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//
//@Service
//public class CountryServiceImpl implements CountryService {
//
//    //---------------------------------------------------CONSTRUCTOR INJECTION------------------------------------------
//
//    private final CountryDao countryDao;
//
//    @Autowired
//    public CountryServiceImpl(CountryDao countryDao) {
//        this.countryDao = countryDao;
//    }
//
//
//    //    -------------------------------------------------UTILITY METHODS----------------------------------------------
//    public static CountryDto countryToDto(Country country) {
//        return new CountryDto(country.getCountryId(), country.getCountryName(), country.isStatus(), country.isDeleted());
//    }
//
//    public static Country dtoToCountry(CountryDto dto) {
//        return new Country(dto.getCountryId(), dto.getCountryName(), dto.isStatus(), dto.isDeleted());
//    }
//
//    public static List<CountryDto> countriesToDtos(List<Country> countries) {
//        List<CountryDto> dtos = new ArrayList<>();
//        for (Country c : countries) {
//            dtos.add(new CountryDto(c.getCountryId(), c.getCountryName(), c.isStatus(), c.isDeleted()));
//        }
//        return dtos;
//    }
//
//    public static List<Country> dtosToCountries(List<CountryDto> dtos) {
//        List<Country> countries = new ArrayList<>();
//        for (CountryDto dto : dtos) {
//            countries.add(new Country(dto.getCountryId(), dto.getCountryName(), dto.isStatus(), dto.isDeleted()));
//        }
//        return countries;
//    }
//
//
//    //----------------------------------------------------SERVICE METHODS-----------------------------------------------
//
//    @Override
//    public CountryDto addCountry(CountryDto c) {
//
//        // HANDLE DUPLICATE NAME ENTRY EXCEPTION
//        Country country = countryDao.findByCountryName(c.getCountryName());
//        if(country != null)
//            throw new DuplicateEntryException("Country with name '"+c.getCountryName()+"' already exist!");
//
//        c.setCountryName(c.getCountryName().toUpperCase());
//        return countryToDto(countryDao.save(dtoToCountry(c)));
//    }
//
//    @Override
//    public CountryDto getCountryById(int id) {
//        return countryToDto(countryDao.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Country with id '" + id + "' Not Found!")));
//    }
//
//    @Override
//    public List<CountryDto> getAllCountries(int pageNumber, int pageSize, String sortBy, String sortDirection) {
//        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ?
//                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
//        Page<Country> pageCountry = countryDao.findAll(pageable);
//        return countriesToDtos(pageCountry.getContent());
//    }
//
//    @Override
//    public CountryDto updateCountry(CountryDto c, int id) {
//        // HANDLE IF COUNTRY EXIST BY ID
//        Country country = countryDao.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Country with Id '" + id + "' not found!"));
//
//        // HANDLE DUPLICATE ENTRY EXCEPTION
//        Country existedCountry = countryDao.findByCountryName(c.getCountryName());
//        if(existedCountry != null)
//            throw new DuplicateEntryException("Country with name '"+c.getCountryName()+"' already exist!");
//
//        country.setCountryName(c.getCountryName().toUpperCase());
//        country.setStatus(c.isStatus());
//        country.setDeleted(c.isDeleted());
//        return countryToDto(countryDao.save(country));
//
//    }
//
//    @Override
//    public String deleteCountryById(int id) {
//        countryDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country with Id '" + id + "' not found!"));
//
//        countryDao.deleteById(id);
//        return "Country with Id '" + id + "' deleted";
//    }
//
//    @Override
//    public String softDeleteCountryById(int id) {
//
//        Country country = countryDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country with Id '" + id + "' not found!"));
//        country.setDeleted(true);
//        countryDao.save(country);
//        return "Country with Id '" + id + "' Soft Deleted";
//    }
//
//    @Override
//    public String restoreCountryById(int id) {
//        Country country = countryDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Country with Id '" + id + "' not found!"));
//        country.setDeleted(false);
//        countryDao.save(country);
//        return "Country with id '" + id + "' restored!";
//    }
//}