angular.module('WebChat').factory '_ChannelListController', 
    ['_Controller', '_JoinMessage', 'ChannelModel',
     (_Controller, _JoinMessage, ChannelModel) ->

        class ChannelListController extends _Controller

            constructor: ($scope) ->
                super($scope)
            
                @channelmodel = ChannelModel

                $scope.getChannels = =>
                    return @channelmodel.getItems()

                $scope.join = (id) =>
                    message = new _JoinMessage(id)
                    @sendMessage(message)
                    @setActiveChannelId(id)
                    $scope.selected = id


        return ChannelListController

    ]