package com.hamdanas;

import com.hamdanas.controllers.MedController;
import com.hamdanas.utilities.CommonUtils;

public class App 
{
    public static void main( String[] args )
    {
        new MedController(CommonUtils.getJsonConvertor());
    }
}
