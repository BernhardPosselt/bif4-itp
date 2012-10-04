(function() {
  var app;

  app = angular.module('WebChat', []).config(function($provide) {
    $provide.value('WEBSOCKET_DOMAIN', document.location.host);
    $provide.value('WEBSOCKET_PATH', '/websocket');
    $provide.value('WEBSOCKET_SSL', false);
  });

  app.run(function($rootScope) {});

}).call(this);
