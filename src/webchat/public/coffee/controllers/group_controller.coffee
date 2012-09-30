WebChat = window.WebChat

class GroupController extends WebChat.BaseController

    constructor: ($scope, @websocket, @activeChannel) ->
        super($scope, 'group')
        $scope.items = [
            { id: 0, name: "john" }
            { id: 1, name: "mino" }
        ]

        $scope.invite = (groupId) =>
            activeChannelId = @activeChannel.getActiveChannel().id
            if activeChannelId != undefined
                message = new WebChat.InviteGroupMessage(groupId, activeChannelId)
                @websocket.sendJSON(message.serialize())


angular.module('WebChat').controller 'GroupController', ($scope, websocket, activeChannel) -> 
    new GroupController($scope, websocket, activeChannel)
