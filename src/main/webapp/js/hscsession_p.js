/*This file Uses:
 * 1-Register Step Enter and Step out event.
 * 2-Send direction to server to push question to users.
 3-Load slides data from server and generate dynamic site.
 * Author: Vinay Mavi vinaymavi@gmail.com*/


var hscsession = (function () {
    /*Firebase Initialization*/
    //TODO security pending.
    var firebaseObj = new Firebase('https://hscsession.firebaseio.com/');
    return {
        'set': function (deferredReq) {
            deferredReq.execute(function (resp) {
                console.log(resp);
                if (resp.items && resp.items.length > 0) {
                    firebaseObj.set(resp.items);
                }
            });
        },
        'loadByGroupId': function (groupid) {
            return gapi.client.hscsession.questionsByGroupId({
                'groupid': groupid
            });
        },
        'slides': function (session) {
            return gapi.client.hscsession.slides({'sessionName': session})
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
        console.log(localStorage.getItem(event.target.id))
        if (!localStorage.getItem(event.target.id)) {
            hscsession.set(hscsession.loadByGroupId(event.target.id));
        }
    }, false);
    document.addEventListener
    ("impress:stepleave", function (event) {
        console.log("Leave Id-" + event.target.id);
        localStorage.setItem(event.target.id, true);
    }, false);
});

function init() {
    var ROOT = 'https://hscsession.appspot.com/_ah/api';
    var SESSION = 'jssession';
    var START_X = -1000;
    gapi.client.load('hscsession', 'v1', function () {
        console.log("GAPI Loaded.");
        /*ImpressJs Initialization.*/
        hscsession.slides(SESSION).execute(function (resp) {
            console.log(resp);
            var slidesHtml = [];
            resp.items.forEach(function (value, index) {
                console.log(value);
                var data_x = START_X + index * 1000;
                slidesHtml.push('<div id="' + value.htmlId + '" class="step slide-svg" data-x="' + data_x + '" data-y="-1500"><img src="' + value.url + '"/></div>')
            });
            $('#impress').html(slidesHtml.join(''));

            impress().init();
            console.log("ImpressJS loaded.")
        })

    }, ROOT);
}
