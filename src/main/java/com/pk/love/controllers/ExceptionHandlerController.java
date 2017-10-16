package com.pk.love.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.pk.love.exceptions.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleNumberFormat(Exception ex){
		log.error("Bad Request for Number Format Exception");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("400Error");
		mv.addObject("exception", ex);
		return mv;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception ex){
		log.error("Bad Request for Not found");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("404Error");
		mv.addObject("exception", ex);
		return mv;
	}

}
