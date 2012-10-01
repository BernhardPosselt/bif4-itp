WebChat = window.WebChat

class FileController extends WebChat.BaseController

    constructor: ($scope, @websocket) ->
        super($scope, 'file')
        $scope.items = [
            { id: 0, name: "john" }
            { id: 1, name: "mino" }
        ]


angular.module('WebChat').controller 'FileController', 
    ['$scope', 'websocket', ($scope, websocket) -> 
        new FileController($scope, websocket)
    ]
