package com.ame.spring.converters;

import org.springframework.core.convert.converter.Converter;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

public class XMLGregorianCalendar2DateConverter implements Converter<XMLGregorianCalendar, Date> {
    @Override
    public Date convert(XMLGregorianCalendar source) {
        return source.toGregorianCalendar().getTime();
    }
}
