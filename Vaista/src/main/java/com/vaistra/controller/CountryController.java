package com.vaistra.controller;

import com.vaistra.dto.CountryDto;
import com.vaistra.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RequestMapping("country")
    @RestController
    public class CountryController
    {

        @Autowired
        CountryService countryservice;
        @GetMapping("/allcountry")
        public ResponseEntity<List<CountryDto>> getAllCountries(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                @RequestParam(value = "sortBy", defaultValue = "Id", required = false) String sortBy,
                                                                @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection)
        {

            return new ResponseEntity<List<CountryDto>>( countryservice.getAllCountries(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
        }


        @PostMapping
        public ResponseEntity<CountryDto> addCountry(@Valid @RequestBody CountryDto country) {
            return new ResponseEntity<>(countryservice.addCountry(country), HttpStatus.CREATED);
        }





        @GetMapping("/{id}")
        public ResponseEntity<CountryDto> getCountryById(@PathVariable int id) {
            return new ResponseEntity<>(countryservice.getCountryById(id), HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<CountryDto> updateCountry(@RequestBody CountryDto country, @PathVariable int id) {
            return new ResponseEntity<>(countryservice.updateCountry(country, id), HttpStatus.ACCEPTED);
        }

        @DeleteMapping("hardDelete/{id}")
        public ResponseEntity<String> deleteCountryById(@PathVariable int id) {
            return new ResponseEntity<>(countryservice.deleteCountryById(id), HttpStatus.OK);
        }

        @DeleteMapping("{id}")
        public ResponseEntity<String> softDeleteById(@PathVariable int id) {
            return new ResponseEntity<>(countryservice.softDeleteCountryById(id), HttpStatus.OK);
        }


        @PutMapping("restore/{id}")
        public ResponseEntity<String> restoreCountryById(@PathVariable int id) {
            return new ResponseEntity<>(countryservice.restoreCountryById(id), HttpStatus.OK);
        }








































//    @GetMapping("allcountry")
//    public ResponseEntity<List<CountryDto>> getAllcountry()
//    {
//
//
//        return countryservice.getAllCountry();
//    }
//
//
//     //add country
//    @PostMapping("add")
//    public CountryDto addCountry (@Valid @RequestBody CountryDto country)
//    {
//        return  countryservice.addCountry(country);
//       // return ResponseEntity.ok("User is valid");
//
//    }
//
//    // delete country
//    @PostMapping("delete")
//    public ResponseEntity<String> deleteCountry(@RequestBody CountryDto country)
//    {
//        return  countryservice.deleteCountry(country);
//
//    }
//
//
//    // get detail by id
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public CountryDto getCountry(@PathVariable int id)
//    {
//        CountryDto countryResponse = (CountryDto) countryservice.getCountry(id);
//        return countryResponse;
//    }
//
//
//    // delete by id
//
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public String deleteCountryById(@PathVariable int id) {
//        countryservice.deleteCountryById(id);
//        return "data has been deleted successfully";
//    }
//
//
//    // update data
//
//    @PutMapping("{id}")
//    public ResponseEntity<CountryDto> updateCountry(@RequestBody CountryDto country, @PathVariable int id) {
//        return new ResponseEntity<>(countryservice.updateCountry(country, id), HttpStatus.ACCEPTED);
//    }
//
//
//
//    // apply soft delete
//
//    @DeleteMapping("delete/{id}")
//    public void removeOne(@PathVariable("No") Integer id)
//    {
//        countryservice.remove(id);
//    }
//


    }


