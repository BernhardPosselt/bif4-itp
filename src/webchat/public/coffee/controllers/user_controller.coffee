class UserController extends WebChat.BaseController

    constructor: ($scope) ->
        super($scope, 'user')
        $scope.items = [
            { id: 0, name: "john" }
            { id: 1, name: "mino" }
        ]


angular.module('WebChat').controller 'UserController', ($scope) -> 
    new UserController($scope)
