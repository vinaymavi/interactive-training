'use strict';

/**
 * @ngdoc function
 * @name appApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the appApp
 */
angular.module('appApp')
    .controller('MainCtrl', function ($scope, $mdSidenav, $firebaseObject, $window, hscsession) {
        var ref = new Firebase("https://hscsession.firebaseio.com");
        $scope.questions = $firebaseObject(ref);
        $scope.answers = [];
        $scope.sidenav = function ($event) {
            console.log($event);
            $event.stopPropagation()
            $mdSidenav('left').toggle();
        };

        $scope.addAnswer = function (index, answer, giveUp) {
            var question = $scope.questions[index];
            console.log(question);
            var reqObj = {};
            reqObj.user = "vinay";
            reqObj.session = question.session;
            reqObj.groupId = question.groupId;
            reqObj.questionId = question.id;
            reqObj.answer = answer;
            reqObj.rightAnswer = question.rightOption;
            reqObj.result = reqObj.answer == reqObj.rightAnswer ? "RIGHT" : "WRONG";
            reqObj.giveUp = giveUp;
            hscsession.addAnswer(reqObj);
        };

        $window.init = function () {
            console.log("controller init");
        };
    });
