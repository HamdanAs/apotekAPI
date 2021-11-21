package com.hamdanas.routes;

import static spark.Spark.*;

import com.hamdanas.controllers.MedController;

public class Router{

    private static Router instance = new Router();

    private final MedController medController;

    private Router(){
        medController = new MedController();
    }

    public static Router getInstance(){
        return instance;
    }

    public void init(){
        path("/api", () -> {
            before("/*", (q, a) -> System.out.println("Accessing API Endpoint"));
            path("/med", () -> {
                get("/", (req, res) -> medController.getAll(res));
                get("/:id", (req, res) -> medController.find(req, res));
                post("/add", (req, res) -> medController.insert(req, res));
                patch("/:id/update", (req, res) -> medController.update(req, res));
                delete("/:id/delete", (req, res) -> medController.delete(req, res));
            });
        });
    }
}