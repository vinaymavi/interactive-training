/*This file Uses:
 * 1-Register Step Enter and Step out event.
 * 2-Send direction to server to push question to users.
 * Author: Vinay Mavi vinaymavi@gmail.com*/


var hscsession = (function () {
    /*Firebase Initialization*/
    //TODO security pending.
    var firebaseObj = new Firebase('https://hscsession.firebaseio.com/');
    return {
        'set': function (deferredReq) {
            deferredReq.execute(function (resp) {
                console.log(resp);
                console.log("Vinay Kumar");
                if (resp.items && resp.items.length > 0) {
                    firebaseObj.set(resp.items);
                }
            });
        },
        'loadByGroupId': function (groupid) {
            return gapi.client.hscsession.questionsByGroupId({
                'groupid': groupid
            });
        }
    }
}());

console.log(hscsession);

/*
 * Register StepEnter and StepLeave listeners
 * */
$(document).ready(function () {
    document.addEventListener
    ("impress:stepenter", function (event) {
        /*API calling for question list.*/
        console.log("SetpEnter Calling.")
        hscsession.set(hscsession.loadByGroupId(event.target.id));
    }, false);
    document.addEventListener
    ("impress:stepleave", function (event) {
        console.log("Leave Id-" + event.target.id);
    }, false);
});

function init() {
    var ROOT = 'https://hscsession.appspot.com/_ah/api';
    gapi.client.load('hscsession', 'v1', function () {
        console.log("GAPI Loaded.");
        /*ImpressJs Initialization.*/
        impress().init();
        console.log("ImpressJS loaded.")
    }, ROOT);
}
