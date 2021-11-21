package com.hamdanas;

import com.hamdanas.routes.Router;

public class App 
{
    public static void main( String[] args )
    {
        Router routes = Router.getInstance();
        routes.init();
    }
}
