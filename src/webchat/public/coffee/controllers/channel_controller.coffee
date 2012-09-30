WebChat = window.WebChat

class ChannelController extends WebChat.BaseController

    constructor: ($scope, @websocket) ->
        super($scope, 'channel')

        @activeChannelId = 0

        $scope.join = (id) =>
            message = new WebChat.JoinMessage(id)
            @websocket.sendJSON(message.serialize())
            @activeChannelId = id

        $scope.sendMessage = (textInput, messageType) =>
            activeChannelId = @activeChannelId
            message = new WebChat.SendMessage(textInput, messageType, activeChannelId)
            console.log message.serialize()
            @websocket.sendJSON(message.serialize())

        @create { id: 0, name: 'channel'}


angular.module('WebChat').controller 'ChannelController', ($scope, websocket) -> 
    new ChannelController($scope, websocket)



