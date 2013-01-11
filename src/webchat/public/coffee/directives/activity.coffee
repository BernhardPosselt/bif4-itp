pingTimout = true

angular.module('WebChat').directive 'activity',
['_PingMessage', 'WebChatWebSocket', 'PING_TIMEOUT',
(_PingMessage, WebChatWebSocket, PING_TIMEOUT) ->

    ping = ->
        if pingTimout
            pingTimout = false
            message = new _PingMessage()
            WebChatWebSocket.sendJSON(message.serialize())
            setTimeout ->
                pingTimout = true
            , PING_TIMEOUT

    return (scope, elm, attr) ->

        $(elm).keyup ->
            ping()
            scope.$apply attr.activity;

        $(elm).mousemove ->
            ping()
            scope.$apply attr.activity;

        $(elm).click ->
            ping()
            scope.$apply attr.activity;
]

