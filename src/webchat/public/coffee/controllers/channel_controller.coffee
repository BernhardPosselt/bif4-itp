class ChannelController extends WebChat.BaseController

    constructor: ($scope) ->
        super($scope, 'channel')



angular.module('WebChat').controller 'ChannelController', ($scope) -> 
    new ChannelController($scope)



