WebChat = window.WebChat

class MessageController extends WebChat.BaseController

    constructor: ($scope, @websocket) ->
        super($scope, 'message')
        $scope.items = [
            { id: 0, message: "john" }
            { id: 1, message: "mino" }
        ]


angular.module('WebChat').controller 'MessageController', 
    ['$scope', 'websocket', ($scope, websocket) -> 
        new MessageController($scope, websocket)
    ]
