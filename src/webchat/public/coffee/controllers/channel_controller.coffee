WebChat = window.WebChat


class ChannelController extends WebChat.BaseController

    constructor: ($scope) ->
        super($scope, 'channel')
        $scope.items = [
            { id: 0, name: "john" }
            { id: 1, name: "mino" }
        ]


app = angular.module('WebChat', []);

app.controller 'ChannelController', ($scope) -> 
    new ChannelController($scope)





