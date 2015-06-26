/*This file Uses:
 * 1-Register Step Enter and Step out event.
 * 2-Send direction to server to push question to users.
 * Author: Vinay Mavi vinaymavi@gmail.com*/

$(document).ready(function(){
    document.addEventListener
    ("impress:stepenter", function (event) {
        console.log("Enter Id-"+event.target.id);
    }, false);
    document.addEventListener
    ("impress:stepleave", function (event) {
        console.log("Leave Id-"+event.target.id);
    }, false);
});

//TODO security pending.
var myDataRef = new Firebase('https://hscsession.firebaseio.com/');
//TODO pending appengine integration.
myDataRef.set({"id1":{
    "id": "vinay",
    "session": "jssession",
    "groupId": "id1",
    "question": "what is Javascript.",
    "option1": "Scripting language.",
    "option2": "Web Scripting language.",
    "option3": "Web Style Language.",
    "option4": "Web Design Language.",
    "rightOption": 2,
    "kind": "hscsession#resourcesItem"
}});

