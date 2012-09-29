class GroupController extends WebChat.BaseController

    constructor: ($scope) ->
        super($scope, 'group|user')
        $scope.items = [
            { id: 0, name: "john" }
            { id: 1, name: "mino" }
        ]

angular.module('WebChat').controller 'GroupController', ['websocket', ($scope, websocket) -> 
    new GroupController($scope)]
