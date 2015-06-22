'use strict';

/**
 * @ngdoc overview
 * @name Carrot
 * @description
 * # Carrot
 *
 * Main module of the application.
 */
angular
    .module('Carrot', [
        'ngAnimate',
        'ngCookies',
        'ngResource',
        'ngRoute',
        'ngSanitize',
        'ngTouch',
        'ngTable',
        'angular-flash.service',
        'angular-flash.flash-alert-directive',
        'angular-loading-bar',
        'ui.bootstrap',
        'angular.validators'
    ])
    .config(function ($routeProvider, flashProvider, cfpLoadingBarProvider) {
        // General
        flashProvider.errorClassnames.push('alert-danger');
        flashProvider.warnClassnames.push('alert-warning');
        flashProvider.infoClassnames.push('alert-info');
        flashProvider.successClassnames.push('alert-success');
        cfpLoadingBarProvider.includeSpinner = true;
        //cfpLoadingBarProvider.latencyThreshold = 200;

        $routeProvider
            // Login
            .when('/login', {
                templateUrl: 'views/login.html',
                controller: 'LoginController'
            })
            // Content
            .when('/', {
                templateUrl: 'views/dashboard.html',
                controller: 'DashboardController'
            })
            .when('/apps', {
                templateUrl: 'views/apps.html',
                controller: 'AppController'
            })
            .when('/apps/:id', {
                templateUrl: 'views/apps_edit.html',
                controller: 'AppViewController'
            })
            .when('/apps/new', {
                templateUrl: 'views/apps_edit.html',
                controller: 'AppViewController'
            })
            .when('/beacons', {
                templateUrl: 'views/beacons.html',
                controller: 'BeaconController'
            })
            .when('/beacons/:id', {
                templateUrl: 'views/beacons_edit.html',
                controller: 'BeaconViewController'
            })
            .when('/beacons/new', {
                templateUrl: 'views/beacons_edit.html',
                controller: 'BeaconViewController'
            })
            .when('/events', {
                templateUrl: 'views/events.html',
                controller: 'EventController'
            })
            .when('/events/:id', {
                templateUrl: 'views/events_edit.html',
                controller: 'EventViewController'
            })
            .when('/events/new', {
                templateUrl: 'views/events_edit.html',
                controller: 'EventViewController'
            })

            // General
            .otherwise({
                redirectTo: "/login"
            });
    }).run(function ($rootScope, $http, $location, $cookieStore, $log) {
        /* Route changes */
        $rootScope.$on('$routeChangeStart', function (ev, next, curr) {
            if (next.$$route) {
                var user = $rootScope.user;
                if (user && next.$$route.originalPath == "/login") {
                    $location.path('/')
                }
            }
        });

        /** Global functions **/
        $rootScope.isActive = function (viewLocation) {
            if (viewLocation == "/") {
                return viewLocation === $location.path();
            }

            return $location.path().indexOf(viewLocation) > -1;
        };


        $rootScope.logout = function () {
            delete $http.defaults.headers.common["x-auth-token"];
            delete $rootScope.user;
            $cookieStore.remove("user");
            $location.path("/login");
        };

        $rootScope.go = function (path) {
            $location.path(path);
        };

        /** User related **/
        /* Try getting valid user session cookie or go to login page */
        var originalPath = $location.path();
        var user = $cookieStore.get("user");

        if (user !== undefined) {
            $rootScope.user = user;
            $http.defaults.headers.common["x-auth-token"] = user.token;
            $location.path(originalPath);
        } else {
            $location.path("/login");
        }
    });

var baseURL = "http://localhost:8080";
