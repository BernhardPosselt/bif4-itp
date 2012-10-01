WebChat = window.WebChat

class ChannelController extends WebChat.BaseController

    constructor: ($scope, @websocket, @activeChannel) ->
        super($scope, 'channel')

        @getActiveChannelId = =>
            return @activeChannel.getActiveChannelId()
        @setActiveChannelId = (id) =>
            @activeChannel.setActiveChannelId(id)

        $scope.join = (id) =>
            message = new WebChat.JoinMessage(id)
            @websocket.sendJSON(message.serialize())
            @setActiveChannelId(id)
            $scope.selected = id

        $scope.sendMessage = (textInput, messageType) =>
            activeChannelId = @getActiveChannelId()
            if activeChannelId != undefined
                message = new WebChat.SendMessage(textInput, messageType, activeChannelId)
                @websocket.sendJSON(message.serialize())

        $scope.inviteUser = (userId) =>
            activeChannelId = @getActiveChannelId()
            if activeChannelId != undefined
                message = new WebChat.InviteUserMessage(userId, activeChannelId)
                @websocket.sendJSON(message.serialize())

        $scope.inviteGroup = (groupId) =>
            activeChannelId = @getActiveChannelId()
            if activeChannelId != undefined
                message = new WebChat.InviteGroupMessage(groupId, activeChannelId)
                @websocket.sendJSON(message.serialize())

        @create { id: 1, name: 'channel'}


angular.module('WebChat').controller 'ChannelController', ($scope, websocket, activeChannel) -> 
    new ChannelController($scope, websocket, activeChannel)



