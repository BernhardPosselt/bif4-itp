class UserController extends WebChat.BaseController

    constructor: ($scope) ->
        super($scope, 'user')
        $scope.items = [
            { id: 0, name: "ben", groups: [0, 1] }
            { id: 1, name: "tom", groups: [0] }
        ]


angular.module('WebChat').controller 'UserController', ($scope) -> 
    new UserController($scope)
