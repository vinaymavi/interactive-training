'use strict';

/**
 * @ngdoc function
 * @name appApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the appApp
 */
angular.module('appApp')
    .controller('MainCtrl', function ($scope, $mdSidenav, $firebaseObject) {
        var ref = new Firebase("https://hscsession.firebaseio.com");
        $scope.questions = $firebaseObject(ref);
        $scope.sidenav = function($event){
            console.log($event);
            $event.stopPropagation()
            $mdSidenav('left').toggle();
        }
    });
