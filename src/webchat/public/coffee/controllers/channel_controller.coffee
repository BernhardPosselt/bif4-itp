WebChat = window.WebChat

class ChannelController extends WebChat.BaseController

    constructor: ($scope, @websocket, @activeChannel) ->
        super($scope, 'channel')

        @getActiveChannelId = =>
            return @activeChannel.getActiveChannelId()
        @setActiveChannelId = (id) =>
            @activeChannel.setActiveChannelId(id)

        @simpleChannelMessage = (id, Message, value) =>
            activeChannelId = @getActiveChannelId()
            if activeChannelId != undefined
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
            if activeChannelId != undefined
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

        @create { id: 1, name: 'channel', groups: [0], users: [0, 1]}


angular.module('WebChat').controller 'ChannelController', 
    ['$scope', 'websocket', 'activeChannel', ($scope, websocket, activeChannel) -> 
        new ChannelController($scope, websocket, activeChannel)
    ]


