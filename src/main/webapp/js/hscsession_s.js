// TODO security pending.
var myDataRef = new Firebase('https://hscsession.firebaseio.com/');
myDataRef.on('value',function(dataSnapShot){
    console.log(dataSnapShot.val());
});