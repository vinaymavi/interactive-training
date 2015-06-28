'use strict';

/**
 * @ngdoc service
 * @name appApp.hscsession
 * @description
 * # hscsession
 * Service in the appApp.
 */
angular.module('appApp')
    .service('hscsession', function ($mdToast) {
        this.addAnswer = function (reqObj) {
            gapi.client.hscsession.addAnswer(reqObj).execute(function (resp) {
                console.log(resp);
                $mdToast.showSimple("Answer Updated.")
            })
        }
        return this;
    });
