"use strict";function init(){var a="https://hscsession.appspot.com/_ah/api";gapi.client.load("hscsession","v1",function(){console.log("GAPI Loaded."),window.init()},a),console.log("User="+localStorage.getItem("user")),localStorage.getItem("user")||(localStorage.setItem("user",prompt("Enter your emp id.")),$("#user").html(localStorage.getItem("user")))}angular.module("appApp",["ngAnimate","ngCookies","ngResource","ngRoute","ngSanitize","ngTouch","ngMaterial","firebase"]).config(["$routeProvider",function(a){a.when("/",{templateUrl:"views/main.html",controller:"MainCtrl"}).when("/about",{templateUrl:"views/about.html",controller:"AboutCtrl"}).otherwise({redirectTo:"/"})}]),angular.module("appApp").controller("MainCtrl",["$scope","$mdSidenav","$firebaseObject","$window","hscsession","$mdToast",function(a,b,c,d,e,f){var g=new Firebase("https://hscsession.firebaseio.com");a.submitBtn=[],a.giveBtn=[];for(var h=0;50>h;h++)a.submitBtn.push("Submit"),a.giveBtn.push("Give Up");a.questions=c(g),a.answers=[],a.sidenav=function(a){a.stopPropagation(),b("left").toggle()},a.logout=function(){localStorage.clear(),a.user=localStorage.getItem("user"),$("#user").html(localStorage.getItem("user"))},a.addAnswer=function(b,c,d,g){console.log(g);var h=a.questions[b];console.log(h);var i={};i.user=localStorage.getItem("user"),i.session=h.session,i.groupId=h.groupId,i.questionId=h.id,i.answer=c,i.rightAnswer=h.rightOption,i.result=i.answer==i.rightAnswer?"RIGHT":"WRONG",i.giveUp=d,d?a.giveBtn[b]="....":a.submitBtn[b]="....",e.addAnswer(i).execute(function(c){console.log("question added"),console.log(c),console.log("giveUp"+d),d?a.giveBtn[b]="Give Up":a.submitBtn[b]="Submit",f.showSimple("Answer Updated."),a.$apply()})},d.init=function(){console.log("controller init"),$("#user").html(localStorage.getItem("user"))}}]),angular.module("appApp").controller("AboutCtrl",["$scope",function(a){a.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"]}]),angular.module("appApp").service("hscsession",["$mdToast",function(a){return this.addAnswer=function(a){return gapi.client.hscsession.addAnswer(a)},this}]);