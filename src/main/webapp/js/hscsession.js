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


