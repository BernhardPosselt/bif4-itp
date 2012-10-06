angular.module('WebChat').factory '_Controller', 
    ['_SendMessage', 'WebChatWebSocket', 'ActiveChannel', 
     (_SendMessage, WebChatWebSocket, ActiveChannel) ->

        class Controller

            constructor: ($scope) ->
                @getActiveChannelId = =>
                    return ActiveChannel.getActiveChannelId()
                @setActiveChannelId = (id) =>
                    ActiveChannel.setActiveChannelId(id)

                @simpleChannelMessage = (id, Msg, value) =>
                    activeChannelId = @getActiveChannelId()
                    if activeChannelId != null
                        message = new Msg(id, activeChannelId, value)

                @sendMessage = (Msg) =>
                    WebChatWebSocket.sendJSON(message.serialize())

                $scope.getActiveChannelId = =>
                    return @getActiveChannelId()

        return Controller

    ]