'use strict';

/**
 * @ngdoc function
 * @name appApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the appApp
 */
angular.module('appApp')
    .controller('MainCtrl', function ($scope, $mdSidenav, $firebaseObject, $window, hscsession, $mdToast) {
        var ref = new Firebase("https://hscsession.firebaseio.com");
        $scope.submitBtn = [];
        $scope.giveBtn = [];

        for (var i = 0; i < 50; i++) {
            $scope.submitBtn.push("Submit");
            $scope.giveBtn.push("Give Up");
        }
        ;

        $scope.questions = $firebaseObject(ref);
        $scope.answers = [];
        $scope.sidenav = function ($event) {
            $event.stopPropagation()
            $mdSidenav('left').toggle();
        };

        $scope.logout = function () {
            localStorage.clear();
            $scope.user = localStorage.getItem("user");
            $('#user').html(localStorage.getItem("user"));
        }

        $scope.addAnswer = function (index, answer, giveUp, $event) {
            console.log($event);
            var question = $scope.questions[index];
            console.log(question);
            var reqObj = {};
            reqObj.user = localStorage.getItem("user");
            reqObj.session = question.session;
            reqObj.groupId = question.groupId;
            reqObj.questionId = question.id;
            reqObj.answer = answer;
            reqObj.rightAnswer = question.rightOption;
            reqObj.result = reqObj.answer == reqObj.rightAnswer ? "RIGHT" : "WRONG";
            reqObj.giveUp = giveUp;
            reqObj.level = question.level;
            reqObj.type = question.type;
            if (giveUp) {
                $scope.giveBtn[index] = '....';
            } else {
                $scope.submitBtn[index] = '....';
            }
            hscsession.addAnswer(reqObj).execute(function (resp) {
                console.log("question added");
                console.log(resp);
                console.log("giveUp" + giveUp);
                if (giveUp) {
                    $scope.giveBtn[index] = 'Give Up';
                } else {
                    $scope.submitBtn[index] = 'Submit';
                }
                $mdToast.showSimple("Answer Updated.")
                $scope.$apply();
            });
        };

        $scope.isUrl = function (url) {
            var urlRegex = /^http[s]?:///;
            return urlRegex.test(url);
        }

        $window.init = function () {
            console.log("controller init");
            $('#user').html(localStorage.getItem("user"));
        };

    });
