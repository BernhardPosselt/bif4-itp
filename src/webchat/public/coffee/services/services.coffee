angular.module('WebChat').factory 'websocket', ($rootScope, websocket_domain, websocket_path, websocket_ssl, $window) =>
    socket = new $window.WebChat.WebSocket()
    socket.connect(websocket_domain, websocket_path, websocket_ssl)
    socket.onReceive (message) ->
        $rootScope.$broadcast('message', message)
    return socket


angular.module('WebChat').factory 'activeChannel', ($rootScope) ->
    activeChannel = {}
    activeChannel.activeChannel = {}

    activeChannel.setActiveChannel = (channel) ->
        @activeChannel = channel
        $rootScope.$broadcast 'changed_channel'

    activeChannel.getActiveChannel = ->
        return @activeChannel

    return activeChannel