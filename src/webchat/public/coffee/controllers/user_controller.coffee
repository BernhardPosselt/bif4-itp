WebChat = window.WebChat

class UserController extends WebChat.BaseController

    constructor: ($scope, @websocket, @activeChannel) ->
        super($scope, 'user')
        $scope.items = [
            { id: 0, name: "ben", groups: [0, 1], online: true }
            { id: 1, name: "tom", groups: [0], online: false }
        ]

        $scope.invite = (userId) =>
            activeChannelId = @activeChannel.getActiveChannel().id
            if activeChannelId != undefined
                message = new WebChat.InviteUserMessage(userId, activeChannelId)
                @websocket.sendJSON(message.serialize())
            


angular.module('WebChat').controller 'UserController', ($scope, websocket, activeChannel) -> 
    new UserController($scope, websocket, activeChannel)
