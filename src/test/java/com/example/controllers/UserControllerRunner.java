package com.example.controllers;

import com.intuit.karate.junit5.Karate;

class UserControllerRunner {

    @Karate.Test
    Karate testUsers() {
        return Karate.run("getUsers").tags("@ts1").relativeTo(getClass());
    }

}

