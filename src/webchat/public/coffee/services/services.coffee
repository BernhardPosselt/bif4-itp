
angular.module('WebChat').factory 'websocket', ($rootScope, websocket_domain, websocket_path, websocket_ssl, $window) =>
    socket = new $window.WebChat.WebSocket()
    socket.connect(websocket_domain, websocket_path, websocket_ssl)
    socket.onReceive (message) ->
        $rootScope.$broadcast('message', message)
    return socket