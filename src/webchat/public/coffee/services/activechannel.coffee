angular.module('WebChat').factory 'ActiveChannel', ['$rootScope', ($rootScope) ->
    ActiveChannel = {}
    ActiveChannel.activeChannelId = null

    ActiveChannel.setActiveChannelId = (id) ->
        @activeChannelId = id
        $rootScope.$broadcast 'changed_channel'

    ActiveChannel.getActiveChannelId = ->
        return @activeChannelId

    return ActiveChannel
]