 package org.alfresco.wcm.client.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alfresco.wcm.client.Asset;

public abstract class HeaderHelper 
{
    private ThreadLocal<SimpleDateFormat> httpDateFormat = new ThreadLocal<SimpleDateFormat>() 
    {
        @Override
        protected SimpleDateFormat initialValue()
        {
            return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        }
    };  	     

    /**
     * This base implementation simply returns true to indicate that the request should be re-rendered.
     * Override in a subclass as necessary
     * @param asset Asset
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return boolean true if browser has old copy and so content should be rendered
     */
    public boolean setHeaders(Asset asset, HttpServletRequest request, HttpServletResponse response) 
    {
        return setHeaders(asset, false, request, response);
    }
    
    /**
     * This base implementation simply returns true to indicate that the request should be re-rendered.
     * Override in a subclass as necessary
     * @param asset Asset
     * @param attach boolean
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return boolean true if browser has old copy and so content should be rendered
     */
    public boolean setHeaders(Asset asset, boolean attach, HttpServletRequest request, HttpServletResponse response) 
    {
        return true;
    }
    
    public final String getHttpDate(Date date)
    {
        return dateFormatter().format(date);
    }

    public final Date getDateFromHttpDate(String date) throws ParseException
    {
        return dateFormatter().parse(date);
    }
    
    /**
     * Get a date formatter for the thread as SimpleDateFormat is not thread-safe
     * @return SimpleDateFormat
     */
    public final SimpleDateFormat dateFormatter() 
    {
    	return httpDateFormat.get();
    }	
}
