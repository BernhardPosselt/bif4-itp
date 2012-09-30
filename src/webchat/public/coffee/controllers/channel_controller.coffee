WebChat = window.WebChat

class ChannelController extends WebChat.BaseController

    constructor: ($scope, @websocket, @activeChannel) ->
        super($scope, 'channel')

        $scope.join = (id) =>
            message = new WebChat.JoinMessage(id)
            @websocket.sendJSON(message.serialize())
            @activeChannel.setActiveChannel(@getItemById(id))

        $scope.sendMessage = (textInput, messageType) =>
            activeChannelId = @activeChannel.getActiveChannel().id
            if activeChannelId != undefined
                message = new WebChat.SendMessage(textInput, messageType, activeChannelId)
                @websocket.sendJSON(message.serialize())

        @create { id: 1, name: 'channel'}


angular.module('WebChat').controller 'ChannelController', ($scope, websocket, activeChannel) -> 
    new ChannelController($scope, websocket, activeChannel)



