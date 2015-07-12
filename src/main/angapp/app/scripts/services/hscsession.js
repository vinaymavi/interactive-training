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
            return gapi.client.hscsession.addAnswer(reqObj);
        }
        return this;
    });



