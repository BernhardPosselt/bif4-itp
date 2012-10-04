class ChannelController

    constructor: ($scope, @websocket, @activechannel, @channelmodel) ->

        @activeChannelId = null

        @getActiveChannelId = =>
            return @activechannel.getActiveChannelId()
        @setActiveChannelId = (id) =>
            @activechannel.setActiveChannelId(id)

        @simpleChannelMessage = (id, Message, value) =>
            activeChannelId = @getActiveChannelId()
            if activeChannelId != null
                message = new Message(id, activeChannelId, value)
                @websocket.sendJSON(message.serialize())

        $scope.getActiveChannelId = =>
            return @getActiveChannelId()

        $scope.join = (id) =>
            message = new WebChat.JoinMessage(id)
            @websocket.sendJSON(message.serialize())
            @setActiveChannelId(id)
            $scope.selected = id

        $scope.sendMessage = (textInput, messageType) =>
            activeChannelId = @getActiveChannelId()
            if activeChannelId != null
                message = new WebChat.SendMessage(textInput, messageType, activeChannelId)
                @websocket.sendJSON(message.serialize())

        $scope.inviteUser = (userId, value) =>
            @simpleChannelMessage(userId, WebChat.InviteUserMessage, value)

        $scope.inviteGroup = (groupId, value) =>
            @simpleChannelMessage(groupId, WebChat.InviteGroupMessage, value)

        $scope.modUser = (userId, value) =>
            @simpleChannelMessage(userId, WebChat.ModUserMessage, value)

        $scope.modGroup = (groupId, value) =>
            @simpleChannelMessage(groupId, WebChat.ModGroupMessage, value)

        $scope.readonlyUser = (userId, value) =>
            @simpleChannelMessage(userId, WebChat.ReadonlyUserMessage, value)

        $scope.readonlyGroup = (groupId, value) =>
            @simpleChannelMessage(groupId, WebChat.ReadonlyGroupMessage, value)


angular.module('WebChat').controller 'ChannelController', 
    ['$scope', 'websocket', 'activechannel', 'channelmodel', ($scope, websocket, activechannel, channelmodel) -> 
        new ChannelController($scope, websocket, activechannel, channelmodel)
    ]


