/*
 * Copyright 2000-2001,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jetspeed.util;

//standard java stuff
import java.util.Hashtable;
import java.util.Map;
import java.util.Iterator;

/**
Defines a standard object configuration
A Config provides the parameters passed in the current request as well
as init parameters.

@author <a href="mailto:raphael@apache.org">Rapha�l Luta</a>
@version $Id: BaseConfig.java,v 1.3 2004/02/23 03:23:42 jford Exp $
*/

public class BaseConfig extends Hashtable<String, Object> implements Config
{

    private String name = null;

    /**
    Returns the name for this configuration
    */
    public String getName()
    {
        return this.name;
    }

    /**
    Sets the name of this configuration
    */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
    Used to define a Portlet's parameters.
    */
    @Override
    public void setInitParameters( Map<String, Object> init_params )
    {
        clear();
        putAll( init_params );
    }

    /**
    Used to override Portlet's parameters.
    */
    @Override
    public void addInitParameters( Map<String, Object> init_params )
    {
        Iterator<?> keys = init_params.keySet().iterator();
        
        while (keys.hasNext() )
        {
            String key = (String)keys.next();
            
            if( ! containsKey( key ) )
            {
                put( key , (String)init_params.get( key ) );
            }
        }
    }


    /**
    Retrieves the PortletController parameters
    */
    public Map<String, Object> getInitParameters()
    {
        return this;
    }


    /**
    Used to define a PortletController's parameter.if value is null, removes
    the key from the stored properties
    */
    public void setInitParameter(String name, Object value)
    {
        if (name!=null)
        {
            if (value==null)
            {
                remove(name);
            }
            else
            {
                put(name,value);
            }
        }
    }

    /**
    Returns a parameter (or null) that was given the controller.
    */
    @Override
    public Object getInitParameter(String name)
    {
        return getInitParameter( name, null );
    }

    /**
    Returns a parameter (or defaultValue) that was given the controller.
    */
    @Override
    public Object getInitParameter(String name, Object defaultValue)
    {
      Object value = null;

        try
        {
            value = get(name);
            if (value==null) value=defaultValue;
        }
        catch (RuntimeException e)
        {
            value=defaultValue;
        }

        return value;
    }

    /**
    Returns the parameter names of this Config.
    */
    public Iterator<String> getInitParameterNames()
    {
        return keySet().iterator();
    }

}
