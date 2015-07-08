'use strict';

/**
 * @ngdoc overview
 * @name appApp
 * @description
 * # appApp
 *
 * Main module of the application.
 */
angular
    .module('appApp', [
        'ngAnimate',
        'ngCookies',
        'ngResource',
        'ngRoute',
        'ngSanitize',
        'ngTouch',
        'ngMaterial',
        'firebase'
    ])
    .config(function ($routeProvider) {

        $routeProvider
            .when('/', {
                templateUrl: 'views/main.html',
                controller: 'MainCtrl'
            })
            .when('/about', {
                templateUrl: 'views/about.html',
                controller: 'AboutCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    });

function init() {
    var ROOT = 'https://hscsession.appspot.com/_ah/api';
    gapi.client.load('hscsession', 'v1', function () {
        console.log("GAPI Loaded.");
        window.init();
    }, ROOT);
    /*Add user to local storage.*/
    console.log("User=" + localStorage.getItem("user"));
    if (!localStorage.getItem("user")) {
        localStorage.setItem("user", prompt("Enter your emp id."));
        $('#user').html(localStorage.getItem("user"));
    }
}
