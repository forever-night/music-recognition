app.controller('LoginCtrl', function($window){
   $window.onload = function() {
       loadNavElements();
       setActive(navElements.login);
   };
});
