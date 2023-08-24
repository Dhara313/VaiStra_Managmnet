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


}












