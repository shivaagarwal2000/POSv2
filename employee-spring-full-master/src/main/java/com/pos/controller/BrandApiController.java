package com.pos.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.pos.dto.BrandDto;
import com.pos.model.MessageData;
import com.pos.model.data.BrandData;
import com.pos.model.forms.BrandForm;
import org.commons.util.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: class level request mapping
@Api
@RestController
public class BrandApiController {

    @Autowired
    private BrandDto brandDto;

    @ApiOperation(value = "Adds a brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        brandDto.add(form);
    }

    @ApiOperation(value = "upload bulk brands") // TODO: logic - change to use this than loopwise add
    @RequestMapping(path = "/api/brand/all", method = RequestMethod.POST)
    public void bulkAdd(@RequestBody List<BrandForm> forms) throws ApiException {
        brandDto.bulkAdd(forms);
    }

    @ApiOperation(value = "gets a brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        return brandDto.get(id);
    }

    @ApiOperation(value = "Gets list of all brands")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        return brandDto.getAll();
    }

    @ApiOperation(value = "updates a brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm brandForm) throws ApiException {
        brandDto.update(id, brandForm);
    }

    @ApiOperation(value = "deletes a brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException {
        brandDto.delete(id);
    }

//    @ExceptionHandler(Exception.class)
//    public String handleIOException(NumberFormatException ex, HttpServletRequest request) {
//        return ClassUtils.getShortName(ex.getClass());
//    }

}
