angular.module('WebChat').factory '_ChannelListController', 
    ['_JoinMessage', '_SendMessage', '_InviteUserMessage', '_InviteGroupMessage',
     '_ModUserMessage', '_ModGroupMessage', '_ReadonlyUserMessage', '_ReadonlyGroupMessage',
     (_JoinMessage, _SendMessage, _InviteUserMessage, _InviteGroupMessage,
      _ModUserMessage, _ModGroupMessage, _ReadonlyUserMessage, _ReadonlyGroupMessage) ->

        class ChannelListController

            constructor: ($scope, @websocket, @activechannel, @channelmodel) ->
                @activeChannelId = null

                @getActiveChannelId = =>
                    return @activechannel.getActiveChannelId()
                @setActiveChannelId = (id) =>
                    @activechannel.setActiveChannelId(id)

                @simpleChannelMessage = (id, Msg, value) =>
                    activeChannelId = @getActiveChannelId()
                    if activeChannelId != null
                        message = new Msg(id, activeChannelId, value)
                        @websocket.sendJSON(message.serialize())

                $scope.channels = @channelmodel.getItems()

                $scope.getActiveChannelId = =>
                    return @getActiveChannelId()

                $scope.join = (id) =>
                    message = new _JoinMessage(id)
                    @websocket.sendJSON(message.serialize())
                    @setActiveChannelId(id)
                    $scope.selected = id

                $scope.sendMessage = (textInput, messageType) =>
                    activeChannelId = @getActiveChannelId()
                    if activeChannelId != null
                        message = new _SendMessage(textInput, messageType, activeChannelId)
                        @websocket.sendJSON(message.serialize())

                $scope.inviteUser = (userId, value) =>
                    @simpleChannelMessage(userId, _InviteUserMessage, value)

                $scope.inviteGroup = (groupId, value) =>
                    @simpleChannelMessage(groupId, _InviteGroupMessage, value)

                $scope.modUser = (userId, value) =>
                    @simpleChannelMessage(userId, _ModUserMessage, value)

                $scope.modGroup = (groupId, value) =>
                    @simpleChannelMessage(groupId, _ModGroupMessage, value)

                $scope.readonlyUser = (userId, value) =>
                    @simpleChannelMessage(userId, _ReadonlyUserMessage, value)

                $scope.readonlyGroup = (groupId, value) =>
                    @simpleChannelMessage(groupId, _ReadonlyGroupMessage, value)


        return ChannelListController

    ]