WebChat = window.WebChat

class UserController extends WebChat.BaseController

    constructor: ($scope, @websocket) ->
        super($scope, 'user')
        $scope.items = [
            { id: 0, name: "ben", groups: [0, 1], online: true }
            { id: 1, name: "tom", groups: [0], online: false }
        ]

angular.module('WebChat').controller 'UserController', ($scope, websocket) -> 
    new UserController($scope, websocket)
