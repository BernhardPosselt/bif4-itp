angular.module('WebChat').factory '_ActiveChannel', ['$rootScope', ($rootScope) ->
    
    class ActiveChannel

        constructor: ->
            @activeChannelId = null

        setActiveChannelId: (id) ->
            @activeChannelId = id
            $rootScope.$broadcast 'changed_channel'

        getActiveChannelId: ->
            return @activeChannelId

    return ActiveChannel
]